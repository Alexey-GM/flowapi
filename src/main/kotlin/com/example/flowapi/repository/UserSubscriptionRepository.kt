package com.example.flowapi.repository

import com.example.flowapi.model.UserSubscription
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserSubscriptionRepository : JpaRepository<UserSubscription, Int> {
    fun findByUserId(userId: Int): List<UserSubscription>
    fun findBySubscriberId(subscriberId: Int): List<UserSubscription>
    fun findByUserIdAndSubscriberId(userId: Int, subscriberId: Int): UserSubscription?
    fun existsByUserIdAndSubscriberId(userId: Int, subscriberId: Int): Boolean
    fun deleteByUserIdAndSubscriberId(userId: Int, subscriberId: Int)
}
