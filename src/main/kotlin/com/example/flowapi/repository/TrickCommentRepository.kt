package com.example.flowapi.repository

import com.example.flowapi.model.TrickComment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TrickCommentRepository : JpaRepository<TrickComment, Int> {
    fun findByTrickId(trickId: Int): List<TrickComment>
}