package com.system.reservation.util

import com.system.reservation.adapters.repository.model.TablesEntity
import com.system.reservation.adapters.web.model.response.TablesResponse
import com.system.reservation.core.domain.model.enumerated.StatusTable
import com.system.reservation.core.domain.model.tables.Tables

class UtilsTest {
    companion object {
        fun buildTablesEntity() =
            TablesEntity(
                id = 1,
                name = "M1",
                capacity = 4,
                idStatus = 1,
            )

        fun buildTables(nameInvalid: Boolean = false) =
            Tables(
                id = 1,
                name = if (nameInvalid) "m4" else "M1",
                capacity = 4,
                status = 1,
            )

        fun buildTablesResponse() =
            TablesResponse(
                id = 1,
                name = "M1",
                capacity = 4,
                status = StatusTable.AVAILABLE,
            )
    }
}
