package ru.pskda.kfupass.android.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import net.glxn.qrgen.android.QRCode
import ru.pskda.kfupass.android.domain.entity.TokenList
import ru.pskda.kfupass.android.ui.compose.LoadingScreen
import ru.pskda.kfupass.android.ui.theme.MyApplicationTheme
import ru.pskda.kfupass.android.ui.viewmodel.MainViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoadingScreen()
        }

        initObservers()
        getLocalTokens()

    }

    private fun setPassList(tokens: TokenList) {
        setContent {
            PassList(tokens)
        }
    }

    private fun setNewPassScreen() {
        startActivity(
            Intent(
                this,
                NewPassActivity::class.java
            )
        )
    }

    private fun showError(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    private fun checkTokens(tokens: TokenList) {
        setContent {
            PassList(tokens = tokens)
        }
    }

    private fun getLocalTokens() {
        viewModel.getLocalTokens()
    }

    private fun initObservers() {
        viewModel.validTokens.observe(this) { tokens ->
            tokens.fold(
                onSuccess = { validTokenList ->
                    setPassList(validTokenList)
                },
                onFailure = { error ->
                    showError(error.message.toString())
                }
            )
        }

        viewModel.localTokens.observe(this) { tokens ->
            tokens.fold(
                onSuccess = { localTokenList ->
                    checkTokens(localTokenList)
                },
                onFailure = { error ->
                    showError(error.message.toString())
                }
            )

        }
    }


    @Composable
    fun PassList(tokens: TokenList) {
        val emptyList: Boolean = tokens.list.isEmpty()
        MyApplicationTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column {
                    if (emptyList) {
                        Text(
                            text = "У вас нет действующих пропусков",
                            textAlign = TextAlign.Center
                        )

                        NewPassButton()
                    } else {
                        tokens.list.iterator().forEach { token ->
                            PassListItem(token.building, token.address, token.status, token.token)
                        }

                        NewPassButton()
                    }
                }
            }
        }
    }

    @Composable
    private fun PassListItem(name: String, address: String, status: String, token: String) {
        var expanded by remember { mutableStateOf(false) }

        Surface(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .animateContentSize(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(12.dp)
                    ) {
                        Text(text = name)
                        Text(text = address)

                    }
                    ElevatedButton(
                        onClick = { expanded = !expanded },
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(if (expanded) "Убрать QR" else "Показать QR")
                    }
                }
                if (expanded) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (status == "Ожидает подтверждения" || status == "Неактивен")
                            Text(text = "Ваш пропуск еще не готов")
                        else {
                            val myBitmap: Bitmap = QRCode.from(token).bitmap()
                            Image(
                                bitmap = myBitmap.asImageBitmap(),
                                contentDescription = "qr",
                                modifier = Modifier
                                    .size(160.dp)
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun NewPassButton() {
        Surface(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
            IconButton(
                onClick = {
                    setNewPassScreen()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    Icons.Rounded.Add,
                    contentDescription = "Запросить новый пропуск",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}