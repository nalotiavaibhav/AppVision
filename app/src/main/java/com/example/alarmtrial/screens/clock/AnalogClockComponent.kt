package com.example.alarmtrial.screens.clock

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.example.alarmtrial.ui.theme.AnalogClockHourHandColor
import com.example.alarmtrial.ui.theme.AnalogClockInnerBoxColor
import com.example.alarmtrial.ui.theme.AnalogClockMinuteHandColor
import com.example.alarmtrial.ui.theme.AnalogClockOuterBoxColor
import com.example.alarmtrial.ui.theme.AnalogClockSecondHandColor
import kotlin.math.min

@Composable
fun AnalogClockComponent(
    hour: Int,
    minute: Int,
    second: Int,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(300.dp) // Adjust size as needed
            .aspectRatio(1f)
            .shadow(
                elevation = 10.dp,
                shape = CircleShape,
                clip = false
            )
            .clip(CircleShape)
            .background(AnalogClockOuterBoxColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(fraction = 0.78f)
                .aspectRatio(1f)
                .shadow(
                    elevation = 10.dp,
                    shape = CircleShape,
                    clip = false
                )
                .clip(CircleShape)
                .background(AnalogClockInnerBoxColor)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val diameter = min(size.width, size.height) * 0.9f
                val radius = diameter / 2

                // Draw hour markers
                repeat(12) { i ->
                    val angle = i * 30f
                    val start = Offset(center.x, center.y - radius)
                    val end = Offset(center.x, center.y - radius + 20.dp.toPx())
                    rotate(angle, center) {
                        drawLine(
                            color = Color.White,
                            start = start,
                            end = end,
                            strokeWidth = 5.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }

                // Calculate ratios for hands
                val secondRatio = second / 60f
                val minuteRatio = minute / 60f
                val hourRatio = (hour % 12 + minuteRatio) / 12f

                // Draw hour hand
                rotate(hourRatio * 360, center) {
                    drawLine(
                        color = AnalogClockHourHandColor,
                        start = center,
                        end = Offset(center.x, center.y - radius * 0.5f),
                        strokeWidth = 8.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }

                // Draw minute hand
                rotate(minuteRatio * 360, center) {
                    drawLine(
                        color = AnalogClockMinuteHandColor,
                        start = center,
                        end = Offset(center.x, center.y - radius * 0.7f),
                        strokeWidth = 6.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }

                // Draw second hand
                rotate(secondRatio * 360, center) {
                    drawLine(
                        color = AnalogClockSecondHandColor,
                        start = center,
                        end = Offset(center.x, center.y - radius * 0.8f),
                        strokeWidth = 4.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }

                // Draw center circle
                drawCircle(
                    color = AnalogClockSecondHandColor,
                    radius = 5.dp.toPx(),
                    center = center
                )
            }
        }
    }
}
