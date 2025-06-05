package com.system.reservation.adapters.web.doc

import com.system.reservation.adapters.web.model.request.CreateFormTable
import com.system.reservation.adapters.web.model.response.TablesResponse
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "Tables Controller", description = "Manage Tables")
interface TablesOpenAPI {
    fun registerNewTable(createNewTable: CreateFormTable)

    fun listAllTables(
        @Parameter(
            description = "List tables by filter statusId",
            examples =
                [
                    ExampleObject(value = "1 - AVAILABLE"),
                    ExampleObject(value = "2 - RESERVED"),
                    ExampleObject(value = "3 - INACTIVE"),
                ],
        ) statusTableIds: List<Int>?,
    ): List<TablesResponse>
}
