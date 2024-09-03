package com.example.mywishlistapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mywishlistapp.data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController
){
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    if(id != 0L){
        val wish = viewModel.getAWishById(id).collectAsState(initial = Wish(0L, "", ""))
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description
    } else {
        viewModel.wishTitleState = ""
        viewModel.wishDescriptionState = ""
    }

    Scaffold(
        topBar = { AppBarView( title =
        if(id != 0L) "Update Wish"
        else "Add Wish"
        )
        {navController.navigateUp()}
        },
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                WishTextField(label = "Title",
                    value = viewModel.wishTitleState,
                    onValueChanged = { viewModel.onWishTitleChanged(it) }
                )

                Spacer(modifier = Modifier.height(10.dp))

                WishTextField(label = "Description",
                    value = viewModel.wishDescriptionState,
                    onValueChanged = { viewModel.onWishDescriptionChanged(it) }
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(R.color.navy_blue),
                    contentColor = Color.White
                ),
                    onClick = {
                        if (viewModel.wishTitleState.isNotEmpty() && viewModel.wishDescriptionState.isNotEmpty()) {
                            if (id != 0L) {
                                viewModel.updateWish(
                                    Wish(
                                        id = id,
                                        title = viewModel.wishTitleState.trim(),
                                        description = viewModel.wishDescriptionState.trim()
                                    )
                                )
                            } else {
                                // Adding a  Wish
                                viewModel.addWish(
                                    Wish(
                                        title = viewModel.wishTitleState.trim(),
                                        description = viewModel.wishDescriptionState.trim()
                                    )
                                )
                            }
                        } else { }
                        scope.launch {
                            navController.navigateUp()
                        }
                    }) {
                    Text(
                        text =
                        if (id != 0L) stringResource(id = R.string.update_wish)
                        else stringResource(id = R.string.add_wish),
                        style = TextStyle(fontSize = 18.sp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishTextField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label, color = Color.White) },
        modifier = Modifier.fillMaxWidth().padding(top = 2.dp, bottom = 2.dp, start = 16.dp, end = 16.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.Sentences
        ),
        textStyle = TextStyle(color = Color.White),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White
        )
    )
}