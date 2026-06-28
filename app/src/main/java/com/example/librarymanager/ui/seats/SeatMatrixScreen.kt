package com.example.librarymanager.ui.seats

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



@Composable
internal fun SeatMatrixScreen(onBack: () -> Unit) {
    val seats = (65 downTo 34).map { "S-$it" }
    var memberId by remember { mutableStateOf("") }
    var selectedShift by remember { mutableStateOf("Morning") }
    var selectedSeat by remember { mutableStateOf<String?>(null) }
    var actionMessage by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().background(Color.White)) {
        AppBar(title = "Geeta Library (65 Seats)", onBack = onBack)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            SeatStat("All Seats", "65", null, Modifier.weight(1f))
            SeatStat("Allotted", "0", Orange, Modifier.weight(1f))
            SeatStat("Un Allotted", "65", Green, Modifier.weight(1f))
        }
        ShiftTabs(selectedShift = selectedShift, onSelected = { selectedShift = it })
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = memberId,
                onValueChange = { memberId = it },
                placeholder = { Text("Enter Member Id") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = RoyalBlue,
                    cursorColor = RoyalBlue
                ),
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    actionMessage = when {
                        memberId.isBlank() -> "Please enter Member ID"
                        selectedSeat == null -> "Please select a vacant seat"
                        else -> "${selectedSeat.orEmpty()} allocated to Member $memberId ($selectedShift)"
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.height(52.dp)
            ) {
                Text("Get Seat", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
                .height(1.dp)
                .border(1.dp, Color(0xFFD6D6D6), RoundedCornerShape(1.dp))
        )
        if (actionMessage.isNotBlank()) {
            Text(
                actionMessage,
                color = if (actionMessage.startsWith("Please")) Color(0xFFC83A32) else DeepNavy,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 76.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(seats) { seat ->
                SeatCell(
                    seatNo = seat,
                    selected = selectedSeat == seat,
                    onClick = {
                        selectedSeat = seat
                        actionMessage = "$seat selected"
                    }
                )
            }
        }
    }
}


@Composable
private fun ShiftTabs(selectedShift: String, onSelected: (String) -> Unit) {
    val tabs = listOf("Morning", "Afternoon", "Evening", "Night", "Full Day")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        tabs.forEach { label ->
            val selected = label == selectedShift
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (selected) orangeGradient() else Brush.linearGradient(listOf(Color.White, Color.White)))
                    .border(1.dp, Border, RoundedCornerShape(8.dp))
                    .clickable { onSelected(label) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    label,
                    color = if (selected) Color.White else TextDark,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun SeatCell(seatNo: String, selected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (selected) LibraryGold.copy(alpha = 0.16f) else Color.Transparent)
            .border(if (selected) 1.dp else 0.dp, LibraryGold, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(vertical = 3.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Start)
                .clip(RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp))
                .background(Green)
                .padding(horizontal = 9.dp, vertical = 4.dp)
        ) {
            Text(seatNo, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
        SeatDrawing()
    }
}

@Composable
private fun SeatDrawing() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        val navy = Color(0xFF12223B)
        drawRect(Green, topLeft = Offset(size.width * 0.35f, 4f), size = Size(size.width * 0.32f, 20f))
        drawRect(navy, topLeft = Offset(size.width * 0.32f, 2f), size = Size(size.width * 0.38f, 25f), style = Stroke(4f))
        drawLine(navy, Offset(size.width * 0.16f, 35f), Offset(size.width * 0.84f, 35f), 5f, StrokeCap.Round)
        drawRect(Green, topLeft = Offset(size.width * 0.28f, 35f), size = Size(size.width * 0.44f, 18f))
        drawRect(navy, topLeft = Offset(size.width * 0.26f, 33f), size = Size(size.width * 0.48f, 22f), style = Stroke(4f))
        drawLine(navy, Offset(size.width * 0.18f, 35f), Offset(size.width * 0.18f, 61f), 4f, StrokeCap.Round)
        drawLine(navy, Offset(size.width * 0.82f, 35f), Offset(size.width * 0.82f, 61f), 4f, StrokeCap.Round)
        drawLine(navy, Offset(size.width * 0.34f, 55f), Offset(size.width * 0.34f, 61f), 4f, StrokeCap.Round)
        drawLine(navy, Offset(size.width * 0.66f, 55f), Offset(size.width * 0.66f, 61f), 4f, StrokeCap.Round)
    }
}

@Composable
private fun SeatStat(title: String, value: String, swatch: Color?, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(title, color = Muted, fontSize = 13.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            swatch?.let {
                Spacer(Modifier.width(5.dp))
                Box(Modifier.size(10.dp).background(it))
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp)
                .clip(RoundedCornerShape(6.dp))
                .border(1.dp, Border, RoundedCornerShape(6.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(value, color = TextDark, fontWeight = FontWeight.Bold)
        }
    }
}

