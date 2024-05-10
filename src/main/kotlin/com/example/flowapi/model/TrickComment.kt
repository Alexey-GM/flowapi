package com.example.flowapi.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "trick_comments")
data class TrickComment (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trick_id", nullable = false)
    val trick: Trick,

    @Column(nullable = false)
    val comment: String = "",

    @Column(name = "comment_date", nullable = false)
    val commentDate: String = LocalDateTime.now().toString()
)