package inbound.resource

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Serializable
@Resource("/users")
class Users {
    @Serializable @Resource("{id}")
    class UserId(val parent: Users = Users(), val id:Long)
}