package com.example.librarymanager.ui.splash

import com.example.librarymanager.R
import com.example.librarymanager.ui.theme.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ContactSupport
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.AlternateEmail
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.CardGiftcard
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material.icons.outlined.EventSeat
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.QrCode2
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Sms
import androidx.compose.material.icons.outlined.Subscriptions
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Density
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.librarymanager.data.model.Expense
import com.example.librarymanager.data.model.Member
import com.example.librarymanager.data.network.ApiClient
import com.example.librarymanager.data.network.SessionUser
import com.example.librarymanager.ui.theme.LibraryManagerTheme
import com.example.librarymanager.viewmodel.AuthUiState
import com.example.librarymanager.viewmodel.AuthViewModel
import com.example.librarymanager.viewmodel.ExpenseViewModel
import com.example.librarymanager.viewmodel.MemberViewModel
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.abs

@Composable
internal fun SplashScreen(onDone: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2200)
        onDone()
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCFBF8))
            .clickable { onDone() }
    ) {
        val compact = maxHeight < 720.dp
        val narrow = maxWidth < 360.dp
        val logoSize = when {
            compact -> 158.dp
            narrow -> 176.dp
            else -> 204.dp
        }
        val cardHeight = if (compact) 132.dp else 168.dp

        PaperStudyBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = if (narrow) 14.dp else 22.dp, vertical = if (compact) 8.dp else 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(
                modifier = Modifier.size(logoSize),
                shape = RoundedCornerShape(if (compact) 26.dp else 32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 14.dp)
            ) {
                Box(Modifier.fillMaxSize().padding(5.dp), contentAlignment = Alignment.Center) {
                    SplashLogo()
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("GEETA", color = DeepNavy, fontSize = if (compact) 30.sp else 40.sp, fontWeight = FontWeight.Black)
                Text("LIBRARY", color = LibraryGold, fontSize = if (compact) 15.sp else 19.sp, fontWeight = FontWeight.Bold)
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 350.dp)
                    .height(if (compact) 46.dp else 54.dp)
                    .border(1.dp, LibraryGold.copy(alpha = 0.5f), RoundedCornerShape(28.dp)),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.96f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 7.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IndiaFlag(if (compact) 30.dp else 36.dp)
                    Text("Made with ", color = DeepNavy, fontSize = if (compact) 16.sp else 19.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 10.dp))
                    Text("\u2665", color = Color(0xFFD71920), fontSize = if (compact) 23.sp else 28.sp, fontWeight = FontWeight.Black)
                    Text(" in India", color = DeepNavy, fontSize = if (compact) 16.sp else 19.sp, fontWeight = FontWeight.Bold)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().widthIn(max = 390.dp),
                horizontalArrangement = Arrangement.spacedBy(if (narrow) 10.dp else 14.dp)
            ) {
                SplashTrustCard(
                    title = "100% Secure",
                    description = "Your data is safe with us",
                    color = RoyalBlue,
                    icon = "\u2713",
                    compact = compact,
                    modifier = Modifier.weight(1f).height(cardHeight)
                )
                SplashTrustCard(
                    title = "Cloud Backup",
                    description = "Access your library anytime, anywhere",
                    color = RoyalBlue,
                    icon = "UP",
                    compact = compact,
                    modifier = Modifier.weight(1f).height(cardHeight)
                )
            }
        }
    }
}

@Composable
private fun PaperStudyBackground() {
    Canvas(Modifier.fillMaxSize()) {
        drawRect(Color(0xFFFCFBF8))
        drawCircle(
            color = Color(0xFFDCE8FF).copy(alpha = 0.72f),
            radius = size.width * 0.68f,
            center = Offset(-size.width * 0.34f, -size.height * 0.02f),
            style = Stroke(size.width * 0.12f)
        )
        drawCircle(
            color = Color(0xFFE4ECFF).copy(alpha = 0.75f),
            radius = size.width * 0.62f,
            center = Offset(size.width * 1.28f, size.height * 0.78f),
            style = Stroke(size.width * 0.13f)
        )
        drawCircle(
            color = Color(0xFFFFF2D6).copy(alpha = 0.55f),
            radius = size.width * 0.46f,
            center = Offset(size.width * 0.92f, size.height * 0.08f)
        )

        for (row in 0..3) {
            for (column in 0..4) {
                drawCircle(
                    color = RoyalBlue.copy(alpha = 0.12f),
                    radius = 5f,
                    center = Offset(size.width * 0.82f + column * 22f, size.height * 0.05f + row * 22f)
                )
                drawCircle(
                    color = RoyalBlue.copy(alpha = 0.12f),
                    radius = 5f,
                    center = Offset(size.width * 0.04f + column * 22f, size.height * 0.66f + row * 22f)
                )
            }
        }

        drawLine(LibraryGold.copy(alpha = 0.35f), Offset(0f, size.height * 0.18f), Offset(size.width * 0.36f, 0f), 3f, StrokeCap.Round)
        drawLine(LibraryGold.copy(alpha = 0.32f), Offset(size.width, size.height * 0.62f), Offset(size.width * 0.78f, size.height), 3f, StrokeCap.Round)

        val booksY = size.height - 116f
        val bookColors = listOf(Color(0xFF123F9A), Color(0xFF6E93D5), Color(0xFFF1D49A), Color(0xFF315FB4))
        bookColors.forEachIndexed { index, color ->
            drawRoundRect(
                color = color.copy(alpha = 0.78f),
                topLeft = Offset(-12f, booksY + index * 24f),
                size = Size(size.width * 0.24f, 26f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f)
            )
        }

        val bookPath = Path().apply {
            moveTo(size.width * 0.58f, size.height - 88f)
            quadraticTo(size.width * 0.72f, size.height - 148f, size.width * 0.86f, size.height - 78f)
            quadraticTo(size.width * 0.73f, size.height - 106f, size.width * 0.58f, size.height - 88f)
            moveTo(size.width * 0.86f, size.height - 78f)
            quadraticTo(size.width * 0.93f, size.height - 118f, size.width, size.height - 92f)
        }
        drawPath(bookPath, Color.White.copy(alpha = 0.94f))
        drawPath(bookPath, RoyalBlue.copy(alpha = 0.24f), style = Stroke(3f))

        drawCircle(RoyalBlue.copy(alpha = 0.2f), radius = 66f, center = Offset(size.width * 0.94f, size.height - 160f))
        drawCircle(Color(0xFF7BA4DF).copy(alpha = 0.5f), radius = 48f, center = Offset(size.width * 0.94f, size.height - 160f))
        drawCircle(LibraryGold.copy(alpha = 0.8f), radius = 54f, center = Offset(size.width * 0.94f, size.height - 160f), style = Stroke(7f))
    }
}

@Composable
private fun SplashLogo() {
    Image(
        painter = painterResource(R.drawable.geeta_library_logo),
        contentDescription = "Geeta Library logo",
        contentScale = ContentScale.Fit,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun IndiaFlag(diameter: androidx.compose.ui.unit.Dp = 44.dp) {
    Canvas(Modifier.size(diameter)) {
        drawArc(Color(0xFFFF8F1F), 180f, 180f, true)
        drawArc(Color(0xFF128807), 0f, 180f, true)
        drawCircle(Color.White, radius = size.minDimension * 0.34f, center = center)
        drawCircle(Color(0xFF1A237E), radius = size.minDimension * 0.08f, center = center, style = Stroke(3f))
    }
}

@Composable
private fun SplashTrustCard(
    title: String,
    description: String,
    color: Color,
    icon: String,
    compact: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(if (compact) 18.dp else 24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.96f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 9.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = if (compact) 8.dp else 12.dp, vertical = if (compact) 9.dp else 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
        Box(
            modifier = Modifier.size(if (compact) 48.dp else 62.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(Color(0xFFF3F7FF))
                    .border(1.dp, color.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(icon, color = color, fontSize = if (compact) 24.sp else 30.sp, fontWeight = FontWeight.Black)
            }
        }
        Text(
            title,
            color = DeepNavy,
            fontSize = if (compact) 14.sp else 17.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
        if (!compact) {
            Text(
                description,
                color = Muted,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
        }
    }
}
