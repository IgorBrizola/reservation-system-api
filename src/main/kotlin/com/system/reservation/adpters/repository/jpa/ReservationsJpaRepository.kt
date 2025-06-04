package com.system.reservation.adpters.repository.jpa

import com.system.reservation.adpters.repository.model.ReservationsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationsJpaRepository : JpaRepository<ReservationsEntity, Int> {
}