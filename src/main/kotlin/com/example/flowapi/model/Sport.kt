package com.example.flowapi.model

import jakarta.persistence.*

@Entity
@Table(name = "sports")
data class Sport(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false, unique = true)
    val name: String = "",

    @Column(nullable = false, name = "image_url")
    val imageUrl: String = "",

    @OneToMany(mappedBy = "sport", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val tricks: List<Trick> = mutableListOf(),

    @OneToMany(mappedBy = "sport", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val users: List<UserSport> = mutableListOf(),

    @OneToMany(mappedBy = "sport", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val events: List<Event> = mutableListOf()
)