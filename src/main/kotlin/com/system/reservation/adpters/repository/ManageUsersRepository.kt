package com.system.reservation.adpters.repository

import com.system.reservation.adpters.repository.jpa.UsersJpaRepository
import com.system.reservation.adpters.repository.model.UsersEntity
import com.system.reservation.core.domain.model.users.Users
import com.system.reservation.core.ports.output.UsersOutPutPort
import org.springframework.stereotype.Repository

@Repository
class ManageUsersRepository(
    private val usersJpaRepository: UsersJpaRepository
): UsersOutPutPort {
    override fun save(user: Users): UsersEntity {
       val userEntity = UsersEntity(
           name = user.name,
           email = user.email,
           password = user.password
       )

        return usersJpaRepository.save(userEntity)
    }
}