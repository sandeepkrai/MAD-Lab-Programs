package com.example.listclickprint

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.listclickprint.ui.theme.ListClickPrintTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListClickPrintTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Cart()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun Cart() {
    val groceryItems = listOf("Apple", "Banana", "Orange", "Milk", "Bread")
    var selectedItem by remember { mutableStateOf<String?>(null) }
    var checked by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        Text("Welcome to the grocery store!")
        Text("Here are the available items:")
        groceryItems.forEachIndexed { index, item ->
            Button(onClick = {
                selectedItem = item
                Toast.makeText(context, item.toString(), Toast.LENGTH_SHORT).show()
            }) {
                Text(item)
            }
        }
        selectedItem?.let {
            Text("You selected: $it")
        }
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
                Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ListClickPrintTheme {
        Cart()
    }
}