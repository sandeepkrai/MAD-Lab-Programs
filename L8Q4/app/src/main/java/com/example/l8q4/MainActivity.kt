package com.example.l8q4

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.l8q4.ui.theme.L8Q4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            L8Q4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navContraller = rememberNavController()
                    NavHost(navController = navContraller, startDestination = "home"){
                        composable(NavigationItem.Home.route){
                            MyApp(navContraller)
                        }
                        composable(NavigationItem.DETAIL.route){backStackEntry->
//                            val name = backStackEntry.arguments?.getString("name")
                            DetailScreen(navContraller)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyApp(navController: NavController) {
    val context = LocalContext.current
    val dbHelper = remember { DBHelper(context) }
    var name by remember { mutableStateOf("") }
    var phoneNo by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        TextField(
            value = phoneNo,
            onValueChange = { phoneNo = it },
            label = { Text("studentId") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Sem") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Button(
            onClick = {
                if (name.isNotEmpty() && phoneNo.isNotEmpty()) {
                    dbHelper.insertData(name, phoneNo, email)
                    name = ""
                    phoneNo = ""
                    email = ""
                }
            },
//            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add Note")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Notes",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Button(onClick = {
            navController.navigate(Screen.DETAIL.name)
        }) {
            Text("See Students")
        }
        MyComposableFunction(context)

    }
}

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveData(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getData(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }
}
@Composable
fun MyComposableFunction(context: Context) {
    val preferencesManager = remember { PreferencesManager(context) }
    val data = remember { mutableStateOf(preferencesManager.getData("myKey", "Hello")) }

    // Use the data variable in your Composable
    Text(data.toString())

    // Update data and save to SharedPreferences
    preferencesManager.saveData("myKey", "newDataValue")
    data.value = "newDataValue"
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    L8Q4Theme {
//        Greeting("Android")
    }
}