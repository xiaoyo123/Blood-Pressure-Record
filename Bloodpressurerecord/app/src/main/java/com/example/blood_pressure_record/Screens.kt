package com.example.blood_pressure_record

sealed class Screens (val screen: String){
    data object Home: Screens("Home")
    data object History: Screens("History")
    data object Chart: Screens("Chart")
}