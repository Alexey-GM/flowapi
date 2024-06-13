package com.example.flowapi.exception

class ApiException(val status: Int, message: String) : RuntimeException(message)

