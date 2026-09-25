package com.horizon.lifeos.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.horizon.lifeos.data.FocusSessionEntity
import com.horizon.lifeos.data.LifeOSDao
import com.horizon.lifeos.theme.AccentPeach
import com.horizon.lifeos.util.PersianDateHelper
import kotlinx.coroutines.launch

@Composable
fun FocusScreen(dao: LifeOSDao) {
    var isRunning by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableIntStateOf(25 * 60) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("حالت تمرکز", style = MaterialTheme.typography.headlineLarge, color = Color.White)
        Spacer(modifier = Modifier.height(32.dp))

        val minutes = timeLeft / 60
        val seconds = timeLeft % 60
        Text(
            text = PersianDateHelper.toPersianDigits(String.format("%02d:%02d", minutes, seconds)),
            fontSize = 54.sp,
            color = AccentPeach
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                isRunning = !isRunning
                if (!isRunning) {
                    coroutineScope.launch {
                        dao.recordFocusSession(FocusSessionEntity(durationMinutes = 25))
                    }
                }
            },
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(if (isRunning) "پایان تمرکز" else "شروع تمرکز (۲۵ دقیقه)")
        }
    }
}
