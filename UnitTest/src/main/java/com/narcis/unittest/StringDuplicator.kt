package com.narcis.unittest

class StringDuplicator {
    /**
     * @return concatenation of the argument with itself e.g. "hi" -> "hihi"
     */
    fun duplicate(string: String): String {
        // the bug is triplication instead of duplication
        return string + string
    }
}