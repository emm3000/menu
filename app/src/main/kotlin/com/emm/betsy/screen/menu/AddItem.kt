package com.emm.betsy.screen.menu

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.emm.betsy.dateToLegibleDate
import com.emm.betsy.formatDuration
import com.emm.betsy.ui.theme.BetsyTheme
import menu.item.Item
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddItem(navigationController: NavHostController, vm: AddItemViewModel = koinViewModel()) {

    val itemList: List<Item> by vm.allItems.collectAsState()

    AddItem(
        itemList = itemList,
        nameValue = vm.name,
        changeDescription = vm::updateDescription,
        changeName = vm::updateName,
        descriptionValue = vm.description,
        addItem = vm::addItem,
        popBackStack = { navigationController.popBackStack() }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddItem(
    itemList: List<Item> = emptyList(),
    nameValue: String = "",
    changeName: (String) -> Unit = {},
    descriptionValue: String = "",
    changeDescription: (String) -> Unit = {},
    addItem: () -> Unit = {},
    popBackStack: () -> Unit = {}
) {

    val (showDialog, setShowDialog) = remember {
        mutableStateOf(false)
    }

    if (showDialog) {
        AddItemDialog(
            setShowDialog = setShowDialog,
            nameValue = nameValue,
            changeName = changeName,
            descriptionValue = descriptionValue,
            changeDescription = changeDescription,
            addItem = addItem
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Add Item") },
                navigationIcon = {
                    IconButton(
                        onClick = popBackStack
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { setShowDialog(true) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            state = rememberLazyListState(),
            contentPadding = innerPadding
        ) {
            if (itemList.isEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Sin items", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                }
            }
            items(itemList) {
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 10.dp),
                    onClick = {}
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {
                        Text(text = it.name)
                        Text(text = it.description)
                        Text(text = it.createdAt.dateToLegibleDate())
                        Text(text = it.createdAt.formatDuration())
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AddItemDialog(
    setShowDialog: (Boolean) -> Unit,
    nameValue: String,
    changeName: (String) -> Unit,
    descriptionValue: String,
    changeDescription: (String) -> Unit,
    addItem: () -> Unit
) {
    Dialog(
        onDismissRequest = { setShowDialog(false) },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = true,
        )
    ) {
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .heightIn(max = screenHeight * .7f)
                .clip(RoundedCornerShape(50f))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(50f)
                )
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

                OutlinedTextField(
                    value = descriptionValue,
                    onValueChange = changeDescription,
                    maxLines = 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    placeholder = {
                        Text(text = "Descripción del plato")
                    }
                )

                FilledTonalButton(onClick = {
                    addItem()
                    setShowDialog(false)
                }) {
                    Text(text = "Agregar item")
                }
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