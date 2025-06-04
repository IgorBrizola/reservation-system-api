package com.system.reservation.core.domain.model.tables

import com.system.reservation.adpters.repository.model.StatusTable

data class Tables(
    val id: Int,
    val name: String,
    val capacity: Int,
    val status: StatusTable
)