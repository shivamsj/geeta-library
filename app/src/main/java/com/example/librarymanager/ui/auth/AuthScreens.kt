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
        val compact = maxHeight < 740.dp
        val narrow = maxWidth < 380.dp
        val keyboardOpen = WindowInsets.ime.getBottom(LocalDensity.current) > 0
        val sidePadding = if (narrow) 14.dp else 20.dp
        val heroHeight = when {
            keyboardOpen -> 92.dp
            isRegister && compact -> 188.dp
            isRegister -> 220.dp
            compact -> 178.dp
            else -> 210.dp
        }
        val formCompact = compact || keyboardOpen
        val helpSize = if (narrow) 46.dp else 52.dp
        val sheetMaxWidth = 440.dp

        AuthPageBackground(heroHeight = heroHeight)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthPortalHero(
                isRegister = isRegister,
                compact = compact,
                narrow = narrow,
                keyboardOpen = keyboardOpen,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(heroHeight)
                    .padding(horizontal = sidePadding)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = sheetMaxWidth)
                    .padding(horizontal = sidePadding)
                    .offset(y = if (keyboardOpen) (-4).dp else (-14).dp),
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 18.dp)
            ) {
                Column(
                    modifier = Modifier
                        .border(1.dp, Color.White.copy(alpha = 0.85f), RoundedCornerShape(30.dp))
                        .padding(
                            horizontal = if (narrow) 16.dp else 24.dp,
                            vertical = if (formCompact) 14.dp else 22.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 58.dp, height = 6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Brush.horizontalGradient(listOf(DeepNavy, Color(0xFF5275B7))))
                    )
                    Spacer(Modifier.height(if (formCompact) 8.dp else 14.dp))
                    AuthWelcomeDivider(if (isRegister) "Start setup" else "Welcome back")
                    Spacer(Modifier.height(if (formCompact) 8.dp else 14.dp))

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
                    Spacer(Modifier.height(if (formCompact) 9.dp else 15.dp))

                    if (isRegister) {
                        RegisterForm(state = authState, viewModel = authViewModel)
                        AuthPrimaryButton(
                            text = if (authState.isLoading) "Creating Account..." else "Register Now",
                            onClick = {
                                focusManager.clearFocus()
                                authViewModel.register()
                            },
                            enabled = !authState.isLoading,
                            modifier = Modifier.padding(top = if (formCompact) 10.dp else 18.dp)
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
                            modifier = Modifier.padding(top = if (compact) 14.dp else 24.dp)
                        )
                        Text(
                            text = "Forgot Password ?",
                            color = DeepNavy,
                            fontSize = if (compact) 15.sp else 17.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(top = if (compact) 14.dp else 20.dp)
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

            Spacer(Modifier.height(if (formCompact) 36.dp else 72.dp))
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
        val narrow = maxWidth < 360.dp
        val compact = maxHeight < 720.dp
        val keyboardOpen = WindowInsets.ime.getBottom(LocalDensity.current) > 0
        val heroHeight = when {
            keyboardOpen -> 126.dp
            compact -> 228.dp
            else -> 278.dp
        }
        val sidePadding = if (narrow) 12.dp else 16.dp

        AuthPageBackground(heroHeight)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ForgotPasswordHero(
                keyboardOpen = keyboardOpen,
                onBack = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(heroHeight)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 440.dp)
                    .padding(horizontal = sidePadding)
                    .offset(y = (-28).dp),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.98f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = if (narrow) 18.dp else 24.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 58.dp, height = 6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Brush.horizontalGradient(listOf(DeepNavy, Color(0xFF5275B7))))
                    )
                    Text(
                        "Recover your account",
                        color = DeepNavy,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        "Enter the email linked with your Geeta Library account.",
                        color = Muted,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 7.dp, bottom = 20.dp)
                    )

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
                        modifier = Modifier.padding(top = 22.dp)
                    )

                    state.error?.let { AuthStatusMessage(it, isError = true) }
                    state.message?.let { AuthStatusMessage(it, isError = false) }

                    Row(
                        modifier = Modifier
                            .padding(top = 20.dp)
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

            Spacer(Modifier.height(if (compact) 36.dp else 70.dp))
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
private fun ForgotPasswordHero(
    keyboardOpen: Boolean,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 10.dp, top = 8.dp)
                .size(42.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.1f))
        ) {
            Icon(
                Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.size(if (keyboardOpen) 52.dp else 82.dp),
                shape = RoundedCornerShape(if (keyboardOpen) 15.dp else 22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                LogoMark(Modifier.fillMaxSize().padding(5.dp))
            }
            Column(Modifier.weight(1f).padding(start = 18.dp)) {
                Text(
                    "Forgot Password",
                    color = Color.White,
                    fontSize = if (keyboardOpen) 18.sp else 25.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (!keyboardOpen) {
                    Text(
                        "Securely recover access to your account.",
                        color = Color.White.copy(alpha = 0.82f),
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(top = 7.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun AuthPageBackground(heroHeight: androidx.compose.ui.unit.Dp) {
    Canvas(Modifier.fillMaxSize()) {
        drawRect(Color(0xFFF8F9FC))
        drawRect(
            brush = Brush.verticalGradient(listOf(Color(0xFF062F83), DeepNavy)),
            size = Size(size.width, heroHeight.toPx())
        )

        val shelfColor = Color.White.copy(alpha = 0.055f)
        val heroBottom = heroHeight.toPx()
        for (row in 0..4) {
            val y = heroBottom * (0.28f + row * 0.16f)
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

        drawCircle(Color(0xFF0A4FAF).copy(alpha = 0.28f), size.width * 0.42f, Offset(size.width * 0.08f, heroBottom * 0.1f))
        drawCircle(Color.White.copy(alpha = 0.9f), size.width * 0.8f, Offset(size.width * 0.12f, size.height * 1.03f))
        drawCircle(Color(0xFFE9EDF5).copy(alpha = 0.7f), size.width * 0.66f, Offset(size.width * 0.95f, size.height * 0.88f))
    }
}

@Composable
private fun AuthPortalHero(
    isRegister: Boolean,
    compact: Boolean,
    narrow: Boolean,
    keyboardOpen: Boolean,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        val heroCardHeight = when {
            keyboardOpen -> 72.dp
            compact -> 146.dp
            else -> 166.dp
        }
        val logoSize = when {
            keyboardOpen -> 44.dp
            narrow -> 62.dp
            compact -> 68.dp
            else -> 74.dp
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(heroCardHeight)
                .shadow(12.dp, RoundedCornerShape(26.dp))
                .clip(RoundedCornerShape(26.dp))
        ) {
            Canvas(Modifier.fillMaxSize()) {
                drawRect(brush = Brush.linearGradient(listOf(Color(0xFF0D64D8), Color(0xFF073A9D), DeepNavy)))
                val shelf = Color.White.copy(alpha = 0.08f)
                repeat(4) { row ->
                    val y = size.height * (0.3f + row * 0.2f)
                    drawLine(shelf, Offset(0f, y), Offset(size.width, y), 3f)
                    repeat(12) { column ->
                        val cell = size.width / 12f
                        val bookHeight = 18f + ((row * 17 + column * 11) % 34)
                        drawRoundRect(
                            color = Color.White.copy(alpha = 0.045f),
                            topLeft = Offset(column * cell + 5f, y - bookHeight),
                            size = Size(cell * 0.6f, bookHeight),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(3f)
                        )
                    }
                }
                drawCircle(
                    color = Color(0xFF0A76D8).copy(alpha = 0.28f),
                    radius = size.width * 0.34f,
                    center = Offset(size.width * 0.88f, size.height * 0.12f)
                )
                drawCircle(
                    color = LibraryGold.copy(alpha = 0.16f),
                    radius = size.width * 0.22f,
                    center = Offset(size.width * 0.04f, size.height * 0.98f)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = if (narrow) 14.dp else 18.dp, vertical = if (keyboardOpen) 8.dp else 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier.size(logoSize),
                    shape = RoundedCornerShape(if (keyboardOpen) 14.dp else 20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    LogoMark(modifier = Modifier.fillMaxSize().padding(5.dp))
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = if (narrow) 12.dp else 16.dp, end = if (narrow) 0.dp else 8.dp)
                ) {
                    if (!keyboardOpen) {
                        Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                            PortalBadge(if (isRegister) "NEW" else "EXISTING")
                            PortalBadge("SECURE")
                        }
                    }
                    Text(
                        text = if (isRegister) {
                            if (keyboardOpen) "Create account" else "Create Library account"
                        } else {
                            if (narrow) "Admin Portal" else "Library Admin"
                        },
                        color = Color.White,
                        fontSize = if (keyboardOpen) 16.sp else if (narrow) 20.sp else 24.sp,
                        fontWeight = FontWeight.Black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = if (narrow) 22.sp else 26.sp,
                        modifier = Modifier.padding(top = if (keyboardOpen) 3.dp else 8.dp)
                    )
                    if (!keyboardOpen) {
                        Text(
                            text = if (isRegister) {
                                "Create an admin account and start managing faster."
                            } else {
                                "Manage members, seats, fees and reports from one place."
                            },
                            color = Color.White.copy(alpha = 0.84f),
                            fontSize = if (narrow) 12.sp else 13.sp,
                            lineHeight = 17.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                }

                if (!keyboardOpen && !compact && !narrow) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .border(1.dp, Color.White.copy(alpha = 0.35f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.AutoMirrored.Outlined.MenuBook, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun PortalBadge(text: String) {
    Row(
        modifier = Modifier
            .border(1.dp, Color.White.copy(alpha = 0.38f), RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .padding(horizontal = 7.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(Icons.Outlined.Security, contentDescription = null, tint = LibraryGold, modifier = Modifier.size(11.dp))
        Text(text, color = Color.White.copy(alpha = 0.94f), fontSize = 8.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun AuthWelcomeDivider(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(Modifier.weight(1f).height(1.dp).background(Border))
        Text(
            text = title,
            color = DeepNavy,
            fontSize = 15.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .background(Brush.horizontalGradient(listOf(RoyalBlue.copy(alpha = 0.08f), LibraryGold.copy(alpha = 0.12f))))
                .padding(horizontal = 14.dp, vertical = 6.dp)
        )
        Box(Modifier.weight(1f).height(1.dp).background(Border))
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
            Text("Register", color = if (isRegister) DeepNavy else Muted, fontSize = 16.sp, fontWeight = FontWeight.Black)
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

