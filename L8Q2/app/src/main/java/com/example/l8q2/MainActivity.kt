package com.example.l8q2

import android.database.Cursor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.l8q2.ui.theme.L8Q2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            L8Q2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navContraller = rememberNavController()
                    NavHost(navController = navContraller, startDestination = "home"){
                        composable(NavigationItem.Home.route){
                            HomeScreen(modifier = Modifier, navContraller)
                        }
                        composable(NavigationItem.DETAIL.route){backStackEntry->
//                            val name = backStackEntry.arguments?.getString("name")
                            HomeScreen(modifier = Modifier, navContraller)
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier, navController: NavController){
    val myData = listOf(MyData(0, "Apples",5), MyData(1, "Bananas",30), MyData(2, "Kiwis",40))
    val context = LocalContext.current
    val dbHelper = remember { DBHelper(context) }
    var name by remember { mutableStateOf("") }
    var phoneNo by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    Column(

    ) {
        SpinnerSample(
            myData,
            preselected = myData.first(),
            onSelectionChanged = {it->
                dbHelper.insertData(it.name, it.price.toString(), "")
                navController.navigate(Screen.DETAIL.name)
            },
            modifier = Modifier.fillMaxWidth()
        )

        val cursor = dbHelper.getAllData()
        if (cursor != null) {
            NoteList(cursor = cursor, onDelete = {
                    id -> dbHelper.deleteData(id)
                name = "."
                phoneNo = ""
                navController.navigate(Screen.DETAIL.name)
            }, onEdit = {id ->
                val n : Note? = dbHelper.getNoteById(id);
                dbHelper.deleteData(id)

                name = n?.name.toString()
                phoneNo = n?.phoneNo.toString()
                email = n?.email.toString()
                navController.navigate(Screen.DETAIL.name)
            })
        } else {
            Text(
                text = "No notes available",
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
fun NoteList(cursor: Cursor, onDelete: (Int) -> Unit, onEdit: (Int) -> Unit) {
    Column {
        while (cursor.moveToNext()) {
            val id = cursor.getIntValue(COL_ID)
            val name = cursor.getStringValue(COL_NAME)
            val phoneNo = cursor.getStringValue(COL_PHONENO)
            val email = cursor.getStringValue(COL_EMAIL)
            NoteItem(name = name, phoneNo = phoneNo, email=email, onDelete = { onDelete(id) }, onEdit = {onEdit(id)})
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun NoteItem(name: String, phoneNo: String, email: String, onDelete: () -> Unit, onEdit: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
//        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = name)
            Text(text = phoneNo)
            Text(text = email)
            Spacer(modifier = Modifier.height(4.dp))
            Button(onClick = onDelete) {
                Text("Delete")
            }
            Button(onClick = onEdit) {
                Text("Edit")
            }
        }
    }
}


data class MyData (
    val id: Int,
    val name: String,
    val price: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerSample(
    list: List<MyData>,
    preselected: MyData,
    onSelectionChanged: (myData: MyData) -> Unit,
    modifier: Modifier = Modifier
) {

    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) } // initial value

    OutlinedCard(
        modifier = modifier.clickable {
            expanded = !expanded
        }
    ) {


        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {

            Text(
                text = selected.name,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Icon(Icons.Outlined.ArrowDropDown, null, modifier = Modifier.padding(8.dp))

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()   // delete this modifier and use .wrapContentWidth() if you would like to wrap the dropdown menu around the content
            ) {
                list.forEach { listEntry ->

                    DropdownMenuItem(
                        onClick = {
                            selected = listEntry
                            expanded = false
                            onSelectionChanged(selected)
                        },
                        text = {
                            Text(
                                text = listEntry.name + "                "+ listEntry.price.toString(),
                                modifier = Modifier
                                    //.wrapContentWidth()  //optional instad of fillMaxWidth
                                    .fillMaxWidth()
                                    .align(Alignment.Start)
                            )
                        },
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    L8Q2Theme {
//        Greeting("Android")
    }
}