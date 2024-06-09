package com.example.flowapi.model

import jakarta.persistence.*

@Entity
@Table(name = "posts")
data class Post (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = true)
    val text: String? = "",

    @Column(nullable = false)
    val date: String = "",

    @Column(nullable = true)
    val media: String? = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val comments: List<PostComment> = mutableListOf(),

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val likes: List<PostLike> = mutableListOf()
)