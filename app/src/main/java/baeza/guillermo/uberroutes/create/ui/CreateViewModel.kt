package baeza.guillermo.uberroutes.create.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import baeza.guillermo.uberroutes.create.domain.entity.RouteModelFactory
import baeza.guillermo.uberroutes.create.domain.usecase.CreateUseCase
import baeza.guillermo.uberroutes.create.domain.usecase.GetCurrentUserIdUseCase
import baeza.guillermo.uberroutes.ui.model.Routes
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Math.*
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val createUseCase: CreateUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val routeModelFactory: RouteModelFactory
): ViewModel() {
    private lateinit var currentUserId: String

    private val _view = MutableLiveData<Int>()
    val view: LiveData<Int> = _view

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    private val _duration = MutableLiveData<String>()
    val duration: LiveData<String> = _duration

    private val _enterprise = MutableLiveData<String>()
    val enterprise: LiveData<String> = _enterprise

    private val _url = MutableLiveData<String>()
    val url: LiveData<String> = _url

    private val _ciclismo = MutableLiveData<Boolean>()
    val ciclismo: LiveData<Boolean> = _ciclismo

    private val _senderismo = MutableLiveData<Boolean>()
    val senderismo: LiveData<Boolean> = _senderismo

    private val _natacion = MutableLiveData<Boolean>()
    val natacion: LiveData<Boolean> = _natacion

    private val _kayak = MutableLiveData<Boolean>()
    val kayak: LiveData<Boolean> = _kayak

    private val _facil = MutableLiveData<Boolean>()
    val facil: LiveData<Boolean> = _facil

    private val _moderado = MutableLiveData<Boolean>()
    val moderado: LiveData<Boolean> = _moderado

    private val _dificil = MutableLiveData<Boolean>()
    val dificil: LiveData<Boolean> = _dificil

    private val _enableButton = MutableLiveData<Boolean>()
    val enableButton: LiveData<Boolean> = _enableButton

    private var distance: Double = 0.0

    private val _markers = MutableLiveData<MutableList<LatLng>>()
    val markers: LiveData<MutableList<LatLng>> = _markers

    private val coordinates: MutableList<MutableList<Double>> = mutableListOf(mutableListOf())

    init {
        viewModelScope.launch {
            currentUserId = getCurrentUserIdUseCase()
            Log.i("DAM", "Se ha recuperado el ID del usuario")
        }
        onSenderismoChanged()
        onFacilChanged()
    }

    fun onCiclismoChanged(){
        _ciclismo.value = true
        _senderismo.value = false
        _natacion.value = false
        _kayak.value = false
    }

    fun onSenderismoChanged(){
        _ciclismo.value = false
        _senderismo.value = true
        _natacion.value = false
        _kayak.value = false
    }

    fun onNatacionChanged(){
        _ciclismo.value = false
        _senderismo.value = false
        _natacion.value = true
        _kayak.value = false
    }

    fun onKayakChanged(){
        _ciclismo.value = false
        _senderismo.value = false
        _natacion.value = false
        _kayak.value = true
    }

    fun onFacilChanged() {
        _facil.value = true
        _moderado.value = false
        _dificil.value = false
    }

    fun onModeradoChanged() {
        _facil.value = false
        _moderado.value = true
        _dificil.value = false
    }

    fun onDificilChanged() {
        _facil.value = false
        _moderado.value = false
        _dificil.value = true
    }

    fun onCreateChanged(name: String, description: String, duration: String, enterprise: String, url: String) {
        _name.value = name
        _description.value = description
        _duration.value = duration
        _enterprise.value = enterprise
        _url.value = url
        _enableButton.value = enableButton()
    }

    fun enableButton(): Boolean =
        _name.value!!.isNotEmpty() &&
            _description.value!!.isNotEmpty() &&
                _duration.value!!.isNotEmpty()

    fun onBackToMapPressed() {
        _view.value = 1
    }

    fun addMarker(marker: LatLng) {
        _markers.value = (_markers.value?.plus(marker) ?: listOf(marker)) as MutableList<LatLng>?
    }

    fun onContinueButtonPressed() {
        _view.value = 2
    }

    fun getDistance(coordinate1: LatLng, coordinate2: LatLng){

        val decimalFormat = DecimalFormat("#.00")
        val earthRadius = 6372.8
        val latDistance = toRadians(coordinate1.latitude - coordinate2.latitude)
        val longDistance = toRadians(coordinate1.longitude - coordinate2.longitude)
        val lat1 = toRadians(coordinate1.latitude)
        val lat2 = toRadians(coordinate2.latitude)

        val total = 2 * earthRadius * asin(
            sqrt(
                pow(sin(latDistance / 2), 2.0) +
                        pow(sin(longDistance / 2), 2.0) *
                        cos(lat1) *
                        cos(lat2)
            )
        )

        distance += total
        distance = decimalFormat.format(distance).toDouble()
    }

    fun onCreateButtonPressed(navigationController: NavHostController) {
        for (i in 0 until _markers.value!!.size-1) {
            getDistance(_markers.value!![i], _markers.value!![i+1])
        }

        for (i in 0 until _markers.value!!.size) {
            coordinates.add(mutableListOf(_markers.value!![0].latitude, _markers.value!![0].longitude))
        }

        val cat = if (_ciclismo.value!!) {
            "Ciclismo"
        } else if (_senderismo.value!!) {
            "Senderismo"
        } else if (_natacion.value!!) {
            "Natación"
        } else {
            "Kayak"
        }

        val dif = if (_facil.value!!) {
            "Fácil"
        } else if (_moderado.value!!) {
            "Moderado"
        } else {
            "Difícil"
        }

        if (_enterprise.value!!.isEmpty()) {
            _enterprise.value = "No enterprise"
        }

        if (_url.value!!.isEmpty()) {
            _url.value = "No URL"
        }

        viewModelScope.launch {
            val result = createUseCase(
                routeModelFactory(
                    name = _name.value!!,
                    description = _description.value!!,
                    category = cat,
                    distance = "${distance}Km",
                    difficulty = dif,
                    duration = _duration.value!!,
                    coordinates = coordinates,
                    privacy = "Public",
                    enterprise = enterprise.value!!,
                    user = currentUserId,
                    url = _url.value!!
                )
            )
            Log.i("DAM", "El POST de la ruta se ha completado con resultado: $result")
            if (result) {
                navigationController.navigate(Routes.ProfileScreen.route)
                onCreateChanged("", "", "", "", "")
                onSenderismoChanged()
                onFacilChanged()
                _markers.value!!.clear()
                _view.value = 1
            }
        }

    }
}