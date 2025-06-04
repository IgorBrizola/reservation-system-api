package com.system.reservation.adpters.web.doc

import com.system.reservation.adpters.web.model.request.CreateFormUser
import com.system.reservation.adpters.web.model.response.UserResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "Users Controller", description = "Manage Users")
interface UsersOpenAPI {
    fun createNewUser(
        createFormUser: CreateFormUser
    )
}