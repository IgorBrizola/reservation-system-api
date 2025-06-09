package com.system.reservation.adapters.web

import com.system.reservation.adapters.web.doc.ReservationOpenAPI
import com.system.reservation.adapters.web.model.request.CreateFormReservation
import com.system.reservation.core.domain.model.reservations.Reservations
import com.system.reservation.core.ports.input.ReservationsInputPort
import com.system.reservation.core.ports.input.TablesInputPort
import com.system.reservation.core.ports.input.UsersInputPort
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/reservation")
class ReservationsController(
    private val reservationsInputPort: ReservationsInputPort,
    private val usersInputPort: UsersInputPort,
    private val tablesInputPort: TablesInputPort,
) : ReservationOpenAPI {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    override fun createNewReservation(
        @RequestBody createFormReservation: CreateFormReservation,
    ) {
        val user = usersInputPort.findUserById(createFormReservation.userId)
        val table = tablesInputPort.findTableById(createFormReservation.tableId)

        val reservation =
            Reservations(
                user = user,
                table = table,
                dateReservation = createFormReservation.dateReservation,
            )

        reservationsInputPort.save(reservation)
    }
}
