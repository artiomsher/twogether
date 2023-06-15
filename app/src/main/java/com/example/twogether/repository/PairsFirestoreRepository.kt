package com.example.twogether.repository

import com.example.twogether.data.DataOrException
import com.example.twogether.model.Pair
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PairsFirestoreRepository @Inject constructor(
    private val queryPair: Query
) {
    suspend fun getPairByUserId(
        userId: String
    ): DataOrException<Pair, Boolean, Exception> {
        val dataOrException = DataOrException<Pair, Boolean, Exception>()

        try {
            dataOrException.loading = true
            dataOrException.data =
                queryPair.where(Filter.or(
                    Filter.equalTo("first_user_id", userId),
                    Filter.equalTo("second_user_id", userId)
                )).limit(1).get()
                    .await().documents[0].toObject(Pair::class.java)

            dataOrException.data?.let {
                dataOrException.loading = false
            }
        } catch (exception: FirebaseFirestoreException) {
            dataOrException.e = exception
        }

        return dataOrException
    }
}