package com.horizonloop.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.horizonloop.app.ui.AppRoot
import com.horizonloop.app.ui.container.PhoneContainer
import com.horizonloop.app.ui.theme.HorizonLoopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val transparent = Color.Transparent.toArgb()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(transparent),
            navigationBarStyle = SystemBarStyle.dark(transparent)
        )
        super.onCreate(savedInstanceState)
        setContent {
            HorizonLoopTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF18181B)
                ) {
                    PhoneContainer {
                        AppRoot()
                    }
                }
            }
        }
    }
}
