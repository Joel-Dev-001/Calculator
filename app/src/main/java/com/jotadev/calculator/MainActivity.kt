package com.jotadev.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jotadev.calculator.ui.theme.CalculatorTheme
import com.jotadev.calculator.ui.theme.components.ButtonsCalculate
import com.jotadev.calculator.ui.theme.components.Paneldigital
import com.jotadev.calculator.ui.theme.orange

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val buttonValues = listOf(
                "C",
                "+/-",
                "%",
                "÷",
                "7",
                "8",
                "9",
                "x",
                "4",
                "5",
                "6",
                "-",
                "1",
                "2",
                "3",
                "+",
                "0",
                ".",
                "="
            )
            CalculatorTheme {
                Scaffold(
                    modifier = Modifier
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Paneldigital()
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxWidth().padding(15.dp),
                            columns = GridCells.Fixed(4),
                        ) {
                            items(buttonValues.size, span = { index ->
                                if (buttonValues[index] == "0") {
                                    GridItemSpan(2)
                                } else {
                                    GridItemSpan(1)
                                }
                            }) { index ->
                                val isOperator =
                                    buttonValues[index] in listOf("÷", "x", "-", "+", "=")
                                ButtonsCalculate(
                                    modifier = Modifier
                                        .padding(5.dp),
                                    onClick = { /*TODO*/ },
                                    label = buttonValues[index],
                                    backgroundColor =
                                        if (isOperator) orange else MaterialTheme.colorScheme.surfaceVariant,
                                    contentColor =
                                        if (isOperator) Color.White else MaterialTheme.colorScheme.onSurface,
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}

