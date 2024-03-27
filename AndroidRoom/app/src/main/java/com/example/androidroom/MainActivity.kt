package com.example.androidroom

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
import com.example.androidroom.ui.theme.AndroidRoomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidRoomTheme {
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
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Button(
            onClick = {
                if (title.isNotEmpty() && content.isNotEmpty()) {
                    dbHelper.insertData(title, content)
                    title = ""
                    content = ""
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
                title = "."
                content = ""
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
fun NoteList(cursor: Cursor, onDelete: (Int) -> Unit) {
    Column {
        while (cursor.moveToNext()) {
            val id = cursor.getIntValue(COL_ID)
            val title = cursor.getStringValue(COL_TITLE)
            val content = cursor.getStringValue(COL_CONTENT)
            NoteItem(title = title, content = content, onDelete = { onDelete(id) })
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun NoteItem(title: String, content: String, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
//        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title)
            Text(text = content)
            Spacer(modifier = Modifier.height(4.dp))
            Button(onClick = onDelete) {
                Text("Delete")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidRoomTheme {
//        Greeting("Android")
    }
}