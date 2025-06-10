package com.system.reservation.adapters.web

import com.system.reservation.adapters.web.doc.UsersOpenAPI
import com.system.reservation.adapters.web.model.request.CreateFormUser
import com.system.reservation.adapters.web.model.response.UserResponse
import com.system.reservation.core.domain.model.users.Users
import com.system.reservation.core.ports.input.UsersInputPort
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/users")
class UsersController(
    val usersInputPort: UsersInputPort,
) : UsersOpenAPI {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    override fun createNewUser(
        @RequestBody createFormUser: CreateFormUser,
    ) {
        val user =
            Users(
                name = createFormUser.name,
                email = createFormUser.email,
                password = createFormUser.password,
            )

        usersInputPort.createNewUser(user)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    override fun findAllUsers(): List<UserResponse> =
        usersInputPort.findAllUsers().map {
            UserResponse(
                id = it.id,
                name = it.name,
                email = it.email,
            )
        }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    override fun findUserById(
        @PathVariable id: Int,
    ): UserResponse {
        val user = usersInputPort.findUserById(id)

        return UserResponse(
            id = user.id,
            name = user.name,
            email = user.email,
        )
    }
}
