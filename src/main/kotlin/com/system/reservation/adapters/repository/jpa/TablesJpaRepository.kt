package com.system.reservation.adapters.repository.jpa

import com.system.reservation.adapters.repository.model.TablesEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TablesJpaRepository : JpaRepository<TablesEntity, Int> {
    fun existsTablesEntityByName(tableName: String): Boolean

    fun findTablesByIdStatusIn(idStatus: List<Int>?): List<TablesEntity>
}
