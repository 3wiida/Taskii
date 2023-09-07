package com.mahmoudibrahem.taskii.model

data class CheckListItem(
    val id: Int,
    val content: String,
    val isComplete: Boolean = false
)
