package com.system.reservation.core.ports.input

import com.system.reservation.core.domain.model.users.Users
import com.system.reservation.core.domain.model.users.request.NewUser

interface UsersInputPort {
    fun createNewUser(user: Users)
}