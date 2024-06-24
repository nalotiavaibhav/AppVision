package com.example.alarmtrial.screens.clock

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alarmtrial.R
import com.example.alarmtrial.ui.theme.AlarmTrialTheme
import com.example.alarmtrial.ui.theme.NavigationBarColor
import kotlinx.coroutines.delay
import java.util.Calendar

class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }
}


@Composable
fun HomeScreen(navController: NavController) {

    var hour by remember { mutableStateOf("0") }
    var minute by remember { mutableStateOf("0") }
    var second by remember { mutableStateOf("0") }
    var amOrPm by remember { mutableStateOf("0") }

    LaunchedEffect(Unit) {
        while (true) {
            val cal = Calendar.getInstance()
            hour = cal.get(Calendar.HOUR).run {
                if (this.toString().length == 1) "0$this" else "$this"
            }
            minute = cal.get(Calendar.MINUTE).run {
                if (this.toString().length == 1) "0$this" else "$this"
            }
            second = cal.get(Calendar.SECOND).run {
                if (this.toString().length == 1) "0$this" else "$this"
            }
            amOrPm = cal.get(Calendar.AM_PM).run {
                if (this == Calendar.AM) "AM" else "PM"
            }

            delay(1000)
        }
    }

    AlarmTrialTheme {
        Scaffold(
            bottomBar = { NavigationBarComponent(navController) }
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    HeaderComponent()
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxHeight(0.8f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            AnalogClockComponent(
                                hour = hour.toInt(),
                                minute = minute.toInt(),
                                second = second.toInt()
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            DigitalClockComponent(
                                hour = hour,
                                minute = minute,
                                amOrPm = amOrPm
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun HeaderComponent() {
    Box(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(text = "Clock", style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun NavigationBarComponent(navController: NavController) {
    NavigationBar(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            .background(NavigationBarColor)
            .padding(16.dp),
        containerColor = NavigationBarColor
    ) {
        NavigationBarItem(
            icon = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_alarm_24),
                        contentDescription = "Alarm"
                    )
                    Text(text = "Alarm")
                }
            },
            selected = false,
            onClick = { navController.navigate("alarm") }
        )

        NavigationBarItem(
            icon = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_access_time_24),
                        contentDescription = null
                    )
                    Text(text = "Clock")
                }
            },
            selected = true,
            onClick = { navController.navigate("home") }
        )

        NavigationBarItem(
            icon = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_timer_24),
                        contentDescription = null
                    )
                    Text(text = "Timer")
                }
            },
            selected = false,
            onClick = { navController.navigate("timer") }
        )
    }
}
@Composable
fun DigitalClockComponent(
    hour: String,
    minute: String,
    amOrPm: String
) {
    Text(
        text = "$hour:$minute $amOrPm",
        style = MaterialTheme.typography.titleLarge
    )
    Text(
        text = "India",
        style = MaterialTheme.typography.bodyMedium.merge(
            TextStyle(
                color = MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.6f
                )
            )
        )
    )
}
