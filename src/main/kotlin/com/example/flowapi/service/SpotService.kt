package com.example.flowapi.service

import com.example.flowapi.controller.spot.SpotDto
import com.example.flowapi.repository.SpotRepository
import org.springframework.stereotype.Service

@Service
class SpotService(private val spotRepository: SpotRepository) {

    fun getAllSpots(): List<SpotDto> {
        val spots = spotRepository.findAll()
        return spots.map { spot ->
            val photoUrl = spot.photos.firstOrNull()?.imageUrl ?: ""
            SpotDto(
                id = spot.id,
                title = spot.name,
                lat = spot.latitude,
                long = spot.longitude,
                description = spot.description,
                imgUrl = photoUrl
            )
        }
    }
}
