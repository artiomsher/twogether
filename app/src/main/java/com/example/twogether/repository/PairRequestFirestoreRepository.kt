package com.example.twogether.repository

import androidx.compose.runtime.mutableStateOf
import com.example.twogether.data.DataOrException
import com.example.twogether.model.PairRequest
import com.example.twogether.model.User
import com.example.twogether.utils.PairRequestStatus
import com.example.twogether.utils.PairRequestType
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class PairRequestFirestoreRepository @Inject constructor(
    private val pairRequestCollectionReference: CollectionReference
) {

    fun postPairRequest(
        requestedUserUniqueCode: String,
        senderUniqueCode: String
    ) {
        val pairRequest = PairRequest(
            requestingUser = "/users/$senderUniqueCode",
            status = PairRequestStatus.WAITING_FOR_REPLY,

            ).toMutableMap()
        pairRequestCollectionReference.document(requestedUserUniqueCode)
            .collection(PairRequestType.INCOMING.name)
            .add(pairRequest)
    }

    suspend fun getPairRequests(
        userUniqueCode: String,
        requestType: PairRequestType
    ): DataOrException<List<PairRequest>, Boolean, Exception> {
        val dataOrException = DataOrException<List<PairRequest>, Boolean, Exception>()

        try {
            dataOrException.loading = true
            dataOrException.data =
                pairRequestCollectionReference.document(userUniqueCode)
                    .collection(requestType.name).get()
                    .await().map { document ->
                    document.toObject(PairRequest::class.java)
                }
            dataOrException.data?.let {
                dataOrException.loading = false
            }
        } catch (exception: FirebaseFirestoreException) {
            dataOrException.e = exception
        }

        return dataOrException
    }
}