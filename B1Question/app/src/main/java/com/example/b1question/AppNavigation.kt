package com.example.b1question


enum class Screen {
    FIRSTSCREEN,
    SECONDSCREEN,
}
sealed class NavigationItem(val route: String) {
    object FirstScreen : NavigationItem(Screen.FIRSTSCREEN.name)
    object SecondScreen : NavigationItem(Screen.SECONDSCREEN.name)
}
