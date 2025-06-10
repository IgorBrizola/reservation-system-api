package com.system.reservation.core.ports.output

import com.system.reservation.adapters.repository.model.UsersEntity
import com.system.reservation.core.domain.model.users.Users

interface UsersOutPutPort {
    fun save(user: Users): UsersEntity

    fun existsUserByEmail(email: String): Boolean

    fun findAllUsers(): List<Users>

    fun findUserById(id: Int): Users
}
