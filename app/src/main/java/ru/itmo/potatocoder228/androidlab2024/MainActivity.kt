package ru.itmo.potatocoder228.androidlab2024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.itmo.potatocoder228.androidlab2024.ui.theme.ApplicationBackgroundColor
import ru.itmo.potatocoder228.androidlab2024.ui.theme.AuthBox
import ru.itmo.potatocoder228.androidlab2024.ui.theme.AuthPage
import ru.itmo.potatocoder228.androidlab2024.ui.theme.BoxBackgroundColor
import ru.itmo.potatocoder228.androidlab2024.ui.theme.Home
import ru.itmo.potatocoder228.androidlab2024.ui.theme.HomeNameColor
import ru.itmo.potatocoder228.androidlab2024.ui.theme.ListItemNameColor
import ru.itmo.potatocoder228.androidlab2024.ui.theme.LogoutButtonColor
import ru.itmo.potatocoder228.androidlab2024.ui.theme.OutlinedButtonBackgroundColor
import ru.itmo.potatocoder228.androidlab2024.ui.theme.RegistrationPage
import ru.itmo.potatocoder228.androidlab2024.ui.theme.SetHomeInfoColor
import ru.itmo.potatocoder228.androidlab2024.ui.theme.UpdateHomeInfoColor
import ru.itmo.potatocoder228.androidlab2024.ui.theme.credentials
import ru.itmo.potatocoder228.androidlab2024.ui.theme.getHomeRequest
import ru.itmo.potatocoder228.androidlab2024.ui.theme.home
import ru.itmo.potatocoder228.androidlab2024.ui.theme.sendSignUpRequest
import ru.itmo.potatocoder228.androidlab2024.ui.theme.setHomeRequest

val client = HttpClient(CIO) { install(ContentNegotiation) { json() } }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        client.close()
    }
}

enum class SmartHousePage {
    SignInPage,
    SignUpPage,
    HomePage,
    HousePage
}

@Preview(showBackground = true)
@Composable
fun App(
) {
    val navController: NavHostController = rememberNavController()
    var modifier = Modifier
    NavHost(
        navController = navController,
        startDestination = "SignIn"
    ) {

        composable("SignIn") {
            AuthPage(navController)
        }

        composable("SignUp") {
            RegistrationPage(navController)
        }

        composable("Home") {
            HomePage(navController)
        }
    }
}

@Composable
fun HomePage(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize(), color = ApplicationBackgroundColor) {
        HomeBox(navController)
    }
}

@Composable
fun HomeBox(navController: NavController) {
    var modifier = Modifier
    var rememberedHome by remember { mutableStateOf(home) }
    var rememberedLamp by remember { mutableStateOf(home.lampochka) }
    var response: HttpResponse
    Box {
        Box(
            modifier = modifier
                .align(Alignment.Center)
                .size(500.dp, 700.dp)
        ) {
            Button(
                modifier = Modifier
                    .align(Alignment.TopEnd),
                onClick = {
                    credentials.clear()
                    navController.navigate("SignIn")
                }, colors = ButtonDefaults.buttonColors(LogoutButtonColor)
            ) {
                Icon(Icons.Filled.ExitToApp, null, modifier.size(25.dp))
            }
            HomeNameField(
                Modifier
                    .align(Alignment.TopCenter),
                rememberedHome
            )
            Box(
                Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 50.dp)
                    .size(250.dp, 450.dp)
                    .background(BoxBackgroundColor, shape = RoundedCornerShape(25.dp))
                    .border(
                        BorderStroke(
                            width = 4.dp,
                            brush = Brush.linearGradient(
                                listOf(
                                    Color(0xFFD3D2FA),
                                    Color(0xFFD3D2FA)
                                )
                            )
                        ), shape = RoundedCornerShape(25.dp)
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    Modifier
                        .size(200.dp, 100.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = rememberedLamp, onCheckedChange = {
                            home.lampochka = !home.lampochka
                            rememberedLamp = home.lampochka
                        }
                    )
                    Spacer(Modifier.size(6.dp))
                    Text(
                        "Light", color = ListItemNameColor,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .size(150.dp, 35.dp)
                    .offset(y = (-150).dp),
                onClick = {
                    try {
                        GlobalScope.launch {
                            response = getHomeRequest()
                            home = response.body()
                            rememberedHome = home
                            rememberedLamp = home.lampochka
                        }
                    } catch (e: Throwable) {

                    }
                }, colors = ButtonDefaults.buttonColors(UpdateHomeInfoColor)
            ) {
                Text("Update")
            }
            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .size(150.dp, 35.dp)
                    .offset(y = (-100).dp),
                onClick = {
                    try {
                        GlobalScope.launch {
                            response = setHomeRequest()
                        }
                    } catch (e: Throwable) {

                    }
                }, colors = ButtonDefaults.buttonColors(SetHomeInfoColor)
            ) {
                Text("Set")
            }
        }
    }
}

@Composable
fun HomeNameField(modifier: Modifier, rememberedHome: Home) {
    Text(
        rememberedHome.description,
        fontSize = 24.sp,
        modifier = modifier,
        color = HomeNameColor,
        fontFamily = FontFamily.Serif
    )
}
