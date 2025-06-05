package com.system.reservation.adapters.web.model.response

import com.system.reservation.adapters.repository.model.StatusTable

data class TablesResponse(
    val id: Int?,
    val name: String,
    val capacity: Int,
    val status: StatusTable,
)
