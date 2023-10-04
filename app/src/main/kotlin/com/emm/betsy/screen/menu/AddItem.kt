package com.emm.betsy.screen.menu

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.emm.betsy.ui.theme.BetsyTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddItem(navController: NavController, vm: AddItemViewModel = koinViewModel()) {

    val state = vm.channelFlow.collectAsState(Event.None)

    DisposableEffect(state.value) {
        if (state.value is Event.AddSuccess) {
            navController.popBackStack()
        }

        onDispose {  }
    }

    AddItem(
        nameValue = vm.name,
        changeName = vm::updateName,
        descriptionValue = vm.type,
        changeDescription = vm::updateType,
        addItem = vm::addItem
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun AddItem(
    nameValue: String = "",
    changeName: (String) -> Unit = {},
    descriptionValue: ItemType = ItemType.ENTRY,
    changeDescription: (ItemType) -> Unit = {},
    addItem: () -> Unit = {}
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 30.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            Text(text = "New Item", style = MaterialTheme.typography.titleLarge)
            OutlinedTextField(
                value = nameValue,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = changeName,
                isError = nameValue.isEmpty(),
                supportingText = {
                    if (nameValue.isEmpty()) {
                        Text(text = "Ingrese un valor")
                    }
                },
                placeholder = {
                    Text(text = "Nombre del plato")
                }
            )

            RadioButtonTaskType(
                selectedOption = descriptionValue,
                onOptionSelected = changeDescription
            )

            FilledTonalButton(onClick = {
                coroutineScope.launch {
                    keyboardController?.hide()
                    delay(150L)
                    addItem()
                }
            }) {
                Text(text = "Agregar item")
            }
        }
    }
}



@Composable
fun RadioButtonTaskType(
    selectedOption: ItemType = ItemType.ENTRY,
    onOptionSelected: (ItemType) -> Unit = {}
) {

    Column {
        ItemType.values().forEach { itemType: ItemType ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = itemType == selectedOption,
                        onClick = {
                            onOptionSelected(itemType)
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = itemType == selectedOption,
                    onClick = { onOptionSelected(itemType) },
                )
                Text(
                    text = itemType.label,
                    modifier = Modifier.padding(start = 7.dp)
                )
            }
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun AddItemPreview() {
    BetsyTheme(darkTheme = true) {
        Surface {
            AddItem()
        }
    }
}