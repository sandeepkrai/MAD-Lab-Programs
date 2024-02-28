package com.example.l6_q1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.l6_q1.ui.theme.L6_Q1Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            L6_Q1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home"){
                        composable(NavigationItem.Home.route){
                            Greeting(navHostController = navController)
                        }
                        composable(NavigationItem.DETAIL.route+"/{name}"){backStackEntry->
                            val name = backStackEntry.arguments?.getString("name")
                            DetailScreen(navHostController = navController, name = name?:"")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(navHostController: NavHostController, modifier: Modifier = Modifier) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var menuItems = listOf<Pair<Painter,String>>(Pair(painterResource(id = R.drawable.smithapai), "Courses"), Pair(painterResource(id = R.drawable.smithapai), "Admission"),
        Pair(painterResource(id = R.drawable.smithapai),"Faculty"))

    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet { 
            /* Drawer content */ 
                Column {
                    menuItems.map {
                            item->
                        Box(modifier = modifier
                            .padding(20.dp)
                            .fillMaxWidth()
                            .clickable {
                                navHostController.navigate(NavigationItem.DETAIL.route + "/${item.second}")
                            }, contentAlignment = Alignment.Center){
                            Row {
                                Image(painter = item.first, contentDescription = "", modifier.height(50.dp).width(50.dp))
                                Text(text = item.second)
                            }
                        }
                    }
                }
            }
        },
    ) {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Show Menu") },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
            }
        ) { contentPadding ->
            Surface(
                Modifier
                    .padding(contentPadding)
                    .fillMaxSize(),

            ) {
                Box(modifier = Modifier.padding(contentPadding), contentAlignment = Alignment.Center
                    ){
                    Text(text = "XYZ Institute of Technology")
                }
            }
        }
    }
}

@Composable
fun DetailScreen(navHostController: NavHostController, name:String){
    var coursList= listOf<String>("MAD Lab", "NS Lab")
    var admissionDetail= "Admission Details: Don't ever take admission is this college"
    var facultyDetail = listOf<Painter>(painterResource(id = R.drawable.smithapai), painterResource(
        id = R.drawable.manohara
    ))

    when(name){
        "Courses"->{
               Column {
                   coursList.map {course->
                       Text(text = course)
                   }
               }
        }
        "Faculty"->{
           Column {
               facultyDetail.map {
                       faculty-> Image(painter = faculty, contentDescription = "faculty")
               }
           }
        }
        "Admission"->{
            Text(text = admissionDetail)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    L6_Q1Theme {
    }
}