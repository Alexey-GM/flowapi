package com.example.flowapi.controller.auth

data class AuthenticationResponse(
    val userId: Int,
    val accessToken: String,
)
