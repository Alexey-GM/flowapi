package com.example.flowapi.controller.spot

data class SpotDto(
    val id: Int,
    val title: String,
    val lat: Double,
    val long: Double,
    val description: String,
    val imgUrl: String
)

