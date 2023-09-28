package com.plcoding.composepaging3caching.domain

data class Beer(
    val id: Int,
    val name: String,
    val tagline: String,
    val desc: String,
    val firstBrewed: String,
    val url: String?
)
