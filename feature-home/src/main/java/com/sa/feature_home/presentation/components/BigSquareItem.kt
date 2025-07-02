package com.sa.feature_home.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sa.core.presentation.DefaultImage
import com.sa.feature_home.presentation.model.SectionContentUiModel

@Composable
fun BigSquareItem(
    modifier: Modifier,
    itemContent: SectionContentUiModel,
) {
    Box(modifier = modifier) {
        DefaultImage(
            imageUrl = itemContent.avatarUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            Modifier
                .fillMaxWidth()
//                .background(
//                    brush = Brush.verticalGradient(
//                        colors = listOf(
//                            colorTransparent,
//                            colorBlackSemiTransparent10,
//                            colorBlackSemiTransparent25,
//                            colorBlackSemiTransparent50,
//                            colorBlackSemiTransparent,
//                        )
//                    ),
//                )
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp, top = 16.dp)

                .align(Alignment.BottomStart)
        ) {
            Text(
                text = itemContent.name,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = itemContent.episodeCountLabel,
                color = Color.White,
                fontSize = 12.sp,

                )
        }
    }
}
