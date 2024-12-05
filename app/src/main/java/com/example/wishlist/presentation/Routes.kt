package com.example.wishlist.presentation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object HomeScreen: Routes
    @Serializable
    data class DetailScreen(val id:Long= 0L): Routes
}