package com.example.flowapi.controller.help

data class HelpRequestDTO(
    val toUserId: Int,
    val description: String,
    val videoUrl: String
)