package com.example.flowapi.controller.auth

data class AuthenticationRequest(
    val email: String,
    val password: String
)
