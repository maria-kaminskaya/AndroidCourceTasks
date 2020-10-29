package com.kmnvxh222.task8.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "citys")
data class City(
        var content: String
): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}