package com.example.flowapi.repository

import com.example.flowapi.model.PostComment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostCommentRepository: JpaRepository<PostComment, Int>