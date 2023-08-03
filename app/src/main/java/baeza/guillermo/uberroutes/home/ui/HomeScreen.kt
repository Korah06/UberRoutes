package baeza.guillermo.uberroutes.home.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import baeza.guillermo.uberroutes.home.domain.entity.PostModel
import baeza.guillermo.uberroutes.dependencyinjection.Constants.IP_ADDRESS
import baeza.guillermo.uberroutes.home.domain.entity.CommentModel
import baeza.guillermo.uberroutes.ui.theme.*
import coil.compose.AsyncImage

@Composable
fun HomeScreen(navigationController: NavHostController, scaffoldState: ScaffoldState, homeViewModel: HomeViewModel) {
    val posts = homeViewModel.getPosts()
    val view:Int by homeViewModel.view.observeAsState(initial = 1)
    val commentText:String by homeViewModel.commentText.observeAsState(initial = "")

    homeViewModel.updateRoutes()

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            if(view == 1) {
                AllRoutesView(
                    navigationController = navigationController,
                    homeViewModel = homeViewModel,
                    posts = posts
                )
            } else if (view == 2) {
                SingleRouteView(
                    homeViewModel = homeViewModel,
                    postModel = homeViewModel.getSingleModel(),
                    commentText = commentText
                ) { homeViewModel.onCommentChanged(it) }
            } else {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Azul1),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = CenterHorizontally
                ) {
                    CircularProgressIndicator(strokeWidth = 3.dp)
                }
            }
        }
    )
}

@Composable
fun SingleRouteView(homeViewModel: HomeViewModel, postModel: PostModel, commentText: String, onCommentChanged: (String) -> Unit) {
    val rememberScrollState = rememberScrollState()
    val comments = homeViewModel.getComments()
    
    Column(
        modifier = Modifier
            .background(Azul1)
            .padding(10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState)
    ) {
        AsyncImage(
            model = "${IP_ADDRESS}posts/img/${postModel.image}",
            contentDescription = "Imagen de la ruta",
            modifier = Modifier.clip(RoundedCornerShape(5.dp))
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Categoría: ${postModel.category}")
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = postModel.name, fontSize = 32.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.White
                ),
            ) {
                Icon(imageVector = Icons.Default.Person, contentDescription = "Person Icon")
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = postModel.user)
            }
            Spacer(modifier = Modifier.width(10.dp))
            CommentsButton(homeViewModel = homeViewModel, comments = comments, commentText = commentText, onCommentChanged = onCommentChanged)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth(0.5f), horizontalAlignment = CenterHorizontally) {
                Text(text = "Distancia")
                Text(text = postModel.distance, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))
                Divider(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(1.dp)
                        .background(Azul2)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Dificultad")
                Text(text = postModel.difficulty, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            Divider(
                modifier = Modifier
                    .height(120.dp)
                    .width(1.dp)
                    .background(Azul2)
            )
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = CenterHorizontally) {
                Text(text = "Duración")
                Text(text = postModel.duration, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))
                Divider(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(1.dp)
                        .background(Azul2)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Fecha")
                Text(text = postModel.date, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(modifier = Modifier.fillMaxWidth(0.35f)) {
            Text(text = "Descripción", fontSize = 20.sp)
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Azul2)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = postModel.description, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(110.dp))
    }

    Column(
        modifier = Modifier
            .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 62.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        ExtendedFloatingActionButton(
            onClick = { homeViewModel.onBackButtonClicked() },
            text = { Text(text = "Volver") },
            icon = { Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Icon") },
            backgroundColor = Verde5
        )
    }


}

@Composable
fun CommentsButton(homeViewModel: HomeViewModel, comments: List<CommentModel>, commentText: String, onCommentChanged: (String) -> Unit) {
    val listState = rememberLazyListState()
    var showComments by rememberSaveable { mutableStateOf(false) }

    Button(
        onClick = { showComments = true },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.White
        ),
    ) {
        Icon(imageVector = Icons.Default.Chat, contentDescription = "Person Icon")
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = "Comentarios (${comments.size})")
    }

    if (showComments) {
        Dialog(
            onDismissRequest = {
                showComments = false
                onCommentChanged("")
            },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)) {
                Row(modifier = Modifier
                    .height(46.dp)) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .fillMaxHeight()
                            .padding(start = 5.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(text = "Comentarios", fontSize = 20.sp)
                    }
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Azul2)
                )

                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxHeight(0.85f)
                        .fillMaxWidth()
                        .background(Azul1)
                        .padding(5.dp)
                ) {
                    item {
                        comments.forEach {
                            Card(
                                border = BorderStroke(2.dp, Verde5),
                                backgroundColor = Azul2
                            ) {
                                Column(modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Bottom
                                    ) {
                                        Text(
                                            text = it.user,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.width(5.dp))
                                        Text(text = "${it.date} - ${it.time}", fontSize = 14.sp)
                                    }
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Text(text = it.description)
                                }
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                }
                Column(modifier = Modifier
                    .background(Azul1)
                    .padding(5.dp)) {
                    TextField(
                        value = commentText,
                        onValueChange = { onCommentChanged(it) },
                        placeholder = { Text(text = "Comentar", color = Color.Black) },
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(25.dp))
                            .background(Color.LightGray)
                            .border(
                                width = 5.dp,
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        Verde1,
                                        Verde2,
                                        Verde3,
                                        Verde4,
                                        Verde5
                                    )
                                ),
                                shape = RoundedCornerShape(25.dp)
                            ),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black
                        ),
                        trailingIcon = {
                            IconButton(onClick = {
                                homeViewModel.onPostCommentButtonPressed()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = "Send Button",
                                    tint = Azul2
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AllRoutesView(navigationController: NavHostController, homeViewModel: HomeViewModel, posts: List<PostModel>) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .background(Azul1)
            .padding(bottom = 57.dp)
    ) {
        item {
            posts.forEach {
                PostCard(it, homeViewModel, navigationController)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostCard(postModel: PostModel, homeViewModel: HomeViewModel, navigationController: NavHostController) {
    Card (
        border = BorderStroke(2.dp, Verde5),
        modifier = Modifier
            .height(260.dp)
            .background(Azul2)
            .padding(3.dp),
        onClick = {
            homeViewModel.onCardClicked(postModel)
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Azul2)) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.43f)
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(text = postModel.name,
                    modifier = Modifier
                        .fillMaxHeight(0.7f),
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                        Text(text = "Categoría: ${postModel.category}")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(text = postModel.user)
                        Spacer(modifier = Modifier.width(2.dp))
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Person",
                            tint = Color.White
                        )
                    }
                }
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(1.dp)
                    .background(Azul5)
                    .align(CenterHorizontally)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth(0.55f)) {
                    AsyncImage(
                        model = "${IP_ADDRESS}posts/img/${postModel.image}",
                        contentDescription = "Post Image",
                        alignment = Center
                    )
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 15.dp)) {
                    Text(text = "Distancia: ${postModel.distance}")
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Duración: ${postModel.duration}")
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Dificultad: ${postModel.difficulty}")
                }
            }
        }
    }
}