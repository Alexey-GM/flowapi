package com.example.flowapi.controller

import com.example.flowapi.controller.post.dto.PostCommentDto
import com.example.flowapi.controller.post.dto.PostDto
import com.example.flowapi.controller.post.dto.UserDto
import com.example.flowapi.controller.sport.SportResponse
import com.example.flowapi.controller.tricks.StepResponse
import com.example.flowapi.controller.tricks.TricksResponse
import com.example.flowapi.controller.user.UserRequest
import com.example.flowapi.model.*

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

fun Trick.toResponse(userTrickStatus: TrickStatus? = null): TricksResponse {
    return TricksResponse(
        id = this.id,
        name = this.name,
        description = this.description,
        category = this.category,
        imageUrl = this.imageUrl,
        difficulty = this.difficulty,
        steps = this.steps.map { it.toResponse() },
        comments = this.comments,
        status = userTrickStatus ?: TrickStatus.NOT_LEARNED // Установка статуса по умолчанию
    )
}

fun TrickStep.toResponse(): StepResponse {
    return StepResponse(
        id = this.id,
        stepNumber = this.stepNumber,
        description = this.description,
    )
}

fun Post.toDto(): PostDto {
    return PostDto(
        id = this.id,
        text = this.text ?: "",
        date = this.date.toString(),
        comments = this.comments.map { it.toDto() },
        likes = this.likes.size,
        user = this.user.toDto(),
        media = this.media ?: ""
    )
}

fun User.toDto(): UserDto {
    return UserDto(
        id = this.id,
        imageUrl = this.imageUrl,
        email = this.mail,
        firstName = this.firstName,
        lastName = this.lastName,
        birthDate = this.dateOfBirth,
        city = this.city,
        status = this.status
    )
}

fun PostComment.toDto(): PostCommentDto {
    return PostCommentDto(
        id = this.id,
        user = this.user.toDto(),
        likes = 0,
        text = this.text,
        date = this.date.toString()
    )
}
