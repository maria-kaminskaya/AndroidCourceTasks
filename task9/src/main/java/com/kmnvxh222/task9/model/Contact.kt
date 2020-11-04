package com.kmnvxh222.task9.model

import java.io.Serializable

data class Contact(
        var id: String,
        var name: String,
        var typeInfo: String,
        var info: String
) : Serializable