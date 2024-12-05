package com.example.wishlist.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wishlist.R
import com.example.wishlist.data.Wish
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    id: Long,
    viewModel: WishListViewModel,
    navController: NavController
) {
    var snackBarMessage by remember{ mutableStateOf("") }
    val scope= rememberCoroutineScope()
    val snackBarHostState= remember{ SnackbarHostState() }
    if(id!=0L){
        val wish= viewModel.getWishById(id).collectAsState(Wish(0,"",""))
        viewModel.wishTitle= wish.value.title
        viewModel.wishDescription= wish.value.description
    }
    else{
        viewModel.wishTitle= ""
        viewModel.wishDescription= ""

    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TopBar(
                title = if (id != 0L) stringResource(R.string.update_wish) else stringResource(R.string.add_wish),
                onBackNavigation = {
                    navController.popBackStack()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                onClick = {
                    if(viewModel.wishTitle.isNotBlank()){
                        if(id!=0L) {
                            viewModel.updateWish(
                                Wish(
                                    id= id,
                                    title = viewModel.wishTitle.trim(),
                                    description = viewModel.wishDescription.trim()
                                )
                            )
                            snackBarMessage = "Wish Updated"
                        }
                        else{
                            viewModel.addWish(
                                Wish(title = viewModel.wishTitle.trim(),
                                    description = viewModel.wishDescription.trim()
                                )
                            )
                            snackBarMessage= "Wish created!"
                        }
                        }
                    else{
                        snackBarMessage= "Please enter the required fields."
                    }
                    scope.launch {
                        snackBarHostState.showSnackbar(snackBarMessage)
                        navController.popBackStack()
                    }
                }
            ) {
                Icon(Icons.Default.Done, null, Modifier.size(20.dp))
            }
        }

    ) {values->
        Column(
            modifier = Modifier
                .padding(values)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant),
        ){
            Spacer(Modifier.padding(top= 8.dp))

            OutlinedTextField(
                textStyle = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                value = viewModel.wishTitle,
               onValueChange={
                   viewModel.onTitleChange(it)
               },
                placeholder = {
                    Text(
                        text= if(viewModel.wishTitle.isBlank()) "Wish Title" else "",
                        style = MaterialTheme.typography.titleLarge
                    )
                       },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),

            )
            HorizontalDivider(thickness = 3.dp)
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = viewModel.wishDescription,
                onValueChange={
                    viewModel.onDescriptionChange(it)
                },
                placeholder = {
                    Text(
                        text= if(viewModel.wishDescription.isBlank()) "Wish description" else "",
                        style = MaterialTheme.typography.titleMedium)
                       },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

@Preview(showBackground =true)
@Composable
fun DetailScreenPreview(){
    DetailScreen(5, viewModel<WishListViewModel>(), rememberNavController() )
}