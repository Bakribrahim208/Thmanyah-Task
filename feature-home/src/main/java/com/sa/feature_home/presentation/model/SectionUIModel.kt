package com.sa.feature_home.presentation.model

import com.sa.feature_home.domain.entities.ContentType
import com.sa.feature_home.domain.entities.SectionType


data class SectionsListUiModel(
    val sections: List<SectionUiModel> = emptyList(),
    val headersCategories: List<String> = emptyList(),
    val selectedCategoryIndex: Int? = null,
    val error: String? = null,
    val snackError: String? = null,
    val showFullLoading: Boolean = false,
    val showFooterLoading: Boolean = false,
    val showPullToRefresh: Boolean = false,
    val showEmptyView: Boolean = false,
)

data class SectionUiModel(
    val title: String = "",
    val subtitle: String? = null,
    val type: SectionType = SectionType.UNKNOWN,
    val contentType: ContentType = ContentType.UNKNOWN,
    val order: Int = 0,
//    val titleColor: Color = ThemeColors.get.onBackground,
    val content: List<SectionContentUiModel> = emptyList()
)

data class SectionContentUiModel(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val avatarUrl: String = "",
    val episodeCountLabel: String = "",
    val duration: String = "",
    val score: Int? = null,
    val priority: Int = 0,
    val popularityScore: Int? = null,
    val authorName: String? = null,
    val releaseDate: String? = null,
)
