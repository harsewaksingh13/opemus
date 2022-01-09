package com.oigma.opemus

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}