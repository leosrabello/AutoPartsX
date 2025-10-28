package com.autopartsx.autopartsapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.autopartsx.autopartsapp.viewmodel.CartViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    onBack: () -> Unit
) {
    val items = cartViewModel.cartItems
    val msg by cartViewModel.cartMessage
    val total = cartViewModel.totalPrice()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrinho") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Voltar") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            if (msg != null) {
                Text(msg ?: "", color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(8.dp))
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f, fill = true)
                    .fillMaxWidth()
            ) {
                items(items) { cartItem ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(cartItem.part.name, style = MaterialTheme.typography.titleMedium)
                            Text("Qtd: ${cartItem.quantity}")
                            Text(
                                "Preço unit.: R$ ${
                                    ((cartItem.part.price * 100.0).roundToInt() / 100.0)
                                }"
                            )
                            Spacer(Modifier.height(8.dp))
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                OutlinedButton(
                                    onClick = { cartViewModel.changeQty(cartItem.part.id, +1) }
                                ) { Text("+") }

                                OutlinedButton(
                                    onClick = { cartViewModel.changeQty(cartItem.part.id, -1) }
                                ) { Text("-") }

                                TextButton(
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = MaterialTheme.colorScheme.error
                                    ),
                                    onClick = { cartViewModel.removeFromCart(cartItem.part.id) }
                                ) {
                                    Text("Remover")
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            Text(
                "Total: R$ ${((total * 100.0).roundToInt() / 100.0)}",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = { cartViewModel.checkout() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Finalizar compra (simulação)")
            }
        }
    }
}
