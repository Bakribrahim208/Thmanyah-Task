package com.sa.core.data.mapper

import com.sa.core.data.model.Content
import com.sa.core.data.model.Section
import com.sa.core.domain.entities.ContentEntity
import com.sa.core.domain.entities.ContentType
import com.sa.core.domain.entities.HomeSectionEntity
import com.sa.core.domain.entities.SectionType

fun Section.toSectionEntity(): HomeSectionEntity {
    val contentType = ContentType.fromString(contentType.orEmpty())
    return HomeSectionEntity(
        name = name.orEmpty(),
        type = SectionType.fromString(type.orEmpty()),
        contentType = contentType,
        order = order ?: 0,
        content = content?.map { it.toSectionContentEntity(contentType) } ?: emptyList()
    )
}

fun Content.toSectionContentEntity(contentType: ContentType): ContentEntity = ContentEntity(
    id = when (contentType) {
        ContentType.PODCAST -> podcastId ?: ""
        ContentType.EPISODE -> episodeId ?: ""
        ContentType.AUDIO_BOOK -> audiobookId ?: ""
        ContentType.AUDIO_ARTICLE -> articleId ?: ""
        else -> ""
    },
    name = name.orEmpty(),
    description = description.orEmpty(),
    avatarUrl = avatarUrl.orEmpty(),
    episodeCount = episodeCount ?: 0,
    duration = duration ?: 0,
    language = language ?: "ar",
    priority = priority ?: 0,
    popularityScore = popularityScore ?: 0,
    score = score
)