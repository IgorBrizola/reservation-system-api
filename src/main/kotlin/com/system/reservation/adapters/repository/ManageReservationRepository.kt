package com.system.reservation.adapters.repository

import com.system.reservation.adapters.repository.jpa.ReservationsJpaRepository
import com.system.reservation.adapters.repository.model.ReservationsEntity
import com.system.reservation.adapters.repository.model.TablesEntity
import com.system.reservation.adapters.repository.model.UsersEntity
import com.system.reservation.core.domain.exceptions.BusinessException
import com.system.reservation.core.domain.model.reservations.Reservations
import com.system.reservation.core.domain.model.tables.Tables
import com.system.reservation.core.domain.model.users.Users
import com.system.reservation.core.ports.output.ReservationsOutPutPort
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

        reservationsJpaRepository.save(reservationsEntity)
    }

    override fun updateReservation(reservations: Reservations) {
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
                id = reservations.id,
                user = userEntity,
                table = tableEntity,
                dateReservation = reservations.dateReservation,
                idStatus = reservations.status,
            )

        reservationsJpaRepository.save(reservationsEntity)
    }

    override fun findAllReservations(): List<Reservations> {
        val reservationsEntity = reservationsJpaRepository.findAllReservationsEntityByIdStatus()

        return reservationsEntity.map {
            Reservations(
                user =
                    Users(
                        id = it.user.id,
                        name = it.user.name,
                        email = it.user.email,
                        password = it.user.password,
                    ),
                table =
                    Tables(
                        id = it.table.id,
                        name = it.table.name,
                        capacity = it.table.capacity,
                        status = it.table.idStatus,
                    ),
                dateReservation = it.dateReservation,
                status = it.idStatus,
            )
        }
    }

    override fun existsReservationByUserId(userId: Int): Boolean =
        reservationsJpaRepository.existsReservationsEntityByUserIdAndIdStatus(userId)

    override fun findReservationById(reservationId: Int): Reservations {
        val reservationEntity =
            reservationsJpaRepository.findById(reservationId).orElseThrow {
                throw BusinessException("Reservation not found with id - $reservationId")
            }

        val user =
            Users(
                id = reservationEntity.user.id,
                name = reservationEntity.user.name,
                email = reservationEntity.user.email,
                password = reservationEntity.user.password,
            )

        val table =
            Tables(
                id = reservationEntity.table.id,
                name = reservationEntity.table.name,
                capacity = reservationEntity.table.capacity,
                status = reservationEntity.table.idStatus,
            )

        return Reservations(
            id = reservationEntity.id,
            user = user,
            table = table,
            dateReservation = reservationEntity.dateReservation,
            status = reservationEntity.idStatus,
        )
    }
}
