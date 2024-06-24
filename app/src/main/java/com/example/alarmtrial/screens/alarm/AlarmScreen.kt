package com.example.alarmtrial.screens.alarm

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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

class AlarmScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlarmScreen()
        }
    }
}

@Composable
fun AlarmScreen(navController: NavController) {

    AlarmTrialTheme {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    navController.navigate("add alarm")
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Add Alarm")
                }
            },
            bottomBar = { NavigationBarComponent(navController) }
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(){
                    AlarmHeaderComponent()
                }
            }
        }
    }
}

@Composable
fun AlarmListComponent(alarms: List<String>) {
    TODO("Not yet implemented")
}

@Composable
fun AlarmHeaderComponent() {
    Box(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(text = "Alarms", style = MaterialTheme.typography.titleMedium)
    }
}