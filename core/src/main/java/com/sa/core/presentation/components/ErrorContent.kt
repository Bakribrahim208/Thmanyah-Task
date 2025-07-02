package com.sa.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sa.core.R
import com.sa.core.presentation.uiModel.SectionError

@Composable
fun ErrorContent(
    error: SectionError, onRetry: () -> Unit, modifier: Modifier = Modifier
) {
    val (icon, title, message) = when (error) {
        is SectionError.Network -> Triple(
            R.drawable.ic_error,
            "Network Error",
            "Please check your internet connection and try again."
        )

        is SectionError.Server -> Triple(
            R.drawable.ic_error,
            "Server Error",
            "We're experiencing server issues. Please try again later.\n${error.message}"
        )

        is SectionError.Client -> Triple(
            R.drawable.ic_error,
            "Request Error",
            "Something went wrong with the request.\n${error.message}"
        )

        is SectionError.EmptyData -> Triple(
            R.drawable.ic_error,
            "No Data", "There's no content to display right now."
        )

        is SectionError.Unknown -> Triple(
            R.drawable.ic_error,
            "Unknown Error",
            "An unexpected error occurred.\n${error.message}"
        )
    }

    ErrorContentWithDrawable(
        iconResId = icon,
        errorTitle = title,
        errorMessage = message,
        onRetry = onRetry,
        modifier = modifier
    )
}

@Composable
fun ErrorContent(
    errorTitle: String,
    errorMessage: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.AccountBox
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = errorTitle,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = errorMessage,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
fun ErrorContentWithDrawable(
    errorTitle: String,
    errorMessage: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    iconResId: Int
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = errorTitle,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = errorMessage,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}
