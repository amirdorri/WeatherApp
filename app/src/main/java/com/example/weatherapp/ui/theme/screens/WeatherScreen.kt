package com.example.weatherapp.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.WeatherViewModel
import com.example.weatherapp.api.NetworkResponse
import com.example.weatherapp.ui.theme.light
import com.example.weatherapp.ui.theme.screens.error.ErrorState
import com.example.weatherapp.ui.theme.screens.loading.PulseAnimation

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {

    var city by remember { mutableStateOf("") }
    var result = viewModel.weatherResult.observeAsState()
    val keyboardState = LocalSoftwareKeyboardController.current
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.primary
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                value = city,
                shape = RoundedCornerShape(20.dp),
                onValueChange = { city = it },
                modifier = Modifier.weight(1f),
                label = {
                    Text(
                        text = "search for any location",
                        fontFamily = light,
                        fontSize = 18.sp,
                    )
                },
                maxLines = 1
            )
            IconButton(
                onClick = {
                    viewModel.getData(city)
                    keyboardState?.hide()
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.search),
                    contentDescription = "",
                    tint = Color.Unspecified,
                    modifier = Modifier
                )
            }
        }
        when (val responseResult = result.value) {

            NetworkResponse.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    PulseAnimation()
                }
            }

            is NetworkResponse.Success -> {
                WeatherDetails(responseResult.data)
            }

            is NetworkResponse.Error -> {
                ErrorState(text = responseResult.massage, painter = painterResource(R.drawable.sad))
            }
            null -> {

            }
        }
    }
}

