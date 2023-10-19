package com.eventhub.dto

data class CreateEventDto(
    val name: String,
    val description: String,
    val userId: String
)