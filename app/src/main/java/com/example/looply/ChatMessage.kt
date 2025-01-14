package com.example.looply

data class ChatMessage(
    val sender: String = "",
    val message: String = "",
    val timestamp: Long = 0L
)