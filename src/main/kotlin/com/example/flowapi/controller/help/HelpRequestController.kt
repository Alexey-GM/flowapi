package com.example.flowapi.controller.help

import com.example.flowapi.controller.post.dto.UserDto
import com.example.flowapi.model.HelpRequest
import com.example.flowapi.service.HelpRequestService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/help-requests")
class HelpRequestController(private val helpRequestService: HelpRequestService) {

    @PostMapping
    fun sendHelpRequest(@RequestBody helpRequestDTO: HelpRequestDTO): ResponseEntity<HelpRequest> {
        val userId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        val helpRequest = helpRequestService.sendHelpRequest(
            fromUserId = userId,
            toUserId = helpRequestDTO.toUserId,
            description = helpRequestDTO.description,
            videoUrl = helpRequestDTO.videoUrl
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(helpRequest)
    }

    @GetMapping("/professionals")
    fun getAllProfessionalsBySport(@RequestParam sportId: Int): List<UserDto> {
        return helpRequestService.getAllProfessionalsBySport(sportId)
    }
}