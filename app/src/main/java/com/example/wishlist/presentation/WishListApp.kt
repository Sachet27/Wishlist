package com.example.wishlist.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

@Composable
fun WishListApp(modifier: Modifier = Modifier) {
    val wishListViewModel: WishListViewModel = viewModel()
    val navController= rememberNavController()
    NavHost(navController, startDestination = Routes.HomeScreen)
    {

        composable<Routes.HomeScreen> {

            HomeScreen(navController, wishListViewModel)
        }
        composable<Routes.DetailScreen> {
            val args= it.toRoute<Routes.DetailScreen>()

            DetailScreen(
                id= args.id,
                viewModel =wishListViewModel,
                navController = navController,
            )
        }
    }
}