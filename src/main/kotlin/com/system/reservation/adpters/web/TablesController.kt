package com.system.reservation.adpters.web

import com.system.reservation.adpters.web.doc.TablesOpenAPI
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/tables")
class TablesController(

): TablesOpenAPI {
}