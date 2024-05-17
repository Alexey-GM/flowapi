package com.example.flowapi.controller.auth

import com.example.flowapi.controller.toModel
import com.example.flowapi.controller.user.UserRequest
import com.example.flowapi.controller.user.UserResponse
import com.example.flowapi.service.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class AuthController(
    private val authenticationService: AuthenticationService
) {
    @PostMapping("/auth")
    fun authentication(@RequestBody authRequest: AuthenticationRequest): AuthenticationResponse {
        return authenticationService.authenticate(authRequest)
    }

    @PostMapping("/register")
    fun register(@RequestBody userRequest: UserRequest): UserResponse {
        val user = userRequest.toModel()
        val savedUser = authenticationService.createUser(user)
        val authRequest = AuthenticationRequest(savedUser.mail, userRequest.password)
        val token = authenticationService.authenticate(authRequest)
        return UserResponse(savedUser.id, token.accessToken)
    }
}