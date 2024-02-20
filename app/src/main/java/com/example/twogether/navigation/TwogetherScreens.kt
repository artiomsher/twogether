package com.example.twogether.navigation

enum class TwogetherScreens {
    SplashScreen,
    HomeScreen,
    LoginScreen,
    PairRequestScreen;

    companion object {
        fun fromRoute(route: String?): TwogetherScreens = when(route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            HomeScreen.name -> HomeScreen
            LoginScreen.name -> LoginScreen
            PairRequestScreen.name -> PairRequestScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}