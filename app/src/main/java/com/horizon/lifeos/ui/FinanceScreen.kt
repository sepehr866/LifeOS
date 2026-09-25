package com.horizon.lifeos.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.horizon.lifeos.data.FinancialTransactionEntity
import com.horizon.lifeos.data.LifeOSDao
import com.horizon.lifeos.theme.AccentPeach
import com.horizon.lifeos.theme.PrimaryBlue
import com.horizon.lifeos.theme.SurfaceColor
import com.horizon.lifeos.util.CurrencyFormatter
import kotlinx.coroutines.launch

@Composable
fun FinanceScreen(dao: LifeOSDao) {
    val coroutineScope = rememberCoroutineScope()
    val transactions by dao.getAllTransactions().collectAsState(initial = emptyList())
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("مدیریت مالی", style = MaterialTheme.typography.headlineMedium, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("عنوان") }, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("مبلغ (ریال)") }, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            val rials = amount.toLongOrNull() ?: 0L
            if (title.isNotBlank() && rials > 0) {
                coroutineScope.launch {
                    dao.insertTransaction(FinancialTransactionEntity(title = title, amountRials = rials, isExpense = true, date = "امروز"))
                    title = ""; amount = ""
                }
            }
        }, modifier = Modifier.fillMaxWidth()) { Text("ثبت هزینه") }

        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(transactions) { tx ->
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = SurfaceColor)) {
                    Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(tx.title, color = Color.White)
                        Text(CurrencyFormatter.rialsToTomansFormatted(tx.amountRials), color = if (tx.isExpense) AccentPeach else PrimaryBlue)
                    }
                }
            }
        }
    }
}
