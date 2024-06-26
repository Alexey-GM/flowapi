package com.example.flowapi.repository

import com.example.flowapi.controller.user.UserSportInfo
import com.example.flowapi.model.Sport
import com.example.flowapi.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface UserRepository: JpaRepository<User, Int> {
    fun findByMail(email: String): User?

    @Query("SELECT DISTINCT us.sport FROM UserSport us WHERE us.user.id = :userId")
    fun getUserSports(userId: Int): List<Sport>

    @Query("SELECT COUNT(ut.id) FROM UserTrick ut INNER JOIN UserSport us ON ut.user.id = us.user.id WHERE us.user.id = :userId AND us.sport.id = :sportId")
    fun getTricksCountForSport(userId: Int, sportId: Int): Int

    @Query("SELECT COUNT(s) FROM UserSubscription s WHERE s.user.id = :userId")
    fun countSubscribersByUserId(userId: Int): Int

    @Query("SELECT COUNT(s) FROM UserSubscription s WHERE s.subscriber.id = :userId")
    fun countSubscriptionsByUserId(userId: Int): Int

    @Query("SELECT u.role FROM User u WHERE u.id = :userId")
    fun getUserRole(@Param("userId") userId: Int): String

    @Query("SELECT s.subscriber FROM UserSubscription s WHERE s.user.id = :userId")
    fun findSubscribersByUserId(userId: Int): Set<User>

    @Query("""
        SELECT u FROM User u 
        JOIN UserSport us ON u.id = us.user.id
        WHERE u.role = 'PRO' AND us.sport.id = :sportId
    """)
    fun findAllProfessionalsBySport(@Param("sportId") sportId: Int): List<User>
    @Query("SELECT s.user FROM UserSubscription s WHERE s.subscriber.id = :userId")
    fun findSubscriptionsByUserId(userId: Int): Set<User>

    @Query("SELECT COUNT(s) > 0 FROM UserSubscription s WHERE s.user.id = :userId AND s.subscriber.id = :subscriptionId")
    fun isUserSubscribed(userId: Int, subscriptionId: Int): Boolean
}
