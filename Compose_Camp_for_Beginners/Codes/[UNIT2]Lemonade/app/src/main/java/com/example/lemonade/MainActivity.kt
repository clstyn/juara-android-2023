package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonApp()
            }
        }
    }
}

@Composable
fun LemonApp() {
    var pos by remember { mutableStateOf(0) }
    var squeeze by remember { mutableStateOf(0) }
    var userSqueeze by remember { mutableStateOf(0) }

    val imageResource = when (pos) {
        0 -> R.drawable.lemon_tree
        1 -> R.drawable.lemon_squeeze
        2 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    val stringRes = when (pos) {
        0 -> R.string.text_1
        1 -> R.string.text_2
        2 -> R.string.text_3
        else -> R.string.text_4
    }

    val contentDescRes = when (pos) {
        0 -> R.string.text_5
        1 -> R.string.text_6
        2 -> R.string.text_7
        else -> R.string.text_8
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .padding(16.dp),
                onClick = {
                              if (pos == 1){
                                  squeeze = (2..4).random()
                                  if (userSqueeze < squeeze){
                                      userSqueeze++
                                  } else {
                                      pos++
                                  }
                              } else if (pos == 3) {
                                  pos = 0
                              } else {
                                  pos++
                              }
                          },
                shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner_radius)),
                colors =  ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            ){
                Image(painter = painterResource(imageResource), contentDescription = stringResource(contentDescRes))
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = stringResource(stringRes))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LemonadeTheme {
        LemonApp()
    }
}