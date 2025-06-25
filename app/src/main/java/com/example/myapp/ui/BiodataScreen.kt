package com.example.myapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Web
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.google.accompanist.flowlayout.FlowRow
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import com.example.myapp.R
import androidx.compose.foundation.clickable
import android.content.Intent
import android.net.Uri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiodataScreen() {
    val scrollState = rememberScrollState()
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f)
    val elevation by animateDpAsState(if (isPressed) 16.dp else 8.dp)
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFE3F2FD), Color.White)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Profile Picture with Accent Circle
        Card(
            shape = CircleShape,
            modifier = Modifier
                .size(150.dp)
                .scale(scale)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {}
                ),
            elevation = CardDefaults.cardElevation(defaultElevation = elevation)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_foto_saya),
                    contentDescription = "Profile Photo",
                    modifier = Modifier
                        .size(130.dp)
                        .clip(CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Name and Title
        Text(
            text = stringResource(R.string.biodata_value_name),
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary),
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(id = R.string.biodata_role),
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Biodata Details Section
        SectionCard(
            title = stringResource(id = R.string.biodata_title),
            onClick = {}
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                InfoRow(icon = Icons.Default.Person, label = stringResource(R.string.biodata_status), value = stringResource(R.string.biodata_value_status))
                InfoRow(icon = Icons.Default.Home, label = stringResource(R.string.biodata_address), value = stringResource(R.string.biodata_value_address))
                val emailValue = stringResource(id = R.string.biodata_value_email)
                InfoRow(
                    icon = Icons.Default.Email,
                    label = stringResource(R.string.biodata_email),
                    value = emailValue,
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:$emailValue")
                        }
                        context.startActivity(Intent.createChooser(intent, "Send Email"))
                    }
                )
                val phoneValue = stringResource(id = R.string.biodata_value_phone)
                InfoRow(
                    icon = Icons.Default.Phone,
                    label = stringResource(R.string.biodata_phone),
                    value = phoneValue,
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:$phoneValue")
                        }
                        context.startActivity(intent)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // About Me Section
        SectionCard(
            title = stringResource(R.string.about_me_title),
            onClick = {}
        ) {
            Text(
                text = stringResource(R.string.about_me_desc),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Skills Section
        SectionCard(
            title = stringResource(R.string.biodata_skills),
            onClick = {}
        ) {
            FlowRow(
                mainAxisSpacing = 12.dp,
                crossAxisSpacing = 12.dp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                skillsList().forEach { skill ->
                    SkillChip(skill)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String, onClick: (() -> Unit)? = null) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.98f else 1f)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .then(if (onClick != null) Modifier.clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ) else Modifier)
            .padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = Color.Gray)
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = if (onClick != null) MaterialTheme.colorScheme.primary else Color.Unspecified
            )
        }
    }
}

@Composable
fun SkillChip(skill: Skill) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = animateFloatAsState(if (isPressed) 0.95f else 1f)

    Card(
        modifier = Modifier
            .height(50.dp)
            .scale(scale.value)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {}
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Image(
                painter = painterResource(id = skill.iconRes),
                contentDescription = stringResource(id = skill.nameRes),
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = skill.nameRes),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

fun skillsList() = listOf(
    Skill(R.string.biodata_skill_office, R.drawable.ic_logo_microsoft),
    Skill(R.string.biodata_skill_html, R.drawable.logo_html),
    Skill(R.string.biodata_skill_css, R.drawable.logo_css),
    Skill(R.string.biodata_skill_python, R.drawable.logo_python),
    Skill(R.string.biodata_skill_php, R.drawable.logo_php),
    Skill(R.string.biodata_skill_js, R.drawable.icons_javascript)
)

data class Skill(val nameRes: Int, val iconRes: Int)

@Preview(showBackground = true)
@Composable
fun BiodataScreenPreview() {
    BiodataScreen()
} 