package com.example.twogether.navigation

enum class TwogetherScreens {
    SplashScreen,
    HomeScreen;

    companion object {
        fun fromRoute(route: String?): TwogetherScreens = when(route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            HomeScreen.name -> HomeScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}