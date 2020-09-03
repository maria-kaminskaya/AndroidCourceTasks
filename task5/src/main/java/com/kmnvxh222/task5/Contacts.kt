package com.kmnvxh222.task5

import java.io.Serializable

class Contacts(
        var id: String,
        var name: String,
        var typeInfo: String,
        var info: String
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Contacts) {
            return false
        }
        return name == other.name &&
                typeInfo == other.typeInfo &&
                info == other.info
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + typeInfo.hashCode()
        result = 31 * result + info.hashCode()
        return result
    }
}