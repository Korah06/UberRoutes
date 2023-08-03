package baeza.guillermo.uberroutes.profile.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import baeza.guillermo.uberroutes.dependencyinjection.Constants.IP_ADDRESS
import baeza.guillermo.uberroutes.profile.domain.entity.CommentModel
import baeza.guillermo.uberroutes.profile.domain.entity.ProfilePostModel
import baeza.guillermo.uberroutes.profile.domain.entity.UserModel
import baeza.guillermo.uberroutes.ui.model.Routes
import baeza.guillermo.uberroutes.ui.theme.*
import coil.compose.AsyncImage

@Composable
fun ProfileScreen(navigationController: NavHostController, scaffoldState: ScaffoldState, profileViewModel: ProfileViewModel) {
    val user: UserModel = profileViewModel.getProfileUser()
    val posts: List<ProfilePostModel> = profileViewModel.getPosts()
    val followers: List<String> = profileViewModel.getFollowers()
    val following: List<String> = profileViewModel.getFollowing()
    val view:Int by profileViewModel.view.observeAsState(initial = 1)
    val commentText:String by profileViewModel.commentText.observeAsState(initial = "")

    profileViewModel.updateData()

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            if(view == 1) {
                MainProfileView(
                    navigationController = navigationController,
                    profileViewModel = profileViewModel,
                    user = user,
                    followers = followers,
                    following = following,
                    posts = posts
                )
            } else if(view == 2) {
                SingleRouteView(
                    profileViewModel = profileViewModel,
                    postModel = profileViewModel.getSingleModel(),
                    commentText = commentText
                ) { profileViewModel.onCommentChanged(it) }
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
fun SingleRouteView(profileViewModel: ProfileViewModel, postModel: ProfilePostModel, commentText: String, onCommentChanged: (String) -> Unit) {
    val rememberScrollState = rememberScrollState()
    val comments = profileViewModel.getComments()

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
            CommentsButton(profileViewModel = profileViewModel, comments = comments, commentText = commentText, onCommentChanged = onCommentChanged)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth(0.5f), horizontalAlignment = Alignment.CenterHorizontally) {
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
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
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
            onClick = { profileViewModel.onBackButtonClicked() },
            text = { Text(text = "Volver") },
            icon = { Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Icon") },
            backgroundColor = Verde5
        )
    }
}

@Composable
fun CommentsButton(profileViewModel: ProfileViewModel, comments: List<CommentModel>, commentText: String, onCommentChanged: (String) -> Unit) {
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
                                        verticalAlignment = Alignment.Bottom
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
                                profileViewModel.onPostCommentButtonPressed()
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
fun MainProfileView(
    navigationController: NavHostController,
    profileViewModel: ProfileViewModel,
    user: UserModel,
    followers: List<String>,
    following: List<String>,
    posts: List<ProfilePostModel>
) {
    val listState = rememberLazyListState()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Azul1)
        .padding(5.dp)
    ) {
        Row(
            verticalAlignment = CenterVertically,
            modifier = Modifier.padding(5.dp)
        ) {
            UserImage(user = user)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(text = user._id, fontSize = 30.sp)
                Row {
                    FollowingButton(num = user.following, users = following)
                    Spacer(modifier = Modifier.width(5.dp))
                    FollowersButton(num = user.followers, users = followers)
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "${user.name} ${user.surname}", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Miembro desde: ${user.register}")
        Spacer(modifier = Modifier.height(5.dp))
        Row {
            Icon(imageVector = Icons.Filled.Email, contentDescription = "Email icon")
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = user.email)
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row {
            Icon(imageVector = Icons.Filled.Language, contentDescription = "Web icon")
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = user.web)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                navigationController.navigate(Routes.LoginScreen.route)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Verde4,
                contentColor = RojoError
            ),
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
        ) {
            Icon(imageVector = Icons.Default.Logout, contentDescription = "Logout Icon", tint = RojoError)
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "LogOut")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .height(45.dp)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Azul1,
                            Azul2,
                            Azul3,
                            Azul4,
                            Azul5
                        )
                    )
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = CenterVertically
        ) {
            Text(text = "Publicaciones (${posts.size})", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(Color.White)
        )
        Spacer(modifier = Modifier.height(5.dp))
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(bottom = 50.dp)
        ) {
            item {
                posts.forEach {
                    PostCard(it, profileViewModel)
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
}

@Composable
fun UserImage(user: UserModel) {
    AsyncImage(
        model = "${IP_ADDRESS}users/img/${user.picture}",
        contentDescription = "Post Image",
        alignment = Alignment.Center,
        modifier = Modifier
            .clip(CircleShape)
            .size(100.dp)
    )
}

@Composable
fun FollowingButton(num: String, users: List<String>) {
    val listState = rememberLazyListState()
    var showFollowing by rememberSaveable { mutableStateOf(false) }

    Button(
        onClick = {
            showFollowing = true
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        )
    ) {
        Text(text = "$num seguidos")
    }

    if(showFollowing){
        Dialog(
            onDismissRequest = { showFollowing = false },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .background(Azul1)
                    .padding(10.dp)
                    .fillMaxWidth()
                    .height(450.dp)
            ) {
                item {
                    Text(text = "Seguidos ($num)", fontSize = 28.sp)
                    Spacer(modifier = Modifier.height(5.dp))
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Verde5)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    users.forEach {
                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Person",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = it, fontSize = 20.sp)
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun FollowersButton(num: String, users: List<String>) {
    val listState = rememberLazyListState()
    var showFollowers by rememberSaveable { mutableStateOf(false) }

    Button(
        onClick = {
            showFollowers = true
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        )
    ) {
        Text(text = "$num seguidores")
    }

    if(showFollowers){
        Dialog(
            onDismissRequest = { showFollowers = false },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .background(Azul1)
                    .padding(10.dp)
                    .fillMaxWidth()
                    .height(450.dp)
            ) {
                item {
                    Text(text = "Seguidores ($num)", fontSize = 28.sp)
                    Spacer(modifier = Modifier.height(5.dp))
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Verde5)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    users.forEach {
                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Person",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = it, fontSize = 20.sp)
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostCard(postModel: ProfilePostModel, profileViewModel: ProfileViewModel) {
    Card (
        border = BorderStroke(2.dp, Verde5),
        modifier = Modifier
            .height(260.dp)
            .background(Azul2),
        onClick = {
            profileViewModel.onCardClicked(postModel)
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
                    Column(modifier = Modifier.fillMaxWidth(0.6f)) {
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
                    .align(Alignment.CenterHorizontally)
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
                        alignment = Alignment.Center
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