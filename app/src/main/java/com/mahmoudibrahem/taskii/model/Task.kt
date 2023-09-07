package com.mahmoudibrahem.taskii.model

data class Task(
    val id:Int,
    val name:String,
    val description:String,
    val deadline:String,
    val progress:Float
)
