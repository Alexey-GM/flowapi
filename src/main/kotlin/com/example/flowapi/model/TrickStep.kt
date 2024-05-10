package com.example.flowapi.model

import jakarta.persistence.*

@Entity
@Table(name = "trick_steps")
data class TrickStep (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(name = "step_number", nullable = false)
    val stepNumber: Int = 0,

    @Column(nullable = false)
    val description: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trick_id", nullable = false)
    val trick: Trick
)

