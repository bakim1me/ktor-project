package config

import domain.repository.UserRepository
import domain.service.UserService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import outbound.client.createOutboundClient

val appModule = module {
    // Outbound client
    single { createOutboundClient() }

    // Data access
    single { UserRepository() }

    // Service : Inject both Repository and Outbound Client
    // Koin automatically passes the instances registered above into the constructor
    single { UserService(get(), get()) }
}

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
}