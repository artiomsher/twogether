package com.example.twogether.model

import com.example.twogether.utils.PairRequestStatus
import com.google.firebase.Timestamp
import java.util.Date

data class PairRequest(
    val requestingUser: String = "",
    val status: PairRequestStatus = PairRequestStatus.INACTIVE,
    val createdAt: Date = Timestamp.now().toDate()
) {
    fun toMutableMap(): MutableMap<String, String?> {
        return mutableMapOf(
            "first_user_id" to this.requestingUser,
            "status" to this.status.name,
            "created_at" to this.createdAt.toString()
        )
    }
}