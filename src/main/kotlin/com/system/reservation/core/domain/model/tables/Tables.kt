package com.system.reservation.core.domain.model.tables

import com.system.reservation.adapters.repository.model.StatusTable

data class Tables(
    val id: Int? = null,
    val name: String,
    val capacity: Int,
    val status: StatusTable = StatusTable.AVAILABLE,
)
