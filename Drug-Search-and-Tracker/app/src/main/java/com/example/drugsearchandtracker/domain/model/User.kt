package com.example.drugsearchandtracker.domain.model

data class User(
    val uid: String,
    val email: String,
    val name: String? = null
) 