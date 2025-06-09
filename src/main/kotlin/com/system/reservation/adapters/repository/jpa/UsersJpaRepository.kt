package com.system.reservation.adapters.repository.jpa

import com.system.reservation.adapters.repository.model.UsersEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UsersJpaRepository : JpaRepository<UsersEntity, Int> {
}