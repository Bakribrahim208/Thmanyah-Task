package com.sa.core.presentation.uiModel

import com.sa.core.domain.entities.ContentEntity
import com.sa.core.domain.entities.HomeSectionEntity


fun HomeSectionEntity.toSectionUiModel() = SectionUiModel(
    title = name, type = type, contentType = contentType, order = order,
//    titleColor = if(type == SectionType.SQUARE) LightSecondary else ThemeColors.get.onBackground,
    content = content.map { it.toSectionContentUiModel() })

/**
 * Formats duration from seconds to a readable minutes:seconds format
 * @param seconds The duration in seconds
 * @return String in format "mm:ss" or "h:mm:ss" for longer durations
 */
fun formatDuration(seconds: Long): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val remainingSeconds = seconds % 60

    return when {
        hours > 0 -> String.format("%d:%02d:%02d", hours, minutes, remainingSeconds)
        else -> String.format("%d:%02d", minutes, remainingSeconds)
    }
}

fun ContentEntity.toSectionContentUiModel() = SectionContentUiModel(
    id = id,
    name = name,
    description = description,
    avatarUrl = avatarUrl,
    episodeCountLabel = "$episodeCount ",
    duration = formatDuration(duration),
    priority = priority,
    popularityScore = popularityScore.div(10),
    score = score?.toInt()?.div(1000),
    authorName = authorName,
    releaseDate = releaseDate
)