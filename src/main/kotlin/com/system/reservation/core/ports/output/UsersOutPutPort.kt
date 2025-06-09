package com.system.reservation.core.ports.output

import com.system.reservation.adapters.repository.model.UsersEntity
import com.system.reservation.adapters.web.model.response.UserResponse
import com.system.reservation.core.domain.model.users.Users

interface UsersOutPutPort {
    fun save(user: Users): UsersEntity

    fun existsUserByEmail(email: String): Boolean

    fun findAllUsers(): List<UserResponse>

    fun findUserById(id: Int): UserResponse
}
