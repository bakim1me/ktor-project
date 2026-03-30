package config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.ApplicationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.core.Slf4jSqlDebugLogger
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction

object DatabaseFactory {
    fun init(config: ApplicationConfig) {
        val host = config.property("db.host").getString()
        val port = config.property("db.port").getString()
        val schema = config.property("db.schema").getString()

        val hikariConfig = HikariConfig().apply {
            driverClassName = config.property("db.driver").getString()
            jdbcUrl = "jdbc:mysql://${host}:${port}/${schema}?serverTimezone=Asia/Seoul"
            username = config.property("db.username").getString()
            password = config.property("db.password").getString()
            maximumPoolSize = config.property("db.maxPoolSize").getString().toInt()

            isAutoCommit = true
            validate()
        }
        Database.connect(HikariDataSource(hikariConfig))
    }

    // suspendTransaction은 블록 내부에서 다른 suspend 함수 호출을 허용
    suspend fun <T> dbQuery(block: suspend () -> T) : T =
        withContext(Dispatchers.IO + MDCContext()) { // MDCContext()가 REQUEST_ID를 넘겨줌
            suspendTransaction {
                addLogger(Slf4jSqlDebugLogger)
                block()
            }
        }

}