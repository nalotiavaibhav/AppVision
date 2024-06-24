package com.example.alarmtrial.screens.timer

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alarmtrial.screens.clock.NavigationBarComponent
import com.example.alarmtrial.ui.theme.AlarmTrialTheme

class TimerScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimerScreen()
        }
    }
}

@Composable
fun TimerScreen(navController: NavController) {
    AlarmTrialTheme {
        Scaffold(
            bottomBar = { NavigationBarComponent(navController) }
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(){
                    TimerHeaderComponent()
                }
            }
        }
    }
}

@Composable
fun TimerHeaderComponent() {
    Box(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(text = "Set Timer", style = MaterialTheme.typography.titleMedium)
    }
}


