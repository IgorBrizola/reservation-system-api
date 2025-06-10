package com.system.reservation.adapters.web.model.response

import com.system.reservation.core.domain.model.enumerated.StatusTable

data class TablesResponse(
    val id: Int?,
    val name: String,
    val capacity: Int,
    val status: StatusTable,
)
