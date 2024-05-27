package com.example.auth.SkeletonLoading

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun OneVParkingSkeleton()
{
    Box(
        modifier = Modifier
            .width(300.dp)
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(5.dp),
        ) {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .width(298.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray),
            )

            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .background(Color.LightGray)
                    .width(100.dp)
                    .clip(RoundedCornerShape(10.dp)),
            ) {
                Text(
                    text = " ",
                    fontSize = 15.sp,
                )
            }

            Text(
                text = " ",
                fontSize = 19.sp,
                modifier = Modifier
                    .width(200.dp)
                    .padding(bottom = 20.dp)
                    .background(Color.LightGray)

                )

            Text(
                text = " ",
                fontSize = 15.sp,
                modifier = Modifier
                    .width(110.dp)
                    .padding(bottom = 10.dp)
                    .background(Color.LightGray)
            )

            Text(
                text = " ",
                fontSize = 15.sp,
                modifier = Modifier
                    .width(90.dp)
                    .padding(bottom = 10.dp)
                    .background(Color.LightGray)
            )
        }
    }
}


@Composable
fun VerticalParkingSkeleton()
{
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        alpha.animateTo(
            targetValue = .8f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Row (
        modifier = Modifier.graphicsLayer(
            alpha = alpha.value
        ),
    ){
        OneVParkingSkeleton()
        OneVParkingSkeleton()
    }
}