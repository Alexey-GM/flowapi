package com.example.flowapi.controller.user

data class UserRequest (
    val mail: String,
    var password: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val gender: String,
    val city: String,
    val status: String,
    val role: String,
)
