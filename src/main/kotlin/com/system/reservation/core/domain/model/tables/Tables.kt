package com.system.reservation.core.domain.model.tables

data class Tables(
    val id: Int? = null,
    val name: String,
    val capacity: Int,
    val status: Int = 1,
)
