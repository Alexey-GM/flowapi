package com.example.flowapi.controller.tricks

import com.example.flowapi.controller.post.CreateCommentRequest
import com.example.flowapi.controller.toResponse
import com.example.flowapi.model.TrickComment
import com.example.flowapi.service.TricksService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/tricks")
class TrickController(
    private val trickService: TricksService
) {

    @GetMapping("/all/{sportId}")
    fun getAllTricks(@PathVariable sportId: Int): ResponseEntity<List<TricksResponse>> {
        val userId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        val tricks = trickService.getAllTricks(userId, sportId)
        return ResponseEntity.ok(tricks)
    }

    @GetMapping("/{trickId}")
    fun getTrickById(@PathVariable trickId: Int): ResponseEntity<TricksResponse> {
        val userId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        val trick = trickService.getTrickById(userId, trickId)
        return ResponseEntity.ok(trick)
    }

    @PostMapping("/{trickId}/comment")
    fun addComment(@PathVariable trickId: Int, @RequestBody createCommentRequest: CreateCommentRequest): ResponseEntity<Void> {
        val userId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        val savedComment = trickService.addCommentToTrick(userId, trickId, createCommentRequest.text)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/categories/{sportId}")
    fun getCategories(@PathVariable sportId: Int): ResponseEntity<List<String>> {
        val categories = trickService.getCategories(sportId)
        return ResponseEntity.ok(categories)
    }

    @GetMapping("/category/{sportId}/{category}")
    fun getTricksByCategory(@PathVariable sportId: Int, @PathVariable category: String): ResponseEntity<List<TricksResponse>> {
        val userId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        val tricks = trickService.getTricksByCategory(userId, sportId, category)
        return ResponseEntity.ok(tricks)
    }

    @GetMapping("/user/{userId}/done/{sportId}")
    fun getUserTricksDone(@PathVariable userId: Int, @PathVariable sportId: Int): ResponseEntity<List<TricksResponse>> {
        val tricks = trickService.getUserTricksDone(userId, sportId)
        return ResponseEntity.ok(tricks)
    }

    @GetMapping("/recommendations/{sportId}")
    fun getRecommendations(@PathVariable sportId: Int): ResponseEntity<List<TricksResponse>> {
        val userId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        val recommendations = trickService.getRecommendedTricks(userId, sportId)
        return ResponseEntity.ok(recommendations)
    }

    @GetMapping("/user/{userId}/in-process/{sportId}")
    fun getUserTricksInProcess(@PathVariable userId: Int, @PathVariable sportId: Int): ResponseEntity<List<TricksResponse>> {
        val tricks = trickService.getUserTricksInProcess(userId, sportId)
        return ResponseEntity.ok(tricks)
    }

    @PostMapping("/start-learning")
    fun startLearningTrick(@RequestParam trickId: Int): ResponseEntity<Void> {
        val userId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        trickService.startLearningTrick(userId, trickId)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/complete-learning")
    fun completeLearningTrick(@RequestParam trickId: Int, @RequestParam learningDuration: Int): ResponseEntity<Void> {
        val userId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        trickService.completeLearningTrick(userId, trickId, learningDuration)
        return ResponseEntity.ok().build()
    }
}
