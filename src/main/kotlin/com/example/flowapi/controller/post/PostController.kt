package com.example.flowapi.controller.post

import com.example.flowapi.controller.post.dto.PostCommentDto
import com.example.flowapi.controller.post.dto.PostDto
import com.example.flowapi.model.Post
import com.example.flowapi.service.FileService
import com.example.flowapi.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postService: PostService,
    private val fileService: FileService
) {
    @GetMapping("/application")
    fun getPostsFromApplication(): ResponseEntity<List<PostDto>> {
        val userId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        val posts = postService.getPostsFromApplication(userId)
        return ResponseEntity.ok(posts)
    }

    @GetMapping("/user-posts")
    fun getUserPosts(@RequestParam userId: Int): ResponseEntity<List<PostDto>> {
        val requestingUserId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        val posts = postService.getUserPosts(requestingUserId, userId)
        return ResponseEntity.ok(posts)
    }

    @GetMapping("/{postId}")
    fun getPost(@PathVariable postId: Int): ResponseEntity<PostDto> {
        val requestingUserId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        val posts = postService.getPost(requestingUserId, postId)
        return ResponseEntity.ok(posts)
    }

    @GetMapping("/subscriptions")
    fun getPostsFromSubscriptions(): ResponseEntity<List<PostDto>> {
        val userId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        val posts = postService.getPostsByUserSubscriptions(userId)
        return ResponseEntity.ok(posts)
    }

    @PostMapping(consumes = ["multipart/form-data"])
    fun createPost(
        @RequestPart("file") file: MultipartFile?,
        @RequestPart("post") createPostRequest: CreatePostRequest
    ): ResponseEntity<Void> {
        val userId = SecurityContextHolder.getContext().authentication.details.toString().toInt()
        var mediaUrl: String? = null

        if (createPostRequest.media.isNullOrEmpty()) {
            file?.let {
                mediaUrl = try {
                    fileService.saveFile(it)
                } catch (e: Exception) {
                    return ResponseEntity.status(500).body(null)
                }
            }
        } else {
            mediaUrl = createPostRequest.media
        }

        val updatedPostRequest = createPostRequest.copy(media = mediaUrl)
        val post = postService.createPost(userId, updatedPostRequest)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        return try {
            fileService.saveFile(file)
            ResponseEntity.ok("File uploaded successfully: ${file.originalFilename}")
        } catch (e: Exception) {
            ResponseEntity.status(500).body("Failed to upload file: ${file.originalFilename}")
        }
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
