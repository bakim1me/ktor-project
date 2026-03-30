package domain.model

import io.ktor.http.HttpStatusCode


sealed class BaseException(val code:String, override val message: String, val status: HttpStatusCode) : RuntimeException(message) {
    class AuthFailed(msg:String) : BaseException("AUTH_001", msg, HttpStatusCode.Unauthorized)
    class UserNotFound(id: String) : BaseException("USER_001", "유저 $id 를 찾을 수 없습니다.", HttpStatusCode.NotFound)
}