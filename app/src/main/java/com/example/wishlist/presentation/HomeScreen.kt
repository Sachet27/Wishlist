package com.example.wishlist.presentation

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlist.data.DummyWish
import com.example.wishlist.data.Wish

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: WishListViewModel
) {
    Scaffold(
        topBar = { TopBar(
            title = "Wishlist",
            onBackNavigation = {}
        ) },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    //navigate to detail screen
                    navController.navigate(Routes.DetailScreen())
                },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, null )
            }
        }
    ) {values->
        val wishlist by viewModel.getAllWishes.collectAsState(emptyList())
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(values)) {
            items(wishlist,key= { wish->wish.id }){ wish->
                val dismissState= rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                         if(it==SwipeToDismissBoxValue.StartToEnd || it==SwipeToDismissBoxValue.EndToStart){
                             viewModel.deleteWish(wish)
                             true
                         }
                        else
                            false
                    }
                )

             SwipeToDismissBox(
                 modifier = Modifier.padding(top = 12.dp, start = 8.dp, end = 8.dp).clip(CardDefaults.shape),
                 state = dismissState,
                 backgroundContent = {
                     Log.d("Yeet", dismissState.currentValue.toString())
                     val color = animateColorAsState(
                         targetValue = when(dismissState.targetValue){
                             SwipeToDismissBoxValue.EndToStart -> Color.Red
                             SwipeToDismissBoxValue.StartToEnd-> Color.Green
                             else -> Color.Transparent
                         }
                     )
                     Box(
                         modifier = Modifier.fillMaxSize().background(color.value),
                         contentAlignment = Alignment.CenterEnd
                     ){
                         Icon(Icons.Filled.Delete, null, tint= Color.Black)
                     }
                 }
             ) {
                 WishItem(wish) {
                     navController.navigate(Routes.DetailScreen(wish.id))
                 }

             }
            }
        }
    }
}

@Composable
fun WishItem(
    wish: Wish,
    onClick: ()-> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column (
            modifier = Modifier.padding(8.dp)
        ){
            Text(text= wish.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(text= wish.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}