package com.example.flowapi.controller

import com.example.flowapi.controller.help.HelpRequestDTO
import com.example.flowapi.controller.help.HelpRequestResponse
import com.example.flowapi.controller.post.dto.PostCommentDto
import com.example.flowapi.controller.post.dto.PostDto
import com.example.flowapi.controller.post.dto.UserDto
import com.example.flowapi.controller.sport.SportResponse
import com.example.flowapi.controller.tricks.StepResponse
import com.example.flowapi.controller.tricks.TrickCommentDto
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
        comments = this.comments.map {it.toDto()},
        status = userTrickStatus ?: TrickStatus.NOT_LEARNED,
        videos = this.videos.map { it.url }
    )
}

fun TrickComment.toDto(): TrickCommentDto {
    return TrickCommentDto(
        id = this.id,
        user = this.user.toDto(),
        comment = this.comment,
        commentDate = this.commentDate
    )
}

fun TrickStep.toResponse(): StepResponse {
    return StepResponse(
        id = this.id,
        stepNumber = this.stepNumber,
        description = this.description,
    )
}

fun Post.toDto(isLiked: Boolean): PostDto {
    return PostDto(
        id = this.id,
        text = this.text ?: "",
        date = this.date,
        comments = this.comments.map { it.toDto() },
        likes = this.likes.size,
        user = this.user.toDto(),
        media = this.media ?: "",
        isLiked = isLiked
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

fun HelpRequest.toResponse(): HelpRequestResponse {
    return HelpRequestResponse(
        id = this.id,
        description = this.description,
        videoUrl = this.videoUrl,
        fromUser = this.fromUser.toDto(),
        toUser = this.toUser.toDto(),
        reply = this.reply,
        replyDate = this.replyDate,
        requestDate = this.requestDate
    )
}

fun UserRequest.toModel(imageUrl: String? = null): User {
    return User(
        mail = this.mail,
        password = this.password,
        firstName = this.firstName,
        lastName = this.lastName,
        dateOfBirth = this.dateOfBirth,
        gender = this.gender,
        city = this.city,
        imageUrl = imageUrl ?: ""
    )
}
