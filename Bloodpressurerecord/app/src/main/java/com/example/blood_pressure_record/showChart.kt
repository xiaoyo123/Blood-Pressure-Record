package com.example.blood_pressure_record

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollState
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.DefaultAlpha
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry

@Composable
fun showChart(viewModel: RecordViewModel) {
    val context = LocalContext.current
    val recordList = viewModel.recordList.observeAsState()

    val systolicList = arrayListOf<FloatEntry>()
    val diastolicList = arrayListOf<FloatEntry>()
    val heartbeatList = arrayListOf<FloatEntry>()

    val datasetLineSpec = arrayListOf<LineChart.LineSpec>()
    val modelProducer1 = ChartEntryModelProducer()
    val modelProducer2 = ChartEntryModelProducer()
    val modelProducer3 = ChartEntryModelProducer()

    datasetLineSpec.clear()

    datasetLineSpec.add(
        LineChart.LineSpec(
            lineColor = Color.Magenta.value.toInt(),
            lineBackgroundShader = DynamicShaders.fromBrush(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Magenta.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                        Color.Magenta.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_END)
                    )
                )
            )
        )
    )

    val Sz: Int? = recordList.value?.size
    if(Sz != null) {
        for (i in 0..Sz - 1) {
            val now = recordList.value?.get(i)
            if (now != null) {
                systolicList.add(FloatEntry(now.id.toFloat(), now.systolic.toFloat()))
                diastolicList.add(FloatEntry(now.id.toFloat(), now.diastolic.toFloat()))
                heartbeatList.add(FloatEntry(now.id.toFloat(), now.heartbeat.toFloat()))
            }
        }
    }

    modelProducer1.setEntries(systolicList)
    modelProducer2.setEntries(diastolicList)
    modelProducer3.setEntries(heartbeatList)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center), verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "圖表", fontSize = 40.sp)

            Column(modifier = Modifier.fillMaxSize()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    if (systolicList.isNotEmpty()) {
                        Column {
                            Text(text = "收縮壓")
                            ProvideChartStyle {
                                Chart(
                                    chart = LineChart(
                                        lines = datasetLineSpec
                                    ),
                                    chartModelProducer = modelProducer1,

                                    startAxis = rememberStartAxis(
                                        title = "Systolic blood pressure",
                                        tickLength = 0.dp,
                                        valueFormatter = { value, _ ->
                                            ((value.toInt())).toString()
                                        },
                                        itemPlacer = AxisItemPlacer.Vertical.default(
                                            maxItemCount = 5
                                        )
                                    ),
                                    bottomAxis = rememberBottomAxis(
                                        title = "date",
                                        tickLength = 0.dp,
                                        valueFormatter = { value, _ ->

                                            value.toInt().toString()

                                        },
                                        guideline = null
                                    ),
                                    chartScrollState = rememberChartScrollState(),
                                    isZoomEnabled = true
                                )
                            }
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    if (diastolicList.isNotEmpty()) {
                        Column {
                            Text(text = "舒張壓")
                            ProvideChartStyle {
                                Chart(
                                    chart = LineChart(
                                        lines = datasetLineSpec
                                    ),
                                    chartModelProducer = modelProducer2,

                                    startAxis = rememberStartAxis(
                                        title = "Systolic blood pressure",
                                        tickLength = 0.dp,
                                        valueFormatter = { value, _ ->
                                            ((value.toInt())).toString()
                                        },
                                        itemPlacer = AxisItemPlacer.Vertical.default(
                                            maxItemCount = 5
                                        )
                                    ),
                                    bottomAxis = rememberBottomAxis(
                                        title = "date",
                                        tickLength = 0.dp,
                                        valueFormatter = { value, _ ->
                                            ((value.toInt())).toString()
                                        },
                                        guideline = null
                                    ),
                                    chartScrollState = rememberChartScrollState(),
                                    isZoomEnabled = true
                                )
                            }
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    if (heartbeatList.isNotEmpty()) {
                        Column {
                            Text(text = "心跳")
                            ProvideChartStyle {
                                Chart(
                                    chart = LineChart(
                                        lines = datasetLineSpec
                                    ),
                                    chartModelProducer = modelProducer3,

                                    startAxis = rememberStartAxis(
                                        title = "Systolic blood pressure",
                                        tickLength = 0.dp,
                                        valueFormatter = { value, _ ->
                                            ((value.toInt())).toString()
                                        },
                                        itemPlacer = AxisItemPlacer.Vertical.default(
                                            maxItemCount = 5
                                        )
                                    ),
                                    bottomAxis = rememberBottomAxis(
                                        title = "date",
                                        tickLength = 0.dp,
                                        valueFormatter = { value, _ ->
                                            ((value.toInt())).toString()
                                        },
                                        guideline = null
                                    ),
                                    chartScrollState = rememberChartScrollState(),
                                    isZoomEnabled = true
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}
