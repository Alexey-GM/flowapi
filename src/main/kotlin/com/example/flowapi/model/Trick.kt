package com.example.flowapi.model

import jakarta.persistence.*

@Entity
@Table(name = "tricks")
data class Trick (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false)
    val name: String = "",

    @Column(nullable = false)
    val description: String = "",

    @Column(nullable = false, name = "image_url")
    val imageUrl: String = "",

    @Column(nullable = false)
    val difficulty: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sport_id", nullable = false)
    val sport: Sport,

    @OneToMany(mappedBy = "trick", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val users: List<UserTrick> = mutableListOf(),

    @OneToMany(mappedBy = "trick", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val comments: List<TrickComment> = mutableListOf()
)