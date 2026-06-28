package com.example.librarymanager

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

private val Orange = Color(0xFF0A43A3)
private val OrangeDark = Color(0xFF061F62)
private val OrangeSoft = Color(0xFF1763C8)
private val Green = Color(0xFF00B825)
private val Blue = Color(0xFF0C95B8)
private val Pink = Color(0xFFE5006E)
private val Border = Color(0xFFE4E7EC)
private val TextDark = Color(0xFF1C2430)
private val Muted = Color(0xFF7B8494)
private val RoyalBlue = Color(0xFF063BAA)
private val DeepNavy = Color(0xFF061F62)
private val LibraryGold = Color(0xFFD9A52A)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val density = LocalDensity.current
            CompositionLocalProvider(LocalDensity provides Density(density.density, fontScale = 1f)) {
                LibraryManagerTheme(darkTheme = false, dynamicColor = false) {
                    GoLibraryApp()
                }
            }
        }
    }
}

private enum class Page {
    Splash,
    Login,
    ForgotPassword,
    Dashboard,
    Seats,
    Members,
    Expenses,
    EditExpense,
    Module
}

private data class DashboardCardData(
    val title: String,
    val value: String,
    val color: Color,
    val icon: String
)

@Composable
private fun SplashScreen(onDone: () -> Unit) {
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

@Composable
private fun GoLibraryApp() {
    val authViewModel: AuthViewModel = viewModel()
    val sessionExpired by ApiClient.sessionExpired.collectAsStateWithLifecycle()
    val currentUser by ApiClient.currentUser.collectAsStateWithLifecycle()
    val backStack = remember { mutableStateListOf(Page.Splash) }
    val page = backStack.last()
    var selectedExpense by remember { mutableStateOf<Expense?>(null) }
    var editingExpense by remember { mutableStateOf<Expense?>(null) }
    var moduleTitle by remember { mutableStateOf("Module") }
    var drawerOpen by remember { mutableStateOf(false) }

    fun navigate(destination: Page) {
        if (backStack.lastOrNull() != destination) backStack.add(destination)
    }

    fun replaceRoot(destination: Page) {
        backStack.clear()
        backStack.add(destination)
    }

    fun goBack() {
        if (backStack.size > 1) backStack.removeAt(backStack.lastIndex)
    }

    fun openModule(title: String) {
        if (title == "Member Management" || title == "Add Member") {
            navigate(Page.Members)
            return
        }
        moduleTitle = title
        navigate(Page.Module)
    }

    fun openFromDrawer(destination: Page) {
        drawerOpen = false
        navigate(destination)
    }

    LaunchedEffect(sessionExpired) {
        if (sessionExpired) {
            replaceRoot(Page.Login)
            ApiClient.consumeSessionExpiry()
        }
    }

    BackHandler(enabled = drawerOpen || selectedExpense != null || backStack.size > 1) {
        when {
            drawerOpen -> drawerOpen = false
            selectedExpense != null -> selectedExpense = null
            else -> goBack()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        when (page) {
            Page.Splash -> SplashScreen(onDone = { replaceRoot(Page.Login) })
            Page.Login -> AuthScreen(
                viewModel = authViewModel,
                onLogin = { replaceRoot(Page.Dashboard) },
                onForgotPassword = { navigate(Page.ForgotPassword) },
                onOpenModule = ::openModule
            )
            Page.ForgotPassword -> ForgotPasswordScreen(
                viewModel = authViewModel,
                onBack = ::goBack,
                onOpenHelp = { openModule("Need Help?") }
            )
            Page.Dashboard -> DashboardScreen(
                onMenu = { drawerOpen = true },
                onSeats = { navigate(Page.Seats) },
                onExpenses = { navigate(Page.Expenses) },
                onOpenModule = ::openModule
            )
            Page.Seats -> SeatMatrixScreen(onBack = ::goBack)
            Page.Members -> MemberManagementScreen(onBack = ::goBack)
            Page.Expenses -> ExpensesScreen(
                onBack = ::goBack,
                onAdd = {
                    editingExpense = null
                    navigate(Page.EditExpense)
                },
                onEdit = {
                    editingExpense = it
                    navigate(Page.EditExpense)
                },
                onView = { selectedExpense = it },
                onOpenModule = ::openModule
            )
            Page.EditExpense -> EditExpenseScreen(
                expense = editingExpense,
                onBack = ::goBack
            )
            Page.Module -> ModuleScreen(title = moduleTitle, onBack = ::goBack)
        }

        selectedExpense?.let {
            ExpenseDetailSheet(expense = it, onClose = { selectedExpense = null })
        }

        DrawerScreen(
            visible = drawerOpen,
            user = currentUser,
            onBack = { drawerOpen = false },
            onLogout = {
                drawerOpen = false
                authViewModel.signOut()
                replaceRoot(Page.Login)
            },
            onSeats = { openFromDrawer(Page.Seats) },
            onExpenses = { openFromDrawer(Page.Expenses) },
            onOpenModule = {
                drawerOpen = false
                openModule(it)
            }
        )
    }
}

@Composable
private fun AuthScreen(
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
        val compact = maxHeight < 720.dp
        val narrow = maxWidth < 360.dp
        val keyboardOpen = WindowInsets.ime.getBottom(LocalDensity.current) > 0
        val sidePadding = if (narrow) 12.dp else 16.dp
        val heroHeight = when {
            keyboardOpen -> 86.dp
            isRegister && compact -> 196.dp
            isRegister -> 238.dp
            compact -> 190.dp
            else -> 224.dp
        }
        val formCompact = compact || keyboardOpen
        val helpSize = if (narrow) 46.dp else 52.dp
        val sheetMaxWidth = 420.dp

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
                    .padding(horizontal = if (narrow) 12.dp else 18.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = sheetMaxWidth)
                    .padding(horizontal = sidePadding)
                    .offset(y = if (keyboardOpen) (-6).dp else (-22).dp),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.98f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = if (narrow) 16.dp else 24.dp,
                        vertical = if (formCompact) 12.dp else 20.dp
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
                                .padding(top = if (compact) 8.dp else 12.dp),
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
private fun ForgotPasswordScreen(
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (keyboardOpen) 70.dp else if (compact) 154.dp else 180.dp)
                .shadow(12.dp, RoundedCornerShape(26.dp))
                .clip(RoundedCornerShape(26.dp))
        ) {
            Canvas(Modifier.fillMaxSize()) {
                drawRect(brush = Brush.horizontalGradient(listOf(Color(0xFF0B58BD), DeepNavy)))
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
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = if (narrow) 14.dp else 18.dp, vertical = if (keyboardOpen) 8.dp else 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier.size(if (keyboardOpen) 44.dp else if (narrow) 66.dp else 76.dp),
                    shape = RoundedCornerShape(if (keyboardOpen) 14.dp else 20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    LogoMark(modifier = Modifier.fillMaxSize().padding(5.dp))
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = if (narrow) 12.dp else 16.dp)
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
                            "Library Admin Portal"
                        },
                        color = Color.White,
                        fontSize = if (keyboardOpen) 15.sp else if (narrow) 18.sp else 20.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = if (keyboardOpen) 5.dp else 8.dp)
                    )
                    if (!keyboardOpen) {
                        Text(
                            text = if (isRegister) {
                                "Register today and manage your library with confidence."
                            } else {
                                "Manage members, seats, fees, attendance and reports."
                            },
                            color = Color.White.copy(alpha = 0.84f),
                            fontSize = if (narrow) 11.sp else 12.sp,
                            lineHeight = 16.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                }

                if (!narrow && !keyboardOpen) {
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
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .background(RoyalBlue.copy(alpha = 0.06f))
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
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .border(1.dp, RoyalBlue.copy(alpha = 0.14f), RoundedCornerShape(24.dp))
            .background(RoyalBlue.copy(alpha = 0.07f))
            .padding(3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(24.dp))
                .then(if (!isRegister) Modifier.shadow(3.dp, RoundedCornerShape(24.dp)) else Modifier)
                .background(if (!isRegister) Color.White else Color.Transparent)
                .clickable { onLogin() },
            contentAlignment = Alignment.Center
        ) {
            Text("Login", color = if (!isRegister) DeepNavy else Muted, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(24.dp))
                .then(if (isRegister) Modifier.shadow(3.dp, RoundedCornerShape(24.dp)) else Modifier)
                .background(if (isRegister) Color.White else Color.Transparent)
                .clickable { onRegister() },
            contentAlignment = Alignment.Center
        ) {
            Text("Register", color = if (isRegister) DeepNavy else Muted, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun LoginForm(state: AuthUiState, viewModel: AuthViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
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

@Composable
private fun DashboardScreen(
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
private fun DrawerScreen(
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

@Composable
private fun SeatMatrixScreen(onBack: () -> Unit) {
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
private fun ExpensesScreen(
    onBack: () -> Unit,
    onAdd: () -> Unit,
    onEdit: (Expense) -> Unit,
    onView: (Expense) -> Unit,
    onOpenModule: (String) -> Unit
) {
    val viewModel: ExpenseViewModel = viewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val total = state.expenses.sumOf { it.amount }

    Box(Modifier.fillMaxSize().background(Color.White)) {
        Column(Modifier.fillMaxSize()) {
            AppBar(title = "Manage Expenses", onBack = onBack)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(94.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SummaryCell("Total", "", Modifier.weight(1f))
                Box(Modifier.width(1.dp).height(58.dp).background(Border))
                SummaryCell("Expenses", "%.2f".format(total), Modifier.weight(1f))
            }
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 70.dp)
            ) {
                items(state.expenses, key = { it.id }) { expense ->
                    ExpenseRow(
                        expense = expense,
                        onEdit = { onEdit(expense) },
                        onDelete = { viewModel.delete(expense) },
                        onView = { onView(expense) }
                    )
                }
                if (!state.isLoading && state.expenses.isEmpty()) {
                    item { EmptyState("No expenses yet. Tap + to add the first expense.") }
                }
            }
        }
        FloatingCircle(
            label = "+",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 22.dp, bottom = 76.dp)
                .clickable { onAdd() }
        )
        Row(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(54.dp)
        ) {
            ExportButton("Export as xls", onClick = { onOpenModule("Excel Export") }, modifier = Modifier.weight(1f))
            Box(Modifier.width(1.dp).fillMaxHeight().background(Color.White))
            ExportButton("Export as pdf", onClick = { onOpenModule("PDF Export") }, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun EditExpenseScreen(expense: Expense?, onBack: () -> Unit) {
    val viewModel: ExpenseViewModel = viewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var date by remember(expense?.id) { mutableStateOf(expense?.date.orEmpty()) }
    var title by remember(expense?.id) { mutableStateOf(expense?.title.orEmpty()) }
    var description by remember(expense?.id) { mutableStateOf(expense?.description.orEmpty()) }
    var amount by remember(expense?.id) { mutableStateOf(expense?.amount?.takeIf { it > 0 }?.toString().orEmpty()) }
    Column(Modifier.fillMaxSize().background(Color.White)) {
        AppBar(title = if (expense == null) "Add Expense" else "Edit Expense", onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            CrudField("Date (YYYY-MM-DD)", date, { date = it })
            CrudField("Title", title, { title = it })
            CrudField("Description", description, { description = it }, minLines = 3)
            CrudField("Amount", amount, { amount = it }, keyboardType = KeyboardType.Decimal)
            state.error?.let { Text(it, color = Color(0xFFC83A32), fontWeight = FontWeight.SemiBold) }
            AuthPrimaryButton(
                text = if (state.isLoading) "Saving..." else if (expense == null) "Save Expense" else "Update Expense",
                enabled = !state.isLoading,
                onClick = {
                    val valid = viewModel.save(
                        Expense(
                            id = expense?.id.orEmpty(),
                            title = title.trim(),
                            amount = amount.toDoubleOrNull() ?: 0.0,
                            date = date.trim(),
                            description = description.trim()
                        )
                    )
                    if (valid) onBack()
                },
                modifier = Modifier
                    .padding(horizontal = 42.dp, vertical = 12.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun MemberManagementScreen(onBack: () -> Unit) {
    val viewModel: MemberViewModel = viewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var editing by remember { mutableStateOf<Member?>(null) }
    var name by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var seat by remember { mutableStateOf("") }
    var plan by remember { mutableStateOf("") }

    fun load(member: Member?) {
        editing = member
        name = member?.name.orEmpty()
        mobile = member?.mobile.orEmpty()
        address = member?.address.orEmpty()
        seat = member?.seatNumber.orEmpty()
        plan = member?.planName.orEmpty()
    }

    Column(Modifier.fillMaxSize().background(Color(0xFFF6F8FC))) {
        AppBar(title = "Member Management", onBack = onBack)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(if (editing == null) "Add Member" else "Edit Member", color = DeepNavy, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        CrudField("Full name", name, { name = it })
                        CrudField("Mobile number", mobile, { mobile = it }, keyboardType = KeyboardType.Phone)
                        CrudField("Address", address, { address = it }, minLines = 2)
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            CrudField("Seat number", seat, { seat = it }, modifier = Modifier.weight(1f))
                            CrudField("Plan", plan, { plan = it }, modifier = Modifier.weight(1f))
                        }
                        state.error?.let { Text(it, color = Color(0xFFC83A32), fontSize = 12.sp, fontWeight = FontWeight.SemiBold) }
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            if (editing != null) {
                                Button(onClick = { load(null) }, modifier = Modifier.weight(1f)) { Text("Cancel") }
                            }
                            AuthPrimaryButton(
                                text = if (state.isLoading) "Saving..." else "Save Member",
                                enabled = !state.isLoading,
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    val valid = viewModel.save(
                                        Member(
                                            id = editing?.id.orEmpty(),
                                            name = name.trim(),
                                            mobile = mobile.trim(),
                                            address = address.trim(),
                                            seatNumber = seat.trim(),
                                            planName = plan.trim(),
                                            joiningDate = editing?.joiningDate.orEmpty(),
                                            expiryDate = editing?.expiryDate.orEmpty(),
                                            dueAmount = editing?.dueAmount ?: 0.0,
                                            status = editing?.status ?: "ACTIVE"
                                        )
                                    )
                                    if (valid) load(null)
                                }
                            )
                        }
                    }
                }
            }

            item {
                Text("Members (${state.members.size})", color = DeepNavy, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            items(state.members, key = { it.id }) { member ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        IconBubble(member.name.take(2).uppercase(), RoyalBlue, 44.dp)
                        Column(Modifier.weight(1f).padding(horizontal = 12.dp)) {
                            Text(member.name, color = TextDark, fontWeight = FontWeight.Bold)
                            Text(member.mobile, color = Muted, fontSize = 13.sp)
                            Text(listOf(member.seatNumber, member.planName).filter { it.isNotBlank() }.joinToString(" | "), color = DeepNavy, fontSize = 12.sp)
                        }
                        MiniAction("EDIT", "Edit") { load(member) }
                        MiniAction("DEL", "Delete") { viewModel.delete(member) }
                    }
                }
            }
            if (!state.isLoading && state.members.isEmpty()) {
                item { EmptyState("No members found. Add your first member above.") }
            }
        }
    }
}

@Composable
private fun CrudField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    minLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        minLines = minLines,
        maxLines = if (minLines > 1) 4 else 1,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = RoyalBlue, cursorColor = RoyalBlue),
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
private fun EmptyState(message: String) {
    Text(
        message,
        color = Muted,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth().padding(28.dp)
    )
}

@Composable
private fun ModuleScreen(title: String, onBack: () -> Unit) {
    var input by remember(title) { mutableStateOf("") }
    var status by remember(title) { mutableStateOf("") }

    Column(Modifier.fillMaxSize().background(Color(0xFFF6F8FC))) {
        AppBar(title = title, onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    IconBubble(title.take(2).uppercase(), RoyalBlue, 46.dp)
                    Text(title, color = DeepNavy, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text(
                        "This module is connected to the Geeta Library navigation and is ready for data entry.",
                        color = Muted,
                        fontSize = 14.sp
                    )
                }
            }

            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("Enter details") },
                minLines = 3,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = RoyalBlue, cursorColor = RoyalBlue),
                modifier = Modifier.fillMaxWidth()
            )

            AuthPrimaryButton(
                text = if (title.contains("Export")) "Generate $title" else "Save & Continue",
                onClick = {
                    status = if (input.isBlank()) "Please enter details first" else "$title updated successfully"
                }
            )

            if (status.isNotBlank()) {
                Text(
                    status,
                    color = if (status.startsWith("Please")) Color(0xFFC83A32) else Green,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
private fun ExpenseDetailSheet(expense: Expense, onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x99000000))
            .clickable { onClose() },
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .background(Color.White)
                .clickable(enabled = false) {}
                .padding(22.dp)
        ) {
            Text("Expense on ${expense.date}", fontSize = 24.sp, color = TextDark)
            Spacer(Modifier.height(30.dp))
            Row(Modifier.fillMaxWidth()) {
                Column(Modifier.weight(1f)) {
                    Text("#1", fontSize = 18.sp, color = TextDark)
                    Text("Description", color = Orange, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(expense.description, color = TextDark, modifier = Modifier.padding(top = 12.dp))
                }
                Column(Modifier.weight(1f)) {
                    Text("Amount", color = Orange, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text("%.2f".format(expense.amount), color = TextDark, modifier = Modifier.padding(top = 18.dp))
                }
            }
            Text(
                "CLOSE",
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 38.dp)
                    .clickable { onClose() },
                color = TextDark,
                fontWeight = FontWeight.Medium
            )
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
private fun AppBar(title: String, onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp)
            .background(orangeGradient())
            .statusBarsPadding()
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("<", color = Color.White, fontSize = 28.sp, modifier = Modifier.clickable { onBack() })
        Text(
            title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 18.dp)
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

@Composable
private fun ExpenseRow(expense: Expense, onEdit: () -> Unit, onDelete: () -> Unit, onView: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(104.dp)
            .border(0.5.dp, Border)
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .size(62.dp)
                .clip(CircleShape)
                .background(Orange),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(expense.date.takeLast(2).ifBlank { "--" }, color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
            Text(expense.date.take(7).ifBlank { "DATE" }, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp)
        ) {
            Text("Title", color = Muted, fontSize = 13.sp)
            Text(expense.title, color = TextDark, fontSize = 15.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text("Amount", color = Muted, fontSize = 13.sp)
            Text("%.2f".format(expense.amount), color = TextDark, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
        MiniAction("EDIT", "Edit", onEdit)
        MiniAction("DEL", "Delete", onDelete)
        Box(
            modifier = Modifier
                .width(56.dp)
                .height(42.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(orangeGradient())
                .clickable { onView() },
            contentAlignment = Alignment.Center
        ) {
            Text("View", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun MiniAction(icon: String, label: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(40.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(icon, color = Orange, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Text(label, color = Orange, fontSize = 10.sp)
    }
}

@Composable
private fun SummaryCell(title: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, color = TextDark, fontSize = 17.sp, fontWeight = FontWeight.Bold)
        if (value.isNotBlank()) {
            Text(value, color = DeepNavy, fontSize = 21.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
        }
    }
}

@Composable
private fun ExportButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(orangeGradient())
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun IconTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    password: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(label, color = Muted, fontSize = 15.sp, fontWeight = FontWeight.SemiBold) },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Muted,
                    modifier = Modifier.size(22.dp)
                )
            },
            trailingIcon = if (password) {
                {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = if (passwordVisible) RoyalBlue else Muted,
                            modifier = Modifier.size(23.dp)
                        )
                    }
                }
            } else null,
            visualTransformation = if (password && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (password) KeyboardType.Password else keyboardType,
                capitalization = KeyboardCapitalization.None,
                autoCorrectEnabled = false,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextDark,
                unfocusedTextColor = TextDark,
                focusedBorderColor = RoyalBlue,
                unfocusedBorderColor = Color(0xFFDDE5EF),
                cursorColor = RoyalBlue,
                focusedContainerColor = Color(0xFFF8FBFE),
                unfocusedContainerColor = Color(0xFFF8FBFE)
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
    }
}

@Composable
private fun CheckLabel(text: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checked, 
            onCheckedChange = onChange, 
            colors = CheckboxDefaults.colors(
                checkedColor = DeepNavy,
                uncheckedColor = DeepNavy.copy(alpha = 0.8f)
            )
        )
        Text(
            text = text, 
            color = DeepNavy, 
            fontSize = 13.sp, 
            fontWeight = FontWeight.SemiBold, 
            maxLines = 1
        )
    }
}

@Composable
private fun AuthPrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, disabledContainerColor = Color.Transparent),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Brush.horizontalGradient(if (enabled) listOf(Color(0xFF0844A4), DeepNavy) else listOf(Muted, Muted)))
    ) {
        Text(text, color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun OrangeButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(orangeGradient())
    ) {
        Text(text, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, maxLines = 1)
    }
}

@Composable
private fun FloatingCircle(label: String, modifier: Modifier = Modifier, size: androidx.compose.ui.unit.Dp = 64.dp) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(orangeGradient()),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun LogoMark(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.geeta_library_logo),
        contentDescription = "Geeta Library logo",
        contentScale = ContentScale.Fit,
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
    )
}

@Composable
private fun IconBubble(text: String, color: Color, size: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(10.dp))
            .background(color.copy(alpha = 0.14f))
            .border(1.dp, color.copy(alpha = 0.35f), RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = color, fontSize = 11.sp, fontWeight = FontWeight.Black, textAlign = TextAlign.Center)
    }
}

private fun orangeGradient(): Brush {
    return Brush.linearGradient(listOf(OrangeSoft, Orange, OrangeDark))
}

@Preview(showBackground = true)
@Composable
private fun GoLibraryPreview() {
    LibraryManagerTheme(dynamicColor = false) {
        GoLibraryApp()
    }
}
