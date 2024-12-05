package com.example.wishlist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "wish_table")
@Serializable
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id:Long= 0L,
    val title: String= "",
    val description: String= ""
)

object DummyWish{
    val dummyWishList= listOf(
        Wish(title = "Body", description = "6 packs"),
        Wish(title = "Finance", description = "Self-sustained"),
        Wish(title = "Skills", description = "Piano, Kotlin"),
    )
}
