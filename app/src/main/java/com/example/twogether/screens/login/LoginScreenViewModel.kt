package com.example.twogether.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twogether.model.User
import com.example.twogether.repository.UsersFirestoreRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val usersRepository: UsersFirestoreRepository,
) : ViewModel() {
    companion object {
        private const val UNIQUE_CODE_LENGTH = 5
    }
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun createUserWithEmailAndPassword(email: String, password: String, home: () -> Unit) {
        if (!_loading.value!!) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val displayName = task.result.user?.email?.split('@')?.get(0)
                    createUser(displayName)
                    home()
                } else {
                    Log.d("Reader", "createUserWithEmailAndPassword: ${task.result.toString()}")
                }
                _loading.value = false
            }
        }
    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val alphabet : List<Char> = ('A'..'Z') + ('0'..'9')
        val uniqueCode = List(UNIQUE_CODE_LENGTH) { alphabet.random() }.joinToString("")

        val userMutableMap = User(
            userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            id = null,
            uniqueCode = uniqueCode
        ).toMutableMap()
        usersRepository.createUser(userMutableMap, uniqueCode)
    }

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        home()
                    } else {
                        Log.d("Twogether", "singInWithEmailAndPassword: ${task.result.toString()}")
                    }
                }
            } catch (ex: Exception) {
                Log.d("Reader", "createUserWithEmailAndPassword: ${ex.message}")
            }
        }
    }
}