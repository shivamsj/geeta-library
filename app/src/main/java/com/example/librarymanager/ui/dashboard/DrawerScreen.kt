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



@Composable
internal fun DrawerScreen(
    visible: Boolean,
    user: SessionUser?,
    onBack: () -> Unit,
    onLogout: () -> Unit,
    onSeats: () -> Unit,
    onExpenses: () -> Unit,
    onOpenModule: (String) -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(DeepNavy.copy(alpha = 0.58f))
                    .clickable { onBack() }
            )
        }

        AnimatedVisibility(
            visible = visible,
            enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
        ) {
            Column(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.82f)
                    .widthIn(max = 390.dp)
                    .clip(RoundedCornerShape(topEnd = 30.dp, bottomEnd = 30.dp))
                    .background(Color(0xFFFAFBFE))
                    .clickable(enabled = false) {}
            ) {
                DrawerProfileHeader(user = user, onClose = onBack)
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item { DrawerRow("Geeta Library", Icons.AutoMirrored.Outlined.MenuBook, trailing = DrawerTrailing.Verified) { onOpenModule("Library Settings") } }
                    item { DrawerRow("AI Assistant", Icons.Outlined.AutoAwesome, badge = "NEW") { onOpenModule("AI Assistant") } }
                    item { DrawerRow("Subscription", Icons.Outlined.Subscriptions) { onOpenModule("Subscription") } }
                    item { DrawerRow("Need Help?", Icons.AutoMirrored.Outlined.HelpOutline) { onOpenModule("Need Help?") } }
                    item { DrawerRow("Refer & Earn", Icons.Outlined.CardGiftcard) { onOpenModule("Refer & Earn") } }
                    item { DrawerRow("Master", Icons.Outlined.School, trailing = DrawerTrailing.Expand) { onOpenModule("Master Settings") } }
                    item { DrawerRow("Seat", Icons.Outlined.EventSeat, trailing = DrawerTrailing.Expand, onClick = onSeats) }
                    item { DrawerRow("Member Management", Icons.Outlined.Groups) { onOpenModule("Member Management") } }
                    item { DrawerRow("Enquiry Management", Icons.AutoMirrored.Outlined.ContactSupport) { onOpenModule("Enquiry Management") } }
                    item { DrawerRow("Manage Expenses", Icons.Outlined.AccountBalanceWallet, onClick = onExpenses) }
                    item { DrawerRow("Attendance", Icons.Outlined.EventAvailable, trailing = DrawerTrailing.Expand) { onOpenModule("Attendance") } }
                    item { DrawerRow("SMS", Icons.Outlined.Sms, trailing = DrawerTrailing.Expand) { onOpenModule("SMS") } }
                    item { DrawerRow("Reports", Icons.Outlined.Assessment, trailing = DrawerTrailing.Chevron) { onOpenModule("Reports") } }
                    item { DrawerRow("Generate QR Code", Icons.Outlined.QrCode2) { onOpenModule("Generate QR Code") } }
                    item { DrawerRow("Logout", Icons.AutoMirrored.Outlined.Logout, onClick = onLogout) }
                }
            }
        }
    }
}

@Composable
private fun DrawerProfileHeader(user: SessionUser?, onClose: () -> Unit) {
    val displayName = user?.name?.ifBlank { "Geeta User" } ?: "Geeta User"
    val role = user?.role?.lowercase()?.replaceFirstChar { it.uppercase() } ?: "Admin"
    val accountId = user?.userId?.padStart(5, '0') ?: "-----"
    Box(
        Modifier
            .fillMaxWidth()
            .background(Color(0xFFFAFBFE))
            .statusBarsPadding()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(146.dp)
                .shadow(10.dp, RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))
                .background(orangeGradient())
                .border(1.dp, LibraryGold.copy(alpha = 0.65f), RoundedCornerShape(24.dp))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(64.dp)) {
                    Box(
                        modifier = Modifier
                            .size(58.dp)
                            .align(Alignment.TopStart)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(2.dp, LibraryGold.copy(alpha = 0.8f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            displayName.take(1).uppercase(),
                            color = DeepNavy,
                            fontSize = 23.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(1.dp, LibraryGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Outlined.Edit, contentDescription = "Edit profile", tint = DeepNavy, modifier = Modifier.size(13.dp))
                    }
                }
                Column(Modifier.weight(1f).padding(start = 14.dp)) {
                    Text(
                        "$displayName ($role)",
                        color = Color(0xFFFFDA78),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        user?.email.orEmpty(),
                        color = Color.White.copy(alpha = 0.86f),
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(38.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color.White.copy(alpha = 0.08f))
                    .border(1.dp, Color.White.copy(alpha = 0.38f), RoundedCornerShape(18.dp))
                    .padding(horizontal = 13.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.Verified, contentDescription = null, tint = LibraryGold, modifier = Modifier.size(16.dp))
                Text(
                    "GEETA ID (GL-$accountId)",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            IconButton(
                onClick = onClose,
                modifier = Modifier.align(Alignment.TopEnd).size(34.dp)
            ) {
                Text("×", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Light)
            }
        }
    }
}


private enum class DrawerTrailing { None, Chevron, Expand, Verified }

@Composable
private fun DrawerRow(
    title: String,
    icon: ImageVector,
    trailing: DrawerTrailing = DrawerTrailing.None,
    badge: String? = null,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .border(1.dp, LibraryGold.copy(alpha = 0.25f), RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(RoyalBlue.copy(alpha = 0.08f))
                    .border(1.dp, RoyalBlue.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = RoyalBlue, modifier = Modifier.size(21.dp))
            }
            Text(
                title,
                color = DeepNavy,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f).padding(start = 12.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            badge?.let {
                Text(
                    it,
                    color = DeepNavy,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier
                        .clip(RoundedCornerShape(7.dp))
                        .background(LibraryGold.copy(alpha = 0.24f))
                        .padding(horizontal = 7.dp, vertical = 3.dp)
                )
                Spacer(Modifier.width(6.dp))
            }
            when (trailing) {
                DrawerTrailing.Chevron -> Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = RoyalBlue)
                DrawerTrailing.Expand -> Icon(Icons.Outlined.ExpandMore, contentDescription = null, tint = RoyalBlue)
                DrawerTrailing.Verified -> Icon(Icons.Outlined.Verified, contentDescription = "Verified", tint = RoyalBlue)
                DrawerTrailing.None -> Unit
            }
        }
    }
}

