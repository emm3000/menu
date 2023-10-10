package com.emm.betsy.screen.additem.common

import android.content.Intent
import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.emm.betsy.screen.additem.RadioButtonTaskType
import com.emm.betsy.screen.menu.ItemType
import com.emm.betsy.ui.theme.BetsyTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ItemEditor(
    title: String = "",
    nameValue: String = "",
    imageUri: String? = null,
    updateImageUri: (String?) -> Unit = {},
    changeName: (String) -> Unit = {},
    descriptionValue: ItemType = ItemType.ENTRY,
    changeDescription: (ItemType) -> Unit = {},
    buttonAction: () -> Unit = {},
    buttonName: String = ""
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { inputUri ->
//            val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            context.grantUriPermission(context.packageName, inputUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            context.contentResolver.takePersistableUriPermission(inputUri!!, takeFlags)
            updateImageUri(inputUri.toString())
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 30.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {

            Text(text = title, style = MaterialTheme.typography.titleLarge)
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

            Box(
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .height(180.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(50f))
                    .border(1.dp, Color.White, RoundedCornerShape(50f))
                    .clickable {
                        launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                contentAlignment = Alignment.Center,
            ) {
                if (!imageUri.isNullOrEmpty()) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(50f)),
                        contentScale = ContentScale.Crop
                    )
                    IconButton(
                        onClick = {
                            updateImageUri(null)
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(end = 5.dp, top = 5.dp)
                    ) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = null)
                    }
                } else {
                    Text(
                        text = "Seleccionar foto",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )

                }

            }

            FilledTonalButton(onClick = {
                coroutineScope.launch {
                    keyboardController?.hide()
                    delay(150L)
                    buttonAction()
                }
            }) {
                Text(text = buttonName)
            }
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun AddItemPreview() {
    BetsyTheme(darkTheme = true) {
        Surface {
            ItemEditor()
        }
    }
}