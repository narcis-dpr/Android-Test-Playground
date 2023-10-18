package com.narcis.unittest

class StringReverser {
    fun reverse(string: String): String {
        val stringBuilder = StringBuilder()
        for ( i in string.length-1 downTo 0) {
            stringBuilder.append(string[i])
        }
        return stringBuilder.toString()
    }
}