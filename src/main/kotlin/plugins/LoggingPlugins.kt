package plugins

import config.MAX_LOG_LENGTH
import config.log
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.http.HttpMethod
import io.ktor.http.content.OutgoingContent
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.request.httpMethod
import io.ktor.server.request.receiveText
import io.ktor.server.request.uri
import io.ktor.server.response.header
import io.ktor.util.AttributeKey
import kotlinx.coroutines.runBlocking
import org.slf4j.MDC

fun createInboundLogging() = createApplicationPlugin(name="InboundLogger") {
    onCallRespond { call, body ->
        val requestId = MDC.get("REQUEST_ID")
        if(requestId != null) {
            call.response.header("X-REQUEST-ID", requestId)
        }
        if(body !is OutgoingContent) {
            val uri = call.request.uri
            val requestBody = when {
                call.request.httpMethod == HttpMethod.Post -> {
                    runBlocking { call.receiveText().replace("\\s".toRegex(), "") }
                }
                else  -> {
                    "No Data"
                }
            }

            val responseBody =  when {
                body.toString().isEmpty() -> {
                    "No Data"
                }
                body.toString().length > MAX_LOG_LENGTH -> {
                    "${body.toString().take(MAX_LOG_LENGTH)}... (truncated)"
                }
                else  -> {
                    body.toString()
                }
            }
                log.info("$uri - {$requestBody} - {$responseBody} ")
        }
    }

}

fun createOutboundLogging() = createClientPlugin("OutboundLogger") {
    onRequest { request, _ ->
        val requestId = MDC.get("REQUEST_ID")
        request.headers.append("X-REQUEST-ID", requestId)
        log.info("==> [OUT][${requestId}] To: ${request.url}")
    }

    onResponse { response ->
        log.info("<== [OUT] [${MDC.get("REQUEST_ID")} Status: ${response.status}")
    }
}


// TODO :
//      로깅 확장함수 고민 https://tech.kakaopay.com/post/ktor-api-server/#ktor-client -> 특정 http method 기준으로 처리 가능
//      로깅 고도화 -> dev 에서만 body 입력

