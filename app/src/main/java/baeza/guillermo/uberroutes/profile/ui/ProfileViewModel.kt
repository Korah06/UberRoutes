package baeza.guillermo.uberroutes.profile.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import baeza.guillermo.uberroutes.profile.data.network.response.PostCommentResponse
import baeza.guillermo.uberroutes.profile.domain.entity.CommentModel
import baeza.guillermo.uberroutes.profile.domain.entity.ProfilePostModel
import baeza.guillermo.uberroutes.profile.domain.entity.UserModel
import baeza.guillermo.uberroutes.profile.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val getProfileRoutesUseCase: GetProfileRoutesUseCase,
    private val getFollowersUseCase: GetFollowersUseCase,
    private val getFollowingUseCase: GetFollowingUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val postCommentUseCase: PostCommentUseCase
): ViewModel() {
    var routes: List<ProfilePostModel> = listOf()
    var user: UserModel = UserModel("","","","","","","","","",false)
    var followers: List<String> = listOf()
    var following: List<String> = listOf()
    private var comments: List<CommentModel> = listOf()
    private lateinit var singleModel: ProfilePostModel

    private val _view = MutableLiveData<Int>()
    val view: LiveData<Int> = _view

    private val _commentText = MutableLiveData<String>()
    val commentText: LiveData<String> = _commentText

    fun updateData() {
        _view.value = 3
        viewModelScope.launch {
            user = profileUseCase()
            Log.i("DAM", "Se ha recuperado el usuario ${user._id}")
            routes = getProfileRoutesUseCase(user._id)
            Log.i("DAM", "Se han recogido las rutas")
            followers = getFollowersUseCase(user._id)
            Log.i("DAM", "Se han recogido los seguidores")
            following = getFollowingUseCase(user._id)
            Log.i("DAM", "Se han recogido los seguidos")
            _view.value = 1
        }
    }

    fun getPosts(): List<ProfilePostModel> {
        return routes
    }

    fun getProfileUser(): UserModel {
        return user
    }

    @JvmName("getFollowers1")
    fun getFollowers(): List<String> {
        return followers
    }

    @JvmName("getFollowing1")
    fun getFollowing(): List<String> {
        return following
    }

    fun onCardClicked(singleModel: ProfilePostModel) {
        _view.value = 3
        viewModelScope.launch {
            comments = getCommentsUseCase(singleModel._id)
            Log.i("DAM", "Se han recogido los comentarios")
            _view.value = 2
        }
        this.singleModel = singleModel
    }

    fun getSingleModel(): ProfilePostModel {
        return singleModel
    }

    fun onBackButtonClicked() {
        _view.value = 1
    }

    fun getComments(): List<CommentModel> {
        return comments
    }

    fun onCommentChanged(commentText: String) {
        _commentText.value = commentText
    }

    fun onPostCommentButtonPressed() {
        if(_commentText.value!!.isNotEmpty()){
            _view.value = 3
            viewModelScope.launch {
                val result = _commentText.value?.let { postCommentUseCase(it, user._id, singleModel._id) }
                Log.i("DAM", "El post del nuevo comentario es $result")
                comments = getCommentsUseCase(singleModel._id)
                Log.i("DAM", "Se han actualizado los comentarios")
                _view.value = 2
            }
            _commentText.value = ""
        }
    }
}