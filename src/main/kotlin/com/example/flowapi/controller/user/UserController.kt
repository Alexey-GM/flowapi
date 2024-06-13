package com.example.flowapi.controller.user

import com.example.flowapi.controller.post.dto.UserDto
import com.example.flowapi.controller.sport.SportResponse
import com.example.flowapi.controller.toDto
import com.example.flowapi.controller.toModel
import com.example.flowapi.controller.toResponse
import com.example.flowapi.model.User
import com.example.flowapi.service.UserService
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val service: UserService) {

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Int): ResponseEntity<UserDto> {
        val user = service.getUser(userId)
        return ResponseEntity.ok(user)
    }

    @PostMapping("/{userId}/sports/{sportId}")
    fun addSportToUser(@PathVariable userId: Int, @PathVariable sportId: Int) {
        service.addSportToUser(userId, sportId)
    }

    @GetMapping("/{userId}/sports")
    fun getUserSports(@PathVariable userId: Int): List<SportResponse> {
        val sports = service.getUserSports(userId).map { it.toResponse() }
        return sports
    }

    @GetMapping("/{userId}/sports-and-tricks-count")
    fun getUserSportsAndTricksCount(@PathVariable userId: Int): ResponseEntity<List<UserSportInfo>> {
        val userSportInfos = service.getUserSportsAndTricksCount(userId)
        return ResponseEntity.ok(userSportInfos)
    }

    @GetMapping("/{id}/subscribers/count")
    fun getUserSubscribersCount(@PathVariable id: Int): ResponseEntity<Int> {
        val count = service.getUserSubscribersCount(id)
        return ResponseEntity.ok(count)
    }

    @GetMapping("/check-pro")
    fun checkIfUserIsPro(): ResponseEntity<Void> {
        val userId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        val role = service.getUserRole(userId)
        return if (role == "PRO") {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }
    }

    @GetMapping("/{userId}/subscribers")
    fun getUserSubscribers(@PathVariable userId: Int): ResponseEntity<List<UserDto>> {
        val subscribers = service.getUserSubscribers(userId)
        return ResponseEntity.ok(subscribers.map { it.toDto() })
    }

    @GetMapping("/{userId}/subscriptions")
    fun getUserSubscriptions(@PathVariable userId: Int): ResponseEntity<List<UserDto>> {
        val subscriptions = service.getUserSubscriptions(userId)
        return ResponseEntity.ok(subscriptions.map { it.toDto() })
    }

    @PostMapping("/subscribe/{subscriptionId}")
    fun subscribe(@PathVariable subscriptionId: Int): ResponseEntity<Void> {
        val userId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        service.subscribe(userId, subscriptionId)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/unsubscribe/{subscriptionId}")
    fun unsubscribe(@PathVariable subscriptionId: Int): ResponseEntity<Void> {
        val userId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        service.unsubscribe(userId, subscriptionId)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/check-subscription/{subscriptionId}")
    fun checkSub(@PathVariable subscriptionId: Int): ResponseEntity<Void> {
        val userId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        val result = service.isUserSubscribed(userId, subscriptionId)
        return if (result) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }
}