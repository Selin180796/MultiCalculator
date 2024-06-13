package org.example.multicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CalculatorContent()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    CalculatorContent()
}

@Composable
fun CalculatorContent() {
    val displayState = remember { mutableStateOf("0") }

    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CalcDisplay(displayState)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                CalcNumericButton(1, displayState)
                CalcNumericButton(2, displayState)
                CalcNumericButton(3, displayState)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                CalcOperationButton("+", displayState)
                CalcOperationButton("-", displayState)
                CalcOperationButton("*", displayState)
                CalcOperationButton("/", displayState)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                CalcEqualsButton(displayState)
                CalcClearButton(displayState)
            }
        }
    }
}

@Composable
fun CalcDisplay(displayState: MutableState<String>) {
    Text(
        text = displayState.value,
        style = MaterialTheme.typography.h4,
        modifier = Modifier.padding(16.dp),
        color = Color.Black
    )
}

@Composable
fun CalcNumericButton(number: Int, displayState: MutableState<String>) {
    Button(
        onClick = { displayState.value = if (displayState.value == "0") "$number" else "${displayState.value}$number" },
        modifier = Modifier.padding(4.dp),
    ) {
        Text(text = number.toString())
    }
}

@Composable
fun CalcOperationButton(operation: String, displayState: MutableState<String>) {
    Button(
        onClick = { displayState.value += " $operation " },
        modifier = Modifier.padding(4.dp),
    ) {
        Text(text = operation)
    }
}

@Composable
fun CalcEqualsButton(displayState: MutableState<String>) {
    Button(
        onClick = {
            try {
                val result = evaluateExpression(displayState.value)
                displayState.value = result.toString()
            } catch (e: ArithmeticException) {
                displayState.value = "Error"
            }
        },
        modifier = Modifier.padding(4.dp),
    ) {
        Text(text = "=")
    }
}

@Composable
fun CalcClearButton(displayState: MutableState<String>) {
    Button(
        onClick = { displayState.value = "0" },
        modifier = Modifier.padding(4.dp),
    ) {
        Text(text = "C")
    }
}

fun evaluateExpression(expression: String): Int {
    val parts = expression.split(" ")
    val left = parts[0].toInt()
    val right = parts[2].toInt()
    val operator = parts[1]

    return when (operator) {
        "+" -> left + right
        "-" -> left - right
        "*" -> left * right
        "/" -> left / right
        else -> throw IllegalArgumentException("Unknown operator")
    }
}
