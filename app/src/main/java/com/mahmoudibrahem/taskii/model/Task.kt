package com.mahmoudibrahem.taskii.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val deadline: String,
    val progress: Float,
    val isCompleted: Boolean = false,
)
