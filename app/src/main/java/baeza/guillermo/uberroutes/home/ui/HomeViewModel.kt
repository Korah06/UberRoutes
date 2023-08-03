package baeza.guillermo.uberroutes.home.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import baeza.guillermo.uberroutes.home.domain.entity.CommentModel
import baeza.guillermo.uberroutes.home.domain.entity.PostModel
import baeza.guillermo.uberroutes.home.domain.entity.UserModel
import baeza.guillermo.uberroutes.home.domain.usecase.GetCommentsUseCase
import baeza.guillermo.uberroutes.home.domain.usecase.GetUserUseCase
import baeza.guillermo.uberroutes.home.domain.usecase.HomeUseCase
import baeza.guillermo.uberroutes.home.domain.usecase.PostCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val postCommentUseCase: PostCommentUseCase,
    private val getUserUseCase: GetUserUseCase
): ViewModel() {
    private var routes: List<PostModel> = listOf()
    private var comments: List<CommentModel> = listOf()
    private var currentUser: UserModel = UserModel("", "", "", "", "", "", "", "", "", false)
    private lateinit var singleModel: PostModel

    private val _view = MutableLiveData<Int>()
    val view: LiveData<Int> = _view

    private val _commentText = MutableLiveData<String>()
    val commentText: LiveData<String> = _commentText

    init {
        viewModelScope.launch {
            currentUser = getUserUseCase()
        }
    }

    fun updateRoutes() {
        _view.value = 3
        viewModelScope.launch {
            routes = homeUseCase()
            Log.i("DAM", "Se han llamado las rutas")
            _view.value = 1
        }
    }

    fun getPosts(): List<PostModel> {
        return routes
    }

    fun onCardClicked(singleModel: PostModel) {
        _view.value = 3
        viewModelScope.launch {
            comments = getCommentsUseCase(singleModel._id)
            Log.i("DAM", "Se han recogido los comentarios")
            _view.value = 2
        }
        this.singleModel = singleModel
    }

    fun getSingleModel(): PostModel {
        return singleModel
    }

    fun onBackButtonClicked() {
        _view.value = 1
    }

    @JvmName("getComments1")
    fun getComments(): List<CommentModel> {
        return comments
    }

    fun onCommentChanged(commentText: String) {
        _commentText.value = commentText
    }

    fun onPostCommentButtonPressed() {
        if(_commentText.value!!.isNotEmpty()) {
            _view.value = 3
            viewModelScope.launch {
                val result = _commentText.value?.let {
                    postCommentUseCase(
                        it,
                        currentUser._id,
                        singleModel._id
                    )
                }
                Log.i("DAM", "El post del nuevo comentario es $result")
                comments = getCommentsUseCase(singleModel._id)
                Log.i("DAM", "Se han actualizado los comentarios")
                _view.value = 2
            }
            _commentText.value = ""
        }
    }
}