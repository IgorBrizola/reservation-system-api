package com.system.reservation.adapters.web

import com.system.reservation.adapters.web.doc.TablesOpenAPI
import com.system.reservation.adapters.web.model.request.CreateFormTable
import com.system.reservation.adapters.web.model.request.UpdateFormTable
import com.system.reservation.adapters.web.model.response.TablesResponse
import com.system.reservation.core.domain.model.enumerated.StatusTable
import com.system.reservation.core.domain.model.tables.Tables
import com.system.reservation.core.domain.model.tables.request.UpdateTable
import com.system.reservation.core.ports.input.TablesInputPort
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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
    @Transactional
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
                    status = StatusTable.getById(item.status),
                )
            }

        return listAllTables
    }

    @PatchMapping("{tableId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    override fun updateTablesById(
        @PathVariable tableId: Int,
        @RequestBody updateFormTable: UpdateFormTable,
    ): TablesResponse =
        run {
            val tables =
                tablesInputPort.updateTablesById(
                    tableId,
                    UpdateTable(
                        name = updateFormTable.name,
                        capacity = updateFormTable.capacity,
                        status = updateFormTable.status,
                    ),
                )

            return TablesResponse(
                id = tables.id,
                name = tables.name,
                capacity = tables.capacity,
                status = StatusTable.getById(tables.status),
            )
        }

    @DeleteMapping("{tableId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun deleteTableById(
        @PathVariable tableId: Int,
    ) = tablesInputPort.deleteTableById(tableId)
}
