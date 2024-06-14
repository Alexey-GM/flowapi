package com.example.flowapi.controller.spot

import com.example.flowapi.service.SpotService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/spots")
class SpotController(private val spotService: SpotService) {

    @GetMapping("/all")
    fun getAllSpots(): List<SpotDto> {
        return spotService.getAllSpots()
    }
}