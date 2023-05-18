package com.example.livez.util

class Event<out T>(private val content: T) {

    var consumed = false

    fun consume(): T? {
        return if (consumed) {
            null
        } else {
            consumed = true
            content
        }
    }

}
