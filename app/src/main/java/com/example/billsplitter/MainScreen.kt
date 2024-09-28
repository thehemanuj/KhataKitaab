package com.example.billsplitter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier){
    var slider by remember{ mutableFloatStateOf(0f) }
    var moneyString by remember{ mutableStateOf("") }
    var peopleString by remember{ mutableStateOf("") }
    var charges by remember{ mutableDoubleStateOf(0.0) }
    var payTip by remember{ mutableStateOf(true) }

    Scaffold(
        topBar= { TopAppBar(title = {Text("Khata Kitaab",color=Color(73,93,146,255),
            fontWeight=FontWeight.Bold)
        }
        )
        }
    ) {
        innerPadding->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(value = moneyString,
                onValueChange = { moneyString = it },
                placeholder = { Text("Enter the amount") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(value = peopleString.toString(),
                onValueChange = { peopleString = it },
                placeholder = { Text("How Many People To Share Bill With?") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Pay Tip?😕")
                Switch(
                    checked = payTip,
                    onCheckedChange = {
                        payTip = !payTip
                        if (payTip == false) {
                            slider = 0f
                        }
                    },
                )
            }
            var money = moneyString.toIntOrNull() ?: 0
            var people = peopleString.toIntOrNull() ?: 1
            Slider(
                value = slider,
                onValueChange = { slider = it },
                valueRange = 0f..10f,
                enabled = payTip,
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            Text("Every Person Should Pay ${charges}")
            Text("You're tipping ${(slider.toInt() * money) / 100.0}")
            Button(onClick = {
                charges =
                    calculate(money, people, if (payTip) (slider.toInt() * money) / 100.0 else 0.0)
            }) {
                Text("Calculate")
            }
        }
    }
}

fun calculate(money:Int,people:Int,tip:Double):Double{
    val money2:Double=(money+tip)/(people.toDouble())
    return money2
}