package com.example.flowapi.service

import com.example.flowapi.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser = com.example.flowapi.model.User

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByMail(username)
            ?.mapToUserDetails()
            ?: throw UsernameNotFoundException("User not found")
    }

    private fun ApplicationUser.mapToUserDetails(): UserDetails {
        return User.builder()
            .username(this.mail)
            .password(this.password)
            .roles(this.role)
            .build()
    }
}