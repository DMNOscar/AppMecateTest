package com.flexicode.testmecate.Models

import android.webkit.WebResourceResponse
import com.flexicode.testmecate.Entity.UserEntity

data class UserState (
    val showAlert: Boolean = false,
    val response: UserData = UserData(),
    val action: String = "",
    val titleAlert: String = "",
    val descriptionAlert: String = "",
    val history: List<UserEntity> = listOf(),
)