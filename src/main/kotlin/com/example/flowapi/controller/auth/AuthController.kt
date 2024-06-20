package com.example.flowapi.controller.auth

import com.example.flowapi.controller.toModel
import com.example.flowapi.controller.user.UserRequest
import com.example.flowapi.controller.user.UserResponse
import com.example.flowapi.service.AuthenticationService
import com.example.flowapi.service.FileService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api")
class AuthController(
    private val authenticationService: AuthenticationService,
    private val fileService: FileService
) {
    @PostMapping("/auth")
    fun authentication(@RequestBody authRequest: AuthenticationRequest): AuthenticationResponse {
        return authenticationService.authenticate(authRequest)
    }

    @PostMapping("/register", consumes = ["multipart/form-data"])
    fun register(
        @RequestPart("user") userRequest: UserRequest,
        @RequestPart("avatar") avatar: MultipartFile?
    ): ResponseEntity<UserResponse> {
        val avatarUrl = avatar?.let { fileService.saveFile(it) }
        val user = userRequest.toModel(avatarUrl)
        val savedUser = authenticationService.createUser(user)
        val authRequest = AuthenticationRequest(savedUser.mail, userRequest.password)
        val token = authenticationService.authenticate(authRequest)
        return ResponseEntity.ok(UserResponse(savedUser.id, token.accessToken))
    }
}