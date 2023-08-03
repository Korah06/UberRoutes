package baeza.guillermo.uberroutes.login.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import baeza.guillermo.uberroutes.R
import baeza.guillermo.uberroutes.ui.theme.*
import baeza.guillermo.uberroutes.R.drawable.*
import baeza.guillermo.uberroutes.ui.model.Routes
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(navigationController: NavHostController, scaffoldState: ScaffoldState, loginViewModel: LoginViewModel) {
    val user:String by loginViewModel.user.observeAsState(initial = "")
    val password:String by loginViewModel.password.observeAsState(initial = "")
    val email:String by loginViewModel.email.observeAsState(initial = "")
    val validEmail:Boolean by loginViewModel.validEmail.observeAsState(initial = false)
    val incorrectData:Boolean by loginViewModel.incorrectData.observeAsState(initial = false)
    val view:Int by loginViewModel.view.observeAsState(initial = 1)
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            if (view == 1) {

                if (incorrectData) {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "Credenciales incorrectas",
                            duration = SnackbarDuration.Short
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .background(Azul1)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LogoImage()
                        Spacer(modifier = Modifier.height(20.dp))
                        UserTextField(user) { loginViewModel.onLoginChanged(it, password) }
                        Spacer(modifier = Modifier.height(15.dp))
                        PasswordTextField(password) { loginViewModel.onLoginChanged(user, it) }
                        Spacer(modifier = Modifier.height(15.dp))
                        RememberPassword(
                            email,
                            validEmail,
                            scaffoldState
                        ) { loginViewModel.onRememberPassword(it) }
                        Spacer(modifier = Modifier.height(25.dp))
                        LoginButton(navigationController) { loginViewModel.onLoginButtonPressed(navigationController) }
                        Spacer(modifier = Modifier.height(25.dp))
                        RegisterLink(navigationController)
                    }
                }
            } else {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Azul1),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(strokeWidth = 3.dp)
                }
            }
        }
    )


}

@Composable
fun LogoImage() {
    Image(
        painter = painterResource(id = logo_transparent),
        contentDescription = "Uber Routes Logo",
        modifier = Modifier
            .size(220.dp)
    )
}

@Composable
fun UserTextField(user: String, onValueChanged: (String) -> Unit) {
    TextField(
        value = user,
        onValueChange = { onValueChanged(it) },
        label = { Text("Usuario") },
        leadingIcon = {
            Icon(painter = painterResource(id = person), contentDescription = "Usuario")
        },
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 5.dp,
                brush = Brush.horizontalGradient(listOf(Azul2, Azul3, Azul4, Azul5)),
                shape = RoundedCornerShape(35.dp)
            ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            textColor = Color.White
        )
    )
}

@Composable
fun PasswordTextField(password: String, onValueChanged: (String) -> Unit) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    TextField(
        value = password,
        onValueChange = { onValueChanged(it) },
        label = { Text("Password") },
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(
                onClick = {passwordVisible = !passwordVisible}){
                if (passwordVisible) {
                    Icon(
                        painter = painterResource(id = eye_cross),
                        contentDescription = "Revelar"
                    )
                } else {
                    Icon(
                        painter = painterResource(id = eye),
                        contentDescription = "Revelar"
                    )
                }
            }
        },
        leadingIcon = {
            Icon(painter = painterResource(id = lock), contentDescription = "Password")
        },
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 5.dp,
                brush = Brush.horizontalGradient(listOf(Azul2, Azul3, Azul4, Azul5)),
                shape = RoundedCornerShape(35.dp)
            ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            textColor = Color.White
        )
    )
}

@Composable
fun RememberPassword(email: String, validEmail: Boolean, scaffoldState: ScaffoldState, onRememberPassword: (String) -> Unit) {
    var rememberPassword by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    ClickableText(
        text = AnnotatedString("¿Has olvidado la contraseña?"),
        onClick = {
            rememberPassword = true
        },
        style = TextStyle(color = Color.White, textDecoration = TextDecoration.Underline)
    )

    if(rememberPassword) {
        Dialog(
            onDismissRequest ={
                rememberPassword = false
                onRememberPassword("")
            },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {
            Column(
                Modifier
                    .background(Azul1)
                    .padding(end = 20.dp, start = 20.dp, top = 15.dp, bottom = 15.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Introduce tu correo electrónico para recuperar la contraseña",
                    fontSize = 20.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = email,
                    onValueChange = { onRememberPassword(it) },
                    label = { Text("Email") },
                    leadingIcon = {
                        Icon(painter = painterResource(id = R.drawable.email), contentDescription = "Email")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 5.dp,
                            brush = Brush.horizontalGradient(listOf(Azul2, Azul3, Azul4, Azul5)),
                            shape = RoundedCornerShape(35.dp)
                        ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        textColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.padding(top = 15.dp))
                Button(
                    onClick = {
                        onRememberPassword("")
                        rememberPassword = false
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(
                                "Recibirás un correo con instrucciones para recuperar la contraseña dentro de poco",
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Verde4,
                        contentColor = Azul3
                    ),
                    enabled = validEmail
                ) {
                    Text("Enviar")
                }
            }
        }
    }
}

@Composable
fun LoginButton(navigationController: NavHostController, onLoginButtonPressed: (NavHostController) -> Unit) {
    Button(
        onClick = {
            onLoginButtonPressed(navigationController)
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Verde4,
            contentColor = Azul1
        ),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(50.dp)
            .clip(RoundedCornerShape(35.dp))
    ) {
        Text(text = "Iniciar sesión")
    }
}

@Composable
fun RegisterLink(navigationController: NavHostController) {
    Row() {
        Text(text = "¿No tienes una cuenta?", fontSize = 16.sp, color = Color.White)
        Spacer(modifier = Modifier.width(3.dp))
        ClickableText(
            text = AnnotatedString(text = "Crear cuenta"),
            onClick = {
                navigationController.navigate(Routes.RegisterScreen.route)
            },
            style = TextStyle(
                color = Verde4,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        )
    }
}