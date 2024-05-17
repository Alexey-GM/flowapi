package com.example.flowapi.controller.tricks

import com.example.flowapi.model.Sport
import com.example.flowapi.model.TrickComment
import com.example.flowapi.model.TrickStep
import com.example.flowapi.model.UserTrick
import jakarta.persistence.*

data class TricksResponse(
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val imageUrl: String,
    val difficulty: String,
    val steps: List<StepResponse>,
    val comments: List<TrickComment>
)
