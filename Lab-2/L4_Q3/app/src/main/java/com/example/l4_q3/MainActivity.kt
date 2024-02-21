package com.example.l4_q3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.l4_q3.ui.theme.L4_Q3Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            L4_Q3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContentView(Modifier)
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
    var selectedItemText by remember { mutableStateOf("") }
    var selectedItemImage by remember { mutableStateOf(R.drawable.a) }

    val items = listOf(
        Item("Item 1", R.drawable.a),
        Item("Item 2", R.drawable.b),
        Item("Item 3", R.drawable.c),
        Item("Item 4", R.drawable.a),
        Item("Item 5", R.drawable.b)
    )

    Surface(
        color = Color.DarkGray,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Select an item:", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                items.forEach { item ->
                    ItemRow(item = item) { clickedItem ->
                        selectedItemText = clickedItem.name
                        selectedItemImage = clickedItem.imageId
                        coroutineScope.launch {
                            snackBarState.showSnackbar("Selected: $selectedItemText")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
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
                    Image(
                        painter = painterResource(id = selectedItemImage),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(selectedItemText)
                }
            }
        }
    }
}

@Composable
fun ItemRow(item: Item, onItemClick: (Item) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = item.name, fontSize = 14.sp)
    }
}

data class Item(val name: String, val imageId: Int)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    L4_Q3Theme {
        ContentView(Modifier)
    }
}