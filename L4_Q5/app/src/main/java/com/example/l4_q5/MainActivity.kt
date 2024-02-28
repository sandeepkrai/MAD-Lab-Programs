package com.example.l4_q5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.l4_q5.ui.theme.L4_Q5Theme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            L4_Q5Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContentView()
                }
            }
        }
    }
}

@Composable
fun ContentView(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val snackBarState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var orderLocked by remember { mutableStateOf(false) }
    var totalCost by remember { mutableStateOf(0.0) }
    val foodItems = listOf(
        FoodItem("Pizza", 10.0),
        FoodItem("Burger", 5.0),
        FoodItem("Pasta", 8.0),
        FoodItem("Salad", 6.0)
    )
    val selectedItems = remember { mutableStateMapOf<String, Boolean>() }
    var messageText by remember {
        mutableStateOf("")
    }

    Surface(
        color = Color.DarkGray,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Food Menu", fontSize = 20.sp, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                foodItems.forEach { foodItem ->
                    FoodItemRow(
                        foodItem = foodItem,
                        onCheckedChange = { itemName, isChecked ->
                            if (!orderLocked) {
                                selectedItems[itemName] = isChecked
                            }
                        },
                        isCheck = selectedItems[foodItem.name] == true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (!orderLocked) {
                    orderLocked = true
                    val orderedItems = selectedItems.filterValues { it }.keys.joinToString(", ")
                    totalCost = selectedItems.filterValues { it }.map { foodItems.find { item -> item.name == it.key }?.cost ?: 0.0 }.sum()
                    messageText = buildString {
                        append("Ordered Items: $orderedItems\n")
                        append("Total Cost: $totalCost")
                    }
                    coroutineScope.launch {
                        snackBarState.showSnackbar("")
                    }
                }
            }) {
                Text(text = "Submit Order")
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        SnackbarHost(hostState = snackBarState) {
            // Custom Snackbar Here
            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(messageText)
            }
        }
    }
}

@Composable
fun FoodItemRow(foodItem: FoodItem, onCheckedChange: (String, Boolean) -> Unit, isCheck : Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isCheck,
            onCheckedChange = { isChecked -> onCheckedChange(foodItem.name, isChecked) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "${foodItem.name} - $${foodItem.cost}")
    }
}

data class FoodItem(val name: String, val cost: Double)







@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    L4_Q5Theme {
        ContentView()
    }
}