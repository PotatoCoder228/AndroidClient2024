package ru.itmo.potatocoder228.androidlab2024.ui.theme

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.append
import io.ktor.http.contentType
import io.ktor.http.parameters
import ru.itmo.potatocoder228.androidlab2024.client

import kotlinx.serialization.*

@Suppress("PLUGIN_IS_NOT_ENABLED")
@Serializable
data class Customer(val login: String, val password: String)
@Serializable
data class Token(val token: String)

suspend fun sendSignInRequest(login: String, password: String): Token {
    return client.post(SIGN_IN_URL){
        contentType(ContentType.Application.Json)
        setBody(Customer(login, password))
    }
    .body()
}

suspend fun sendSignUpRequest(login: String, password: String): HttpResponse {
    return client.post(SIGN_UP_URL){
        contentType(ContentType.Application.Json)
        setBody(Customer(login, password))
    }
}

suspend fun getHomeRequest(): HttpResponse{
    return client.get(HOUSE_URL){
        headers.append("Authorization", credentials.getToken())
    }
}

suspend fun setHomeRequest(): HttpResponse{
    return client.post(HOUSE_URL){
        headers.append("Authorization", credentials.getToken())
        contentType(ContentType.Application.Json)
        setBody(home)
    }
}