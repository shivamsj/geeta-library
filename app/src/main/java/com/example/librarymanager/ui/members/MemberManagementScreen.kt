package com.example.librarymanager.ui.members

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
internal fun MemberManagementScreen(onBack: () -> Unit) {
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

