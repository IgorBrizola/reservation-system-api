package com.system.reservation.core.domain.model.tables

import com.system.reservation.core.domain.model.enumerated.StatusTable

data class Tables(
    val id: Int? = null,
    val name: String,
    val capacity: Int,
    val status: Int = StatusTable.AVAILABLE.statusId,
)
