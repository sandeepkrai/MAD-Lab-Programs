package com.example.l8q2


enum class Screen{
    HOME,
    DETAIL
}

sealed class NavigationItem(val route : String){
    object Home: NavigationItem(Screen.HOME.name)
    object DETAIL: NavigationItem(Screen.DETAIL.name)
}