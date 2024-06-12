package com.example.flowapi.controller.help

import com.example.flowapi.controller.post.dto.UserDto
import com.example.flowapi.model.PostComment
import com.example.flowapi.model.PostLike
import com.example.flowapi.model.User
import jakarta.persistence.*
import java.time.LocalDateTime

data class HelpRequestResponse(
    val id: Int,
    val fromUser: UserDto,
    val toUser: UserDto,
    val description: String,
    val videoUrl: String,
    var reply: String?,
    var replyDate: String?,
    val requestDate: String
)