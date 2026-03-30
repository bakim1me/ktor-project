package domain.service

import domain.repository.UserRepository
import inbound.resource.Users
import java.net.http.HttpClient

class UserService (
    private val userRepository: UserRepository,
    private val externalClient : HttpClient
) {
    fun check(userId: Users.UserId)  {
    }
}


