package com.system.reservation.adapters.web.doc

import com.system.reservation.adapters.web.model.request.CreateFormReservation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "Reservation Controller", description = "Manage Reservations")
interface ReservationOpenAPI {
    fun createNewReservation(createFormReservation: CreateFormReservation)
}
