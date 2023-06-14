package com.example.twogether.model

data class User(
     val id: String?,
     val userId: String,
     val displayName: String,
     val uniqueCode: String,
     val avatarUrl: String
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "user_id" to this.userId,
            "display_name" to this.displayName,
            "unique_code" to this.uniqueCode,
            "avatar_url" to this.avatarUrl
        )
    }
}
