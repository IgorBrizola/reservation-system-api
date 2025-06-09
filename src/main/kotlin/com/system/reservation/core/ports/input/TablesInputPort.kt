package com.system.reservation.core.ports.input

import com.system.reservation.adapters.repository.model.TablesEntity
import com.system.reservation.adapters.web.model.request.UpdateFormTable
import com.system.reservation.adapters.web.model.response.TablesResponse
import com.system.reservation.core.domain.model.tables.Tables

interface TablesInputPort {
    fun createNewTable(table: Tables)

    fun listAllTables(statusTableIds: List<Int>?): List<TablesEntity>

    fun findTableById(tableId: Int): Tables

    fun updateTablesById(
        tableId: Int,
        updateFormTable: UpdateFormTable,
    ): TablesResponse

    fun deleteTableById(tableId: Int)
}
