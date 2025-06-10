package com.system.reservation.core.useCases

import com.system.reservation.core.domain.model.reservations.Reservations
import com.system.reservation.core.ports.input.ReservationsInputPort
import com.system.reservation.core.ports.output.ReservationsOutPutPort
import org.springframework.stereotype.Service

@Service
class ReservationsUseCase(
    private val reservationsOutPutPort: ReservationsOutPutPort,
) : ReservationsInputPort {
    override fun save(reservations: Reservations) = reservationsOutPutPort.save(reservations)

    override fun findAllReservation(): List<Reservations> = reservationsOutPutPort.findAllReservations()

    override fun verifyIfUserAlreadyReservation(userId: Int): Boolean = reservationsOutPutPort.existsReservationByUserId(userId)

    override fun findReservationById(reservationId: Int): Reservations = reservationsOutPutPort.findReservationById(reservationId)

    override fun cancelReservation(reservations: Reservations) {
        val updateReservation =
            reservations.copy(
                id = reservations.id,
                status = 2,
            )

        reservationsOutPutPort.updateReservation(updateReservation)
    }
}
