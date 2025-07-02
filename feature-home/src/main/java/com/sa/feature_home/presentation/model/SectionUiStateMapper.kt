package com.sa.thmanyah.feature.home.presentation.model//package com.sa.thmanyah.feature.home.presentation.model
//
//
//import com.sa.thmanyah.feature.home.domain.entities.ContentEntity
//import com.sa.thmanyah.feature.home.domain.entities.HomeSectionEntity
//
//fun HomeSectionEntity.toSectionUiState() = SectionUiModel(
//    title = name,
//    type = type,
//    contentType = contentType,
//    order = order,
//    content = content.map { it.toSectionContentUiState() }
//)
//
//fun ContentEntity.toSectionContentUiState() = SectionContentUiState(
//    id = id,
//    name = name,
//    description = description,
//    avatarUrl = avatarUrl,
//    episodeCount = episodeCount,
//    duration = duration,
//    language = language,
//    priority = priority,
//    popularityScore = popularityScore,
//    score = score,
//    authorName = authorName,
//    releaseDate = releaseDate,
//)