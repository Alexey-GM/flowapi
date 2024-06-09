package com.example.flowapi.service

import com.example.flowapi.controller.post.dto.UserDto
import com.example.flowapi.exception.ApiException
import com.example.flowapi.model.HelpRequest
import com.example.flowapi.repository.HelpRequestRepository
import com.example.flowapi.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class HelpRequestService(
    private val helpRequestRepository: HelpRequestRepository,
    private val userRepository: UserRepository
) {

    fun sendHelpRequest(fromUserId: Int, toUserId: Int, description: String, videoUrl: String): HelpRequest {
        val fromUser = userRepository.findById(fromUserId).orElseThrow { ApiException(404, "User not found") }
        val toUser = userRepository.findById(toUserId).orElseThrow { ApiException(404, "User not found") }

        val helpRequest = HelpRequest(
            fromUser = fromUser,
            toUser = toUser,
            description = description,
            videoUrl = videoUrl
        )

        return helpRequestRepository.save(helpRequest)
    }

    fun getAllProfessionalsBySport(sportId: Int): List<UserDto> {
        val professionals = userRepository.findAllProfessionalsBySport(sportId)
        return professionals.map { user ->
            UserDto(
                id = user.id,
                email = user.mail,
                firstName = user.firstName,
                lastName = user.lastName,
                birthDate = user.dateOfBirth,
                city = user.city,
                status = user.status,
                imageUrl = user.imageUrl
            )
        }
    }

}