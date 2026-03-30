import config.DatabaseFactory
import config.configureLogging
import config.configureContentNegotiation
import config.configureKoin
import config.configureSwagger
import inbound.configureHeaders
import inbound.configureStatusPages
import inbound.route.healthRouter
import inbound.route.userRouter
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.resources.Resources
import io.ktor.server.routing.*

/**
 * 서버의 메인 진입점
 * command line arguments를 통해 EngineMain을 실행
 */
fun main(args: Array<String>) {
    EngineMain.main(args)
}
/**
 * Ktor Application Module
 * application.yaml의 설정에 의해 로드
 */
fun Application.module() {

    //Database 초기화
    DatabaseFactory.init(environment.config)

    // DI - Koin 설정
    configureKoin()

    // 서버 플러그인
    configureLogging() // 통합 inbound 로깅
    configureContentNegotiation() // Json 직렬화
    install(Resources) // Type-safe Resources 플러그인 - 클래스 기반 라우팅
    configureHeaders() //
    configureStatusPages() // 전역 예외
    configureSwagger() // swagger

    // 앤드포인트 정의
    routing {
        healthRouter()
        userRouter()
    }
}

