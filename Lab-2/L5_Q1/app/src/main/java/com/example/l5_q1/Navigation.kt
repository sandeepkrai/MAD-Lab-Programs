package com.example.l5_q1

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable


@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SampleSpinner_Preview.route){
        composable(
            route = Screen.SampleSpinner_Preview.route
        ){
            SampleSpinner_Preview(navController = navController)
        }
        composable(
            route = Screen.DetailScreen.route + "/{name}",
//            arguments = listOf(
//                navArgument("name"){
//                    type = NavType.StringType
//                    defaultValue = "Sandeep"
//                    nullable = true
//                }
//            )
        ){
                entry -> DetailScreen(name = entry.arguments?.getString("name"), navController)
        }
    }
}



@Composable
fun DetailScreen(name:String?, navController : NavController){
    var context = LocalContext.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        Column {
            Text("Hello, $name")
            Button(onClick = {
                navController.popBackStack()
            },
            ) {
                Text(text = "Back")
            }
            Button(onClick = {
                Toast.makeText(context,name,Toast.LENGTH_SHORT).show()
            },
            ) {
                Text(text = "Submit")
            }
        }
    }
}