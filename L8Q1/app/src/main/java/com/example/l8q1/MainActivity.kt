package com.example.l8q1

import android.database.Cursor
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
import androidx.compose.material3.Card
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
import com.example.l8q1.ui.theme.L8Q1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            L8Q1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val context = LocalContext.current
    val dbHelper = remember { DBHelper(context) }
    var name by remember { mutableStateOf("") }
    var phoneNo by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
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
            label = { Text("phoneNo") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
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

        val cursor = dbHelper.getAllData()
        if (cursor != null) {
            NoteList(cursor = cursor, onDelete = {
                    id -> dbHelper.deleteData(id)
                name = "."
                phoneNo = ""
            }, onEdit = {id ->
            val n : Note? = dbHelper.getNoteById(id);
                    dbHelper.deleteData(id)

                name = n?.name.toString()
                phoneNo = n?.phoneNo.toString()
                email = n?.email.toString()
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


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    L8Q1Theme {
//        Greeting("Android")
    }
}