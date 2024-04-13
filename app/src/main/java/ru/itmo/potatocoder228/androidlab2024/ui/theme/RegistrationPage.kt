package ru.itmo.potatocoder228.androidlab2024.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Composable
private fun LoginField(modifier: Modifier, placeholder: String = "Login") {
    var text by remember() {
        mutableStateOf("")
    }
    Row(modifier) {
        TextField(value = text, onValueChange = {
            text = it
            credentials.setLogin(text)
        }, label = { Text(text) }, placeholder = {
            Text(
                text = placeholder,
                fontSize = 11.sp,
                fontFamily = FontFamily.Serif,
                color = PlaceholderTextColor
            )
        }, singleLine = true
        )
    }
}

@Composable
private fun PasswordField(modifier: Modifier, placeholder: String = "Password") {
    var text by remember() {
        mutableStateOf("")
    }
    Row(modifier) {
        TextField(
            value = text,
            onValueChange = {
                text = it
                credentials.setPassword(text)
            },
            label = { Text(text) },
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Serif,
                    color = PlaceholderTextColor
                )
            },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
private fun AuthButton(
    modifier: Modifier, navController: NavController, placeholder: String = "Authorization"
) {
    val text by remember { mutableStateOf(placeholder) }
    //val alert = false
    var msg by rememberSaveable {
        mutableStateOf(errorMsg)
    }
    Text(
        msg,
        modifier.offset(y = (-25).dp),
        fontSize = 14.sp,
        fontFamily = FontFamily.Serif,
        textAlign = TextAlign.Center,
        color = ErrorColor
    )
    Button(
        onClick = {
            try {
//                GlobalScope.launch {
//                    credentials.setToken(
//                        sendSignInRequest(
//                            credentials.getLogin(), credentials.getPassword()
//                        ).token
//                    )
//                    msg = sendSignInRequest(credentials.getLogin(), credentials.getPassword()).token
//                }
                navController.navigate("SignIn")
            } catch (e: Throwable) {
                //alert TODO
            }
        }, modifier = modifier, colors = ButtonDefaults.buttonColors(ButtonBackgroundColor)
    ) {
        Text(text)
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable

private fun SignUpButton(
    modifier: Modifier,
    navController: NavController,
    placeholder: String = "Sign Up"
) {
    var response: HttpResponse
    Button(
        onClick = {
            GlobalScope.launch {
                response = sendSignUpRequest(credentials.getLogin(), credentials.getPassword())
            }
            navController.navigate("SignIn")
        }, modifier = modifier, colors = ButtonDefaults.buttonColors(OutlinedButtonBackgroundColor)
    ) {
        Text(placeholder)
    }
}

@Composable
fun RegistrationBox(navController: NavController) {
    Box {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(250.dp, 350.dp)
                .background(BoxBackgroundColor, shape = RoundedCornerShape(25.dp))
                .border(
                    BorderStroke(
                        width = 4.dp,
                        brush = Brush.linearGradient(listOf(Color(0xFFD3D2FA), Color(0xFFD3D2FA)))
                    ), shape = RoundedCornerShape(25.dp)
                )
        ) {
            Text(
                "Registration",
                Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 20.dp),
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif,
                color = SignInPageTextColor
            )

            LoginField(
                modifier = Modifier
                    .padding(top = 100.dp)
                    .background(RowBackgroundColor)
                    .size(200.dp, 30.dp)
                    .align(Alignment.TopCenter)
            )
            PasswordField(
                modifier = Modifier
                    .background(RowBackgroundColor)
                    .size(200.dp, 30.dp)
                    .align(Alignment.Center)
            )
            SignUpButton(
                Modifier
                    .align(Alignment.BottomCenter)
                    .size(150.dp, 35.dp)
                    .offset(y = (-75).dp),
                navController
            )
            AuthButton(
                Modifier
                    .align(Alignment.BottomCenter)
                    .size(150.dp, 35.dp)
                    .offset(y = (-30).dp), navController
            )
        }
    }
}

@Composable
fun RegistrationPage(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize(), color = ApplicationBackgroundColor) {
        RegistrationBox(navController)
    }
}

@Composable
private fun SimpleAlertDialog() {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            TextButton(onClick = {})
            { Text(text = "OK") }
        },
        dismissButton = {
            TextButton(onClick = {})
            { Text(text = "Cancel") }
        },
        title = { Text(text = "Please confirm") },
        text = { Text(text = "Should I continue with the requested action?") }
    )
}
