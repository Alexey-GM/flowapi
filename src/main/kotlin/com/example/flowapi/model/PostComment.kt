package com.example.flowapi.model

import jakarta.persistence.*

@Entity
@Table(name = "post_comments")
data class PostComment (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false)
    val text: String = "",

    @Column(nullable = false)
    val date: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    val post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User
)