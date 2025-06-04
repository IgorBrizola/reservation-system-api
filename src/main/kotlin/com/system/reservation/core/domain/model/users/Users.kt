package com.system.reservation.core.domain.model.users

data class Users(
    val id: Int? = null,
    val name: String,
    val email: String,
    val password: String
)
