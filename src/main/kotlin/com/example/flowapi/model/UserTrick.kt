package com.example.flowapi.model

import jakarta.persistence.*

@Entity
@Table(name = "user_tricks")
data class UserTrick (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trick_id", nullable = false)
    val trick: Trick,

    @Column(name = "start_date", nullable = true)
    val startDate: String?,

    @Column(name = "learning_duration", nullable = true)
    val learningDuration: Int?
)