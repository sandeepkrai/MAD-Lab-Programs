package com.example.l4_q2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.l4_q2.ui.theme.L4_Q2Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            L4_Q2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
//                        ContentView()
                        ContentViewExtended()
                    }
                }
            }
        }
    }
}


@Composable
fun ContentView(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val snackBarState = remember {
        SnackbarHostState()
    }
    var coroutineScope = rememberCoroutineScope()
    var painterResource  = painterResource(id = R.drawable.a)

    Surface(
        color = Color.DarkGray,
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = modifier.fillMaxSize()
        ){
            Button(onClick = {
                coroutineScope.launch {
                    snackBarState.showSnackbar("")
                }
            }) {
                Text(text = "Show SnackBar", fontSize = 16.sp)
            }
        }



        Row (
            verticalAlignment = Alignment.Bottom
            , horizontalArrangement = Arrangement.Center
        ){
            SnackbarHost(
                hostState = snackBarState
            ) {
                // Custom Snackbar Here
                Row(){
                    Image(painter = painterResource, contentDescription = null )
                    Text("Hi")
                }
            }
        }
    }
}

@Composable
fun ContentViewExtended(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val snackBarState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var imageToShow by remember { mutableStateOf("a") }

    val painterResource: Painter = painterResource(id = if (imageToShow == "a") R.drawable.a else R.drawable.b)

    Surface(
        color = Color.DarkGray,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                coroutineScope.launch {
                    snackBarState.showSnackbar("")
                }
            }) {
                Text(text = "Show SnackBar", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Switch(
                checked = imageToShow == "a",
                onCheckedChange = { checked ->
                    imageToShow = if (checked) "a" else "b"
                }
            )
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
                    Text("Hi")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    L4_Q2Theme {
        ContentView()
    }
}