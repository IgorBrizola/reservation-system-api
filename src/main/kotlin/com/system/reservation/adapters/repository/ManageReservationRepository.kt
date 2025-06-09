package com.system.reservation.adapters.repository

import com.system.reservation.adapters.repository.jpa.ReservationsJpaRepository
import com.system.reservation.adapters.repository.model.ReservationsEntity
import com.system.reservation.adapters.repository.model.TablesEntity
import com.system.reservation.adapters.repository.model.UsersEntity
import com.system.reservation.adapters.web.model.enumerated.StatusReservation
import com.system.reservation.adapters.web.model.enumerated.StatusTable
import com.system.reservation.adapters.web.model.response.ReservationsResponse
import com.system.reservation.adapters.web.model.response.TablesResponse
import com.system.reservation.adapters.web.model.response.UserResponse
import com.system.reservation.core.domain.model.reservations.Reservations
import com.system.reservation.core.ports.output.ReservationsOutPutPort
import com.system.reservation.util.AppUtil
import com.system.reservation.util.AppUtil.getDateFormatBR
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

    override fun findAllReservations(): List<ReservationsResponse> {
        val reservationsEntity = reservationsJpaRepository.findAllReservationsEntityByIdStatus()

        return reservationsEntity.map {
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
                        status = StatusTable.getById(it.table.idStatus),
                    ),
                date = getDateFormatBR(it.dateReservation),
                status = StatusReservation.getById(it.idStatus),
            )
        }
    }

    override fun existsReservationByUserId(userId: Int): Boolean =
        reservationsJpaRepository.existsReservationsEntityByUserIdAndIdStatus(userId)
}
