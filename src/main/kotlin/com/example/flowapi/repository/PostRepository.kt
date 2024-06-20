package com.example.flowapi.repository

import com.example.flowapi.model.Post
import com.example.flowapi.model.PostLike
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PostRepository: JpaRepository<Post, Int> {
    fun findByUserId(userId: Int): List<Post>

    @Query(value = "SELECT p.* FROM posts p " +
            "WHERE p.user_id IN ( " +
            "    SELECT s.user_id " +
            "    FROM user_subscriptions s " +
            "    WHERE s.subscriber_id = :userId " +
            ") " +
            "ORDER BY p.id DESC " +
            "LIMIT 50", nativeQuery = true)
    fun findPostsByUserSubscriptions(@Param("userId") userId: Int): List<Post>

}

@Repository
interface PostLikeRepository: JpaRepository<PostLike, Int> {
    fun findByUserIdAndPostId(userId: Int, postId: Int): PostLike?
}
