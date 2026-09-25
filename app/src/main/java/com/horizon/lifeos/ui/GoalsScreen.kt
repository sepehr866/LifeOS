package com.horizon.lifeos.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.horizon.lifeos.data.GoalEntity
import com.horizon.lifeos.data.LifeOSDao
import com.horizon.lifeos.theme.PrimaryBlue
import com.horizon.lifeos.theme.SurfaceColor
import kotlinx.coroutines.launch

@Composable
fun GoalsScreen(dao: LifeOSDao) {
    val coroutineScope = rememberCoroutineScope()
    val goals by dao.getAllGoals().collectAsState(initial = emptyList())
    var goalTitle by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("اهداف", style = MaterialTheme.typography.headlineMedium, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(value = goalTitle, onValueChange = { goalTitle = it }, label = { Text("هدف جدید...") }, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (goalTitle.isNotBlank()) {
                    coroutineScope.launch {
                        dao.insertGoal(GoalEntity(title = goalTitle, category = "شخصی", progress = 0.5f))
                        goalTitle = ""
                    }
                }
            }) { Text("افزودن") }
        }

        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(goals) { goal ->
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = SurfaceColor)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(goal.title, color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(progress = { goal.progress }, modifier = Modifier.fillMaxWidth(), color = PrimaryBlue)
                    }
                }
            }
        }
    }
}
