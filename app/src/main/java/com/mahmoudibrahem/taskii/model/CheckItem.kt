package com.mahmoudibrahem.taskii.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CheckItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val taskId: Int,
    val content: String,
    var isComplete: Boolean = false
)
