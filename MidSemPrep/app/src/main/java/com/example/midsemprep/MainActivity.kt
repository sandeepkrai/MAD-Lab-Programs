package com.example.midsemprep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.midsemprep.ui.theme.MidSemPrepTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MidSemPrepTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(Modifier)
                }
            }
        }
    }
}

@Composable
fun Screen(ind:Int, onClick: ()->Unit){
    when(ind){
        0 -> FirstScreen(onClick= onClick)
        1-> SecondScreen(onClick= onClick)
    }
}

@Composable
fun MainScreen(modifier: Modifier){
    var screenNumber by remember {
        mutableStateOf(0)
    }
    Column {
        Screen(ind = screenNumber, onClick = {screenNumber = (screenNumber+1)%2})

    }
}

@Composable
fun SecondScreen(onClick: ()->Unit, modifier: Modifier = Modifier){
    Column {
        Text(
            text = "Second Screen"
        )
        Button(onClick = onClick) {
            Text(text = "Change Screen")
        }
    }
}

@Composable
fun FirstScreen(onClick: ()->Unit, modifier: Modifier = Modifier){
    val books = listOf<String>("Hi","Hello")
    Column (){
        LazyColumn(modifier = Modifier.height(200.dp)){
            items(50){index ->
                Text(text = books[index%2])
            }

        }
        Button(onClick = onClick) {
            Text(text = "Change Screen")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MidSemPrepTheme {
    }
}