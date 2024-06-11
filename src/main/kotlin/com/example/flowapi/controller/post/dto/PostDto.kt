package com.example.flowapi.controller.post.dto

data class PostDto(
    val id: Int,
    val text: String,
    val date: String,
    val comments: List<PostCommentDto>,
    val likes: Int,
    val user: UserDto,
    val media: String,
    val isLiked: Boolean
)

