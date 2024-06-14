package com.example.flowapi.service

import com.example.flowapi.controller.post.dto.UserDto
import com.example.flowapi.controller.toDto
import com.example.flowapi.controller.user.UserSportInfo
import com.example.flowapi.exception.ApiException
import com.example.flowapi.model.Sport
import com.example.flowapi.model.User
import com.example.flowapi.model.UserSport
import com.example.flowapi.model.UserSubscription
import com.example.flowapi.repository.SportRepository
import com.example.flowapi.repository.TrickRepository
import com.example.flowapi.repository.UserRepository
import com.example.flowapi.repository.UserSubscriptionRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository, private val sportRepository: SportRepository, private val trickRepository: TrickRepository, private val userSubscriptionRepository: UserSubscriptionRepository) {

    fun getUser(userId: Int): UserDto {
        val user = userRepository.findById(userId).orElseThrow { ApiException(404, "User not found") }
        return user.toDto()
    }

    fun addSportToUser(userId: Int, sportId: Int) {
        val user = userRepository.findById(userId).orElseThrow { ApiException(404, "User not found") }
        val sport = sportRepository.findById(sportId).orElseThrow { ApiException(404, "Sport not found") }
        val userSport = UserSport(user = user, sport = sport)
        user.sports += userSport
        userRepository.save(user)
    }

    fun getUserSubscribersCount(userId: Int): Int {
        return userRepository.countSubscribersByUserId(userId)
    }

    fun getUserSubscriptionsCount(userId: Int): Int {
        return userRepository.countSubscriptionsByUserId(userId)
    }

    fun getUserSports(userId: Int): List<Sport> {
        val user = userRepository.findById(userId).orElseThrow { ApiException(404, "User not found") }
        return user.sports.map { it.sport }
    }

    fun getUserRole(userId: Int): String {
        return userRepository.getUserRole(userId)
    }

    fun getUserIdFromToken(): Int? {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null && authentication.details is UserDetails) {
            val userDetails = authentication.details as UserDetails
            return userRepository.findByMail(userDetails.username)?.id
        }
        return null
    }

    fun getUserSportsAndTricksCount(userId: Int): List<UserSportInfo> {
        val userSports = userRepository.getUserSports(userId)
        val userSportInfos = mutableListOf<UserSportInfo>()

        for (sport in userSports) {
            val tricksCount = trickRepository.findCompletedTricksByUserIdAndSportId(userId, sport.id)
            userSportInfos.add(UserSportInfo(sport.name, tricksCount.size))
        }

        return userSportInfos
    }

    fun getUserSubscribers(userId: Int): Set<User> {
        return userRepository.findSubscribersByUserId(userId)
    }

    fun getUserSubscriptions(userId: Int): Set<User> {
        return userRepository.findSubscriptionsByUserId(userId)
    }

    fun subscribe(userId: Int, subscriptionId: Int) {
        if (userSubscriptionRepository.existsByUserIdAndSubscriberId(subscriptionId, userId)) {
            throw ApiException(400, "Already subscribed")
        }
        val user = userRepository.findById(userId).orElseThrow { ApiException(404, "User not found") }
        val subscription = userRepository.findById(subscriptionId).orElseThrow { ApiException(404, "Subscription not found") }
        val userSubscription = UserSubscription(user = subscription, subscriber = user)
        user.subscriptions.add(userSubscription)
        userRepository.save(user)
    }

    fun unsubscribe(userId: Int, subscriptionId: Int) {
        val userExists = userRepository.existsById(userId)
        val subscriptionExists = userRepository.existsById(subscriptionId)

        if (!userExists || !subscriptionExists) {
            throw ApiException(404, "User or Subscription not found")
        }

        val userSubscription = userSubscriptionRepository.findByUserIdAndSubscriberId(subscriptionId, userId)
            ?: throw ApiException(400, "Subscription not found")

        userSubscriptionRepository.delete(userSubscription)
    }



    fun isUserSubscribed(userId: Int, subscriptionId: Int): Boolean {
        return userSubscriptionRepository.existsByUserIdAndSubscriberId(subscriptionId, userId)
    }
}

