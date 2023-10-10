package com.emm.betsy

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.emm.betsy.ui.theme.BetsyTheme

class SharedActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        checkIntent(intent = intent)
        setContent {
            BetsyTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    SharedScreen()
                }
            }
        }
    }

    private fun checkIntent(intent: Intent?) {
        when {
            intent?.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    handleSendText(intent) // Handle text being sent
                } else if (intent.type?.startsWith("image/") == true) {
                    handleSendImage(intent)
                }
            }

            intent?.action == Intent.ACTION_SEND_MULTIPLE
                    && intent.type?.startsWith("image/") == true -> {
                handleSendMultipleImages(intent) // Handle multiple images being sent
            }

            else -> {
            }
        }

    }

    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            // Update UI to reflect text being shared
        }
    }

    private fun handleSendImage(intent: Intent) {
        (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let {
            // Update UI to reflect image being shared
            Log.e("aea", it.toString())
            Log.e("aea", it.authority.toString())
            Log.e("aea", it.host.toString())
        }
    }

    private fun handleSendMultipleImages(intent: Intent) {
        intent.getParcelableArrayListExtra<Parcelable>(Intent.EXTRA_STREAM)?.let {
            // Update UI to reflect multiple images being shared
        }
    }


}

@Composable
fun SharedScreen() {

    var currentImage by remember {
        mutableStateOf("")
    }

    val currentContext = LocalContext.current
    val currentIntent: Intent = remember {
        (currentContext as Activity).intent
    }

    DisposableEffect(key1 = Unit) {
        if (currentIntent.action == Intent.ACTION_SEND) {
            if (currentIntent.type?.startsWith("image/") == true) {
                (currentIntent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let {
                    currentImage = it.toString()
                }
            }
        }
        onDispose { }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = currentImage)
        if (currentImage.isNotEmpty()) {
            AsyncImage(
                model = currentImage,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )

        }
    }
}