package com.example.flowapi.controller.tricks

import com.example.flowapi.model.*
import jakarta.persistence.*

data class TricksResponse(
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val imageUrl: String,
    val difficulty: String,
    val status: TrickStatus,
    val steps: List<StepResponse>,
    val comments: List<TrickCommentDto>,
    val videos: List<TrickVideo>
)
