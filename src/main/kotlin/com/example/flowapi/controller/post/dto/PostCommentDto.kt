package com.example.flowapi.controller.post.dto

data class PostCommentDto(
    val id: Int,
    val user: UserDto,
    val likes: Int,
    val text: String,
    val date: String
)