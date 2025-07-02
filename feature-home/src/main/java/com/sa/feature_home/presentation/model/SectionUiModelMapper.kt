package com.sa.feature_home.presentation.model

import com.sa.feature_home.domain.entities.ContentEntity
import com.sa.feature_home.domain.entities.HomeSectionEntity


fun HomeSectionEntity.toSectionUiModel() = SectionUiModel(
    title = name, type = type, contentType = contentType, order = order,
//    titleColor = if(type == SectionType.SQUARE) LightSecondary else ThemeColors.get.onBackground,
    content = content.map { it.toSectionContentUiModel() })

fun ContentEntity.toSectionContentUiModel() = SectionContentUiModel(
    id = id,
    name = name,
    description = description,
    avatarUrl = avatarUrl,
    episodeCountLabel = "$episodeCount ",
    duration = duration.toString(),
    priority = priority,
    popularityScore = popularityScore.div(10),
    score = score?.toInt()?.div(1000),
    authorName = authorName,
    releaseDate = releaseDate
)