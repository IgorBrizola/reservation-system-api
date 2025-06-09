package com.system.reservation.adapters.web.doc

import com.system.reservation.adapters.web.model.request.CreateFormUser
import com.system.reservation.adapters.web.model.response.UserResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "Users Controller", description = "Manage Users")
interface UsersOpenAPI {
    fun createNewUser(createFormUser: CreateFormUser)

    fun findAllUsers(): List<UserResponse>

    fun findUserById(id: Int): UserResponse
}
