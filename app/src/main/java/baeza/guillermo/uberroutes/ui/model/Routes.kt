package baeza.guillermo.uberroutes.ui.model

sealed class Routes(val route: String) {
    object LoginScreen: Routes("loginScreen")
    object RegisterScreen: Routes("registerScreen")
    object HomeScreen: Routes("homeScreen")
    object ProfileScreen: Routes("profileScreen")
    object CreateScreen: Routes("createScreen")
}