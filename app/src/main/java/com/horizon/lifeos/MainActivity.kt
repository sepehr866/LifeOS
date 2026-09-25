package com.horizon.lifeos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.horizon.lifeos.data.AppDatabase
import com.horizon.lifeos.theme.LifeOSTheme
import com.horizon.lifeos.ui.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = AppDatabase.getDatabase(this)
        val dao = database.lifeOSDao()

        setContent {
            LifeOSTheme {
                MainScreen(dao = dao)
            }
        }
    }
}
