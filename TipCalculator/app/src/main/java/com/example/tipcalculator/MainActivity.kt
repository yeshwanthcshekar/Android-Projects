package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorTheme {
                TipCalculator()
            }
        }
    }
}


@Composable
fun TipCalculator(){
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(32.dp)
            .verticalScroll(rememberScrollState()),
        ) {
        var inputAmount by remember { mutableStateOf("") }
        var tipPercentage by remember { mutableStateOf("") }
        var tipAmount by remember { mutableStateOf("") }
        var roundTip by remember { mutableStateOf(false) }

        val amount = inputAmount.toDoubleOrNull() ?: 0.0
        val tipPercent = tipPercentage.toDoubleOrNull() ?: 0.0

        Text(
            text = "Calculate Tip", modifier = Modifier.align(Alignment.Start))

        tipAmount = calculateTip(amount, tipPercent, roundTip)

//        EditNumberField(inputAmountValue = inputAmount,
//            onValueChangeInputAmount = {inputAmount = it},
//            tipPercentageValue = tipPercentage,
//            modifier = Modifier.padding(8.dp),
//            onValueChangeInputPercentage = {tipPercentage = it})
        EditNumberField(inputAmount,{inputAmount =it}, tipPercentage, {tipPercentage = it}, Modifier.padding(8.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically){
            Text("Round the Tip")
            Switch(checked = roundTip, onCheckedChange = {roundTip = it})
        }
        
        Text(text = stringResource(R.string.tip_amount, tipAmount),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(16.dp)
        )

    }

}

@Composable
private fun EditNumberField(inputAmountValue : String,
                            onValueChangeInputAmount : (String) -> Unit,
                            tipPercentageValue : String,
                            onValueChangeInputPercentage: (String) -> Unit,
                            modifier : Modifier): Unit{

    TextField(
        value = inputAmountValue,
        onValueChange = onValueChangeInputAmount,
        modifier = modifier,
        label = { Text(text = stringResource(R.string.bill_amount)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    TextField(
        value = tipPercentageValue,
        onValueChange = onValueChangeInputPercentage,
        label = { Text(text = stringResource(R.string.tip_percent)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier,
    )
}

fun calculateTip(billAmount: Double, tipPercent : Double = 15.0, roundTip : Boolean): String{
    var tip = ((billAmount * tipPercent)/100)
    if (roundTip) tip = ceil(tip)
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    TipCalculatorTheme {
       TipCalculator()
    }
}