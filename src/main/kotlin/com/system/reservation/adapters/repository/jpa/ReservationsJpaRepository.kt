package com.system.reservation.adapters.repository.jpa

import com.system.reservation.adapters.repository.model.ReservationsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationsJpaRepository : JpaRepository<ReservationsEntity, Int> {
}