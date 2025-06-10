package com.system.reservation.adapters.web

import com.system.reservation.adapters.web.doc.ReservationOpenAPI
import com.system.reservation.adapters.web.model.request.CreateFormReservation
import com.system.reservation.adapters.web.model.response.ReservationsResponse
import com.system.reservation.adapters.web.model.response.TablesResponse
import com.system.reservation.adapters.web.model.response.UserResponse
import com.system.reservation.core.domain.exceptions.BusinessException
import com.system.reservation.core.domain.model.enumerated.StatusReservation
import com.system.reservation.core.domain.model.enumerated.StatusTable
import com.system.reservation.core.domain.model.reservations.Reservations
import com.system.reservation.core.domain.model.tables.request.UpdateTable
import com.system.reservation.core.ports.input.ReservationsInputPort
import com.system.reservation.core.ports.input.TablesInputPort
import com.system.reservation.core.ports.input.UsersInputPort
import com.system.reservation.util.AppUtil
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
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
    @Transactional
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

        tablesInputPort.updateTablesById(tableId = table.id!!, UpdateTable(status = 2))

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
        if (capacity > capacityTable) throw BusinessException("The table that you picked, has only capacity for $capacityTable people!")
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    override fun findAllReservations(): List<ReservationsResponse> =
        reservationsInputPort.findAllReservation().map {
            ReservationsResponse(
                user =
                    UserResponse(
                        id = it.user.id,
                        name = it.user.name,
                        email = it.user.email,
                    ),
                table =
                    TablesResponse(
                        id = it.table.id,
                        name = it.table.name,
                        capacity = it.table.capacity,
                        status = StatusTable.getById(it.table.status),
                    ),
                date = AppUtil.getDateFormatBR(it.dateReservation),
                status = StatusReservation.getById(it.status),
            )
        }

    @PatchMapping("{reservationId}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    override fun cancelReservationById(
        @PathVariable reservationId: Int,
    ) = run {
        val reservation = reservationsInputPort.findReservationById(reservationId)
        verifyIfStatusReservationIsActive(reservation.status)

        reservationsInputPort.cancelReservation(reservation).also {
            tablesInputPort.updateTablesById(
                reservation.table.id!!,
                updateTable = UpdateTable(status = StatusTable.AVAILABLE.statusId),
            )
        }
    }

    private fun verifyIfStatusReservationIsActive(status: Int) =
        run {
            if (status != StatusReservation.ACTIVE.statusId) throw BusinessException("Reservation is not active!")
        }
}
