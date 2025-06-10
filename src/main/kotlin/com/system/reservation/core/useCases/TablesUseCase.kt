package com.system.reservation.core.useCases

import com.system.reservation.core.domain.exceptions.BusinessException
import com.system.reservation.core.domain.model.enumerated.StatusTable
import com.system.reservation.core.domain.model.tables.Tables
import com.system.reservation.core.domain.model.tables.request.UpdateTable
import com.system.reservation.core.ports.input.TablesInputPort
import com.system.reservation.core.ports.output.TablesOutPutPort
import com.system.reservation.util.AppUtil
import org.springframework.stereotype.Service

@Service
class TablesUseCase(
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
                AppUtil.logContextError("Table does not follow name default, replace $nameTable to ${nameTable.uppercase()}")
                throw BusinessException("Table does not follow name default, replace $nameTable to ${nameTable.uppercase()}")
            }
        }

    override fun listAllTables(statusTableIds: List<Int>?): List<Tables> = tablesOutPutPort.findAllTables(statusTableIds)

    override fun findTableById(tableId: Int): Tables = tablesOutPutPort.findTablesById(tableId)

    override fun updateTablesById(
        tableId: Int,
        updateTable: UpdateTable,
    ): Tables {
        val tables = findTableById(tableId)

        updateTable.name?.let {
            validateNameTable(it)
            verifyNameUppercaseTable(it)
        }

        updateTable.status?.let {
            validateStatusTable(it, tables)
        }

        val tableUpdated =
            tables.copy(
                id = tables.id,
                name = updateTable.name ?: tables.name,
                capacity = updateTable.capacity ?: tables.capacity,
                status = updateTable.status ?: tables.status,
            )

        return tablesOutPutPort.updateTable(tableUpdated)
    }

    override fun deleteTableById(tableId: Int) =
        run {
            findTableById(tableId)
            tablesOutPutPort.deleteTableById(tableId)
        }

    private fun validateStatusTable(
        statusId: Int,
        table: Tables,
    ) = run {
        if (statusId == table.status) throw BusinessException("Status of the table already is ${StatusTable.getById(statusId)}")
    }
}
