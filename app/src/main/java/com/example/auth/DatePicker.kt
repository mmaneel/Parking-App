@file:Suppress("DEPRECATION")

package com.example.auth

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.auth.Screens.formatTime
import java.util.Calendar
import java.util.Date



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePicker(context: Context, callback: (Date) -> Unit)
{


    val dateTime = remember {
        mutableLongStateOf(Calendar.getInstance().timeInMillis)
    }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = dateTime.longValue,
    )
    var showDatePicker by remember { mutableStateOf(true) }

    val timePickerState = rememberTimePickerState(
        initialHour = 12,
        initialMinute = 30,
    )
    var showTimePicker by remember { mutableStateOf(false) }


    // date picker component
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest =
            {
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedDate = Calendar.getInstance().apply {
                            timeInMillis = datePickerState.selectedDateMillis!! + 23*60*60*1000-1
                        }
                        if (selectedDate.timeInMillis >= Calendar.getInstance().timeInMillis) {
                            showTimePicker = true
                            showDatePicker = false
                        } else {
                            Toast.makeText(
                                context,
                                "La date selectionée doit étre dans le future",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                ) { Text("Valider") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) { Text("Annuler") }
            }
        )
        {
            DatePicker(state = datePickerState,
                colors = DatePickerDefaults.colors(
                todayContentColor = Color.Blue,
                todayDateBorderColor = Color.Blue,
                selectedDayContainerColor = Color(0xFF7136ff),
            ))
        }
    }

// time picker component
    if (showTimePicker) {
        TimePickerDialog(
            onDismissRequest =
            {
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedDateTime = Calendar.getInstance().apply {
                            timeInMillis = datePickerState.selectedDateMillis!! +
                                    ((timePickerState.hour -1)*60  + timePickerState.minute) *60*1000
                        }
                        if (selectedDateTime.timeInMillis >= Calendar.getInstance().timeInMillis) {
                            callback(selectedDateTime.time)
                            showTimePicker = false
                        } else {
                            Toast.makeText(
                                context,
                                "La date selectionée doit étre dans le future ${formatTime(selectedDateTime.time)}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                ) { Text("Valider") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showTimePicker = false
                    }
                ) { Text("Annuler") }
            }
        )
        {
            TimePicker(state = timePickerState)
        }
    }



}




@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onDismissRequest: () -> Unit,
    confirmButton: @Composable (() -> Unit),
    dismissButton: @Composable (() -> Unit)? = null,
    containerColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = containerColor
                ),
            color = containerColor
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    dismissButton?.invoke()
                    confirmButton()
                }
            }
        }
    }
}
