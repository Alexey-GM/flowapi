package com.example.flowapi.repository

import com.example.flowapi.model.UserTrick
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserTrickRepository: JpaRepository<UserTrick, Int> {
    fun findByUserIdAndTrickId(userId: Int, trickId: Int): UserTrick?
}