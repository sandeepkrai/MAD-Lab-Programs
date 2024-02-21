package com.example.q3

import android.inputmethodservice.Keyboard.Row
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.q3.ui.theme.Q3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Q3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ColumnContainer(modifier = Modifier)
                }
            }
        }
    }
}

@Composable
fun RowContainer(modifier: Modifier){
    Box(modifier = Modifier.height(100.dp)){
        Row (
            modifier = Modifier.height(200.dp)
        ){
            Box(modifier = Modifier
                .weight(1F)
                .background(Color.Red)
                .fillMaxSize())
            Box(modifier = Modifier
                .weight(1F)
                .background(Color.Green)
                .fillMaxSize())
            Box(modifier = Modifier
                .weight(1F)
                .background(Color.Blue)
                .fillMaxSize())
            Box(modifier = Modifier
                .weight(1F)
                .background(Color.Yellow)
                .fillMaxSize())
        }
    }
}

@Composable
fun ColumnContainer(modifier: Modifier){
    var text by remember {
        mutableStateOf("")
    }
    Column {
        RowContainer(modifier = Modifier)
        Text(text = "row one")
        Text(text = "row two")
        Text(text = "row three")
        Text(text = "row four")
        Spacer(modifier = Modifier.height(50.dp))
        TextField(value = text, onValueChange = {text = it})
        Row(
            modifier = Modifier.width(200.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Button(onClick = { /*TODO*/ }) {
                Text(text = "OK")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Cancel")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Q3Theme {
        RowContainer(modifier = Modifier)
    }
}