package com.example.twogether.navigation

enum class TwogetherScreens {
    SplashScreen,
    HomeScreen,
    LoginScreen;

    companion object {
        fun fromRoute(route: String?): TwogetherScreens = when(route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            HomeScreen.name -> HomeScreen
            LoginScreen.name -> LoginScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}