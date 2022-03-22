package com.oigma.opemus.data.services

import com.oigma.opemus.httpClient
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.utils.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * Created by Harsewak Singh on 12/03/2022.
 */
class ApiClient() {
    companion object {
        private const val URL = "https://7189caa8-6ea8-478e-beae-656fbf13526a.mock.pstmn.io/"
    }

    private val httpApiClient: HttpClient = httpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    suspend fun post(url: String, body: Any = EmptyContent): HttpResponse {
        return httpApiClient.post(URL.plus(url)) {
            setBody(body)
        }
    }

    suspend fun get(url: String): HttpResponse {
        return httpApiClient.get(URL.plus(url))
    }
}