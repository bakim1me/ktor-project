package config

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.doublereceive.DoubleReceive
import io.ktor.server.request.path
import org.slf4j.event.Level
import plugins.createInboundLogging
import java.util.UUID

const val MAX_LOG_LENGTH = 1000

fun Application.configureLogging() {
    install(DoubleReceive)
    install(CallLogging) {
        level = Level.INFO
        mdc("REQUEST_ID") { _ ->
            UUID.randomUUID().toString().substringBefore("-") // 요청 아이디 부여
        }
        filter { call -> !call.request.path().startsWith("/health") } // 특정 경로에 대해서만 로깅 처리

    }
    install(createInboundLogging())
}
