package com.example.flowapi.service

import com.example.flowapi.controller.toResponse
import com.example.flowapi.controller.tricks.TricksResponse
import com.example.flowapi.model.TrickComment
import com.example.flowapi.model.TrickStatus
import com.example.flowapi.model.User
import com.example.flowapi.model.UserTrick
import com.example.flowapi.repository.TrickCommentRepository
import com.example.flowapi.repository.TrickRepository
import com.example.flowapi.repository.UserTrickRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime


@Service
class TricksService(
    private val trickRepository: TrickRepository,
    private val userTrickRepository: UserTrickRepository,
    private val trickCommentRepository: TrickCommentRepository
) {

    fun getTrickById(userId: Int, trickId: Int): TricksResponse {
        val trick = trickRepository.getTrickById(trickId)
        val status = getTrickStatusForUser(trick.id, userId)
        return trick.toResponse(status)
    }

    fun getAllTricks(userId: Int, sportId: Int): List<TricksResponse> {
        val tricks = trickRepository.findBySportId(sportId)
        return tricks.map { trick ->
            val status = getTrickStatusForUser(trick.id, userId)
            trick.toResponse(status)
        }
    }

    fun getCategories(sportId: Int): List<String> {
        val tricks = trickRepository.findBySportId(sportId)
        return tricks.map { it.category }.distinct()
    }

    fun getTricksByCategory(userId: Int, sportId: Int, category: String): List<TricksResponse> {
        val tricks = trickRepository.findBySportIdAndCategory(sportId, category)
        return tricks.map { trick ->
            val status = getTrickStatusForUser(trick.id, userId)
            trick.toResponse(status)
        }
    }

    fun getUserTricksDone(userId: Int, sportId: Int): List<TricksResponse> {
        val tricks = trickRepository.findCompletedTricksByUserIdAndSportId(userId, sportId)
        return tricks.map { trick ->
            val status = getTrickStatusForUser(trick.id, userId)
            trick.toResponse(status)
        }
    }

    fun getUserTricksInProcess(userId: Int, sportId: Int): List<TricksResponse> {
        val tricks = trickRepository.findInProcessTricksByUserIdAndSportId(userId, sportId)
        return tricks.map { trick ->
            val status = getTrickStatusForUser(trick.id, userId)
            trick.toResponse(status)
        }
    }


    fun getTrickStatusForUser(trickId: Int, userId: Int): TrickStatus {
        val userTrick = userTrickRepository.findByUserIdAndTrickId(userId, trickId)
        return when {
            userTrick == null -> TrickStatus.NOT_LEARNED
            userTrick.startDate != null && userTrick.learningDuration != null -> TrickStatus.LEARNED
            userTrick.startDate != null -> TrickStatus.IN_PROGRESS
            else -> TrickStatus.NOT_LEARNED
        }
    }

    fun startLearningTrick(userId: Int, trickId: Int) {
        val userTrick = userTrickRepository.findByUserIdAndTrickId(userId, trickId)
        val trick = trickRepository.getTrickById(trickId)
        if (userTrick == null) {
            val newUserTrick = UserTrick(
                user = User(id = userId),
                trick = trick,
                startDate = LocalDate.now().toString(),
                learningDuration = null
            )
            userTrickRepository.save(newUserTrick)
        } else {
            val updatedUserTrick = userTrick.copy(startDate = LocalDate.now().toString())
            userTrickRepository.save(updatedUserTrick)
        }
    }

    fun addCommentToTrick(userId: Int, trickId: Int, commentText: String): TrickComment {
        val user = User(id = userId)
        val trick = trickRepository.getTrickById(trickId)
        val comment = TrickComment(
            user = user,
            trick = trick,
            comment = commentText,
            commentDate = LocalDateTime.now().toString()
        )
        return trickCommentRepository.save(comment)
    }

    fun completeLearningTrick(userId: Int, trickId: Int, learningDuration: Int) {
        val userTrick = userTrickRepository.findByUserIdAndTrickId(userId, trickId)
            ?: throw IllegalArgumentException("User trick not found")

        val updatedUserTrick = userTrick.copy(
            learningDuration = learningDuration
        )
        userTrickRepository.save(updatedUserTrick)
    }

    private fun getInitialRecommendations(sportId: Int): List<TricksResponse> {
        val independentTricks = trickRepository.findIndependentTricksBySportId(sportId).take(4)
        return independentTricks.map { trick ->
            trick.toResponse(TrickStatus.NOT_LEARNED)
        }
    }

    fun getRecommendedTricks(userId: Int, sportId: Int): List<TricksResponse> {
        val learnedTricks = trickRepository.findCompletedTricksByUserIdAndSportId(userId, sportId)
        val learnedTrickIds = learnedTricks.map { it.id }

        val dependentTricks = trickRepository.findDependentTricks(learnedTrickIds)
            .distinctBy { it.id }
            .filter { it.id !in learnedTrickIds }
            .take(5)

        return dependentTricks.map { trick ->
            val status = getTrickStatusForUser(trick.id, userId)
            trick.toResponse(status)
        }
    }
}
