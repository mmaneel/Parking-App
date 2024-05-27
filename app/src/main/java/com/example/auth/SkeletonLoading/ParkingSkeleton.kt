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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp


@Composable
fun OneParkingSkeleton()
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .height(140.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color.White)
    )
    {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1.5f)
                .padding(5.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray),
            )
        }

        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
        ) {

            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxWidth(.5f)
                    .padding(horizontal = 5.dp)
                    .clip(RoundedCornerShape(10.dp)),
            ){
                Text(text = " ")
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .padding(vertical = 5.dp)
                    .background(Color.LightGray)
                    .clip(RoundedCornerShape(10.dp)),
            ){
                Text(text = " ")
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(.4f)
                    .padding(vertical = 5.dp)
                    .background(Color.LightGray)
                    .clip(RoundedCornerShape(10.dp)),
            ){
                Text(text = " ")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(.3f)
                    .padding(vertical = 5.dp)
                    .background(Color.LightGray)
                    .clip(RoundedCornerShape(10.dp)),
            ){
                Text(text = " ")
            }




        }
    }
}


@Composable
fun ParkingSkeleton()
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

    Column (
        modifier = Modifier.graphicsLayer(
            alpha = alpha.value
        ),
    ){
        OneParkingSkeleton()
        OneParkingSkeleton()
        OneParkingSkeleton()
        OneParkingSkeleton()
        OneParkingSkeleton()
    }
}