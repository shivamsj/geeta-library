package com.example.librarymanager.ui.auth

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
import com.example.librarymanager.viewmodel.AuthUiState
import com.example.librarymanager.viewmodel.AuthViewModel
import com.example.librarymanager.viewmodel.ExpenseViewModel
import com.example.librarymanager.viewmodel.MemberViewModel
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.abs

@Composable
internal fun AuthScreen(
    viewModel: AuthViewModel,
    onLogin: () -> Unit,
    onForgotPassword: () -> Unit,
    onOpenModule: (String) -> Unit
) {
    val authViewModel = viewModel
    val authState by authViewModel.uiState.collectAsStateWithLifecycle()
    var isRegister by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    var memberLogin by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(authState.isAuthenticated) {
        if (authState.isAuthenticated) {
            authViewModel.consumeAuthentication()
            onLogin()
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FC))
    ) {
        val compact = maxHeight < 760.dp
        val narrow = maxWidth < 380.dp
        val keyboardOpen = WindowInsets.ime.getBottom(LocalDensity.current) > 0
        val sidePadding = if (narrow) 14.dp else 20.dp
        val topSpace = if (keyboardOpen) 72.dp else if (compact) 118.dp else 148.dp
        val formCompact = compact || keyboardOpen
        val helpSize = if (narrow) 46.dp else 52.dp
        val sheetMaxWidth = 440.dp

        AuthPageBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CompactAuthHeader(
                isRegister = isRegister,
                keyboardOpen = keyboardOpen,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(topSpace)
                    .padding(horizontal = sidePadding)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = sheetMaxWidth)
                    .padding(horizontal = sidePadding),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 18.dp)
            ) {
                Column(
                    modifier = Modifier
                        .border(1.dp, Color.White.copy(alpha = 0.85f), RoundedCornerShape(28.dp))
                        .padding(
                            horizontal = if (narrow) 16.dp else 24.dp,
                            vertical = if (formCompact) 16.dp else 24.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isRegister) "Create account" else "Welcome back",
                        color = DeepNavy,
                        fontSize = if (narrow) 24.sp else 27.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = if (isRegister) {
                            "Start managing your library with one secure account."
                        } else {
                            "Manage members, seats, fees and reports smarter."
                        },
                        color = Muted,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 6.dp, bottom = if (formCompact) 14.dp else 18.dp)
                    )

                    if (!formCompact) {
                        AuthModeHighlights(isRegister = isRegister)
                        Spacer(Modifier.height(16.dp))
                    }

                    AuthTabs(
                        isRegister = isRegister,
                        onLogin = {
                            focusManager.clearFocus()
                            authViewModel.clearFeedback()
                            isRegister = false
                        },
                        onRegister = {
                            focusManager.clearFocus()
                            authViewModel.clearFeedback()
                            isRegister = true
                        }
                    )
                    Spacer(Modifier.height(if (formCompact) 14.dp else 18.dp))

                    if (isRegister) {
                        RegisterForm(state = authState, viewModel = authViewModel)
                        AuthPrimaryButton(
                            text = if (authState.isLoading) "Creating Account..." else "Create Account",
                            onClick = {
                                focusManager.clearFocus()
                                authViewModel.register()
                            },
                            enabled = !authState.isLoading,
                            modifier = Modifier.padding(top = if (formCompact) 14.dp else 18.dp)
                        )
                    } else {
                        LoginForm(state = authState, viewModel = authViewModel)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = if (compact) 8.dp else 14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CheckLabel("Remember me", rememberMe) { rememberMe = it }
                            CheckLabel("I'm Member", memberLogin) { memberLogin = it }
                        }
                        AuthPrimaryButton(
                            text = if (authState.isLoading) "Signing In..." else "Login Now",
                            onClick = {
                                focusManager.clearFocus()
                                authViewModel.login()
                            },
                            enabled = !authState.isLoading,
                            modifier = Modifier.padding(top = if (compact) 14.dp else 22.dp)
                        )
                        Text(
                            text = "Forgot password?",
                            color = RoyalBlue,
                            fontSize = if (compact) 14.sp else 15.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(top = if (compact) 12.dp else 16.dp)
                                .clickable {
                                    focusManager.clearFocus()
                                    authViewModel.clearFeedback()
                                    onForgotPassword()
                                }
                        )
                    }

                    authState.error?.let {
                        AuthStatusMessage(it, isError = true)
                    }
                    authState.message?.let {
                        AuthStatusMessage(it, isError = false)
                    }
                }
            }

            Spacer(Modifier.height(if (keyboardOpen) 18.dp else 72.dp))
        }

        if (!keyboardOpen) {
            AuthHelpButton(
                size = helpSize,
                onClick = { onOpenModule("Need Help?") },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = sidePadding + 4.dp, bottom = 18.dp)
            )
        }
    }
}

@Composable
internal fun ForgotPasswordScreen(
    viewModel: AuthViewModel,
    onBack: () -> Unit,
    onOpenHelp: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) { viewModel.clearFeedback() }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FC))
    ) {
        val narrow = maxWidth < 380.dp
        val compact = maxHeight < 760.dp
        val keyboardOpen = WindowInsets.ime.getBottom(LocalDensity.current) > 0
        val topSpace = if (keyboardOpen) 76.dp else if (compact) 126.dp else 154.dp
        val sidePadding = if (narrow) 14.dp else 20.dp

        AuthPageBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CompactAuthHeader(
                isRegister = false,
                keyboardOpen = keyboardOpen,
                title = "Recover access",
                subtitle = "Verify your email and return to your workspace.",
                onBack = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(topSpace)
                    .padding(horizontal = sidePadding)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 440.dp)
                    .padding(horizontal = sidePadding),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 18.dp)
            ) {
                Column(
                    modifier = Modifier
                        .border(1.dp, Color.White.copy(alpha = 0.85f), RoundedCornerShape(28.dp))
                        .padding(horizontal = if (narrow) 18.dp else 24.dp, vertical = if (keyboardOpen) 18.dp else 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Reset password",
                        color = DeepNavy,
                        fontSize = if (narrow) 24.sp else 27.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "Enter your registered email. We will send an OTP to recover your account.",
                        color = Muted,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 7.dp, bottom = if (keyboardOpen) 16.dp else 22.dp)
                    )

                    SecurityNotice(
                        title = "Password reset",
                        body = "We will send the recovery instructions only to your registered email."
                    )

                    Spacer(Modifier.height(if (keyboardOpen) 14.dp else 18.dp))

                    IconTextField(
                        value = state.email,
                        onValueChange = viewModel::updateEmail,
                        label = "Enter your Email",
                        icon = Icons.Outlined.Email,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    )

                    AuthPrimaryButton(
                        text = if (state.isLoading) "Sending OTP..." else "Send OTP",
                        enabled = !state.isLoading,
                        onClick = {
                            focusManager.clearFocus()
                            viewModel.sendPasswordReset()
                        },
                        modifier = Modifier.padding(top = if (keyboardOpen) 16.dp else 22.dp)
                    )

                    state.error?.let { AuthStatusMessage(it, isError = true) }
                    state.message?.let { AuthStatusMessage(it, isError = false) }

                    Row(
                        modifier = Modifier
                            .padding(top = if (keyboardOpen) 14.dp else 20.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { onBack() }
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(7.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = null,
                            tint = RoyalBlue,
                            modifier = Modifier.size(18.dp)
                        )
                        Text("Back to Login", color = RoyalBlue, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.height(if (keyboardOpen) 18.dp else 70.dp))
        }

        if (!keyboardOpen) {
            AuthHelpButton(
                size = if (narrow) 46.dp else 52.dp,
                onClick = onOpenHelp,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = sidePadding + 4.dp, bottom = 18.dp)
            )
        }
    }
}

@Composable
private fun AuthPageBackground() {
    Canvas(Modifier.fillMaxSize()) {
        drawRect(Color(0xFFF6F8FD))
        drawRect(
            brush = Brush.verticalGradient(listOf(Color(0xFF0D5ED1), Color(0xFF073D9D), DeepNavy)),
            size = Size(size.width, size.height * 0.34f)
        )

        val shelfColor = Color.White.copy(alpha = 0.055f)
        for (row in 0..4) {
            val y = size.height * (0.04f + row * 0.055f)
            drawLine(shelfColor, Offset(0f, y), Offset(size.width, y), 4f)
            for (column in 0..9) {
                val left = column * size.width / 10f + (row % 2) * 8f
                val bookHeight = 26f + ((column * 13 + row * 9) % 42)
                drawRoundRect(
                    color = Color.White.copy(alpha = 0.04f + (column % 3) * 0.01f),
                    topLeft = Offset(left, y - bookHeight),
                    size = Size(size.width / 14f, bookHeight),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(3f, 3f)
                )
            }
        }

        drawCircle(Color(0xFF4DA3FF).copy(alpha = 0.23f), size.width * 0.44f, Offset(size.width * 0.02f, size.height * 0.04f))
        drawCircle(LibraryGold.copy(alpha = 0.13f), size.width * 0.34f, Offset(size.width * 0.98f, size.height * 0.16f))
        drawCircle(Color.White.copy(alpha = 0.86f), size.width * 0.86f, Offset(size.width * 0.03f, size.height * 1.02f))
        drawCircle(Color(0xFFE9EDF5).copy(alpha = 0.72f), size.width * 0.66f, Offset(size.width * 0.96f, size.height * 0.82f))
    }
}

@Composable
private fun CompactAuthHeader(
    isRegister: Boolean,
    keyboardOpen: Boolean,
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    onBack: (() -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        onBack?.let {
            IconButton(
                onClick = it,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.12f))
                    .border(1.dp, Color.White.copy(alpha = 0.22f), CircleShape)
            ) {
                Icon(
                    Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(21.dp)
                )
            }
            Spacer(Modifier.width(10.dp))
        }
        Card(
            modifier = Modifier.size(if (keyboardOpen) 46.dp else 62.dp),
            shape = RoundedCornerShape(if (keyboardOpen) 15.dp else 18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            LogoMark(modifier = Modifier.fillMaxSize().padding(5.dp))
        }
        Column(
            modifier = Modifier
                .weight(1f, fill = false)
                .padding(start = 14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            if (!keyboardOpen) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    HeaderPill(if (isRegister) "NEW SETUP" else "SECURE")
                    HeaderPill("GEETA LIBRARY")
                }
            }
            Text(
                text = title ?: "Geeta Library",
                color = Color.White,
                fontSize = if (keyboardOpen) 18.sp else 23.sp,
                fontWeight = FontWeight.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = if (keyboardOpen) 0.dp else 8.dp)
            )
            if (!keyboardOpen) {
                Text(
                    text = subtitle ?: if (isRegister) {
                        "Admin setup workspace"
                    } else {
                        "Library admin workspace"
                    },
                    color = Color.White.copy(alpha = 0.82f),
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun HeaderPill(text: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.11f))
            .border(1.dp, Color.White.copy(alpha = 0.22f), RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(Icons.Outlined.Verified, contentDescription = null, tint = LibraryGold, modifier = Modifier.size(11.dp))
        Text(text, color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun AuthStatusMessage(message: String, isError: Boolean) {
    val color = if (isError) Color(0xFFB3261E) else Color(0xFF0B7A36)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.08f))
            .padding(horizontal = 12.dp, vertical = 9.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(if (isError) "!" else "✓", color = color, fontWeight = FontWeight.Black)
        Text(message, color = color, fontSize = 12.sp, lineHeight = 17.sp, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun SecurityNotice(title: String, body: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Brush.horizontalGradient(listOf(Color(0xFFF6FAFF), Color(0xFFFFFBF1))))
            .border(1.dp, RoyalBlue.copy(alpha = 0.09f), RoundedCornerShape(18.dp))
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(RoyalBlue.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Outlined.Security, contentDescription = null, tint = RoyalBlue, modifier = Modifier.size(20.dp))
        }
        Column(Modifier.weight(1f)) {
            Text(title, color = DeepNavy, fontSize = 13.sp, fontWeight = FontWeight.Black)
            Text(body, color = Muted, fontSize = 11.sp, lineHeight = 15.sp, modifier = Modifier.padding(top = 2.dp))
        }
    }
}

@Composable
private fun AuthHelpButton(size: androidx.compose.ui.unit.Dp, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(Brush.linearGradient(listOf(Color(0xFF0A4CB2), DeepNavy)))
            .border(4.dp, Color.White.copy(alpha = 0.75f), CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text("?", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Black)
    }
}

@Composable
private fun AuthModeHighlights(isRegister: Boolean) {
    val items = if (isRegister) {
        listOf(
            Triple("Profile", Icons.Outlined.Person, RoyalBlue),
            Triple("Seats", Icons.Outlined.EventSeat, Color(0xFF0B7A36)),
            Triple("Reports", Icons.Outlined.Assessment, LibraryGold)
        )
    } else {
        listOf(
            Triple("Secure", Icons.Outlined.Security, RoyalBlue),
            Triple("Members", Icons.Outlined.Groups, Color(0xFF0B7A36)),
            Triple("Fees", Icons.Outlined.AccountBalanceWallet, LibraryGold)
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { (label, icon, color) ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(color.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(15.dp))
                }
                Text(
                    label,
                    color = Muted,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }
        }
    }
}

@Composable
private fun AuthTabs(isRegister: Boolean, onLogin: () -> Unit, onRegister: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(26.dp))
            .border(1.dp, RoyalBlue.copy(alpha = 0.14f), RoundedCornerShape(26.dp))
            .background(RoyalBlue.copy(alpha = 0.07f))
            .padding(3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(23.dp))
                .then(if (!isRegister) Modifier.shadow(4.dp, RoundedCornerShape(23.dp)) else Modifier)
                .background(if (!isRegister) Color.White else Color.Transparent)
                .clickable { onLogin() },
            contentAlignment = Alignment.Center
        ) {
            Text("Login", color = if (!isRegister) DeepNavy else Muted, fontSize = 16.sp, fontWeight = FontWeight.Black)
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(23.dp))
                .then(if (isRegister) Modifier.shadow(4.dp, RoundedCornerShape(23.dp)) else Modifier)
                .background(if (isRegister) Color.White else Color.Transparent)
                .clickable { onRegister() },
            contentAlignment = Alignment.Center
        ) {
            Text("Sign Up", color = if (isRegister) DeepNavy else Muted, fontSize = 16.sp, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
private fun LoginForm(state: AuthUiState, viewModel: AuthViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        IconTextField(value = state.email, onValueChange = viewModel::updateEmail, label = "Email", icon = Icons.Outlined.AlternateEmail, keyboardType = KeyboardType.Email)
        IconTextField(value = state.password, onValueChange = viewModel::updatePassword, label = "Password", icon = Icons.Outlined.Lock, password = true, imeAction = ImeAction.Done)
    }
}

@Composable
private fun RegisterForm(state: AuthUiState, viewModel: AuthViewModel) {
    var referralEnabled by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        IconTextField(value = state.name, onValueChange = viewModel::updateName, label = "Full Name", icon = Icons.Outlined.Person)
        IconTextField(value = state.mobile, onValueChange = viewModel::updateMobile, label = "Mobile no.", icon = Icons.Outlined.PhoneAndroid, keyboardType = KeyboardType.Phone)
        IconTextField(value = state.email, onValueChange = viewModel::updateEmail, label = "Email", icon = Icons.Outlined.Email, keyboardType = KeyboardType.Email)
        IconTextField(value = state.password, onValueChange = viewModel::updatePassword, label = "Password", icon = Icons.Outlined.Lock, password = true)
        IconTextField(value = state.confirmPassword, onValueChange = viewModel::updateConfirmPassword, label = "Confirm Password", icon = Icons.Outlined.Lock, password = true, imeAction = ImeAction.Done)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Checkbox(
                checked = referralEnabled,
                onCheckedChange = { referralEnabled = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = DeepNavy,
                    uncheckedColor = DeepNavy.copy(alpha = 0.7f)
                )
            )
            Text(
                "Apply Referral Code", 
                fontSize = 14.sp, 
                fontWeight = FontWeight.SemiBold, 
                color = DeepNavy
            )
        }
    }
}

