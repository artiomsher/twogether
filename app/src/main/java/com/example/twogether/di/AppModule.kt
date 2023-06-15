package com.example.twogether.di

import com.example.twogether.repository.PairsFirestoreRepository
import com.example.twogether.repository.UsersFirestoreRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideUsersFirebaseRepository() =
        UsersFirestoreRepository(
            queryUser = FirebaseFirestore.getInstance().collection("users")
        )

    @Singleton
    @Provides
    fun providePairsFirebaseRepository() =
        PairsFirestoreRepository(
            queryPair = FirebaseFirestore.getInstance().collection("pairs")
        )

}