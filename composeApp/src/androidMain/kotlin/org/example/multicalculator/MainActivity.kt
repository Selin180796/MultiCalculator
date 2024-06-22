package org.example.multicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalcView()
        }
    }
}

@Composable
fun CalcView() {
    val displayText = remember { mutableStateOf("0") }

    var leftNumber by rememberSaveable { mutableStateOf(0) }
    var rightNumber by rememberSaveable { mutableStateOf(0) }
    var operation by rememberSaveable { mutableStateOf("") }
    var complete by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize()
            .padding(10.dp),
        //verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (complete && operation != "") {
            var answer = 0
            when (operation) {
                "+" -> answer = leftNumber + rightNumber
                "-" -> answer = leftNumber - rightNumber
                "*" -> answer = leftNumber * rightNumber
                "/" -> if (rightNumber != 0) {
                    answer = leftNumber / rightNumber
                }
            }
            displayText.value = answer.toString()
        } else if (operation != "" && !complete) {
            displayText.value = rightNumber.toString()
        } else {
            displayText.value = leftNumber.toString()
        }

        fun numberPress(btnNum: Int) {
            if (complete) {
                leftNumber = 0
                rightNumber = 0
                operation = ""
                complete = false
            }

            if (operation != "" && !complete) {
                rightNumber = rightNumber * 10 + btnNum
            } else if (operation == "" && !complete) {
                leftNumber = leftNumber * 10 + btnNum
            }
        }

        fun operationPress(op: String) {
            if (!complete) {
                operation = op
            }
        }

        fun equalsPress() {
            complete = true
        }

        CalcDisplay(display = displayText)

        Row(
            modifier = Modifier.padding(top = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                for (i in 7 downTo 1 step 3) {
                    CalcRow(onPress = { numberPress(it) }, startNum = i, numButtons = 3)
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CalcNumericButton(number = 0, onPress = { numberPress(it) })
                    CalcEqualsButton(onPress = { equalsPress() })
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val operations = listOf("+", "-", "*", "/")
                operations.forEach { op ->
                    CalcOperationButton(operation = op, onPress = { operationPress(it) })
                }
            }
        }
    }
}

@Composable
fun CalcRow(onPress: (number: Int) -> Unit, startNum: Int, numButtons: Int) {
    val endNum = startNum + numButtons
    Row(
        modifier = Modifier.padding(0.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (num in startNum until endNum) {
            CalcNumericButton(number = num, onPress = onPress)
        }
    }
}

@Composable
fun CalcDisplay(display: MutableState<String>) {
    Text(
        text = display.value,
        fontSize = 32.sp,

        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(5.dp)
    )
}

@Composable
fun CalcNumericButton(number: Int, onPress: (number: Int) -> Unit) {
    Button(
        onClick = { onPress(number) },
        modifier = Modifier
            .padding(0.dp)
            .size(85.dp)
    ) {
        Text(text = number.toString(), fontSize = 24.sp)
    }
}

@Composable
fun CalcOperationButton(operation: String, onPress: (operation: String) -> Unit) {
    Button(
        onClick = { onPress(operation) },
        modifier = Modifier
            .padding(0.dp)
            .size(85.dp)
    ) {
        Text(text = operation, fontSize = 24.sp)
    }
}

@Composable
fun CalcEqualsButton(onPress: () -> Unit) {
    Button(
        onClick = { onPress() },
        modifier = Modifier
            .padding(0.dp)
            .size(85.dp)
    ) {
        Text(text = "=", fontSize = 24.sp)
    }
}