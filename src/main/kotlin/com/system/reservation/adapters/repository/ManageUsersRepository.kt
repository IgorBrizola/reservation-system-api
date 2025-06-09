package com.system.reservation.adapters.repository

import com.system.reservation.adapters.repository.jpa.UsersJpaRepository
import com.system.reservation.adapters.repository.model.UsersEntity
import com.system.reservation.adapters.web.model.response.UserResponse
import com.system.reservation.core.domain.exceptions.BusinessException
import com.system.reservation.core.domain.model.users.Users
import com.system.reservation.core.ports.output.UsersOutPutPort
import org.springframework.stereotype.Repository

@Repository
class ManageUsersRepository(
    private val usersJpaRepository: UsersJpaRepository,
) : UsersOutPutPort {
    override fun save(user: Users): UsersEntity {
        val userEntity =
            UsersEntity(
                name = user.name,
                email = user.email,
                password = user.password,
            )

        return usersJpaRepository.save(userEntity)
    }

    override fun existsUserByEmail(email: String): Boolean = usersJpaRepository.existsUserEntityByEmail(email)

    override fun findAllUsers(): List<UserResponse> =
        usersJpaRepository.findAll().map {
            UserResponse(id = it.id, name = it.name, email = it.email)
        }

    override fun findUserById(id: Int): UserResponse =
        run {
            val userEntity = usersJpaRepository.findById(id).orElseThrow { BusinessException("User not found with id - $id") }

            return UserResponse(
                id = userEntity.id,
                name = userEntity.name,
                email = userEntity.email,
            )
        }
}
