package com.autopartsx.autopartsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.autopartsx.autopartsapp.navigation.AppNavHost
import com.autopartsx.autopartsapp.ui.theme.AutoPartsTheme
import com.autopartsx.autopartsapp.viewmodel.AuthViewModel
import com.autopartsx.autopartsapp.viewmodel.CartViewModel
import com.autopartsx.autopartsapp.viewmodel.PartViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutoPartsTheme {
                val authVM: AuthViewModel = viewModel()
                val partVM: PartViewModel = viewModel()
                val cartVM: CartViewModel = viewModel()

                AppNavHost(
                    authViewModel = authVM,
                    partViewModel = partVM,
                    cartViewModel = cartVM
                )
            }
        }
    }
}
