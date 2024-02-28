package com.example.l5_q1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.l5_q1.ui.theme.L5_Q1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            L5_Q1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun SampleSpinner(
    list: List<Pair<String, String>>,
    preselected: Pair<String, String>,
    onSelectionChanged: (selection: Pair<String, String>) -> Unit
) {

    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) } // initial value

    Box {
        Column {
            OutlinedTextField(
                value = (selected.second),
                onValueChange = { },
                label = { Text(text = "My List") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = { Icon(Icons.Outlined.ArrowDropDown, null) },
                readOnly = true
            )
            DropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                list.forEach { entry ->

                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            selected = entry
                            expanded = false
                            onSelectionChanged(selected)
                        },
                        text = {
                            Text(
                                text = (entry.second),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .align(Alignment.Start))
                        }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(
                    onClick = { expanded = !expanded }
                )
        )
    }
}




@Composable
fun SampleSpinner_Preview(
    navController: NavController
) {
    MaterialTheme {

        val entry1 = Pair("Key1", "Car")
        val entry2 = Pair("Key2", "Bike")
        val entry3 = Pair("Key3", "Auto")
        var vehicleNo by rememberSaveable  {
            mutableStateOf("")
        }
        var rcNo by rememberSaveable  {
            mutableStateOf("")
        }
        var selectedType by rememberSaveable  {
            mutableStateOf("")
        }

        Column {
            SampleSpinner(
                listOf(entry1, entry2, entry3),
                preselected = entry2,
                onSelectionChanged = { selected -> selectedType=selected.second }
            )
            TextField(value =vehicleNo  , onValueChange ={vehicleNo=it} )
            TextField(value =rcNo  , onValueChange ={rcNo=it} )
            Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            val text = "$selectedType $vehicleNo $rcNo"
            navController.navigate(Screen.DetailScreen.withArgs(text))
        },
        ) {
            Text(text = "To Detail Screen")
        }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    L5_Q1Theme {
//        SampleSpinner_Preview()
    }
}