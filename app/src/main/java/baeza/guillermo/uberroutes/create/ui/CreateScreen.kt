package baeza.guillermo.uberroutes.create.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import baeza.guillermo.uberroutes.ui.model.Routes
import baeza.guillermo.uberroutes.ui.theme.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun CreateScreen(navigationController: NavHostController, scaffoldState: ScaffoldState, createViewModel: CreateViewModel) {
    val view:Int by createViewModel.view.observeAsState(initial = 1)
    val viewModelMarkers:MutableList<LatLng> by createViewModel.markers.observeAsState(initial = mutableListOf())

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            if(view == 1) {
                MapView(viewModelMarkers, navigationController, createViewModel)
            } else if (view == 2) {
                ContinueView(navigationController, createViewModel)
            }
        }
    )
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun MapView(markers: MutableList<LatLng>, navigationController: NavHostController, createViewModel: CreateViewModel) {
    val lastPosition = if (markers.size == 0) {
        LatLng(38.55359897196608, -0.12057169825429333)
    } else {
        markers[markers.size-1]
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(lastPosition, 17f)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 56.dp)) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { marker ->
                createViewModel.addMarker(marker)
                navigationController.navigate(Routes.CreateScreen.route)
            }
        ) {
            Polyline(
                points = markers,
                color = Color.Red,
                visible = true
            )

            markers.forEach {
                Marker(
                    state = rememberMarkerState(position = it),
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                )
            }

        }
    }

    if (markers.size > 1) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp, start = 10.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                onClick = {
                    createViewModel.onContinueButtonPressed()
                    refreshView(navigationController)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Verde4,
                    contentColor = Azul1
                ),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp)
                    .clip(RoundedCornerShape(35.dp))
            ) {
                Text(text = "Continuar")
            }
        }
    }

    if (markers.size > 0) {
        Column(
            modifier = Modifier
                .padding(top = 5.dp, start = 5.dp, end = 5.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.End
        ) {
            ExtendedFloatingActionButton(
                onClick = {
                    markers.removeLast()
                    navigationController.navigate(Routes.CreateScreen.route)
                },
                text = { Text(text = "Borrar último", color = Azul1) },
                icon = { Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Icon", tint = RojoError) },
                backgroundColor = Verde5
            )
        }
    }
}

fun refreshView(navigationController: NavHostController) {
    val idView = navigationController.currentDestination?.id
    navigationController.navigate(idView!!)
    navigationController.popBackStack(idView, inclusive = true)
}

@Composable
fun ContinueView(navigationController: NavHostController, createViewModel: CreateViewModel) {
    val name:String by createViewModel.name.observeAsState(initial = "")
    val description:String by createViewModel.description.observeAsState(initial = "")
    val duration:String by createViewModel.duration.observeAsState(initial = "")
    val enterprise:String by createViewModel.enterprise.observeAsState(initial = "")
    val url:String by createViewModel.url.observeAsState(initial = "")
    val senderismo:Boolean by createViewModel.senderismo.observeAsState(initial = true)
    val ciclismo:Boolean by createViewModel.ciclismo.observeAsState(initial = false)
    val natacion:Boolean by createViewModel.natacion.observeAsState(initial = false)
    val kayak:Boolean by createViewModel.kayak.observeAsState(initial = false)
    val facil:Boolean by createViewModel.facil.observeAsState(initial = true)
    val moderado:Boolean by createViewModel.moderado.observeAsState(initial = false)
    val dificil:Boolean by createViewModel.dificil.observeAsState(initial = false)
    val enableButton:Boolean by createViewModel.enableButton.observeAsState(initial = false)

    val customColors: TextFieldColors = TextFieldDefaults.textFieldColors(
        backgroundColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        textColor = Color.White
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Azul1)
        .padding(bottom = 60.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 5.dp)
        ) {
            TextField(
                value = name,
                onValueChange = { createViewModel.onCreateChanged(it, description, duration, enterprise, url) },
                label = { Text("Nombre *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 5.dp,
                        brush = Brush.horizontalGradient(listOf(Azul2, Azul3, Azul4, Azul5)),
                        shape = RoundedCornerShape(35.dp)
                    ),
                colors = customColors
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Categoría:")

            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth(0.4f)
                    .background(Azul2)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth(0.5f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Checkbox(checked = senderismo, onCheckedChange = {createViewModel.onSenderismoChanged()}, colors = CheckboxDefaults.colors(checkedColor = Verde5))
                    Text(text = "Senderismo", modifier = Modifier.width(100.dp))
                }
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Checkbox(checked = ciclismo, onCheckedChange = {createViewModel.onCiclismoChanged()}, colors = CheckboxDefaults.colors(checkedColor = Verde5))
                    Text(text = "Ciclismo", modifier = Modifier.width(100.dp))
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth(0.5f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Checkbox(checked = natacion, onCheckedChange = {createViewModel.onNatacionChanged()}, colors = CheckboxDefaults.colors(checkedColor = Verde5))
                    Text(text = "Natación", modifier = Modifier.width(100.dp))
                }
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Checkbox(checked = kayak, onCheckedChange = {createViewModel.onKayakChanged()}, colors = CheckboxDefaults.colors(checkedColor = Verde5))
                    Text(text = "Kayak", modifier = Modifier.width(100.dp))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Dificultad:")

            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth(0.4f)
                    .background(Azul2)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth(0.26f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Checkbox(checked = facil, onCheckedChange = {createViewModel.onFacilChanged()}, colors = CheckboxDefaults.colors(checkedColor = Verde5))
                    Text(text = "Fácil")
                }
                Row(modifier = Modifier.fillMaxWidth(0.58f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Checkbox(checked = moderado, onCheckedChange = {createViewModel.onModeradoChanged()}, colors = CheckboxDefaults.colors(checkedColor = Verde5))
                    Text(text = "Moderado")
                }
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Checkbox(checked = dificil, onCheckedChange = {createViewModel.onDificilChanged()}, colors = CheckboxDefaults.colors(checkedColor = Verde5))
                    Text(text = "Difícil")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = duration,
                onValueChange = { createViewModel.onCreateChanged(name, description, it, enterprise, url) },
                label = { Text("Duración *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 5.dp,
                        brush = Brush.horizontalGradient(listOf(Azul2, Azul3, Azul4, Azul5)),
                        shape = RoundedCornerShape(35.dp)
                    ),
                colors = customColors
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth()) {

                TextField(
                    value = enterprise,
                    onValueChange = { createViewModel.onCreateChanged(name, description, duration, it, url) },
                    label = { Text("Empresa") },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .border(
                            width = 5.dp,
                            brush = Brush.horizontalGradient(listOf(Azul2, Azul3, Azul4, Azul5)),
                            shape = RoundedCornerShape(35.dp)
                        ),
                    colors = customColors
                )

                Spacer(modifier = Modifier.width(5.dp))

                TextField(
                    value = url,
                    onValueChange = { createViewModel.onCreateChanged(name, description, duration, enterprise, it) },
                    label = { Text("URL") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 5.dp,
                            brush = Brush.horizontalGradient(listOf(Azul2, Azul3, Azul4, Azul5)),
                            shape = RoundedCornerShape(35.dp)
                        ),
                    colors = customColors
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = description,
                onValueChange = { createViewModel.onCreateChanged(name, it, duration, enterprise, url) },
                label = { Text("Descripción *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .border(
                        width = 5.dp,
                        brush = Brush.horizontalGradient(listOf(Azul2, Azul3, Azul4, Azul5)),
                        shape = RoundedCornerShape(35.dp)
                    ),
                colors = customColors,
                maxLines = 6
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row(modifier = Modifier.fillMaxWidth()) {

                Button(
                    onClick = {
                        createViewModel.onBackToMapPressed()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Verde4,
                        contentColor = Azul1
                    ),
                    modifier = Modifier
                        .height(50.dp)
                        .clip(RoundedCornerShape(35.dp))
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Icon")
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = {
                        createViewModel.onCreateButtonPressed(navigationController)
                    },
                    enabled = enableButton,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Verde4,
                        contentColor = Azul1
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(35.dp))
                ) {
                    Text(text = "Crear ruta")
                }
            }
        }
    }
}