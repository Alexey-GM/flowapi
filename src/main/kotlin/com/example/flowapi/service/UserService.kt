package com.example.flowapi.service

import com.example.flowapi.model.User
import com.example.flowapi.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun createUser(user: User): User {
        user.encodeAndSetPassword(user.password)
        return userRepository.save(user)
    }
}