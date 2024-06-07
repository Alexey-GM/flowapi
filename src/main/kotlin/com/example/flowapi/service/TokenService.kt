package com.example.flowapi.service

import com.example.flowapi.config.JwtProperties
import com.example.flowapi.repository.UserRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(jwtProperties: JwtProperties, private val userRepository: UserRepository) {
    private val secretKey = Keys.hmacShaKeyFor(
        jwtProperties.key.toByteArray()
    )

    fun generate(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String {

        val userId = userRepository.findByMail(userDetails.username)?.id.toString()
        return Jwts.builder()
            .claims()
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(expirationDate)
            .add(additionalClaims)
            .add("userId", userId)
            .and()
            .signWith(secretKey)
            .compact()
    }

    fun extractEmail(token: String): String? {
        return getAllClaims(token).subject
    }

    fun isExpired(token: String): Boolean {
        return getAllClaims(token).expiration.before(Date(System.currentTimeMillis()))
    }

    fun isValid(token: String, userDetails: UserDetails): Boolean {
        val email = extractEmail(token)
        return userDetails.username == email && !isExpired(token)
    }

    private fun getAllClaims(token: String): Claims {
        val parser = Jwts.parser()
            .verifyWith(secretKey)
            .build()
        return parser.parseSignedClaims(token).payload
    }
}