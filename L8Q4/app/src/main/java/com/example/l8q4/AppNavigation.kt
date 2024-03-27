package com.example.l8q4

enum class Screen{
    HOME,
    DETAIL
}

sealed class NavigationItem(val route : String){
    object Home: NavigationItem(Screen.HOME.name)
    object DETAIL: NavigationItem(Screen.DETAIL.name)
}