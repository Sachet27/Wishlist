package com.example.wishlist.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Wish::class],
    version = 1
)
abstract class WishDatabase: RoomDatabase() {
    abstract fun wishDao(): WishDao
}