package com.sa.core.domain.entities

data class HomeSectionEntity(
    val content: List<ContentEntity>,
    val contentType: ContentType,
    val name: String,
    val order: Int,
    val type: SectionType
)