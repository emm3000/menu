package com.emm.betsy.screen.additem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.emm.betsy.screen.additem.common.ItemEditor
import com.emm.betsy.screen.menu.ItemType
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AddItem(
    navController: NavController,
    vm: EditorItemViewModel = koinViewModel(
        parameters = { parametersOf("", "", "", 0L) }
    )
) {

    val state = vm.channelFlow.collectAsState(Event.None)

    DisposableEffect(state.value) {
        if (state.value is Event.AddSuccess) {
            navController.popBackStack()
        }

        onDispose { }
    }

    ItemEditor(
        title = "Add Item",
        nameValue = vm.name,
        changeName = vm::updateName,
        descriptionValue = vm.type,
        changeDescription = vm::updateType,
        imageUri = vm.imageUri,
        updateImageUri = vm::updateImageUri,
        buttonAction = vm::addItem,
        buttonName = "Add Item"
    )
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
