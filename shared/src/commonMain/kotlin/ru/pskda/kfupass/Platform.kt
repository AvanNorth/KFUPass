package ru.pskda.kfupass

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform