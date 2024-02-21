package com.example.l4_q4

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.l4_q4.ui.theme.L4_Q4Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            L4_Q4Theme {
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
    var currentProfile by remember { mutableStateOf("Silent") }
    val painterResource: Painter = painterResource(id = if (currentProfile == "Silent") R.drawable.a else R.drawable.b)

    Surface(
        color = Color.DarkGray,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Current Profile: $currentProfile", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                ProfileToggleButton(
                    text = "Silent",
                    selected = currentProfile == "Silent",
                    onSelectedChange = { selected ->
                        if (selected) {
                            currentProfile = "Silent"
                            coroutineScope.launch {
                                snackBarState.showSnackbar("")
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.width(16.dp))
                ProfileToggleButton(
                    text = "Ringing",
                    selected = currentProfile == "Ringing",
                    onSelectedChange = { selected ->
                        if (selected) {
                            currentProfile = "Ringing"
                            coroutineScope.launch {
                                snackBarState.showSnackbar("")
                            }
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = {
                if (currentProfile == "Silent") {
                    currentProfile = "Ringing"
                    coroutineScope.launch {
                        snackBarState.showSnackbar("")
                    }
                    coroutineScope.launch {
                        snackBarState.showSnackbar("")
                    }
                } else {
                    currentProfile = "Silent"
                    coroutineScope.launch {
                        snackBarState.showSnackbar("")
                    }
                }
            }) {
                Text(text = "Action")
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
                        painter = painterResource,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(currentProfile)
                }
            }
        }
    }
}

@Composable
fun ProfileToggleButton(
    text: String,
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit
) {
    Button(
        onClick = { onSelectedChange(!selected) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color.Green else Color.Gray
        )
    ) {
        Text(text = text)
    }
}






@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    L4_Q4Theme {
        ContentView()
    }
}