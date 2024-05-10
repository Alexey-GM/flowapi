package com.example.flowapi.controller

import com.example.flowapi.model.Sport
import com.example.flowapi.service.SportService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sport")
class SportController(private val sportService: SportService) {

    @GetMapping
    fun getAllUsers(): List<Sport> {
        return sportService.getAll()
    }

    // Добавьте здесь другие обработчики запросов, такие как createUser, updateUser, deleteUser и т.д.
}