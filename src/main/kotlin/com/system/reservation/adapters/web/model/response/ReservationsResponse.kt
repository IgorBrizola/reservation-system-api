package com.system.reservation.adapters.web.model.response

import com.system.reservation.adapters.web.model.enumerated.StatusReservation

data class ReservationsResponse(
    val user: UserResponse,
    val table: TablesResponse,
    val date: String,
    val status: StatusReservation,
)
