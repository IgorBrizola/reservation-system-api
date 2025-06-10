package com.system.reservation.core.ports.output

import com.system.reservation.adapters.repository.model.TablesEntity
import com.system.reservation.core.domain.model.tables.Tables

interface TablesOutPutPort {
    fun save(table: Tables): TablesEntity

    fun existsTablesByName(tableName: String): Boolean

    fun findAllTables(statusTableIds: List<Int>?): List<Tables>

    fun findTablesById(tableId: Int): Tables

    fun updateTable(table: Tables): Tables

    fun deleteTableById(tableId: Int)
}
