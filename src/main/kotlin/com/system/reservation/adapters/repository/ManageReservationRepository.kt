package com.system.reservation.adapters.repository

import com.system.reservation.adapters.repository.jpa.ReservationsJpaRepository
import com.system.reservation.adapters.repository.model.ReservationsEntity
import com.system.reservation.adapters.repository.model.TablesEntity
import com.system.reservation.adapters.repository.model.UsersEntity
import com.system.reservation.core.domain.model.reservations.Reservations
import com.system.reservation.core.ports.output.ReservationsOutPutPort
import com.system.reservation.util.AppUtil
import org.springframework.stereotype.Repository

@Repository
class ManageReservationRepository(
    private val reservationsJpaRepository: ReservationsJpaRepository,
) : ReservationsOutPutPort {
    override fun save(reservations: Reservations) {
        val userEntity =
            UsersEntity(
                id = reservations.user.id,
                name = reservations.user.name,
                email = reservations.user.email,
                password = reservations.user.password,
            )

        val tableEntity =
            TablesEntity(
                id = reservations.table.id,
                name = reservations.table.name,
                capacity = reservations.table.capacity,
                idStatus = reservations.table.status,
            )

        val reservationsEntity =
            ReservationsEntity(
                user = userEntity,
                table = tableEntity,
                dateReservation = reservations.dateReservation,
                idStatus = reservations.status,
            )

        AppUtil.logContext("User", userEntity)
        AppUtil.logContext("Table", tableEntity)
        AppUtil.logContext("Reservation", reservationsEntity)

        reservationsJpaRepository.save(reservationsEntity)
    }
}
