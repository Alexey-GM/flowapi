package com.example.flowapi.controller.tricks

data class UserTricksAggregateResponse(
    val categories: List<String>,
    val done: List<TricksResponse>,
    val inProcess: List<TricksResponse>,
    val next: List<TricksResponse>
)
