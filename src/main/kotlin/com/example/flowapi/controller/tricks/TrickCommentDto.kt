package com.example.flowapi.controller.tricks

import com.example.flowapi.controller.post.dto.UserDto

data class TrickCommentDto(
    val id: Int,
    val user: UserDto,
    val comment: String,
    val commentDate: String
)

