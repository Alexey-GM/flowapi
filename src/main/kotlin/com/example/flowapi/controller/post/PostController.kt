package com.example.flowapi.controller.post

import com.example.flowapi.controller.post.dto.PostCommentDto
import com.example.flowapi.controller.post.dto.PostDto
import com.example.flowapi.model.Post
import com.example.flowapi.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postService: PostService
) {
    @GetMapping("/application")
    fun getPostsFromApplication(): ResponseEntity<List<PostDto>> {
        val posts = postService.getPostsFromApplication()
        return ResponseEntity.ok(posts)
    }

    @GetMapping("/user-posts")
    fun getUserPosts(@RequestParam userId: Int): ResponseEntity<List<PostDto>> {
        val posts = postService.getUserPosts(userId)
        return ResponseEntity.ok(posts)
    }

    @GetMapping("/{postId}")
    fun getPost(@PathVariable postId: Int): ResponseEntity<PostDto> {
        val posts = postService.getPost(postId)
        return ResponseEntity.ok(posts)
    }

    @GetMapping("/subscriptions")
    fun getPostsFromSubscriptions(): ResponseEntity<List<PostDto>> {
        val userId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        val posts = postService.getPostsFromSubscriptions(userId)
        return ResponseEntity.ok(posts)
    }
    @PostMapping
    fun createPost(@RequestBody createPostRequest: CreatePostRequest): ResponseEntity<PostDto> {
        val userId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        val post = postService.createPost(userId, createPostRequest)
        return ResponseEntity.ok(post)
    }

    @DeleteMapping("/{postId}")
    fun deletePost(@PathVariable postId: Int): ResponseEntity<Void> {
        val userId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        postService.deletePost(userId, postId)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{postId}/comments")
    fun addComment(@PathVariable postId: Int, @RequestBody createCommentRequest: CreateCommentRequest): ResponseEntity<PostCommentDto> {
        val userId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        val comment = postService.addComment(userId, postId, createCommentRequest)
        return ResponseEntity.ok(comment)
    }

    @PostMapping("/{postId}/like")
    fun addLike(@PathVariable postId: Int): ResponseEntity<Void> {
        val userId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        postService.addLike(userId, postId)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{postId}/like")
    fun removeLike(@PathVariable postId: Int): ResponseEntity<Void> {
        val userId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        postService.removeLike(userId, postId)
        return ResponseEntity.ok().build()
    }
}