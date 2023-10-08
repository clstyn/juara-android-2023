package com.example.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFD2E8D4)
                ) {
                    BusinessCard(
                        fullname = stringResource(R.string.name),
                        title = stringResource(R.string.title),
                        phoneNo = stringResource(R.string.phoneNo_text),
                        socmed = stringResource(R.string.socmed_text),
                        email = stringResource(R.string.email_text),
                        imagePainter = painterResource(R.drawable.android_logo)
                    )
                }
            }
        }
    }
}

@Composable
fun BusinessCard(fullname: String, title: String, phoneNo: String, socmed: String, email: String, imagePainter: Painter, modifier: Modifier = Modifier) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        Image(
            painter = imagePainter,
            contentDescription = null,
            modifier = Modifier
                .height(120.dp)
                .width(120.dp)
        )
        Text(
            text = fullname,
            modifier = Modifier
                .padding(8.dp),
            fontSize = 32.sp,
            color = Color.White
        )
        Text(
            text = title,
            modifier = modifier,
            fontSize = 16.sp,
            color = Color.Green
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(Icons.Rounded.Call, contentDescription = "icon", modifier = Modifier
                    .padding(4.dp))
                Text(text = phoneNo)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(Icons.Rounded.Share, contentDescription = "icon", modifier = Modifier
                    .padding(4.dp))
                Text(text = socmed)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(Icons.Rounded.Email, contentDescription = "icon", modifier = Modifier
                    .padding(4.dp))
                Text(text = email)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BusinessCardPreview() {
    BusinessCardTheme {
        BusinessCard(
            fullname = stringResource(R.string.name),
            title = stringResource(R.string.title),
            phoneNo = stringResource(R.string.phoneNo_text),
            socmed = stringResource(R.string.socmed_text),
            email = stringResource(R.string.email_text),
            imagePainter = painterResource(R.drawable.android_logo)
        )
    }
}