package com.example.twogether.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twogether.data.DataOrException
import com.example.twogether.model.Pair
import com.example.twogether.model.User
import com.example.twogether.repository.PairsFirestoreRepository
import com.example.twogether.repository.UsersFirestoreRepository
import com.example.twogether.utils.PairStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val usersRepository: UsersFirestoreRepository,
    private val pairsRepository: PairsFirestoreRepository
) : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    val pairPartner: MutableState<DataOrException<User, Boolean, Exception>> = mutableStateOf(
        DataOrException(null, false, Exception(""))
    )

    val pair: MutableState<DataOrException<Pair, Boolean, Exception>> = mutableStateOf(
        DataOrException(null, false, Exception(""))
    )

    val currentUser: MutableState<DataOrException<User, Boolean, Exception>> = mutableStateOf(
        DataOrException(null, false, Exception(""))
    )

    var uniqueCode: MutableState<String> = mutableStateOf("")

    init {
        getCurrentUser()
//        getPartner()
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            val currentUserId = auth.currentUser?.uid
            currentUser.value.loading = true
            currentUser.value.data =
                usersRepository.getUserByField(field = "user_id", value = currentUserId!!).data
            currentUser.value.data?.let {
                uniqueCode.value = currentUser.value.data!!.uniqueCode.toString()
                Log.d("UNIQUECODE", "VIEWMODEL: ${uniqueCode.value}")
                Log.d("CURRENT USER", "VIEWMODEL: $it")
                currentUser.value.loading = false
            }
        }
    }


    private fun getPartner() {

        viewModelScope.launch {
            val currentUserId = auth.currentUser?.uid
            pair.value.loading = true
            pair.value.data = pairsRepository.getPairByUserId(currentUserId!!).data
            pair.value.data?.let { pair ->
                if (pair.status == PairStatus.ACTIVE) {
                    var partnerId = pair.firstUserId
                    if (partnerId == currentUserId) partnerId = pair.secondUserId
                    pairPartner.value.data =
                        usersRepository.getUserByField(field = "user_id", value = partnerId).data
                }
            }
            pair.value.loading = false
        }
    }
}
