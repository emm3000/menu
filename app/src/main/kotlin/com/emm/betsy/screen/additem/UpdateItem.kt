package com.emm.betsy.screen.additem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.emm.betsy.screen.additem.common.ItemEditor
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun UpdateItem(
    navController: NavController,
    name: String,
    type: String,
    id: Long,
    vm: EditorItemViewModel = koinViewModel(
        parameters = { parametersOf(name, type, id) }
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
        title = "Actualizar Plato",
        nameValue = vm.name,
        changeName = vm::updateName,
        descriptionValue = vm.type,
        changeDescription = vm::updateType,
        buttonAction = vm::updateItem,
        buttonName = "Update Item"
    )

}