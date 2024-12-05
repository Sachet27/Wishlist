package com.example.wishlist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface WishDao{
    @Upsert
    suspend fun addWish(wish: Wish)

    @Delete
    suspend fun deleteWish(wish: Wish)

    @Query("Select * FROM wish_table")
    fun getAllWishes(): Flow<List<Wish>>

    @Query("Select * FROM wish_table where id=:id")
    fun getWishById(id: Long): Flow<Wish>

    @Update
    suspend fun updateWish(wish: Wish)
}