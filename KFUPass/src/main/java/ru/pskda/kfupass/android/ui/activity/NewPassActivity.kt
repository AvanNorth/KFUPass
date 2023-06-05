package ru.pskda.kfupass.android.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import ru.pskda.kfupass.android.domain.entity.Token
import ru.pskda.kfupass.android.ui.compose.ErrorScreen
import ru.pskda.kfupass.android.ui.theme.MyApplicationTheme
import ru.pskda.kfupass.android.ui.viewmodel.NewPassViewModel

@AndroidEntryPoint
class NewPassActivity : ComponentActivity() {
    private val viewModel: NewPassViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewPassScreen()
        }

        initObservers()

    }

    private fun saveToken(firstName: String, secondName: String, passNumber: String, selectedBuilding: String) {
        val token = Token(
            building = selectedBuilding,
            address = "ул. Кремлевская, д.18",
            token = (firstName+secondName+passNumber).hashCode().toString(),
            status = "Ожидает подтверждения"
        )

        viewModel.saveToken(token)
    }

    private fun showError(text: String) {
        setContent {
            ErrorScreen(errText = text)
        }
    }

    private fun initObservers() {
        viewModel.savedToken.observe(this) { token ->
            token.fold(
                onSuccess = {
                    val intent = Intent(
                        this,
                        MainActivity::class.java
                    )

                    startActivity(intent)
                },
                onFailure = {
                    showError("Не удалось отправить заявку")
                }
            )

        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NewPassScreen() {
        val context = LocalContext.current
        val buildings = arrayOf("Главное здание", "Здание №2", "Здание №3")
        var expanded by remember { mutableStateOf(false) }
        var selectedBuilding by remember { mutableStateOf(buildings[0]) }

        var firstName by remember { mutableStateOf("") }
        var secondName by remember { mutableStateOf("") }
        var passportNumber by remember { mutableStateOf("") }
        MyApplicationTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Заполните форму для подачи заявки на новый пропуск",
                        fontSize = 20.sp,
                        fontWeight = FontWeight(300),
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Center
                    )
                }
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
                        RegularText(text = "Выберите здание")

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = {
                                    expanded = !expanded
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                TextField(
                                    value = selectedBuilding,
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expanded
                                        )
                                    },
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxWidth()
                                )

                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    buildings.forEach { item ->
                                        DropdownMenuItem(
                                            text = { Text(text = item) },
                                            onClick = {
                                                selectedBuilding = item
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        RegularText(text = "Фамилия")

                        TextField(
                            value = secondName,
                            onValueChange = { newText ->
                                secondName = newText
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        RegularText(text = "Имя")

                        TextField(
                            value = firstName,
                            onValueChange = { newText ->
                                firstName = newText
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        RegularText(text = "Номер паспорта")

                        TextField(
                            value = passportNumber,
                            onValueChange = { newText ->
                                passportNumber = newText
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
                Row(verticalAlignment = Alignment.Bottom) {
                    Surface(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
                        IconButton(
                            onClick = {
                                saveToken(firstName, secondName, passportNumber, selectedBuilding)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                Icons.Rounded.Done,
                                contentDescription = "Запросить пропуск",
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun RegularText(text: String) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight(250),
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.padding(8.dp)
        )
    }

    @Composable
    @Preview
    fun PreviewNewPass() {
        NewPassScreen()
    }
}