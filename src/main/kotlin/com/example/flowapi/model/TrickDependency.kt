package com.example.flowapi.model

import jakarta.persistence.*

@Entity
@Table(name = "trick_dependencies")
data class TrickDependency(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_trick_id", nullable = false)
    val baseTrick: Trick,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dependent_trick_id", nullable = false)
    val dependentTrick: Trick
)