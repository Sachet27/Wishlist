package com.example.wishlist.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wishlist.data.Graph
import com.example.wishlist.data.Wish
import com.example.wishlist.data.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishListViewModel(
    private val wishRepository: WishRepository= Graph.repository
):ViewModel() {

    var wishTitle by mutableStateOf("")

    var wishDescription by mutableStateOf("")

    fun onTitleChange(newString: String){
        wishTitle= newString.replace("\n","")
    }

    fun onDescriptionChange(newString: String){
        wishDescription= newString
    }

    //database stuff
    lateinit var getAllWishes: Flow<List<Wish>>

    init{
            getAllWishes= wishRepository.getAllWishes()
    }

    fun addWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.addWish(wish)
        }
    }


    fun updateWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.updateWish(wish)
        }
    }

    fun deleteWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.deleteWish(wish)
        }
    }

    fun getWishById(id: Long):Flow<Wish>{
            return wishRepository.getWishById(id)
    }
}
