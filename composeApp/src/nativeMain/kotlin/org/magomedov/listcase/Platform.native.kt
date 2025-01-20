package org.magomedov.listcase

class IOSPlatform: Platform {
    override val system: System = System.IOS
}

actual fun platform(): Platform = IOSPlatform()