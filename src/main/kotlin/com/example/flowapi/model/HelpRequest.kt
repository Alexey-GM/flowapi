package com.example.flowapi.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "help_requests")
data class HelpRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "from_user_id", nullable = false)
    val fromUser: User,

    @ManyToOne
    @JoinColumn(name = "to_user_id", nullable = false)
    val toUser: User,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val videoUrl: String,

    @Column(nullable = true)
    var reply: String?,

    @Column(nullable = true)
    var replyDate: String?,

    @Column(nullable = false)
    val requestDate: String = LocalDateTime.now().toString()
)