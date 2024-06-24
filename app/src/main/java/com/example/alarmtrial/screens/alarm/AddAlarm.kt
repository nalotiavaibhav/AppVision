package com.example.alarmtrial.screens.alarm

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun AddAlarmScreen(navController: NavController) {
    var time by remember { mutableStateOf("06:00") }
    var alarmName by remember { mutableStateOf(TextFieldValue("")) }
    var alarmSound by remember { mutableStateOf("Kindergarten") }
    var vibration by remember { mutableStateOf(true) }
    var snooze by remember { mutableStateOf(true) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Time Picker
        Text(text = time, fontSize = 40.sp, modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(16.dp))

        // Date
        Text(
            text = "Tomorrow-Fri, 21 Jun",
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(thickness = 1.dp, color = Color.Gray)

        // Alarm Name
        Text(text = "Alarm name", fontSize = 20.sp)
        BasicTextField(
            value = alarmName,
            onValueChange = { alarmName = it },
            textStyle = TextStyle(fontSize = 20.sp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(thickness = 1.dp, color = Color.Gray)

        // Alarm Sound
        Text(text = "Alarm sound", fontSize = 20.sp)
        Text(text = alarmSound, fontSize = 18.sp, color = Color.Blue)

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(thickness = 1.dp, color = Color.Gray)

        // Vibration
        Text(text = "Vibration", fontSize = 20.sp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Basic call", fontSize = 18.sp)
            Spacer(modifier = Modifier.weight(1f))
            Switch(checked = vibration, onCheckedChange = { vibration = it })
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(thickness = 1.dp, color = Color.Gray)

        // Snooze
        Text(text = "Snooze", fontSize = 20.sp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "5 minutes, 3 times", fontSize = 18.sp)
            Spacer(modifier = Modifier.weight(1f))
            Switch(checked = snooze, onCheckedChange = { snooze = it })
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Cancel and Save Buttons
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(onClick = { navController.navigate("alarm") }) {
                Text(text = "Cancel")
            }
            TextButton(onClick = {
                navController.navigate("alarm")
                Toast.makeText(context, "Alarm Saved", Toast.LENGTH_SHORT).show()
            }) {
                Text(text = "Save")
            }
        }
    }
}

