package com.example.flowapi.config

import com.example.flowapi.repository.UserRepository
import com.example.flowapi.service.CustomUserDetailsService
import com.example.flowapi.service.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val userRepository: UserRepository
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader: String? = request.getHeader("Authorization")
        if (authHeader.doesNotContainBearerToken()) {
            filterChain.doFilter(request, response)
            return
        }

        val jwt = authHeader!!.extractTokenValue()
        val email = tokenService.extractEmail(jwt)



        if (email != null && SecurityContextHolder.getContext().authentication == null) {
            val foundUser = userDetailsService.loadUserByUsername(email)
            val id = userRepository.findByMail(email)!!.id
            if (tokenService.isValid(jwt, foundUser)) {
                updateContext(foundUser, id, request)
            }

            filterChain.doFilter(request, response)
        }
    }

    private fun updateContext(foundUser: UserDetails, userId: Int, request: HttpServletRequest) {
        val authToken = UsernamePasswordAuthenticationToken(foundUser.username, null, foundUser.authorities)
        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        authToken.details = userId
        SecurityContextHolder.getContext().authentication = authToken
    }

    private fun String?.doesNotContainBearerToken(): Boolean =
        this == null || !this.startsWith("Bearer")

    private fun String.extractTokenValue(): String =
        this.substringAfter("Bearer ")
}
