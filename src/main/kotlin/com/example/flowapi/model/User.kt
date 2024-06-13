package com.example.flowapi.model

import jakarta.persistence.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false, unique = true)
    val mail: String = "",

    @Column(name = "image_url", nullable = false)
    val imageUrl: String = "",

    @Column(nullable = false)
    var password: String = "",

    @Column(name = "first_name", nullable = false)
    val firstName: String = "",

    @Column(name = "last_name", nullable = false)
    val lastName: String = "",

    @Column(name = "date_of_birth", nullable = false)
    val dateOfBirth: String = "",

    @Column(nullable = false)
    val gender: String = "",

    @Column(nullable = false)
    val city: String = "",

    @Column(nullable = false)
    val status: String = "",

    @Column(nullable = false)
    val role: String = "",

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var sports: List<UserSport> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val tricks: List<UserTrick> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val comments: List<TrickComment> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val events: List<Event> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val posts: List<Post> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val likes: List<PostLike> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val subscriptions: MutableList<UserSubscription> = mutableListOf(),

    @OneToMany(mappedBy = "subscriber", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val subscribers: MutableSet<UserSubscription> = mutableSetOf()
) {
    fun comparePassword(password: String): Boolean {
        return BCryptPasswordEncoder().matches(password, this.password)
    }

    fun encodeAndSetPassword(value: String) {
        val passwordEncoder = BCryptPasswordEncoder()
        this.password = passwordEncoder.encode(value)
    }
}
