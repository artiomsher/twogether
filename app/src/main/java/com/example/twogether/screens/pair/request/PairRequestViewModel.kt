package com.example.twogether.screens.pair.request

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twogether.data.DataOrException
import com.example.twogether.model.PairRequest
import com.example.twogether.repository.PairRequestFirestoreRepository
import com.example.twogether.utils.PairRequestType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PairRequestViewModel @Inject constructor(
    private val pairRequestFirestoreRepository: PairRequestFirestoreRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val incomingPairRequests: MutableState<DataOrException<List<PairRequest>, Boolean, Exception>> =
        mutableStateOf(
            DataOrException(listOf(), false, Exception(""))
        )

    val outgoingPairRequests: MutableState<DataOrException<List<PairRequest>, Boolean, Exception>> =
        mutableStateOf(
            DataOrException(listOf(), false, Exception(""))
        )

    val uniqueCode: String = savedStateHandle.get<String>("uniqueCode").orEmpty()

    init {
        getPairRequests()
    }

    private fun getPairRequests() {
        viewModelScope.launch {
            incomingPairRequests.value.loading = true
            incomingPairRequests.value.data = pairRequestFirestoreRepository.getPairRequests(
                userUniqueCode = uniqueCode,
                requestType = PairRequestType.INCOMING
            ).data

            if (!incomingPairRequests.value.data.isNullOrEmpty()) {
                incomingPairRequests.value.loading = false
            }

            outgoingPairRequests.value.loading = true
            outgoingPairRequests.value.data = pairRequestFirestoreRepository.getPairRequests(
                userUniqueCode = uniqueCode,
                requestType = PairRequestType.OUTGOING
            ).data

            if (!outgoingPairRequests.value.data.isNullOrEmpty()) {
                outgoingPairRequests.value.loading = false
            }
        }
    }
}