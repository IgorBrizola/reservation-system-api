package com.system.reservation.adapters.web.doc

import com.system.reservation.adapters.web.model.request.CreateFormUser
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "Users Controller", description = "Manage Users")
interface UsersOpenAPI {
    fun createNewUser(
        createFormUser: CreateFormUser
    )
}