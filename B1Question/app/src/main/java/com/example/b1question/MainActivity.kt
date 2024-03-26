package com.example.b1question

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.b1question.ui.theme.B1QuestionTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            B1QuestionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavHost(modifier = Modifier, navController = navController)
//                    CustomeListView()
                }
            }
        }
    }
}



// PASSING COMPLEX DATA IN JETPACK

@Composable
fun CustomeListView(){
    var enabled by remember { mutableStateOf(true)}
    LazyColumn{
        items(complexBook.size){book->
            Button(onClick = {
                selectedComplexBook.add(complexBook[book])
                println(selectedBook.size)
            }) {
                Text(complexBook[book].name)
            }
        }
    }
}









// B1 MID SEM CODE
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.FirstScreen.route,
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = startDestination
    ){
        composable(NavigationItem.FirstScreen.route) {
            FirstScreen(navController = navController)
        }
        composable(NavigationItem.SecondScreen.route+"/{isDark}") {
            var isDark = it.arguments?.getString("isDark")
            if (isDark != null) {
                SecondScreen(isDark = isDark=="true", dark = isDark)
            }
        }
    }
}

@Composable
fun FirstScreen(navController: NavHostController){
    val context = LocalContext.current
    var checked1 by remember {
        mutableStateOf(true)
    }
    var checked2 by remember {
        mutableStateOf(false)
    }
    var checked3 by remember {
        mutableStateOf(false)
    }

    Column {
        Row {
            Text(text = "Dark Background")
            Checkbox(checked = checked1, onCheckedChange ={checked1 = !checked1} )
        }
        Row {
            Text(text = "Light Background")
            Checkbox(checked = checked2, onCheckedChange ={checked2 = !checked2} )
        }
        Row {
            Text(text = "Random Background")
            Checkbox(checked = checked3, onCheckedChange ={checked3 = !checked3} )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Demo_DropDownMenu(
            onCheckCount = {
                var cnt = 0
                if(checked1)cnt+=1
                if(checked2)cnt+=1
                if(checked3)cnt+=1
                Toast.makeText(context, cnt.toString(), Toast.LENGTH_SHORT).show()
                           },
            onUncheck = {
                if(checked1)checked1= false
                if(checked2)checked2 = false
                if(checked3)checked3= false
            }
        )
        Button(onClick = { navController.navigate(NavigationItem.SecondScreen.route+"/${checked1}") }) {
            Text("Go to New Screen")
        }
        CustomeListView()
    }
}

@Composable
fun SecondScreen(isDark : Boolean, dark:String){
    var backGround = if(isDark)Color.Black else Color.White
    Column(Modifier.background(backGround)) {
        Text(text = "Some Random ${selectedComplexBook.size}")
        LazyColumn{
            items(selectedComplexBook.size){ book->
                Text(text = selectedComplexBook[book].name)
            }
        }
    }

}

@Composable
fun Demo_DropDownMenu(onCheckCount : ()->Unit, onUncheck : ()->Unit) {

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

            Button(onClick = { expanded = !expanded }) {
                Text("Menu")
            }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Count Checked") },
                onClick = onCheckCount
            )
            DropdownMenuItem(
                text = { Text("Save") },
                onClick = onUncheck
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    B1QuestionTheme {
    }
}