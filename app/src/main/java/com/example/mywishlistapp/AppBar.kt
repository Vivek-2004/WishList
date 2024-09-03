package com.example.mywishlistapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppBarView(
    title:String,
    onBackNavClicked: () -> Unit = {}
){
    val navigationIcon: (@Composable () -> Unit)? ={
        if(!title.contains("Wish List")){
            IconButton(onClick = { onBackNavClicked() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Button",
                    tint = colorResource(id = R.color.white)
                )
            }
        } else { null }
    }

    TopAppBar(title = {
        Text(
            text = title,
            color = colorResource(id = R.color.white),
            modifier = Modifier.padding(start = 50.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )},
        elevation = 3.dp,
        backgroundColor = colorResource(id = R.color.navy_blue),
        navigationIcon = navigationIcon
    )
}