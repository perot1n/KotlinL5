package com.example.lab5

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun AddEmployeeDialog(
    state: EmployeeState,
    onEvent: (EmployeeEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isEfficiencyEmpty = remember { mutableStateOf(true) }

    AlertDialog(
        modifier = modifier.fillMaxWidth(),
        onDismissRequest = {
            onEvent(EmployeeEvent.HideDialog)
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                TextField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    value = state.name,
                    onValueChange = {
                        onEvent(EmployeeEvent.SetName(it))
                    },
                    placeholder = {
                        Text(text = "Name")
                    }
                )
                TextField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = state.salary,
                    onValueChange = { newValue ->
                        onEvent(EmployeeEvent.SetSalary(newValue))
                    },
                    placeholder = {
                        Text(text = "Base Salary")
                    }
                )
                TextField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = state.experience,
                    onValueChange = { newValue ->
                        val number = newValue.toIntOrNull()
                        if (number != null) {
                            onEvent(EmployeeEvent.SetExperience(number.toString()))}},
                    placeholder = {
                        Text(text = "Experience")
                    }
                )
                if (state.position == "Designer") {
                    TextField(
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        value = state.efficiency,
                        onValueChange = { newValue ->
                            val number = newValue.toDoubleOrNull()
                            if (number != null) {
                                onEvent(EmployeeEvent.SetEfficiency(number.toString()))
                                isEfficiencyEmpty.value = newValue.isEmpty()
                            }
                        },
                        placeholder = {
                            Text(text = "Efficiency")
                        }
                    )
                    if (isEfficiencyEmpty.value) {
                        Text(text = "Efficiency cannot be empty", color = Color.Red)
                    }
                    else {
                        isEfficiencyEmpty.value = false
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    )
                    {
                        RadioButton(
                            selected = (state.position == "Developer"),
                            onClick = { onEvent(EmployeeEvent.SetPosition("Developer")) }
                        )
                        Text(text = "Developer") }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {Row(
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    RadioButton(
                        selected = (state.position == "Designer"),
                        onClick = { onEvent(EmployeeEvent.SetPosition("Designer")) }
                    )
                    Text(text = "Designer")
                }}

            }
        },
        confirmButton = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp, bottom = 8.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    OutlinedButton(
                        onClick = {
                            onEvent(EmployeeEvent.HideDialog)
                        },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(text = "Cancel")
                    }
                    Button(
                        onClick = {
                            if (!isEfficiencyEmpty.value || state.position == "Developer") {
                                onEvent(EmployeeEvent.SaveEmployee)
                            }
                        },
                        enabled = !isEfficiencyEmpty.value || state.position == "Developer"
                    ) {
                        Text(text = "Save")
                    }
                }
            }
        }
    )
}