package com.example.flowapi.model

import jakarta.persistence.*

@Entity
@Table(name = "trick_video")
data class TrickVideo (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(name = "url", nullable = false)
    val url: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trick_id", nullable = false)
    val trick: Trick
)