package com.example.flowapi.service

import com.example.flowapi.config.JwtProperties
import com.example.flowapi.controller.auth.AuthenticationRequest
import com.example.flowapi.controller.auth.AuthenticationResponse
import com.example.flowapi.model.User
import com.example.flowapi.repository.UserRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val userRepository: UserRepository
) {
    fun authenticate(authRequest: AuthenticationRequest): AuthenticationResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authRequest.email,
                authRequest.password
            )
        )

        val user = userDetailsService.loadUserByUsername(authRequest.email)
        val userId = userRepository.findByMail(authRequest.email)!!.id
        val accessToken = tokenService.generate(
            userDetails = user,
            expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
        )

        return AuthenticationResponse(userId,accessToken)
    }

    fun createUser(user: User): User {
        if (userRepository.findByMail(user.mail) != null) {
            throw DataIntegrityViolationException("User with email ${user.mail} already exists")
        }
        user.encodeAndSetPassword(user.password)
        return userRepository.save(user)
    }
}