package com.system.reservation.adapters.repository

import com.system.reservation.adapters.repository.jpa.TablesJpaRepository
import com.system.reservation.adapters.repository.model.TablesEntity
import com.system.reservation.adapters.web.model.enumerated.StatusTable
import com.system.reservation.adapters.web.model.response.TablesResponse
import com.system.reservation.core.domain.exceptions.BusinessException
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
                idStatus = table.status,
            )

        return tablesJpaRepository.save(tableEntity)
    }

    override fun existsTablesByName(tableName: String): Boolean = tablesJpaRepository.existsTablesEntityByName(tableName)

    override fun findAllTables(statusTableIds: List<Int>?): List<TablesEntity> =
        run {
            if (statusTableIds.isNullOrEmpty()) {
                tablesJpaRepository.findAll()
            } else {
                tablesJpaRepository.findTablesByIdStatusIn(
                    statusTableIds,
                )
            }
        }

    override fun findTablesById(tableId: Int): Tables {
        val tableEntity =
            tablesJpaRepository.findById(tableId).orElseThrow {
                BusinessException("Table not found! - id $tableId")
            }

        return Tables(
            id = tableEntity.id,
            name = tableEntity.name,
            capacity = tableEntity.capacity,
            status = tableEntity.idStatus,
        )
    }

    override fun updateTable(table: Tables): TablesResponse {
        val tableEntityUpdated =
            TablesEntity(
                id = table.id,
                name = table.name,
                capacity = table.capacity,
                idStatus = table.status,
            )

        val saveTableUpdated = tablesJpaRepository.save(tableEntityUpdated)

        return TablesResponse(
            id = saveTableUpdated.id,
            name = saveTableUpdated.name,
            capacity = saveTableUpdated.capacity,
            status = StatusTable.getById(saveTableUpdated.idStatus),
        )
    }

    override fun deleteTableById(tableId: Int) = tablesJpaRepository.deleteById(tableId)
}
