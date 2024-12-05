package com.example.wishlist

import android.app.Application
import com.example.wishlist.data.Graph

class WishListApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}