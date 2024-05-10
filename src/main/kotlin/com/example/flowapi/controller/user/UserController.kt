package com.example.flowapi.controller.user

import com.example.flowapi.model.User
import com.example.flowapi.service.UserService
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/register")
class UserController(private val service: UserService) {

    @PostMapping
    fun register(@RequestBody userRequest: UserRequest): UserResponse {
        val user = userRequest.toModel()
        service.createUser(user)
        return UserResponse("Создан")
    }

    private fun UserRequest.toModel(): User {
        return User(
            id = 0,
            mail = this.mail,
            firstName = this.firstName,
            lastName = this.lastName,
            password = this.password,
            dateOfBirth = this.dateOfBirth,
            gender = this.gender,
            city = this.city,
            status = "Новичок",
            role = "USER",
        )
    }
}