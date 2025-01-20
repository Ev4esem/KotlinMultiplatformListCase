package org.magomedov.listcase

interface Platform {
    val system: System
}

enum class System {
    IOS,
    ANDROID,
}

expect fun platform(): Platform