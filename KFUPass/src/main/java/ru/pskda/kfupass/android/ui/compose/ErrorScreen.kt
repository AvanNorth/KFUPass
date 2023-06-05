package ru.pskda.kfupass.android.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.pskda.kfupass.android.R
import ru.pskda.kfupass.android.ui.theme.MyApplicationTheme

@Composable
fun ErrorScreen(errText: String) {
    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painterResource(id = R.drawable.error_icon),
                        "KFU logo",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(8.dp)
                    )
                    Text(
                        text = "Что то пошло не так...",
                        fontSize = 25.sp,
                        fontWeight = FontWeight(450),
                        fontFamily = FontFamily.SansSerif
                    )
                    Text(
                        text = errText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight(200),
                        fontFamily = FontFamily.SansSerif
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewError() {
    ErrorScreen(errText = "Сообщение ошибки")
}