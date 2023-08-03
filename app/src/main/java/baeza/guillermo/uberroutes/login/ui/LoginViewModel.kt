package baeza.guillermo.uberroutes.login.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import baeza.guillermo.uberroutes.login.domain.LoginUseCase
import baeza.guillermo.uberroutes.ui.model.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _user = MutableLiveData<String>()
    val user: LiveData<String> = _user

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _validEmail = MutableLiveData<Boolean>()
    val validEmail: LiveData<Boolean> = _validEmail

    private val _view = MutableLiveData<Int>()
    val view: LiveData<Int> = _view

    private val _incorrectData = MutableLiveData<Boolean>()
    val incorrectData: LiveData<Boolean> = _incorrectData

    fun onLoginChanged(user: String, password: String) {
        _user.value = user
        _password.value = password
    }

    fun onRememberPassword(email: String) {
        _email.value = email
        _validEmail.value = isValidEmail(email)
    }

    fun onLoginButtonPressed(navigationController: NavHostController) {
        _view.value = 2
        viewModelScope.launch {
            val result = loginUseCase(_user.value!!, _password.value!!)
            if(result) {
                Log.i("DAM", "Se ha completado el login")
                onLoginChanged("", "")
                navigationController.navigate(Routes.HomeScreen.route)
                _view.value = 1
            } else {
                _incorrectData.value = true
                _view.value = 1
            }
        }
    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
}