package com.example.flowapi.repository

import com.example.flowapi.model.Sport
import com.example.flowapi.model.Trick
import com.example.flowapi.model.UserTrick
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TrickRepository: JpaRepository<Trick, Int> {
    fun getTrickById(id: Int): Trick
    // Получить все трюки по спорту
    fun findBySportId(sportId: Int): List<Trick>
    // Получить трюки по категории (type) и спорту
    fun findBySportIdAndCategory(sportId: Int, type: String): List<Trick>
    // Получить трюки, которые пользователь уже выполнил (по предположению, что startDate и learningDuration не равны null)
    @Query("SELECT ut.trick FROM UserTrick ut WHERE ut.user.id = :userId AND ut.trick.sport.id = :sportId AND ut.startDate IS NOT NULL AND ut.learningDuration IS NOT NULL")
    fun findCompletedTricksByUserIdAndSportId(userId: Int, sportId: Int): List<Trick>
    // Получить трюки, которые пользователь находится в процессе выполнения (startDate не равен null, но learningDuration равен null)
    @Query("SELECT ut.trick FROM UserTrick ut WHERE ut.user.id = :userId AND ut.trick.sport.id = :sportId AND ut.startDate IS NOT NULL AND ut.learningDuration IS NULL")
    fun findInProcessTricksByUserIdAndSportId(userId: Int, sportId: Int): List<Trick>
    // Получить следующие трюки пользователя (где startDate равен null и learningDuration равен null)
    @Query("SELECT ut FROM UserTrick ut WHERE ut.user.id = :userId")
    fun findAllTricksByUserId(userId: Int): List<UserTrick>

    @Query("SELECT td.dependentTrick FROM TrickDependency td WHERE td.baseTrick.id IN :trickIds")
    fun findDependentTricks(trickIds: List<Int>): List<Trick>

    @Query("SELECT t FROM Trick t WHERE t.id NOT IN (SELECT td.dependentTrick.id FROM TrickDependency td) AND t.sport.id = :sportId")
    fun findIndependentTricksBySportId(@Param("sportId") sportId: Int): List<Trick>
}