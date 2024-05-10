package com.example.flowapi.model

import jakarta.persistence.*

@Entity
@Table(name = "spots")
data class Spot (
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

    @OneToMany(mappedBy = "spot", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val photos: List<SpotPhoto> = mutableListOf()
)