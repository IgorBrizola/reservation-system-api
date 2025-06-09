package com.system.reservation.adapters.repository.jpa

import com.system.reservation.adapters.repository.model.ReservationsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationsJpaRepository : JpaRepository<ReservationsEntity, Int> {
    fun existsReservationsEntityByUserIdAndIdStatus(
        userId: Int,
        idStatus: Int = 1,
    ): Boolean

    fun findAllReservationsEntityByIdStatus(idStatus: Int = 1): List<ReservationsEntity>
}
