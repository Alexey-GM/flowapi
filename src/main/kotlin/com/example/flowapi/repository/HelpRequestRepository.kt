package com.example.flowapi.repository

import com.example.flowapi.model.HelpRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HelpRequestRepository : JpaRepository<HelpRequest, Int> {
    fun findByToUserId(toUserId: Int): List<HelpRequest>
}

