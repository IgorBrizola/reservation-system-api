package com.system.reservation.core.ports.input

import com.system.reservation.core.domain.model.tables.Tables
import com.system.reservation.core.domain.model.tables.request.UpdateTable

interface TablesInputPort {
    fun createNewTable(table: Tables)

    fun listAllTables(statusTableIds: List<Int>?): List<Tables>

    fun findTableById(tableId: Int): Tables

    fun updateTablesById(
        tableId: Int,
        updateTable: UpdateTable,
    ): Tables

    fun deleteTableById(tableId: Int)
}
