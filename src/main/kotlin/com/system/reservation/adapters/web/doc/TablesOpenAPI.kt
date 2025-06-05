package com.system.reservation.adapters.web.doc

import com.system.reservation.adapters.web.model.request.CreateFormTable
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "Tables Controller", description = "Manage Tables")
interface TablesOpenAPI {
    fun registerNewTable(createNewTable: CreateFormTable)
}
