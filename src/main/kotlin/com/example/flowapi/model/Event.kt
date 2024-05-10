package com.example.flowapi.model

import jakarta.persistence.*

@Entity
@Table(name = "events")
data class Event (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false)
    val name: String = "",

    @Column(nullable = false)
    val description: String = "",

    @Column(nullable = false)
    val latitude: Double = 0.0,

    @Column(nullable = false)
    val longitude: Double = 0.0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sport_id", nullable = false)
    val sport: Sport,

    @Column(name = "image_url")
    val imageUrl: String? = null
)
