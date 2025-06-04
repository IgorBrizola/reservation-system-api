package com.system.reservation.core.useCases

import com.system.reservation.core.domain.model.users.Users
import com.system.reservation.core.domain.model.users.request.NewUser
import com.system.reservation.core.ports.input.UsersInputPort
import com.system.reservation.core.ports.output.UsersOutPutPort
import org.springframework.stereotype.Service

@Service
class UsersUseCase(
    private val usersOutPutPort: UsersOutPutPort
): UsersInputPort {
    override fun createNewUser(user: Users) {
        usersOutPutPort.save(user)
    }
}