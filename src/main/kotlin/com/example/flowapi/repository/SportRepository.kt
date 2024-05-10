package com.example.flowapi.repository

import com.example.flowapi.model.Sport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SportRepository: JpaRepository<Sport, Int> {
}