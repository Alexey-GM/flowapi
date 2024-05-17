package com.example.flowapi.exception

class ApiException(val statusCode: Int, override val message: String): RuntimeException()


