package com.example.l7_q1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.l7_q1.ui.theme.L7_Q1Theme
import kotlin.math.exp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            L7_Q1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navContraller = rememberNavController()
                    NavHost(navController = navContraller, startDestination = "home"){
                        composable(NavigationItem.Home.route){
                            PhoneBook(modifier = Modifier, navContraller)
                        }
                        composable(NavigationItem.DETAIL.route+"/{name}"){backStackEntry->
                            val name = backStackEntry.arguments?.getString("name")
                            MessageScreen(navHostController = navContraller)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhoneBook(modifier: Modifier, navHostController: NavHostController){
    var contactList = arrayListOf<contact>(
        contact("Sandeep", "9369204281", "Sim","9369204281", ""),
        contact("Siddhant", "9369204281", "Memory","9369204281", ""),
        contact("Sai", "9369204281", "Sim","9369204281", ""),
        contact("Hardic", "9369204281", "Memory","9369204281", ""),

    )
    var context = LocalContext.current
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedContact by remember {
        mutableStateOf(contactList[0])
    }
    Column {
        contactList.map {

                Text(text = it.name, modifier = Modifier.combinedClickable (
                    onClick = {},
                    onLongClick = {
                        expanded = true
                        selectedContact = it
                        Toast.makeText(context,it.name, Toast.LENGTH_LONG ).show()
                    }
                ))
        }

    }
    DropDown(modifier, navController = navHostController, expanded, selectedContact.saveLocation, onClick ={
        Toast.makeText(context,selectedContact.name, Toast.LENGTH_LONG ).show()
        expanded = false
    } )
}
data class contact(val name:String, val number: String, val saveLocation: String, val whatsApp: String, val gmail: String)


@Composable
fun DropDown(modifier: Modifier, navController : NavController, expanded: Boolean, saveLocation: String, onClick: ()->Unit){
    DropdownMenu(expanded = expanded , onDismissRequest = onClick) {
        DropdownMenuItem(text = {Text("Save Location: ${saveLocation}")}, onClick = onClick)
        DropdownMenuItem(text = {Text("Call")}, onClick = onClick)
        DropdownMenuItem(text = {Text("Message")}, onClick = {
            navController.navigate(Screen.DETAIL.name+"/$saveLocation")
        })
    }
}



@Composable
fun MessageScreen(navHostController: NavHostController){
    var text by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Column {
        TextField(value =text    , onValueChange ={text = it} )
        Button(onClick = {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }) {
            Text("Send Msg")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    L7_Q1Theme {
    }
}