package com.example.twogether.model

import com.example.twogether.utils.PairStatus
import com.google.firebase.Timestamp
import java.util.Date

data class Pair(
    val firstUserId: String = "",
    val secondUserId: String = "",
    val status: PairStatus = PairStatus.INACTIVE,
    val createdAt: Date = Timestamp.now().toDate()
) {
    fun toMutableMap(): MutableMap<String, String?> {
        return mutableMapOf(
            "first_user_id" to this.firstUserId,
            "second_user_id" to this.secondUserId,
            "status" to this.status.name,
            "created_at" to this.createdAt.toString()
        )
    }
}