package com.horizon.lifeos.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.horizon.lifeos.data.LifeOSDao
import com.horizon.lifeos.data.TaskEntity
import com.horizon.lifeos.theme.PrimaryBlue
import com.horizon.lifeos.theme.SurfaceColor
import com.horizon.lifeos.theme.TextSecondary
import com.horizon.lifeos.util.PersianDateHelper
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(dao: LifeOSDao) {
    val coroutineScope = rememberCoroutineScope()
    val today = "1403-01-01"
    val tasks by dao.getTasksByDate(today).collectAsState(initial = emptyList())
    var newTaskTitle by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "برنامه امروز",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )
        Text(
            text = PersianDateHelper.getCurrentPersianDate(),
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newTaskTitle,
                onValueChange = { newTaskTitle = it },
                label = { Text("کار جدید...") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    if (newTaskTitle.isNotBlank()) {
                        coroutineScope.launch {
                            dao.insertTask(TaskEntity(title = newTaskTitle, time = "10:00", date = today))
                            newTaskTitle = ""
                        }
                    }
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(PrimaryBlue)
            ) {
                Icon(Icons.Default.Add, contentDescription = "افزودن", tint = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(tasks) { task ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = SurfaceColor)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = task.title, color = Color.White, fontSize = 16.sp)
                        IconButton(onClick = {
                            coroutineScope.launch {
                                dao.updateTask(task.copy(isCompleted = !task.isCompleted))
                            }
                        }) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = "انجام شد",
                                tint = if (task.isCompleted) PrimaryBlue else TextSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}
