package com.system.reservation.adpters.repository.jpa

import com.system.reservation.adpters.repository.model.UsersEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UsersJpaRepository : JpaRepository<UsersEntity, Int> {
}