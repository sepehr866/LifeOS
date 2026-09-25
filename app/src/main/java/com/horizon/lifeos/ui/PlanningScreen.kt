package com.horizon.lifeos.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.horizon.lifeos.data.LifeOSDao
import com.horizon.lifeos.theme.TextSecondary

@Composable
fun PlanningScreen(dao: LifeOSDao) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("برنامه‌ریزی هفتگی", style = MaterialTheme.typography.headlineMedium, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text("چیدمان و اسلات‌های زمانی هفتگی", color = TextSecondary)
    }
}
