package com.sa.feature_home.domain.entities

enum class ContentType {
    PODCAST,
    EPISODE,
    AUDIO_BOOK,
    AUDIO_ARTICLE,
    UNKNOWN;

    companion object {
        fun fromString(type: String): ContentType {
            return when (type.lowercase()) {
                "podcast" -> PODCAST
                "episode" -> EPISODE
                "audio_book" -> AUDIO_BOOK
                "audio_article" -> AUDIO_ARTICLE
                else -> UNKNOWN
            }
        }
    }
}