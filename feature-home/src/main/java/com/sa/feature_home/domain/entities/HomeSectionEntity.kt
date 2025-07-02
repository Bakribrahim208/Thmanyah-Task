package com.sa.feature_home.domain.entities

data class HomeSectionEntity(
    val content: List<ContentEntity>,
    val contentType: ContentType,
    val name: String,
    val order: Int,
    val type: SectionType
)