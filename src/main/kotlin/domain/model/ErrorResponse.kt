package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val code: String,          // 서비스 고유 에러 코드
    val message: String,       // 사용자 친화적 메시지
    val requestId: String? = null // 트래킹을 위한 요청 아이디
)
