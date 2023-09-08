package com.mahmoudibrahem.taskii.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.mahmoudibrahem.taskii.model.CheckItem
import com.mahmoudibrahem.taskii.model.Task

data class TaskWithCheckItems(
    @Embedded val task:Task,
    @Relation(
        parentColumn = "id",
        entityColumn = "taskId"
    )
    val checkItems:List<CheckItem>
)
