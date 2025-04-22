package com.jotadev.calculator.ui.theme.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonsCalculate(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color=MaterialTheme.colorScheme.onSurface
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(80.dp)
            .height(80.dp),
        shape=RoundedCornerShape(17.dp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
    ) {
        Text(
            text = label,
            fontSize = 28.sp,
        )
    }
}
