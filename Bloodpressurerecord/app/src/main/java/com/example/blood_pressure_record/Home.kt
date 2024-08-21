package com.example.blood_pressure_record

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun Home(viewModel: RecordViewModel){

    val context = LocalContext.current

    ConstraintLayout {
        val (row, systolic, diastolic, heartbeat, Icon, button) = createRefs()
        var Date by rememberSaveable { mutableStateOf(LocalDate.now().toString()) }
        var Time by rememberSaveable { mutableStateOf(LocalTime.now().format(
            DateTimeFormatter.ofPattern("HH:mm")).toString()) }
        var Systolic by rememberSaveable { mutableStateOf("") }
        var Diatolic by rememberSaveable { mutableStateOf("") }
        var Heartbeat by rememberSaveable { mutableStateOf("") }

        Box(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier.fillMaxSize()
                .align(Alignment.Center), verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "登記", fontSize = 40.sp)
            }
        }

        Row(modifier = Modifier.fillMaxWidth().padding(6.dp).constrainAs(row) {
            top.linkTo(parent.top, margin = 60.dp)
            start.linkTo(parent.start, margin = 1.dp)
        }) {
            OutlinedTextField(value = Date, onValueChange = {Date = it}, modifier = Modifier.weight(1f).padding(3.dp))
            OutlinedTextField(value = Time, onValueChange = {Time = it}, modifier = Modifier.weight(1f).padding(3.dp))
        }
        OutlinedTextField(value = Systolic, onValueChange = {Systolic = it}, label = { Text(text = "收縮壓") },
            modifier = Modifier.fillMaxWidth().padding(10.dp).constrainAs(systolic){
                top.linkTo(row.top, margin = 65.dp)
                start.linkTo(parent.start, margin = 1.dp)
            })
        OutlinedTextField(value = Diatolic, onValueChange = {Diatolic = it}, label = { Text(text = "舒張壓") },
            modifier = Modifier.fillMaxWidth().padding(10.dp).constrainAs(diastolic){
                top.linkTo(systolic.top, margin = 60.dp)
                start.linkTo(parent.start, margin = 1.dp)
            })
        OutlinedTextField(value = Heartbeat, onValueChange = {Heartbeat = it}, label = { Text(text = "脈搏") },
            modifier = Modifier.fillMaxWidth().padding(10.dp).constrainAs(heartbeat){
                top.linkTo(diastolic.top, margin = 60.dp)
                start.linkTo(parent.start, margin = 1.dp)
            })
        Image(painter = painterResource(id = R.drawable.blood_pressure), contentDescription = "blood_pressure"
            , modifier = Modifier.size(150.dp, 150.dp).constrainAs(Icon){
                top.linkTo(heartbeat.top, margin = 150.dp)
                start.linkTo(parent.start, margin = 100.dp)
            }
        )

        Button(
            onClick = {
                if(Systolic == "" || Diatolic == "" || Heartbeat == ""){
                    Toast.makeText(context, "資料請輸入完整", Toast.LENGTH_LONG).show()
                }
                else {
                    viewModel.insertRecord(
                        Date,
                        Time,
                        Systolic.toInt(),
                        Diatolic.toInt(),
                        Heartbeat.toInt()
                    )
                    Date = LocalDate.now().toString()
                    Time = LocalTime.now().format(
                        DateTimeFormatter.ofPattern("HH:mm:ss")).toString()
                    Systolic = ""
                    Diatolic = ""
                    Heartbeat = ""
                }
            },
            modifier = Modifier.constrainAs(button) {
                top.linkTo(heartbeat.top, margin = 375.dp)
                start.linkTo(parent.start, margin = 275.dp)
            }
        ) {
            Text("儲存")
        }
    }
}