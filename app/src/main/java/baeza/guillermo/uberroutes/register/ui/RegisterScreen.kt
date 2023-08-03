package baeza.guillermo.uberroutes.register.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import baeza.guillermo.uberroutes.R
import baeza.guillermo.uberroutes.ui.theme.*
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RegisterScreen(navigationController: NavHostController, scaffoldState: ScaffoldState, registerViewModel: RegisterViewModel) {
    val name:String by registerViewModel.name.observeAsState(initial = "")
    val surname:String by registerViewModel.surname.observeAsState(initial = "")
    val username:String by registerViewModel.username.observeAsState(initial = "")
    val email:String by registerViewModel.email.observeAsState(initial = "")
    val password:String by registerViewModel.password.observeAsState(initial = "")
    val passwordRepeat:String by registerViewModel.passwordRepeat.observeAsState(initial = "")
    val buttonEnabled:Boolean by registerViewModel.buttonEnabled.observeAsState(initial = false)
    val view:Int by registerViewModel.view.observeAsState(initial = 1)
    val problem:Boolean by registerViewModel.problem.observeAsState(initial = false)
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            if (view == 1) {

                if (problem) {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "No se ha podido crear el usuario",
                            duration = SnackbarDuration.Short
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Azul1),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LogoImage()

                        Spacer(modifier = Modifier.height(10.dp))

                        RegularTextField("Nombre", name) { registerViewModel.onRegisterChanged(it, surname, username, email, password, passwordRepeat) }

                        Spacer(modifier = Modifier.height(10.dp))

                        RegularTextField("Apellidos", surname) { registerViewModel.onRegisterChanged(name, it, username, email, password, passwordRepeat) }

                        Spacer(modifier = Modifier.height(10.dp))

                        RegularTextField("Nombre de usuario", username) { registerViewModel.onRegisterChanged(name, surname, it, email, password, passwordRepeat) }

                        Spacer(modifier = Modifier.height(10.dp))

                        RegularTextField("Correo electrónico", email) { registerViewModel.onRegisterChanged(name, surname, username, it, password, passwordRepeat) }

                        Spacer(modifier = Modifier.height(10.dp))

                        PasswordTextField("Contraseña", password) { registerViewModel.onRegisterChanged(name, surname, username, email, it, passwordRepeat) }

                        Spacer(modifier = Modifier.height(10.dp))

                        PasswordTextField("Repite la contraseña", passwordRepeat) { registerViewModel.onRegisterChanged(name, surname, username, email, password, it) }

                        Spacer(modifier = Modifier.height(20.dp))

                        RegisterButton(navigationController, buttonEnabled) { registerViewModel.onRegisterButtonPressed(navigationController) }
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
        painter = painterResource(id = R.drawable.logo_transparent),
        contentDescription = "Uber Routes Logo",
        modifier = Modifier
            .size(220.dp)
    )
}

@Composable
fun RegularTextField(title:String, value: String, onValueChanged: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        label = { Text(title) },
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
    if (title == "Nombre de usuario") {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(end = 10.dp), horizontalAlignment = Alignment.End) {
            Text(text = "(Min. 3 carácteres)", fontSize = 12.sp)
        }
    }
}

@Composable
fun PasswordTextField(title: String, value: String, onValueChanged: (String) -> Unit) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        label = { Text(title) },
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(
                onClick = {passwordVisible = !passwordVisible}){
                if (passwordVisible) {
                    Icon(
                        painter = painterResource(id = R.drawable.eye_cross),
                        contentDescription = "Revelar"
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.eye),
                        contentDescription = "Revelar"
                    )
                }
            }
        },
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.lock), contentDescription = "Password")
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
    if(title == "Contraseña"){
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(end = 10.dp), horizontalAlignment = Alignment.End) {
            Text(text = "Min. 6 caracteres (minus, mayus y nums)", fontSize = 12.sp)
        }
    }
}

@Composable
fun RegisterButton(
    navigationController: NavHostController,
    buttonEnabled: Boolean,
    onRegisterButtonPressed: (NavHostController) -> Unit
) {
    Button(
        onClick = {
            onRegisterButtonPressed(navigationController)
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Verde4,
            contentColor = Azul1
        ),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(50.dp)
            .clip(RoundedCornerShape(35.dp)),
        enabled = buttonEnabled
    ) {
        Text(text = "Crear cuenta")
    }
}