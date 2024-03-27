package com.example.l8q4

import android.database.Cursor
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DetailScreen(navController: NavController){
    val context = LocalContext.current
    val dbHelper = remember { DBHelper(context) }
        val cursor = dbHelper.getAllData()
    var name by remember { mutableStateOf("") }
    var phoneNo by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
        if (cursor != null) {
            NoteList(cursor = cursor, navController, onDelete = {
                    id -> dbHelper.deleteData(id)
                navController.navigate(Screen.HOME.name)
            }, onEdit = {id ->
                val n : Note? = dbHelper.getNoteById(id);
                dbHelper.deleteData(id)

                navController.navigate(Screen.HOME.name)
            })
        } else {
            Text(
                text = "No notes available",
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
}

@Composable
fun NoteList(cursor: Cursor, navController: NavController, onDelete: (Int) -> Unit, onEdit: (Int) -> Unit) {
    Column {
        while (cursor.moveToNext()) {
            val id = cursor.getIntValue(COL_ID)
            val name = cursor.getStringValue(COL_NAME)
            val phoneNo = cursor.getStringValue(COL_PHONENO)
            val email = cursor.getStringValue(COL_EMAIL)
            NoteItem(name = name, phoneNo = phoneNo, email=email, navController, onDelete = { onDelete(id) }, onEdit = {onEdit(id)})
            Spacer(modifier = Modifier.height(8.dp))
        }

    }
}

@Composable
fun NoteItem(name: String, phoneNo: String, email: String, navController: NavController, onDelete: () -> Unit, onEdit: () -> Unit) {
    var context = LocalContext.current
    var expanded by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
//        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = name)
            Text(text = phoneNo)
            Text(text = email)
            Spacer(modifier = Modifier.height(4.dp))
            Button(onClick = { expanded = true }) {
                Text("Menu")
            }
            DropDown(Modifier, navController = navController, expanded,  onClick ={
//                Toast.makeText(context,selectedContact.name, Toast.LENGTH_LONG ).show()
                onDelete()
                expanded = false
            } )
//            Button(onClick = onDelete) {
//                Text("Delete")
//            }
//            Button(onClick = onEdit) {
//                Text("Edit")
//            }
        }
    }
}
@Composable
fun DropDown(modifier: Modifier, navController : NavController, expanded: Boolean,  onClick: ()->Unit){
    DropdownMenu(expanded = expanded , onDismissRequest = onClick) {
        DropdownMenuItem(text = {Text("View")}, onClick = onClick)
        DropdownMenuItem(text = {Text("Delete")}, onClick = onClick)
    }
}