package com.example.flowapi.repository

import com.example.flowapi.model.Post
import com.example.flowapi.model.PostLike
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PostRepository: JpaRepository<Post, Int> {
    fun findByUserId(userId: Int): List<Post>

    @Query("SELECT p FROM Post p WHERE p.user.id IN :userIds ORDER BY p.id DESC")
    fun findPostsByUserIds(userIds: List<Int>, pageable: Pageable): List<Post>
}

@Repository
interface PostLikeRepository: JpaRepository<PostLike, Int> {
    fun findByUserIdAndPostId(userId: Int, postId: Int): PostLike?
}
