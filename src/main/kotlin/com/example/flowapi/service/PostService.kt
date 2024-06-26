package com.example.flowapi.service

import com.example.flowapi.controller.post.CreateCommentRequest
import com.example.flowapi.controller.post.CreatePostRequest
import com.example.flowapi.controller.post.dto.PostCommentDto
import com.example.flowapi.controller.post.dto.PostDto
import com.example.flowapi.controller.toDto
import com.example.flowapi.model.Post
import com.example.flowapi.model.PostComment
import com.example.flowapi.model.PostLike
import com.example.flowapi.repository.PostCommentRepository
import com.example.flowapi.repository.PostLikeRepository
import com.example.flowapi.repository.PostRepository
import com.example.flowapi.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val postLikeRepository: PostLikeRepository,
    private val postCommentRepository: PostCommentRepository,
) {
    private val applicationUserId = 1  // Предположим, что id приложения равен 1

    fun getPostsFromApplication(userId: Int): List<PostDto> {
        val posts = postRepository.findByUserId(applicationUserId)
        return posts.map { it.toDto(userHasLikedPost(userId, it.id)) }
    }

    fun getUserPosts(requestingUserId: Int, userId: Int): List<PostDto> {
        val posts = postRepository.findByUserId(userId)
        return posts.map { it.toDto(userHasLikedPost(requestingUserId, it.id)) }
    }

    fun getPost(requestingUserId: Int, postId: Int): PostDto {
        val post = postRepository.findById(postId).orElseThrow { IllegalArgumentException("Post not found") }
        return post.toDto(userHasLikedPost(requestingUserId, post.id))
    }

    fun createPost(userId: Int, createPostRequest: CreatePostRequest): PostDto {
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("User not found") }
        val post = Post(
            text = createPostRequest.text,
            media = createPostRequest.media,
            user = user,
            date = LocalDateTime.now().toString(),
        )
        val savedPost = postRepository.save(post)
        return savedPost.toDto(userHasLikedPost(userId, savedPost.id))
    }

    fun deletePost(userId: Int, postId: Int) {
        val post = postRepository.findById(postId).orElseThrow { IllegalArgumentException("Post not found") }
        if (post.user.id != userId) {
            throw IllegalAccessException("User not authorized to delete this post")
        }
        postRepository.delete(post)
    }

    fun addComment(userId: Int, postId: Int, createCommentRequest: CreateCommentRequest): PostCommentDto {
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("User not found") }
        val post = postRepository.findById(postId).orElseThrow { IllegalArgumentException("Post not found") }
        val comment = PostComment(
            text = createCommentRequest.text,
            user = user,
            post = post,
            date = LocalDateTime.now().toString()
        )
        val savedComment = postCommentRepository.save(comment)
        return savedComment.toDto()
    }

    fun getPostsByUserSubscriptions(userId: Int): List<PostDto> {
        val posts = postRepository.findPostsByUserSubscriptions(userId)
        return posts.map { it.toDto(userHasLikedPost(userId, it.id)) }
    }

    fun addLike(userId: Int, postId: Int) {
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("User not found") }
        val post = postRepository.findById(postId).orElseThrow { IllegalArgumentException("Post not found") }
        val existingLike = postLikeRepository.findByUserIdAndPostId(userId, postId)
        if (existingLike == null) {
            val like = PostLike(user = user, post = post)
            postLikeRepository.save(like)
        }
    }

    fun removeLike(userId: Int, postId: Int) {
        val like = postLikeRepository.findByUserIdAndPostId(userId, postId)
        if (like != null) {
            postLikeRepository.delete(like)
        }
    }

    private fun userHasLikedPost(userId: Int, postId: Int): Boolean {
        return postLikeRepository.findByUserIdAndPostId(userId, postId) != null
    }
}