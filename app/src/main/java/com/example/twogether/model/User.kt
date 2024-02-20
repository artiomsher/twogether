package com.example.twogether.model

import com.google.firebase.firestore.PropertyName

data class User(

     var id: String? = "",
     @get:PropertyName("user_id")
     @set:PropertyName("user_id")
     var userId: String? = "",
     @get:PropertyName("display_name")
     @set:PropertyName("display_name")
     var displayName: String? = "",
     @get:PropertyName("unique_code")
     @set:PropertyName("unique_code")
     var uniqueCode: String? = "",
     @get:PropertyName("avatar_url")
     @set:PropertyName("avatar_url")
     var avatarUrl: String? = ""
) {
    fun toMutableMap(): MutableMap<String, String?> {
        return mutableMapOf(
            "user_id" to this.userId,
            "display_name" to this.displayName,
            "unique_code" to this.uniqueCode,
            "avatar_url" to this.avatarUrl
        )
    }
}
