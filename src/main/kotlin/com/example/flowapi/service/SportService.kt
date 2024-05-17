package com.example.flowapi.service

import com.example.flowapi.exception.ApiException
import com.example.flowapi.model.Sport
import com.example.flowapi.model.UserSport
import com.example.flowapi.repository.SportRepository
import com.example.flowapi.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class SportService(private val sportRepository: SportRepository) {

    fun getAll(): List<Sport> {
        return sportRepository.findAll()
    }

    // Добавьте здесь другие методы, такие как createUser, updateUser, deleteUser и т.д.
}