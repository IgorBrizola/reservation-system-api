package com.system.reservation.core.ports.input

import com.system.reservation.core.domain.model.users.Users

interface UsersInputPort {
    fun createNewUser(user: Users)

    fun findAllUsers(): List<Users>

    fun findUserById(id: Int): Users
}
