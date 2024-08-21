package com.example.blood_pressure_record

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.blood_pressure_record.ui.theme.pink


@Composable
fun History(viewModel: RecordViewModel){

    val recordList = viewModel.recordList.observeAsState()

    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier
            .fillMaxSize()
            .align(Alignment.Center), verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "紀錄", fontSize = 40.sp)

            recordList.let {
                LazyColumn(
                    content = {
                        it.value?.let { it1 ->
                            itemsIndexed(it1.toList()) { index: Int, record: Record ->
                                RecordItem(record, viewModel)

                            }
                        }
                    }
                )
            }
        }
    }

}

@Composable
fun RecordItem(record: Record, viewModel: RecordViewModel){

    var modify = remember { mutableStateOf(false) }
    var alert = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(2.dp, pink)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = record.date + " " + record.time,
                fontSize = 15.sp,
                color = Color.Black
            )
            Text(
                text = "收縮壓: " + record.systolic,
                fontSize = 20.sp,
                color = if(record.systolic >= 130 && record.systolic < 140) { Color(0xffff8000) }
                        else if(record.systolic >= 140) { Color.Red }
                        else Color.Black
            )
            Text(
                text = "舒張壓: " + record.diastolic,
                fontSize = 20.sp,
                color = if(record.diastolic >= 85 && record.diastolic < 90) { Color(0xffff8000) }
                        else if(record.diastolic >= 90) { Color.Red }
                        else Color.Black
            )
            Text(
                text = "心跳: " + record.heartbeat,
                fontSize = 20.sp,
                color = Color.Black
            )
        }
        IconButton(onClick = {
            modify.value = true
        }) {
            Icon(
                Icons.Filled.Edit, contentDescription = null, modifier = Modifier.size(26.dp),
                tint = Color.Black)
        }
        IconButton(onClick = {
            alert.value = true
        }) {
            Icon(
                Icons.Filled.Delete, contentDescription = null, modifier = Modifier.size(26.dp),
                tint = Color.Black)
        }
        if(modify.value == true){
            var Date by rememberSaveable { mutableStateOf(record.date) }
            var Time by rememberSaveable { mutableStateOf(record.time) }
            var Systolic by rememberSaveable { mutableStateOf("") }
            var Diatolic by rememberSaveable { mutableStateOf("") }
            var Heartbeat by rememberSaveable { mutableStateOf("") }
            AlertDialog(
                onDismissRequest = { modify.value = false },
                text = {
                    ConstraintLayout {
                        val (row, systolic, diastolic, heartbeat) = createRefs()


                        Box(modifier = Modifier.fillMaxSize()) {
                            Column(
                                modifier = Modifier.fillMaxSize()
                                    .align(Alignment.Center), verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "修改", fontSize = 40.sp)
                            }
                        }

                        Row(modifier = Modifier.fillMaxWidth().padding(6.dp).constrainAs(row) {
                            top.linkTo(parent.top, margin = 60.dp)
                            start.linkTo(parent.start, margin = 1.dp)
                        }) {
                            OutlinedTextField(
                                value = Date,
                                onValueChange = { Date = it },
                                modifier = Modifier.weight(1f).padding(3.dp)
                            )
                            OutlinedTextField(
                                value = Time,
                                onValueChange = { Time = it },
                                modifier = Modifier.weight(1f).padding(3.dp)
                            )
                        }
                        OutlinedTextField(value = Systolic,
                            onValueChange = { Systolic = it },
                            label = { Text(text = "收縮壓") },
                            modifier = Modifier.fillMaxWidth().padding(10.dp)
                                .constrainAs(systolic) {
                                    top.linkTo(row.top, margin = 65.dp)
                                    start.linkTo(parent.start, margin = 1.dp)
                                })
                        OutlinedTextField(value = Diatolic,
                            onValueChange = { Diatolic = it },
                            label = { Text(text = "舒張壓") },
                            modifier = Modifier.fillMaxWidth().padding(10.dp)
                                .constrainAs(diastolic) {
                                    top.linkTo(systolic.top, margin = 60.dp)
                                    start.linkTo(parent.start, margin = 1.dp)
                                })
                        OutlinedTextField(value = Heartbeat,
                            onValueChange = { Heartbeat = it },
                            label = { Text(text = "脈搏") },
                            modifier = Modifier.fillMaxWidth().padding(10.dp)
                                .constrainAs(heartbeat) {
                                    top.linkTo(diastolic.top, margin = 60.dp)
                                    start.linkTo(parent.start, margin = 1.dp)
                                })
                    }
                },

                confirmButton = {
                    TextButton(onClick = {
                        viewModel.updateRecord(record.id, Date, Time, Systolic.toInt(), Diatolic.toInt(), Heartbeat.toInt())
                        modify.value = false }) { Text("確認") }
                },
                dismissButton = {
                    TextButton(onClick = {
                        modify.value = false }) { Text("取消") }
                }
            )
        }

        if(alert.value == true){
            AlertDialog(
                onDismissRequest = {
                    alert.value = false
                },
                title = { Text(text = "警告") },
                text = { Text(text = "確定要刪除嗎") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.deleteRecord(record.id)
                        alert.value = false }) { Text("確認") }
                },
                dismissButton = {
                    TextButton(onClick = {
                        alert.value = false }) { Text("取消") }
                }
            )
        }
    }
}
