package com.system.reservation.core.useCases

import com.system.reservation.core.domain.exceptions.BusinessException
import com.system.reservation.core.domain.model.users.Users
import com.system.reservation.core.ports.input.UsersInputPort
import com.system.reservation.core.ports.output.UsersOutPutPort
import org.springframework.stereotype.Service

@Service
class UsersUseCase(
    private val usersOutPutPort: UsersOutPutPort,
) : UsersInputPort {
    override fun createNewUser(user: Users) {
        validateUserExistByEmail(user.email)

        usersOutPutPort.save(user)
    }

    override fun findAllUsers(): List<Users> = usersOutPutPort.findAllUsers()

    override fun findUserById(id: Int): Users = usersOutPutPort.findUserById(id)

    private fun validateUserExistByEmail(email: String) =
        run {
            if (usersOutPutPort.existsUserByEmail(email)) throw BusinessException("User with email ($email) already exist!")
        }
}
