package baeza.guillermo.uberroutes.register.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import baeza.guillermo.uberroutes.register.domain.entity.UserModelFactory
import baeza.guillermo.uberroutes.register.domain.usecase.RegisterUseCase
import baeza.guillermo.uberroutes.ui.model.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val userModelFactory: UserModelFactory
) : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _surname = MutableLiveData<String>()
    val surname: LiveData<String> = _surname

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _passwordRepeat = MutableLiveData<String>()
    val passwordRepeat: LiveData<String> = _passwordRepeat

    private val _buttonEnabled = MutableLiveData<Boolean>()
    val buttonEnabled: LiveData<Boolean> = _buttonEnabled

    private val regexPassword = "^(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9._-]{6,}\$"

    private val regexUser = "[a-zA-Z0-9]{3,}\$"

    private val regexEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\$"

    private val _view = MutableLiveData<Int>()
    val view: LiveData<Int> = _view

    private val _problem = MutableLiveData<Boolean>()
    val problem: LiveData<Boolean> = _problem

    fun onRegisterChanged(
        name: String,
        surname: String,
        username: String,
        email: String,
        password: String,
        passwordRepeat: String
    ) {
        _name.value = name
        _surname.value = surname
        _username.value = username
        _email.value = email
        _password.value = password
        _passwordRepeat.value = passwordRepeat
        _buttonEnabled.value =
            enableButton(name, surname, username, email, password, passwordRepeat)
    }

    fun enableButton(
        name: String,
        surname: String,
        username: String,
        email: String,
        password: String,
        passwordRepeat: String
    ) =
        regexEmail.toRegex().containsMatchIn(email) &&
                regexUser.toRegex().containsMatchIn(username) &&
                regexPassword.toRegex().containsMatchIn(password) &&
                password == passwordRepeat &&
                name.isNotEmpty() &&
                surname.isNotEmpty()

    fun onRegisterButtonPressed(navigationController: NavHostController) {
        _view.value = 2
        viewModelScope.launch {
            val result = registerUseCase(
                userModelFactory(
                    id = username.value!!,
                    name = name.value!!,
                    surname = surname.value!!,
                    password = password.value!!,
                    email = email.value!!
                )
            )
            Log.i("DAM", "Se ha completado el envio de datos y es: $result")
            if(result) {
                navigationController.navigate(Routes.LoginScreen.route)
                onRegisterChanged("", "", "", "", "", "")
            } else {
                _problem.value = true
            }
            _view.value = 1
        }
    }
}