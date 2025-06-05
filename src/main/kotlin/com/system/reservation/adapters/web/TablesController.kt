package com.system.reservation.adapters.web

import com.system.reservation.adapters.web.doc.TablesOpenAPI
import com.system.reservation.adapters.web.model.enumerated.StatusTable
import com.system.reservation.adapters.web.model.request.CreateFormTable
import com.system.reservation.adapters.web.model.response.TablesResponse
import com.system.reservation.core.domain.model.tables.Tables
import com.system.reservation.core.ports.input.TablesInputPort
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/tables")
class TablesController(
    private val tablesInputPort: TablesInputPort,
) : TablesOpenAPI {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    override fun registerNewTable(createNewTable: CreateFormTable) {
        val table =
            Tables(
                name = createNewTable.name,
                capacity = createNewTable.capacity,
            )

        tablesInputPort.createNewTable(table)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    override fun listAllTables(
        @RequestParam statusTableIds: List<Int>?,
    ): List<TablesResponse> {
        val listTablesEntity = tablesInputPort.listAllTables(statusTableIds)

        val listAllTables =
            listTablesEntity.map { item ->
                TablesResponse(
                    id = item.id,
                    name = item.name,
                    capacity = item.capacity,
                    status = StatusTable.getById(item.idStatus),
                )
            }

        return listAllTables
    }
}
