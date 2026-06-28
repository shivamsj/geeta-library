package com.example.librarymanager.ui.dashboard

import com.example.librarymanager.ui.components.*
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
import com.example.librarymanager.ui.auth.AuthScreen
import com.example.librarymanager.ui.auth.ForgotPasswordScreen
import com.example.librarymanager.ui.components.*
import com.example.librarymanager.ui.splash.SplashScreen
import com.example.librarymanager.ui.theme.*
import com.example.librarymanager.viewmodel.AuthUiState
import com.example.librarymanager.viewmodel.AuthViewModel
import com.example.librarymanager.viewmodel.ExpenseViewModel
import com.example.librarymanager.viewmodel.MemberViewModel
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.abs



private data class DashboardCardData(
    val title: String,
    val value: String,
    val color: Color,
    val icon: String
)


@Composable
internal fun DashboardScreen(
    onMenu: () -> Unit,
    onSeats: () -> Unit,
    onExpenses: () -> Unit,
    onOpenModule: (String) -> Unit
) {
    val memberViewModel: MemberViewModel = viewModel()
    val expenseViewModel: ExpenseViewModel = viewModel()
    val memberState by memberViewModel.uiState.collectAsStateWithLifecycle()
    val expenseState by expenseViewModel.uiState.collectAsStateWithLifecycle()
    val today = remember { LocalDate.now() }
    fun expiringBetween(fromDay: Long, toDay: Long): Int = memberState.members.count { member ->
        val days = runCatching { ChronoUnit.DAYS.between(today, LocalDate.parse(member.expiryDate)) }.getOrNull()
        days != null && days in fromDay..toDay
    }
    val activeMembers = memberState.members.count { it.status.equals("ACTIVE", ignoreCase = true) }
    val expiredMembers = memberState.members.count { it.status.equals("EXPIRED", ignoreCase = true) }
    val dueAmount = memberState.members.sumOf { it.dueAmount }
    val totalExpense = expenseState.expenses.sumOf { it.amount }
    val cards = listOf(
        DashboardCardData("Live Members", activeMembers.toString(), RoyalBlue, "\uD83D\uDC65"),
        DashboardCardData("Total Members", memberState.members.size.toString(), RoyalBlue, "\uD83D\uDC65"),
        DashboardCardData("Memberships", expiredMembers.toString(), RoyalBlue, "\u26A0"),
        DashboardCardData("Expiring (1-3 days)", expiringBetween(1, 3).toString(), RoyalBlue, "\u23F0"),
        DashboardCardData("Expiring (4-7 days)", expiringBetween(4, 7).toString(), RoyalBlue, "\u23F0"),
        DashboardCardData("Expiring (8-15 days)", expiringBetween(8, 15).toString(), RoyalBlue, "\u23F0"),
        DashboardCardData("Today Collection", "0", RoyalBlue, "\uD83D\uDC5B"),
        DashboardCardData("Collection (Jun)", "0", RoyalBlue, "\uD83D\uDC5B"),
        DashboardCardData("Collection (May)", "0", RoyalBlue, "\uD83D\uDC5B"),
        DashboardCardData("Today Check-in", "0", RoyalBlue, "\u2713"),
        DashboardCardData("Due Amount", "%.0f".format(dueAmount), RoyalBlue, "\u20B9"),
        DashboardCardData("Today Reminder", "0", RoyalBlue, "\uD83D\uDD14"),
        DashboardCardData("Total Expense", "%.0f".format(totalExpense), RoyalBlue, "\u20B9"),
        DashboardCardData("Follow-ups", "0", RoyalBlue, "FLW"),
        DashboardCardData("Enquiry", "0", RoyalBlue, "ENQ"),
        DashboardCardData("Today Birthdays", "0", RoyalBlue, "BDAY"),
        DashboardCardData("Today Anniversaries", "0", RoyalBlue, "ANN")
    )

    Column(Modifier.fillMaxSize().background(Color.White)) {
        HomeTopBar(onMenu = onMenu, onSeats = onSeats, onOpenModule = onOpenModule)
        HeroBanner()
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 102.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cards) { card ->
                DashboardCard(card = card, onClick = {
                    when {
                        card.title.contains("Expense") -> onExpenses()
                        card.title.contains("Member") -> onOpenModule("Member Management")
                        card.title.contains("Collection") -> onOpenModule("Collections")
                        card.title.contains("Check-in") -> onOpenModule("Attendance")
                        card.title.contains("Reminder") -> onOpenModule("Reminders")
                        card.title.contains("Due") -> onOpenModule("Due Amount")
                        card.title.contains("Follow") -> onOpenModule("Follow-ups")
                        card.title.contains("Enquiry") -> onOpenModule("Enquiry Management")
                        card.title.contains("Birthday") -> onOpenModule("Birthdays")
                        card.title.contains("Anniversar") -> onOpenModule("Anniversaries")
                        else -> onOpenModule(card.title)
                    }
                })
            }
        }
    }
}


@Composable
private fun HomeTopBar(onMenu: () -> Unit, onSeats: () -> Unit, onOpenModule: (String) -> Unit) {
    BoxWithConstraints {
        val narrow = maxWidth < 370.dp
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(92.dp)
                .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
                .background(orangeGradient())
                .statusBarsPadding()
                .padding(horizontal = if (narrow) 8.dp else 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TopBarAction(label = "MENU", accent = true, onClick = onMenu)
            Text(
                "Geeta Library",
                color = Color(0xFFFFDA78),
                fontSize = if (narrow) 18.sp else 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            TopBarAction(label = "+ID", onClick = { onOpenModule("Add Member") })
            TopBarAction(label = "MSG", onClick = { onOpenModule("Messages") })
            if (!narrow) TopBarAction(label = "WA", onClick = { onOpenModule("WhatsApp") })
            TopBarAction(label = "SEAT", onClick = onSeats)
        }
    }
}

@Composable
private fun TopBarAction(label: String, accent: Boolean = false, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(width = if (label == "MENU") 44.dp else 42.dp, height = 48.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            if (label == "MENU") "\u25A6" else label,
            color = if (accent) LibraryGold else Color.White,
            fontSize = if (label == "MENU") 27.sp else 10.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
private fun HeroBanner() {
    val slides = listOf(
        "Your Library's Smartest Assistant" to "Manage every seat with confidence",
        "A Better Place to Study" to "Quiet spaces. Focused minds.",
        "One App. Endless Possibilities" to "Members, attendance and accounts"
    )
    var slideIndex by remember { mutableIntStateOf(0) }
    var dragDistance by remember { mutableStateOf(0f) }

    LaunchedEffect(slideIndex) {
        delay(3500)
        slideIndex = (slideIndex + 1) % slides.size
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(205.dp)
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(16.dp))
            .pointerInput(slides.size) {
                detectHorizontalDragGestures(
                    onDragCancel = { dragDistance = 0f },
                    onDragEnd = {
                        if (abs(dragDistance) > 45f) {
                            slideIndex = if (dragDistance < 0f) {
                                (slideIndex + 1) % slides.size
                            } else {
                                (slideIndex - 1 + slides.size) % slides.size
                            }
                        }
                        dragDistance = 0f
                    }
                ) { change, amount ->
                    change.consume()
                    dragDistance += amount
                }
            },
        contentAlignment = Alignment.Center
    ) {
        DashboardHeroArtwork(slideIndex)
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 42.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Geeta Library", color = Color(0xFFFFDA78), fontSize = 28.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
            Text(slides[slideIndex].first, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Text(slides[slideIndex].second, color = Color.White.copy(alpha = 0.78f), fontSize = 12.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 5.dp))
        }
        Card(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
                .size(54.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            LogoMark(Modifier.fillMaxSize().padding(3.dp))
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
                .size(38.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.92f))
                .clickable { slideIndex = (slideIndex - 1 + slides.size) % slides.size },
            contentAlignment = Alignment.Center
        ) {
            Text("‹", color = DeepNavy, fontSize = 26.sp, fontWeight = FontWeight.Black)
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
                .size(38.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.92f))
                .clickable { slideIndex = (slideIndex + 1) % slides.size },
            contentAlignment = Alignment.Center
        ) {
            Text("›", color = DeepNavy, fontSize = 26.sp, fontWeight = FontWeight.Black)
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            slides.indices.forEach { index ->
                Box(
                    modifier = Modifier
                        .size(if (index == slideIndex) 9.dp else 7.dp)
                        .clip(CircleShape)
                        .background(if (index == slideIndex) LibraryGold else Color.White.copy(alpha = 0.78f))
                        .clickable { slideIndex = index }
                )
            }
        }
    }
}

@Composable
private fun DashboardHeroArtwork(slideIndex: Int) {
    Canvas(Modifier.fillMaxSize()) {
        val palettes = listOf(
            listOf(Color(0xFF164E94), Color(0xFF061F62)),
            listOf(Color(0xFF2467B8), Color(0xFF0A315F)),
            listOf(Color(0xFF123C78), Color(0xFF071A3F))
        )
        drawRect(brush = Brush.linearGradient(palettes[slideIndex]))

        val line = Color.White.copy(alpha = 0.12f)
        val bookColors = listOf(
            Color(0xFFFFDA78).copy(alpha = 0.32f),
            Color.White.copy(alpha = 0.18f),
            Color(0xFF72A9ED).copy(alpha = 0.28f)
        )

        if (slideIndex == 1) {
            for (column in 0..5) {
                val left = column * size.width / 6f
                drawRect(
                    color = Color(0xFF8FC5F2).copy(alpha = 0.17f),
                    topLeft = Offset(left + 5f, 0f),
                    size = Size(size.width / 6f - 10f, size.height * 0.72f)
                )
                drawLine(line, Offset(left, 0f), Offset(left, size.height * 0.78f), 3f)
            }
            drawCircle(Color(0xFFFFDA78).copy(alpha = 0.28f), size.height * 0.34f, Offset(size.width * 0.18f, size.height * 0.18f))
        } else {
            for (row in 0..4) {
                val shelfY = size.height * (0.18f + row * 0.15f)
                drawLine(line, Offset(0f, shelfY), Offset(size.width, shelfY), 4f)
                for (column in 0..12) {
                    val width = size.width / 18f
                    val left = column * size.width / 13f + 4f
                    val height = 18f + ((column * 9 + row * 7) % 34)
                    drawRoundRect(
                        color = bookColors[(column + row) % bookColors.size],
                        topLeft = Offset(left, shelfY - height),
                        size = Size(width, height),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(2f, 2f)
                    )
                }
            }
        }

        val deskY = size.height * 0.77f
        for (desk in 0..3) {
            val centerX = size.width * (0.14f + desk * 0.25f)
            drawRoundRect(
                color = Color(0xFF06162F).copy(alpha = 0.62f),
                topLeft = Offset(centerX - size.width * 0.1f, deskY),
                size = Size(size.width * 0.2f, 18f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f)
            )
            drawCircle(Color.White.copy(alpha = 0.17f), 18f, Offset(centerX, deskY - 16f))
        }
        drawRect(Color.Black.copy(alpha = 0.24f))
    }
}

@Composable
private fun DashboardCard(card: DashboardCardData, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.08f)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .border(1.dp, RoyalBlue.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(RoyalBlue.copy(alpha = 0.08f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    card.icon,
                    color = RoyalBlue,
                    fontSize = if (card.icon.length > 2) 9.sp else 19.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }
            Text(
                card.title,
                color = DeepNavy,
                fontWeight = FontWeight.SemiBold,
                fontSize = 11.sp,
                lineHeight = 13.sp,
                maxLines = 2,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
            Text(card.value, color = DeepNavy, fontWeight = FontWeight.Black, fontSize = 21.sp)
            Box(Modifier.size(width = 28.dp, height = 3.dp).clip(CircleShape).background(LibraryGold))
        }
    }
}

