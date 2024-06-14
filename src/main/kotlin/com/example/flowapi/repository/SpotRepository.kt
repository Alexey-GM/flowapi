package com.example.flowapi.repository

import com.example.flowapi.model.Spot
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpotRepository: JpaRepository<Spot, Int> {
}