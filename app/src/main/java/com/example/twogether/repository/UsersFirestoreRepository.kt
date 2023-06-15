package com.example.twogether.repository

import com.example.twogether.data.DataOrException
import com.example.twogether.model.User
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersFirestoreRepository @Inject constructor(
    private val queryUser: Query
) {
    suspend fun getUserByField(
        field: String,
        value: String
    ): DataOrException<User, Boolean, Exception> {
        val dataOrException = DataOrException<User, Boolean, Exception>()

        try {
            dataOrException.loading = true
            dataOrException.data =
                queryUser.whereEqualTo(field, value).limit(1).get().await().documents[0].toObject(
                    User::class.java
                )

            dataOrException.data?.let {
                dataOrException.loading = false
            }
        } catch (exception: FirebaseFirestoreException) {
            dataOrException.e = exception
        }

        return dataOrException
    }
}