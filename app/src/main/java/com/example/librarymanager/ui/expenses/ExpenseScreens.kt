package com.example.librarymanager.ui.expenses

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
internal fun ExpensesScreen(
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
internal fun EditExpenseScreen(expense: Expense?, onBack: () -> Unit) {
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
internal fun ExpenseDetailSheet(expense: Expense, onClose: () -> Unit) {
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


