package com.example.flowapi.controller.sport

import com.example.flowapi.controller.toResponse
import com.example.flowapi.service.SportService
import com.example.flowapi.service.UserService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/sport")
class SportController(private val sportService: SportService, private val userService: UserService) {

    @GetMapping
    fun getAllSports(): List<SportResponse> {
        return sportService.getAll().map { it.toResponse() }
    }


    @GetMapping("/id")
    fun getUser(principal: Principal): String {
        return SecurityContextHolder.getContext().authentication.details.toString()
    }

}