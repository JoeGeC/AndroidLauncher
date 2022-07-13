package com.joebarker.domain.entities

data class ErrorEntity(val error: ErrorMessage)

data class ErrorMessage(val message: String)