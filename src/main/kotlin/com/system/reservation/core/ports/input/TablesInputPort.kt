package com.system.reservation.core.ports.input

import com.system.reservation.core.domain.model.tables.Tables

interface TablesInputPort {
    fun createNewTable(table: Tables)

    fun validateNameTable(nameTable: String)

    fun verifyNameUppercaseTable(nameTable: String)
}
