package com.leusoft.taskcore.model

data class FireUser(val name: String,
                    val bio: String,
                    val profilePicturePath: String?,
                    val registrationTokens: MutableList<String>) {
    constructor(): this("", "", null, mutableListOf())
}