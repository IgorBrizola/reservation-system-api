package com.system.reservation.core.domain.model.tables.request

data class UpdateTable(
    val name: String? = null,
    val capacity: Int? = null,
    val status: Int? = null,
)
