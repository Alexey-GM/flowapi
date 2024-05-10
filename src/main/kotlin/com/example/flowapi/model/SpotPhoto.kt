package com.example.flowapi.model

import jakarta.persistence.*

@Entity
@Table(name = "spot_photos")
data class SpotPhoto (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id", nullable = false)
    val spot: Spot,

    @Column(nullable = false, name = "image_url")
    val imageUrl: String = "",

    @Column(nullable = false, name = "photo_date")
    val photoDate: String
)