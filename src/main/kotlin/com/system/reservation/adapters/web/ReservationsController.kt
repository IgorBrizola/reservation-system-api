package com.system.reservation.adapters.web

import com.system.reservation.adapters.web.doc.ReservationOpenAPI
import com.system.reservation.adapters.web.model.enumerated.StatusTable
import com.system.reservation.adapters.web.model.request.CreateFormReservation
import com.system.reservation.adapters.web.model.request.UpdateFormTable
import com.system.reservation.adapters.web.model.response.ReservationsResponse
import com.system.reservation.core.domain.exceptions.BusinessException
import com.system.reservation.core.domain.model.reservations.Reservations
import com.system.reservation.core.ports.input.ReservationsInputPort
import com.system.reservation.core.ports.input.TablesInputPort
import com.system.reservation.core.ports.input.UsersInputPort
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
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
        user.id?.let {
            verifyIfUserAlreadyReservation(it)
        }

        val table = tablesInputPort.findTableById(createFormReservation.tableId)
        validateIfTableIsAvailable(table.status)
        verifyIfTableHasCapacity(createFormReservation.capacity, table.capacity)

        tablesInputPort.updateTablesById(tableId = table.id!!, UpdateFormTable(status = 2))

        val reservation =
            Reservations(
                user = user,
                table = table,
                dateReservation = createFormReservation.dateReservation,
            )

        reservationsInputPort.save(reservation)
    }

    private fun validateIfTableIsAvailable(tableStatusId: Int?) =
        run {
            if (tableStatusId != StatusTable.AVAILABLE.statusId) throw BusinessException("Table is not available!")
        }

    private fun verifyIfUserAlreadyReservation(userId: Int) =
        run {
            if (reservationsInputPort.verifyIfUserAlreadyReservation(userId)) throw BusinessException("User already a reservation active!")
        }

    private fun verifyIfTableHasCapacity(
        capacity: Int,
        capacityTable: Int,
    ) = run {
        if (capacity > capacityTable) throw BusinessException("The table that you pick, has not capacity!")
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    override fun findAllReservations(): List<ReservationsResponse> = reservationsInputPort.findAllReservation()
}
