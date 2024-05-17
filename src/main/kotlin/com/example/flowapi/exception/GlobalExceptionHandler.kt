package com.example.flowapi.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(e: Exception): ResponseEntity<Any> {
        val statusCode = if (e is ApiException) e.statusCode else 500
        val message = e.message ?: "Unknown error"
        return ResponseEntity.status(statusCode).body(mapOf("error" to message))
    }
}
