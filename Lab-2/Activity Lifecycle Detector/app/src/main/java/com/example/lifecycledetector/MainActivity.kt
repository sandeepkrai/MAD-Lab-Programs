package com.example.lifecycledetector

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
import com.example.lifecycledetector.ui.theme.LifecycleDetectorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate Called")
        setContent {
            LifecycleDetectorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        println("onStart Called")
    }

    override fun onStop() {
        super.onStop()
        println("onStop Called")
    }

    override fun onRestart() {
        super.onRestart()
        println("onRestart Called")
    }

    override fun onResume() {
        super.onResume()
        println("onResume Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy Called")
    }

    override fun onPause() {
        super.onPause()
        println("onPaused Called")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LifecycleDetectorTheme {
        Greeting("Android")
    }
}