package com.example.simple_navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.simple_navigation.ui.theme.Simple_navigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Simple_navigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavigationApp()
                }
            }
        }
    }
}

@Composable
fun NavigationApp(){
    val navController= rememberNavController()
    NavHost(
        navController=navController,
        startDestination="Home"
    ){
        composable(route="Home"){
            HomePage(navController)
        }
        composable(route="second/{parameter}",
        arguments = listOf(
            navArgument("parameter"){
                type= NavType.StringType
            }
        )
        ){
            SecondScreen(navController, it.arguments?.getString("parameter"))
        }
    }
}


@Composable
fun HomePage(navController: NavController) {
    var text by remember{ mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "This is home screen")
        OutlinedTextField(value = text, onValueChange = {text=it})
        Button(onClick = {navController.navigate("second/$text")},
        enabled = text.isNotEmpty())
        {
            Text(text="To second screen")
        }
    }
    
}
@Composable
fun SecondScreen(navController: NavController, parameter:String?){
    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Text(text = "This is the second screen")
        Text(text = "The parameter from home page is $parameter")
        Button(onClick = {navController.navigateUp()}) {
            Text(text = "Back to home")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Simple_navigationTheme {
        NavigationApp()
    }
}