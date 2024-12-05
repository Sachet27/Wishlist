package com.example.wishlist.data

import android.content.Context
import android.util.Log
import androidx.room.Room

object Graph {
    lateinit var database: WishDatabase
     val repository by lazy{
        WishRepository(wishDao = database.wishDao())
    }

    fun provide(
        context: Context
    ){
        database= Room.databaseBuilder(
            context = context,
            klass = WishDatabase::class.java,
            name = "wishlist_db"
        ).build()
    }
}