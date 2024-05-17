package com.example.flowapi.service

import com.example.flowapi.exception.ApiException
import com.example.flowapi.model.Sport
import com.example.flowapi.model.User
import com.example.flowapi.model.UserSport
import com.example.flowapi.repository.SportRepository
import com.example.flowapi.repository.UserRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository, private val sportRepository: SportRepository) {

    fun addSportToUser(userId: Int, sportId: Int) {
        val user = userRepository.findById(userId).orElseThrow { ApiException(404, "User not found") }
        val sport = sportRepository.findById(sportId).orElseThrow { ApiException(404, "Sport not found") }
        val userSport = UserSport(user = user, sport = sport)
        user.sports += userSport
        userRepository.save(user)
    }

    fun getUserSports(userId: Int): List<Sport> {
        val user = userRepository.findById(userId).orElseThrow { ApiException(404, "User not found") }
        return user.sports.map { it.sport }
    }
}