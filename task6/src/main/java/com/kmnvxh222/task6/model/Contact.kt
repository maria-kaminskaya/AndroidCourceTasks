package com.kmnvxh222.task6.model

import java.io.Serializable

data class Contact(
        var id: String,
        var name: String,
        var typeInfo: String,
        var info: String
) : Serializable