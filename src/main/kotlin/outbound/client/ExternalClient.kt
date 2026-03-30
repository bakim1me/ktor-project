package outbound.client

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.jackson.jackson
import plugins.createOutboundLogging

fun createOutboundClient() = HttpClient(CIO) {
    install(ContentNegotiation) {
        jackson {  propertyNamingStrategy = PropertyNamingStrategies.LowerCamelCaseStrategy() }
    }
    install(createOutboundLogging())
}

