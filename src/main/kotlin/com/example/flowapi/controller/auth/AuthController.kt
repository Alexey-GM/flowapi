package com.example.flowapi.controller.auth

import com.example.flowapi.service.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationService: AuthenticationService
) {
    @PostMapping
    fun authentication(@RequestBody authRequest: AuthenticationRequest): AuthenticationResponse {
        return authenticationService.authenticate(authRequest)
    }
}