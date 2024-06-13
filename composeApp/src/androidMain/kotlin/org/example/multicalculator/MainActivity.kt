package org.example.multicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
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
            CalcNumericButton(1, displayState)
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
        onClick = { displayState.value = number.toString() },
        modifier = Modifier.padding(4.dp),
    ) {
        Text(text = number.toString())
    }
}
