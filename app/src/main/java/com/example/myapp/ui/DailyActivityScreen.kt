package com.example.myapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.example.myapp.R
import androidx.compose.ui.graphics.Color

@Composable
fun DailyActivityScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFE3F2FD), Color.White)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        SectionCard(
            title = stringResource(R.string.activity_title),
            onClick = {}
        ) {
            ActivityList()
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ActivityList() {
    val activities = listOf(
        ActivityItem(stringResource(R.string.activity_1)),
        ActivityItem(stringResource(R.string.activity_2)),
        ActivityItem(stringResource(R.string.activity_3)),
        ActivityItem(stringResource(R.string.activity_4)),
        ActivityItem(stringResource(R.string.activity_5)),
        ActivityItem(stringResource(R.string.activity_6)),
        ActivityItem(stringResource(R.string.activity_7)),
        ActivityItem(stringResource(R.string.activity_8))
    )
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        activities.forEach { act ->
            ActivityItemRow(act)
        }
    }
}

@Composable
fun ActivityItemRow(act: ActivityItem) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = animateFloatAsState(if (isPressed) 0.98f else 1f)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale.value)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {}
            )
            .padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Schedule,
            contentDescription = "Activity Icon",
            modifier = Modifier
                .size(32.dp)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape)
                .padding(6.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = act.name,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

data class ActivityItem(val name: String)

@Preview(showBackground = true)
@Composable
fun DailyActivityScreenPreview() {
    DailyActivityScreen()
} 