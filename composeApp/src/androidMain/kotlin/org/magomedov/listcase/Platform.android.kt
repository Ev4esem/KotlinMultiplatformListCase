package org.magomedov.listcase

class ANDROIDPlatform : Platform {
    override val system: System = System.ANDROID
}

actual fun platform(): Platform = ANDROIDPlatform()