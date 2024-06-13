package com.example.flowapi.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ApiException::class)
    fun handleApiException(ex: ApiException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(ex.status, ex.message ?: "Unexpected error")
        return ResponseEntity(errorResponse, HttpStatus.valueOf(ex.status))
    }

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        ex.printStackTrace() // Логирование ошибки в консоль
        val errorResponse = ErrorResponse(500, "Unknown error")
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

data class ErrorResponse(val status: Int, val message: String)
