package baeza.guillermo.uberroutes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import baeza.guillermo.uberroutes.create.ui.CreateScreen
import baeza.guillermo.uberroutes.create.ui.CreateViewModel
import baeza.guillermo.uberroutes.home.ui.HomeScreen
import baeza.guillermo.uberroutes.home.ui.HomeViewModel
import baeza.guillermo.uberroutes.login.ui.LoginScreen
import baeza.guillermo.uberroutes.login.ui.LoginViewModel
import baeza.guillermo.uberroutes.profile.ui.ProfileScreen
import baeza.guillermo.uberroutes.profile.ui.ProfileViewModel
import baeza.guillermo.uberroutes.register.ui.RegisterScreen
import baeza.guillermo.uberroutes.register.ui.RegisterViewModel
import baeza.guillermo.uberroutes.ui.model.Routes
import baeza.guillermo.uberroutes.ui.theme.UberRoutesTheme
import dagger.hilt.android.AndroidEntryPoint
import baeza.guillermo.uberroutes.ui.theme.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val createViewModel: CreateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navigationController = rememberNavController()
            val scaffoldState = rememberScaffoldState()
            UberRoutesTheme {
                NavHost(
                    navController = navigationController,
                    startDestination = Routes.LoginScreen.route
                ) {
                    composable(route = Routes.LoginScreen.route) {
                        Scaffold(scaffoldState = scaffoldState,
                            content = {
                                LoginScreen(
                                    navigationController = navigationController,
                                    scaffoldState = scaffoldState,
                                    loginViewModel = loginViewModel
                                )
                            }
                        )
                    }
                    composable(route = Routes.RegisterScreen.route) {
                        Scaffold(scaffoldState = scaffoldState,
                            content = {
                                RegisterScreen(
                                    navigationController = navigationController,
                                    scaffoldState = scaffoldState,
                                    registerViewModel = registerViewModel
                                )
                            }
                        )
                    }
                    composable(route = Routes.HomeScreen.route) {
                        Scaffold(scaffoldState = scaffoldState,
                            topBar = { TopNav() },
                            bottomBar = {
                                BottomNav(
                                    navigationController = navigationController,
                                    view = "Home"
                                )
                            },
                            content = {
                                HomeScreen(
                                    navigationController = navigationController,
                                    scaffoldState = scaffoldState,
                                    homeViewModel = homeViewModel
                                )
                            }
                        )
                    }
                    composable(route = Routes.ProfileScreen.route) {
                        Scaffold(scaffoldState = scaffoldState,
                            topBar = { TopNav() },
                            bottomBar = {
                                BottomNav(
                                    navigationController = navigationController,
                                    view = "Profile"
                                )
                            },
                            content = {
                                ProfileScreen(
                                    navigationController = navigationController,
                                    scaffoldState = scaffoldState,
                                    profileViewModel = profileViewModel
                                )
                            }
                        )
                    }
                    composable(route = Routes.CreateScreen.route) {
                        Scaffold(scaffoldState = scaffoldState,
                            topBar = { TopNav() },
                            bottomBar = {
                                BottomNav(
                                    navigationController = navigationController,
                                    view = "Create"
                                )
                            },
                            content = {
                                CreateScreen(
                                    navigationController = navigationController,
                                    scaffoldState = scaffoldState,
                                    createViewModel = createViewModel
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopNav() {
    TopAppBar (
        title = {
            Image(
                painter = painterResource(id = R.drawable.titulo_transparent),
                contentDescription = "Título",
                Modifier.size(250.dp)
            )
        },
        backgroundColor = Verde4,
        elevation = 123.dp,
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
    )
}

@Composable
fun BottomNav(navigationController: NavHostController, view: String) {
    val homeIcon: ImageVector
    val createIcon: ImageVector
    val profileIcon: ImageVector
    when (view) {
        "Home" -> {
            homeIcon = Icons.Filled.Home
            createIcon = Icons.Outlined.AddBox
            profileIcon = Icons.Outlined.Person
        }
        "Create" -> {
            homeIcon = Icons.Outlined.Home
            createIcon = Icons.Filled.AddBox
            profileIcon = Icons.Outlined.Person
        }
        "Profile" -> {
            homeIcon = Icons.Outlined.Home
            createIcon = Icons.Outlined.AddBox
            profileIcon = Icons.Filled.Person
        }
        else -> {
            homeIcon = Icons.Outlined.Home
            createIcon = Icons.Outlined.AddBox
            profileIcon = Icons.Outlined.Person
        }
    }
    BottomNavigation (
        backgroundColor = Verde4,
        contentColor = Azul3
    ) {
        BottomNavigationItem(
            selected = false,
            onClick = {
                if(view == "Home") {
                    refreshView(navigationController)
                } else {
                    navigationController.navigate(Routes.HomeScreen.route)
                }
            },
            icon = {
                Icon(imageVector = homeIcon, contentDescription = "Home")
            }
        )
        BottomNavigationItem(
            selected = false,
            onClick = {
                if(view == "Create") {
                    refreshView(navigationController)
                } else {
                    navigationController.navigate(Routes.CreateScreen.route)
                }
            },
            icon = {
                Icon(imageVector = createIcon, contentDescription = "Create")
            }
        )
        BottomNavigationItem(
            selected = false,
            onClick = {
                if(view == "Profile") {
                    refreshView(navigationController)
                } else {
                    navigationController.navigate(Routes.ProfileScreen.route)
                }
            },
            icon = {
                Icon(imageVector = profileIcon, contentDescription = "Profile")
            }
        )
    }

}

fun refreshView(navigationController: NavHostController) {
    val idView = navigationController.currentDestination?.id
    navigationController.navigate(idView!!)
    navigationController.popBackStack(idView, inclusive = true)
}