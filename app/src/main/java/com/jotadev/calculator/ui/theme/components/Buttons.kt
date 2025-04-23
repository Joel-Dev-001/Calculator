package com.jotadev.calculator.ui.theme.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonsCalculate(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color=MaterialTheme.colorScheme.onSurface,
    elevation: Int = 5,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(80.dp)
            .height(80.dp)
            .shadow(elevation.dp, RoundedCornerShape(17.dp)),
        shape=RoundedCornerShape(17.dp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        elevation = androidx.compose.material3.ButtonDefaults.buttonElevation(
            defaultElevation = elevation.dp,
            pressedElevation = (elevation + 17).dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp
        )
    ) {
        Text(
            text = label,
            fontSize = 29.sp,
            softWrap = false

        )
    }
}
