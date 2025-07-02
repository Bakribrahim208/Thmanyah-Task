package com.sa.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PlayIconWithDuration(
    modifier: Modifier = Modifier
        .background(
            color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(16.dp)
        )
        .padding(horizontal = 6.dp, vertical = 2.dp), durationLabel: String
) {
    Row(
        modifier, verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            modifier = Modifier.size(18.dp),
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Play",
            tint = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = durationLabel, fontSize = 11.sp, color = MaterialTheme.colorScheme.onBackground
        )
    }

}