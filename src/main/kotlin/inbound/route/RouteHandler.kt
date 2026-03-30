package inbound.route

import domain.model.ApiResponse
import domain.model.ErrorResponse
import io.ktor.server.response.*
import io.ktor.server.routing.*
import domain.service.UserService
import inbound.resource.Users
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.get
import org.koin.ktor.ext.inject

fun Route.userRouter() {
    val userService : UserService by inject()

    get<Users.UserId> { param ->
        val result = null
        call.respond(
            ApiResponse<String>(
                code = HttpStatusCode.OK.toString(),
                message = "사용자 정보",
                result
            )
        )
    }
}

fun Route.healthRouter() {
    get("/health") {
        call.respondText("ok")
    }
}
