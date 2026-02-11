package com.transportcompany.api.dto

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String? = null,
    val user: UserInfo? = null
)

data class UserInfo(
    val id: Long,
    val username: String,
    val name: String,
    val role: String
)

