package com.example.flowapi.repository

import com.example.flowapi.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Int> {
    fun findByMail(email: String): User?

    fun save(user: User): User
}