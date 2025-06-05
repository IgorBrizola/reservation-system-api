package com.system.reservation.core.useCases

import com.system.reservation.adapters.repository.model.TablesEntity
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

    override fun validateNameTable(nameTable: String) =
        run {
            if (tablesOutPutPort.existsTablesByName(nameTable)) {
                throw BusinessException("Table already exist with is name - $nameTable")
            }
        }

    override fun verifyNameUppercaseTable(nameTable: String) =
        run {
            if (nameTable != nameTable.uppercase()) {
                throw BusinessException("Table does not follow name default, replace $nameTable to ${nameTable.uppercase()}")
            }
        }

    override fun listAllTables(statusTableIds: List<Int>?): List<TablesEntity> = tablesOutPutPort.findAllTables(statusTableIds)
}
