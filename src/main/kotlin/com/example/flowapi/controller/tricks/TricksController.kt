package com.example.flowapi.controller.tricks

import com.example.flowapi.model.Trick
import com.example.flowapi.service.TricksService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/tricks")
class TrickController(
    private val trickService: TricksService
) {

    @GetMapping("/all/{sportId}")
    fun getAllTricks(@PathVariable sportId: Int): ResponseEntity<List<TricksResponse>> {
        val tricks = trickService.getAllTricks(sportId)
        return ResponseEntity.ok(tricks)
    }

    @GetMapping("/categories/{sportId}")
    fun getCategories(@PathVariable sportId: Int): ResponseEntity<List<String>> {
        val categories = trickService.getCategories(sportId)
        return ResponseEntity.ok(categories)
    }

    @GetMapping("/category/{sportId}/{category}")
    fun getTricksByCategory(@PathVariable sportId: Int, @PathVariable category: String): ResponseEntity<List<TricksResponse>> {
        val tricks = trickService.getTricksByCategory(sportId, category)
        return ResponseEntity.ok(tricks)
    }

    @GetMapping("/user/{userId}/done/{sportId}")
    fun getUserTricksDone(@PathVariable userId: Int, @PathVariable sportId: Int): ResponseEntity<List<TricksResponse>> {
        val tricks = trickService.getUserTricksDone(userId, sportId)
        return ResponseEntity.ok(tricks)
    }

    @GetMapping("/user/{userId}/in-process/{sportId}")
    fun getUserTricksInProcess(@PathVariable userId: Int, @PathVariable sportId: Int): ResponseEntity<List<TricksResponse>> {
        val tricks = trickService.getUserTricksInProcess(userId, sportId)
        return ResponseEntity.ok(tricks)
    }

    @GetMapping("/user/{userId}/next/{sportId}")
    fun getUserTricksNext(@PathVariable userId: Int, @PathVariable sportId: Int): ResponseEntity<List<TricksResponse>> {
        val tricks = trickService.getUserTricksNext(userId, sportId)
        return ResponseEntity.ok(tricks)
    }

    @GetMapping("/sport/{sportId}/user/{userId}")
    fun getUserTricksAggregate(
        @PathVariable userId: Int,
        @PathVariable sportId: Int
    ): ResponseEntity<UserTricksAggregateResponse> {
        val categories = trickService.getCategories(sportId)
        val done = trickService.getUserTricksDone(userId, sportId)
        val inProcess = trickService.getUserTricksInProcess(userId, sportId)
        val next = trickService.getUserTricksNext(userId, sportId)

        val aggregateResponse = UserTricksAggregateResponse(
            categories = categories,
            done = done,
            inProcess = inProcess,
            next = next
        )

        return ResponseEntity.ok(aggregateResponse)
    }
}
