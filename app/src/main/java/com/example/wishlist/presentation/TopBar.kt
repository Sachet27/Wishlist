package com.example.wishlist.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onBackNavigation: ()->Unit
) {
    val navigationIcon: (@Composable ()-> Unit )? = {
        if(!title.contains("Wishlist")) {
            IconButton(
                onClick = onBackNavigation
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        else
            null
    }
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp),
        title = { Text(text= title,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(start = 4.dp)
                .heightIn(max = 24.dp)
        )
                },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = navigationIcon?: {}
    )
}