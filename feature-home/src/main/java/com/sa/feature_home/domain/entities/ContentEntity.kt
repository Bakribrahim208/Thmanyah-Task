package com.sa.feature_home.domain.entities

data class ContentEntity(
    val id: String,
    val name: String,
    val description: String,
    val avatarUrl: String,
    val episodeCount: Int,
    val duration: Long,
    val language: String,
    val priority: Int,
    val popularityScore: Int,
    val score: Double?,
    val authorName: String? = null,
    val releaseDate: String? = null,
)