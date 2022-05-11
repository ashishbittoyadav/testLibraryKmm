package com.example.xcframework

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform} ${Platform().someRandomData}!"
    }
}