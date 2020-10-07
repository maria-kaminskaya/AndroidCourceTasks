package com.kmnvxh222.task5

import java.io.Serializable

data class Contacts(
        var id: String,
        var name: String,
        var typeInfo: String,
        var info: String
) : Serializable