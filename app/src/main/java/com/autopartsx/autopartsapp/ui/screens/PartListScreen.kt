package com.autopartsx.autopartsapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.autopartsx.autopartsapp.viewmodel.AuthViewModel
import com.autopartsx.autopartsapp.viewmodel.CartViewModel
import com.autopartsx.autopartsapp.viewmodel.PartViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartListScreen(
    authViewModel: AuthViewModel,
    partViewModel: PartViewModel,
    cartViewModel: CartViewModel,
    onAddPart: () -> Unit,
    onEditPart: (Int) -> Unit,
    onGoCart: () -> Unit,
    onLogout: () -> Unit
) {
    val parts = partViewModel.parts
    var selectedCategory by remember { mutableStateOf("Todos") }
    val visibleParts = remember(parts, selectedCategory) {
        if (selectedCategory == "Todos") parts
        else parts.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }
    val msg by partViewModel.partMessage
    val user = authViewModel.currentUser.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Peças (${user?.name ?: "deslogado"})")
                },
                actions = {
                    TextButton(onClick = onGoCart) { Text("Carrinho") }
                    TextButton(onClick = onLogout) { Text("Sair") }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddPart) {
                Text("+")
            }
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

            // ---------- ABA DE CATEGORIAS (filtro) ----------
            ScrollableTabRow(
                selectedTabIndex = partViewModel.categories.indexOf(selectedCategory)
                    .coerceAtLeast(0)
            ) {
                partViewModel.categories.forEach { cat ->
                    Tab(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = cat },
                        text = { Text(cat) }
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            // -------------------------------------------------

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(visibleParts) { part ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                "${part.name} - ${part.brand}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text("R$ ${((part.price * 100.0).roundToInt() / 100.0)}")
                            Spacer(Modifier.height(4.dp))
                            Text(part.description, style = MaterialTheme.typography.bodySmall)
                            Spacer(Modifier.height(8.dp))

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(onClick = { cartViewModel.addToCart(part) }) {
                                    Text("Adicionar ao carrinho")
                                }
                                OutlinedButton(onClick = { onEditPart(part.id) }) {
                                    Text("Editar")
                                }
                                TextButton(
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = MaterialTheme.colorScheme.error
                                    ),
                                    onClick = { partViewModel.deletePart(part.id) }
                                ) {
                                    Text("Excluir")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
