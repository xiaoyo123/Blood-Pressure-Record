package com.example.blood_pressure_record

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blood_pressure_record.ui.theme.BloodpressurerecordTheme
import com.example.blood_pressure_record.ui.theme.rose

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recordViewModel = ViewModelProvider(this)[RecordViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            BloodpressurerecordTheme {
                BottomBar(recordViewModel)
            }
        }
    }
}

@Composable
fun BottomBar(recordViewMode: RecordViewModel){
    val navigationController = rememberNavController()
    val selected = remember {
        mutableStateOf(Icons.Filled.Home)
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = rose
            ){
                IconButton(
                    onClick = {
                        selected.value = Icons.Filled.Home
                        navigationController.navigate(Screens.Home.screen){
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)) {
                    Column{
                        Icon(Icons.Filled.Home, contentDescription = null, modifier = Modifier.size(24.dp),
                            tint = if(selected.value == Icons.Filled.Home) Color.White else Color.DarkGray )
                        Text(text = "主頁", fontSize = 12.sp, color = if(selected.value == Icons.Filled.Home) Color.White else Color.DarkGray)
                    }
                }

                IconButton(
                    onClick = {
                        selected.value = Icons.Filled.List
                        navigationController.navigate(Screens.History.screen){
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)) {
                    Column{
                        Icon(Icons.Filled.List, contentDescription = null, modifier = Modifier.size(24.dp),
                            tint = if(selected.value == Icons.Filled.List) Color.White else Color.DarkGray )
                        Text(text = "紀錄", fontSize = 12.sp, color = if(selected.value == Icons.Filled.List) Color.White else Color.DarkGray)
                    }
                }

                IconButton(
                    onClick = {
                        selected.value = Icons.Filled.Info
                        navigationController.navigate(Screens.Chart.screen){
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)) {
                    Column{
                        Icon(Icons.Filled.Info, contentDescription = null, modifier = Modifier.size(24.dp),
                            tint = if(selected.value == Icons.Filled.Info) Color.White else Color.DarkGray )
                        Text(text = "圖表", fontSize = 12.sp, color = if(selected.value == Icons.Filled.Info) Color.White else Color.DarkGray)
                    }
                }
            }
        }
    ) {
        paddingValues ->
        NavHost(navController = navigationController, startDestination = Screens.Home.screen, modifier = Modifier.padding(paddingValues)){
            composable(Screens.Home.screen) { Home(recordViewMode) }
            composable(Screens.History.screen) { History(recordViewMode) }
            composable(Screens.Chart.screen) { showChart(recordViewMode) }

        }
    }
}