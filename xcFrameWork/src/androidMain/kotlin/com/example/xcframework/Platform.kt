package com.example.xcframework

actual class Platform actual constructor() {
    actual val platform: String = "Android ${android.os.Build.VERSION.SDK_INT}"
    actual val someRandomData : String = "android random test..."
}