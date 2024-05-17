package com.example.flowapi.controller.user

import com.example.flowapi.controller.sport.SportResponse
import com.example.flowapi.controller.toModel
import com.example.flowapi.controller.toResponse
import com.example.flowapi.model.User
import com.example.flowapi.service.UserService
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UserController(private val service: UserService) {

    @PostMapping("/{userId}/sports/{sportId}")
    fun addSportToUser(@PathVariable userId: Int, @PathVariable sportId: Int) {
        service.addSportToUser(userId, sportId)
    }

    @GetMapping("/{userId}/sports")
    fun getUserSports(@PathVariable userId: Int): List<SportResponse> {
        val sports = service.getUserSports(userId).map { it.toResponse() }
        return sports
    }
}