package com.example.flowapi.controller.post.dto

data class UserDto(
    val id: Int,
    val email: String,
    val imageUrl: String,
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val city: String,
    val status: String,
)

