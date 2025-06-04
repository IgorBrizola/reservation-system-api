package com.system.reservation.adpters.repository.jpa

import com.system.reservation.adpters.repository.model.TablesEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TablesJpaRepository : JpaRepository<TablesEntity, Int> {
}