package com.kmnvxh222.task8.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.sql.Date


@Entity(tableName = "citys")
data class City(
        var content: String,
        var date: Long
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}