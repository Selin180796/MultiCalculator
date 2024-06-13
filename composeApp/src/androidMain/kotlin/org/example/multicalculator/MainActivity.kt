package org.example.multicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

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
        CalcDisplay(displayState)
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
