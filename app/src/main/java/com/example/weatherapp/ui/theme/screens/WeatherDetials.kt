package com.example.weatherapp.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.R
import com.example.weatherapp.api.model.WeatherModel
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.ui.theme.font_bold
import com.example.weatherapp.ui.theme.font_semi_bold
import com.example.weatherapp.ui.theme.font_standard
import com.example.weatherapp.ui.theme.light
import kotlin.text.Typography.degree

@Composable
fun WeatherDetails(data: WeatherModel) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                painter = painterResource(R.drawable.location),
                contentDescription = "Location icon",
                tint = Color.Unspecified,
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = data.location.name,
                fontSize = 30.sp,
                fontFamily = font_bold,
                modifier = Modifier
                    .padding(top = 18.dp)
                    .padding(start = 4.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = data.location.country,
                fontSize = 18.sp,
                color = Color.Gray,
                fontFamily = font_standard
            )

        }

        Spacer(modifier = Modifier.height(24.dp))

        val degree = "°C"
        Text(
            text = "${data.current.temp_c} $degree",
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = font_bold,
            textAlign = TextAlign.Center
        )


        Image(
            painter = rememberAsyncImagePainter(
                "https:${data.current.condition.icon}".replace("64x64", "128x128")
            ),
            modifier = Modifier.size(160.dp),
            contentDescription = ""
        )
        Text(
            text = data.current.condition.text,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontFamily = light
        )
        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth()

        ) {
            WeatherKeyValue(
                key = "Local Time",
                modifier = Modifier.weight(1f),
                value = data.location.localtime.split(" ")[1],
                icon = painterResource(R.drawable.clock)
            )

            WeatherKeyValue(
                key = "condition",
                modifier = Modifier.weight(1f),
                value = data.current.condition.text,
                icon = painterResource(R.drawable.clouds)
            )

        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            WeatherKeyValue(
                value = data.current.humidity,
                key = "Humidity",
                modifier = Modifier.weight(1f),
                icon = painterResource(R.drawable.humidity)
            )
            WeatherKeyValue(
                value = data.current.wind_kph + "km/h",
                key = "wind speed",
                modifier = Modifier.weight(1f),
                icon = painterResource(R.drawable.windy)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            WeatherKeyValue(
                key = "UV",
                modifier = Modifier.weight(1f),
                value = data.current.uv,
                icon = painterResource(R.drawable.sunny)
            )

            WeatherKeyValue(
                key = "visibility",
                modifier = Modifier.weight(1f),
                value = data.current.vis_km + " km",
                icon = painterResource(R.drawable.visibility)
            )
        }
    }
}

@Composable
fun WeatherKeyValue(key: String, value: String, icon: Painter, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(0.5f)
            .wrapContentHeight(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .background(if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background)
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = Color(0xFF007BFF),
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = key,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontFamily = light
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = value,
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold,
                        fontFamily = font_semi_bold
                    )
                }
            }
        }
    }
}
