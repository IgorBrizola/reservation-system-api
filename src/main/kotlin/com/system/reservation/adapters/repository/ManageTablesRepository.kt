package com.system.reservation.adapters.repository

import com.system.reservation.adapters.repository.jpa.TablesJpaRepository
import com.system.reservation.adapters.repository.model.TablesEntity
import com.system.reservation.core.domain.model.tables.Tables
import com.system.reservation.core.ports.output.TablesOutPutPort
import org.springframework.stereotype.Repository

@Repository
class ManageTablesRepository(
    private val tablesJpaRepository: TablesJpaRepository,
) : TablesOutPutPort {
    override fun save(table: Tables): TablesEntity {
        val tableEntity =
            TablesEntity(
                name = table.name,
                capacity = table.capacity,
                status = table.status,
            )

        return tablesJpaRepository.save(tableEntity)
    }

    override fun existsTablesByName(tableName: String): Boolean = tablesJpaRepository.existsTablesEntityByName(tableName)

    override fun findAllTables(): List<TablesEntity> = tablesJpaRepository.findAll()
}
