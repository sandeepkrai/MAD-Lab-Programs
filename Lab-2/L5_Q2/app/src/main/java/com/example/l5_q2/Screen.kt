package com.example.l5_q2

sealed  class Screen(val route : String) {
    object  DetailScreen : Screen("detail_screen")

    object  SampleSpinner_Preview : Screen("spinner_screen")

    fun withArgs(vararg  args:String):String{
        return buildString {
            append(route)
            args.forEach {
                    arg-> append("/$arg")
            }
        }
    }
}