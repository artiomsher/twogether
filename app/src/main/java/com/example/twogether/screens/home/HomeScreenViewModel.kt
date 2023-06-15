package com.example.twogether.screens.home

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
        DataOrException(null, true, Exception(""))
    )

    val pair: MutableState<DataOrException<Pair, Boolean, Exception>> = mutableStateOf(
        DataOrException(null, true, Exception(""))
    )

    init {
        getPartner()
    }

    private fun getPartner() {

        viewModelScope.launch {
            val ownerId = auth.currentUser?.uid
            pair.value.loading = true
            pair.value.data = pairsRepository.getPairByUserId(ownerId!!).data
            pair.value.data?.let { pair ->
                if (pair.status == PairStatus.ACTIVE) {
                    var partnerId = pair.firstUserId
                    if (partnerId == ownerId) partnerId = pair.secondUserId
                    pairPartner.value.data =
                        usersRepository.getUserByField(field = "user_id", value = partnerId).data
                }
            }
            pair.value.loading = false
        }
    }
}
