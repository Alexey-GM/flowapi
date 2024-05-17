package com.example.flowapi.controller

import com.example.flowapi.controller.sport.SportResponse
import com.example.flowapi.controller.tricks.StepResponse
import com.example.flowapi.controller.tricks.TricksResponse
import com.example.flowapi.controller.user.UserRequest
import com.example.flowapi.model.Sport
import com.example.flowapi.model.Trick
import com.example.flowapi.model.TrickStep
import com.example.flowapi.model.User

fun UserRequest.toModel(): User {
    return User(
        id = 0,
        mail = this.mail,
        firstName = this.firstName,
        lastName = this.lastName,
        password = this.password,
        dateOfBirth = this.dateOfBirth,
        gender = this.gender,
        city = this.city,
        status = "Новичок",
        role = "USER",
    )
}

fun Sport.toResponse(): SportResponse {
    return SportResponse(
        id = this.id,
        name = this.name,
        imgUrl = this.imageUrl,
    )
}

fun Trick.toResponse(): TricksResponse {
    return TricksResponse(
        id = this.id,
        name = this.name,
        description = this.description,
        category = this.category,
        imageUrl = this.imageUrl,
        difficulty = this.difficulty,
        steps = this.steps.map { it.toResponse() },
        comments = this.comments
    )
}

fun TrickStep.toResponse(): StepResponse {
    return StepResponse(
        id = this.id,
        stepNumber = this.stepNumber,
        description = this.description,
    )
}
