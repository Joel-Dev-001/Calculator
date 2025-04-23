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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jotadev.calculator.ui.theme.CalculatorTheme
import com.jotadev.calculator.ui.theme.components.ButtonsCalculate
import com.jotadev.calculator.ui.theme.components.Paneldigital
import com.jotadev.calculator.ui.theme.orange
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.DecimalFormat

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
            val currentOperation = remember { mutableStateOf("") }
            val result = remember { mutableStateOf("") }
            val isResultDisplayed = remember { mutableStateOf(false) }

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
                        Paneldigital(
                            currentOperation = currentOperation.value,
                            result = result.value
                        )
                        LazyVerticalGrid(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp),
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
                                val specialOperator = buttonValues[index] in listOf("C", "+/-", "%")
                                ButtonsCalculate(
                                    modifier = Modifier.padding(5.dp),
                                    onClick = {
                                        when (buttonValues[index]) {
                                            "C" -> {
                                                currentOperation.value = ""
                                                result.value = ""
                                                isResultDisplayed.value = false
                                            }

                                            "+/-" -> {
                                                if (result.value == "Error") {
                                                    currentOperation.value = ""
                                                    result.value = ""
                                                    isResultDisplayed.value = false
                                                }
                                                if (isResultDisplayed.value) {
                                                    currentOperation.value =
                                                        toggleSign(result.value)
                                                    result.value = ""
                                                    isResultDisplayed.value = false
                                                } else {
                                                    currentOperation.value =
                                                        toggleSign(currentOperation.value)
                                                }
                                            }

                                            "%" -> {
                                                if (result.value == "Error") {
                                                    currentOperation.value = ""
                                                    result.value = ""
                                                    isResultDisplayed.value = false
                                                }
                                                if (isResultDisplayed.value) {
                                                    currentOperation.value = ""
                                                    result.value = ""
                                                    isResultDisplayed.value = false
                                                }
                                                currentOperation.value += "%"
                                            }

                                            "=" -> {
                                                if (result.value == "Error") {
                                                    currentOperation.value = ""
                                                    result.value = ""
                                                    isResultDisplayed.value = false
                                                }
                                                result.value =
                                                    evaluateExpression(currentOperation.value)
                                                isResultDisplayed.value = true
                                            }

                                            else -> {
                                                if (result.value == "Error") {
                                                    currentOperation.value = ""
                                                    result.value = ""
                                                    isResultDisplayed.value = false
                                                }
                                                if (isResultDisplayed.value) {
                                                    if (buttonValues[index] in listOf(
                                                            "+",
                                                            "-",
                                                            "x",
                                                            "÷"
                                                        )
                                                    ) {
                                                        currentOperation.value =
                                                            "${result.value}${buttonValues[index]}"
                                                    } else {
                                                        currentOperation.value = buttonValues[index]
                                                    }
                                                    isResultDisplayed.value = false
                                                    result.value = ""
                                                } else {
                                                    if (buttonValues[index] == ".") {
                                                        val lastNumber =
                                                            getLastNumber(currentOperation.value)
                                                        if (lastNumber?.contains(".") == true) {
                                                            // Do nothing
                                                        } else {
                                                            currentOperation.value += "."
                                                        }
                                                    } else {
                                                        currentOperation.value += buttonValues[index]
                                                    }
                                                }
                                            }
                                        }
                                    },
                                    label = buttonValues[index],
                                    backgroundColor = when {
                                        specialOperator -> MaterialTheme.colorScheme.outlineVariant
                                        isOperator -> orange
                                        else -> MaterialTheme.colorScheme.surfaceContainerHigh
                                    },
                                    contentColor = if (isOperator) Color.White else MaterialTheme.colorScheme.onSurface,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun evaluateExpression(expression: String): String {
        return try {
            val modifiedExpression = expression
                .replace("x", "*")
                .replace("÷", "/")
                .replace("%", "*0.01")
            val result = ExpressionBuilder(modifiedExpression).build().evaluate()

            val df = DecimalFormat("#.##########")
            df.format(result).toString()
        } catch (e: Exception) {
            "Error"
        }
    }

    private fun toggleSign(expression: String): String {
        if (expression.isEmpty()) return expression
        val regex = Regex("""([-+]?\d*\.?\d+)$""")
        val matchResult = regex.find(expression)
        return if (matchResult != null) {
            val lastNumber = matchResult.value
            val toggled = if (lastNumber.startsWith("-")) {
                lastNumber.substring(1)
            } else {
                "-$lastNumber"
            }
            expression.replaceRange(matchResult.range, toggled)
        } else {
            "$expression-"
        }
    }

    private fun getLastNumber(expression: String): String? {
        val regex = Regex("""([-+]?\d*\.?\d+)$""")
        val matchResult = regex.find(expression)
        return matchResult?.value
    }
}