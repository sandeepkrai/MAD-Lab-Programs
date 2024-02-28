package com.example.l4_q1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.l4_q1.ui.theme.L4_Q1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            L4_Q1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VersionList(Modifier)
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
fun VersionList(modifier: Modifier){
    var imageList = listOf(
        painterResource(id = R.drawable.a),
        painterResource(id = R.drawable.b),painterResource(id = R.drawable.c),
    )
    var versionList = listOf(
        "R",
    "S",
    "Lollipop"
    )
    LazyColumn(){
        items (versionList.size){
            Row {
                Image(painter = imageList[it], contentDescription = "")
                Text(text = versionList[it])
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    L4_Q1Theme {
        Greeting("Android")
    }
}