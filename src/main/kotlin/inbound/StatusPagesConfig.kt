package inbound

import config.log
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.forwardedheaders.*
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import domain.model.ErrorResponse

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<BadRequestException> { call, cause ->
            log.error(cause.stackTraceToString())
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(
                    HttpStatusCode.BadRequest.description,
                    "잘못된 요청입니다."
                )
            )
        }
        exception<Throwable> { call, cause ->
            log.error(cause.stackTraceToString())
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse(
                HttpStatusCode.InternalServerError.description,
                "서버에 문제가 발생하였습니다. 관리자에게 문의해주세요."
            ))
        }
        status(HttpStatusCode.NotFound) {
            call, _ ->
            call.respond(
                HttpStatusCode.NotFound,
                ErrorResponse(
                    HttpStatusCode.NotFound.description,
                    "요청한 자원을 찾을 수 없습니다."
                ))
            }

    }
}

// 게이트웨이(Nginx)를 사용 시 게이트웨이 IP가 아닌 실제 사용자의 IP를 남기기 위해 필수적인 설정
fun Application.configureHeaders() {
    install(ForwardedHeaders)
    install(XForwardedHeaders)
}