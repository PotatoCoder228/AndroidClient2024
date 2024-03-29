package ru.itmo.potatocoder228.androidlab2024.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.serialization.Serializable

var credentials= Credentials()

@Volatile
var errorMsg = "Test"

val BASE_URL="http://172.30.217.224:8080/"

val SIGN_IN_URL="$BASE_URL/api/auth/login"
val SIGN_UP_URL="$BASE_URL/api/auth/register"

val HOUSE_URL="$BASE_URL/api/house"

@Serializable
data class Home(var description: String, var lampochka: Boolean)

var home = Home("Placeholder", false);