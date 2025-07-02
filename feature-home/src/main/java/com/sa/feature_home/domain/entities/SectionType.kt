package com.sa.feature_home.domain.entities

enum class SectionType {
    SQUARE,
    BIG_SQUARE,
    TWO_LINES_GRID,
    QUEUE,
    UNKNOWN;

    companion object {
        fun fromString(type: String): SectionType {
            return when (type.lowercase()) {
                "square" -> SQUARE
                "big_square", "big square" -> BIG_SQUARE
                "2_lines_grid" -> TWO_LINES_GRID
                "queue" -> QUEUE
                else -> UNKNOWN
            }
        }
    }
}