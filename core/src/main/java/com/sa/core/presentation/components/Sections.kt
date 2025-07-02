package com.sa.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.sa.core.domain.entities.ContentType
import com.sa.core.domain.entities.SectionType

import com.sa.core.presentation.uiModel.SectionUiModel


@Composable
fun SectionContainerItem(
    section: SectionUiModel, onItemClick: (String, ContentType) -> Unit = { _, _ -> }
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 8.dp)
    ) {
        SectionHeaderView(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            title = section.title,
            titleColor = MaterialTheme.colorScheme.onBackground
        )

        when (section.type) {
            SectionType.SQUARE -> {
                SquareSection(
                    modifier = Modifier, section = section, onItemClick = onItemClick
                )
            }

            SectionType.TWO_LINES_GRID -> {
                TwoLinesGridSection(
                    modifier = Modifier.fillMaxWidth(), section
                )
            }

            SectionType.BIG_SQUARE -> {
                BigSquareSection(
                    modifier = Modifier, section = section, onItemClick = onItemClick
                )
            }

            SectionType.QUEUE -> {
                QueueSection(Modifier.fillMaxWidth(), section, onItemClick)
            }

            else -> {}
        }
    }
}


@Composable
fun SquareSection(
    modifier: Modifier, section: SectionUiModel, onItemClick: (String, ContentType) -> Unit
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(section.content) { item ->
            SquareItem(
                modifier = Modifier
                    .width(130.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(onClick = {
                        onItemClick(item.id, section.contentType)
                    }), itemContent = item
            )
        }
    }
}

@Composable
fun BigSquareSection(
    modifier: Modifier, section: SectionUiModel, onItemClick: (String, ContentType) -> Unit
) {
    val width = LocalConfiguration.current.screenWidthDp.dp * 0.6f
    val height = width * 0.7f
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(section.content) { item ->
            BigSquareItem(
                modifier = Modifier
                    .width(width)
                    .height(height)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(onClick = {
                        onItemClick(item.id, section.contentType)
                    }), itemContent = item
            )
        }
    }
}

@Composable
fun QueueSection(
    modifier: Modifier, section: SectionUiModel, onItemClick: (String, ContentType) -> Unit
) {
    val itemWidth = LocalConfiguration.current.screenWidthDp.dp * 0.75f
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(section.content) { item ->
            QueueItem(
                modifier = Modifier
                    .width(itemWidth)
                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                    .padding(12.dp)
                    .clickable(onClick = {
                        onItemClick(item.id, section.contentType)
                    }), episode = item
            )
        }
    }
}

/**
 * Horizontal pager showing multiple pages, with each page containing
 * two vertical items - exactly as shown in the reference image
 */
@Composable
fun TwoLinesGridSection(
    modifier: Modifier = Modifier,
    section: SectionUiModel,
    onItemClick: (String) -> Unit = {},
    onPlayClick: (String) -> Unit = {},
    onMoreClick: (String) -> Unit = {},
    onPlaylistClick: (String) -> Unit = {},
) {
    val pairedItems by remember {
        derivedStateOf {
            section.content.sortedByDescending { it.priority }.chunked(2)
        }
    }
    val pagerState = rememberPagerState(pageCount = { pairedItems.size })
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val pageWidth = screenWidth * 0.8f

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        HorizontalPager(
            state = pagerState, contentPadding = PaddingValues(
                start = 16.dp,
                end = (screenWidth - pageWidth - 16.dp) // Adjusted to show exactly 10% of next page
            ), pageSpacing = 8.dp, modifier = Modifier.fillMaxWidth()
        ) { page ->
            // Each page contains two vertical items
            Column(
                modifier = Modifier
                    .width(pageWidth)
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // First item
                pairedItems[page].getOrNull(0)?.let { item ->
                    TwoLinesGridItem(
                        item = item,
                        onItemClick = { onItemClick(item.id) },
                        onPlayClick = { onPlayClick(item.id) },
                        onMoreClick = { onMoreClick(item.id) },
                        onPlaylistClick = { onPlaylistClick(item.id) })
                }

                // Second item (if exists)
                pairedItems[page].getOrNull(1)?.let { item ->
                    TwoLinesGridItem(
                        item = item,
                        onItemClick = { onItemClick(item.id) },
                        onPlayClick = { onPlayClick(item.id) },
                        onMoreClick = { onMoreClick(item.id) },
                        onPlaylistClick = { onPlaylistClick(item.id) })
                }
            }
        }
    }
}