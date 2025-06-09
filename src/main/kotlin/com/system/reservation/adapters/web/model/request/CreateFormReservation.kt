package com.system.reservation.adapters.web.model.request

import java.time.Instant

data class CreateFormReservation(
    val userId: Int,
    val tableId: Int,
    val dateReservation: Instant,
    val capacity: Int,
)
