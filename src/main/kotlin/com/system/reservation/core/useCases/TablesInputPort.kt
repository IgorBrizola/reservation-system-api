package com.system.reservation.core.useCases

import com.system.reservation.adapters.repository.model.TablesEntity
import com.system.reservation.adapters.web.model.enumerated.StatusTable
import com.system.reservation.adapters.web.model.request.UpdateFormTable
import com.system.reservation.adapters.web.model.response.TablesResponse
import com.system.reservation.core.domain.exceptions.BusinessException
import com.system.reservation.core.domain.model.tables.Tables
import com.system.reservation.core.ports.input.TablesInputPort
import com.system.reservation.core.ports.output.TablesOutPutPort
import org.springframework.stereotype.Service

@Service
class TablesInputPort(
    private val tablesOutPutPort: TablesOutPutPort,
) : TablesInputPort {
    override fun createNewTable(table: Tables) {
        validateNameTable(table.name)
        verifyNameUppercaseTable(table.name)

        tablesOutPutPort.save(table)
    }

    private fun validateNameTable(nameTable: String) =
        run {
            if (tablesOutPutPort.existsTablesByName(nameTable)) {
                throw BusinessException("Table already exist with is name - $nameTable")
            }
        }

    private fun verifyNameUppercaseTable(nameTable: String) =
        run {
            if (nameTable != nameTable.uppercase()) {
                throw BusinessException("Table does not follow name default, replace $nameTable to ${nameTable.uppercase()}")
            }
        }

    override fun listAllTables(statusTableIds: List<Int>?): List<TablesEntity> = tablesOutPutPort.findAllTables(statusTableIds)

    override fun findTableById(tableId: Int): Tables = tablesOutPutPort.findTablesById(tableId)

    override fun updateTablesById(
        tableId: Int,
        updateFormTable: UpdateFormTable,
    ): TablesResponse {
        val tables = findTableById(tableId)

        updateFormTable.name?.let {
            validateNameTable(it)
            verifyNameUppercaseTable(it)
        }

        updateFormTable.status?.let {
            validateStatusTable(it, tables)
        }

        val updateTable =
            tables.copy(
                id = tables.id,
                name = updateFormTable.name ?: tables.name,
                capacity = updateFormTable.capacity ?: tables.capacity,
                status = updateFormTable.status ?: tables.status,
            )

        return tablesOutPutPort.updateTable(updateTable)
    }

    private fun validateStatusTable(
        statusId: Int,
        table: Tables,
    ) = run {
        if (statusId == table.status) throw BusinessException("Status of the table already is ${StatusTable.getById(statusId)}")
    }
}
