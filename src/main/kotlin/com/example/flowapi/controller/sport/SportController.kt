package com.example.flowapi.controller.sport

import com.example.flowapi.controller.toResponse
import com.example.flowapi.model.Sport
import com.example.flowapi.service.SportService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sport")
class SportController(private val sportService: SportService) {

    @GetMapping
    fun getAllUsers(): List<SportResponse> {
        val sports = sportService.getAll().map { it.toResponse() }
        return sports
    }
    // Добавьте здесь другие обработчики запросов, такие как createUser, updateUser, deleteUser и т.д.
}