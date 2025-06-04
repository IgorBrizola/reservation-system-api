package com.system.reservation.core.domain.model.users.request

data class NewUser(
    val name: String,
    val email: String,
    val password: String
)
