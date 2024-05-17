package com.example.flowapi.service

import com.example.flowapi.controller.toResponse
import com.example.flowapi.controller.tricks.TricksResponse
import com.example.flowapi.exception.ApiException
import com.example.flowapi.model.Trick
import com.example.flowapi.repository.SportRepository
import com.example.flowapi.repository.TrickRepository
import org.springframework.stereotype.Service


@Service
class TricksService(
    private val trickRepository: TrickRepository,
) {
    fun getAllTricks(sportId: Int): List<TricksResponse> {
        val tricks = trickRepository.findBySportId(sportId)
        return tricks.map { it.toResponse() }
    }

    fun getCategories(sportId: Int): List<String> {
        val tricks = trickRepository.findBySportId(sportId)
        return tricks.map { it.category }.distinct()
    }

    fun getTricksByCategory(sportId: Int, category: String): List<TricksResponse> {
        val tricks = trickRepository.findBySportIdAndCategory(sportId, category)
        return tricks.map { it.toResponse() }
    }

    fun getUserTricksDone(userId: Int, sportId: Int): List<TricksResponse> {
        val tricks = trickRepository.findCompletedTricksByUserIdAndSportId(userId, sportId)
        return tricks.map { it.toResponse() }
    }

    fun getUserTricksInProcess(userId: Int, sportId: Int): List<TricksResponse> {
        val tricks = trickRepository.findInProcessTricksByUserIdAndSportId(userId, sportId)
        return tricks.map { it.toResponse() }
    }

    fun getUserTricksNext(userId: Int, sportId: Int): List<TricksResponse> {
        val tricks = trickRepository.findNextTricksByUserIdAndSportId(userId, sportId)
        return tricks.map { it.toResponse() }
    }
}
