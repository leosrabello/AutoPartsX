package com.autopartsx.autopartsapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.autopartsx.autopartsapp.viewmodel.PartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartFormScreen(
    partId: Int,
    partViewModel: PartViewModel,
    onSaved: () -> Unit,         // também será usado para fechar após excluir
) {
    val editing = partId != -1
    val existing = if (editing) partViewModel.getPartById(partId) else null

    var name by remember(existing) { mutableStateOf(existing?.name ?: "") }
    var brand by remember(existing) { mutableStateOf(existing?.brand ?: "") }
    var priceTxt by remember(existing) { mutableStateOf(existing?.price?.toString() ?: "") }
    var desc by remember(existing) { mutableStateOf(existing?.description ?: "") }
    var category by remember(existing) { mutableStateOf(existing?.category ?: "") }

    val msg by partViewModel.partMessage
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (editing) "Editar Peça" else "Nova Peça") },
                actions = {
                    if (editing) {
                        TextButton(
                            onClick = { showDeleteDialog = true },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            )
                        ) { Text("Excluir") }
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (msg != null) {
                    Text(msg ?: "", color = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.height(8.dp))
                }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome da peça") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = brand,
                    onValueChange = { brand = it },
                    label = { Text("Marca") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = priceTxt,
                    onValueChange = { priceTxt = it },
                    label = { Text("Preço") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("Descrição") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Categoria (ex.: Motor, Freios)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        val priceDouble = priceTxt.replace(',', '.').toDoubleOrNull()
                        val ok = if (editing) {
                            partViewModel.updatePart(
                                id = partId,
                                name = name,
                                brand = brand,
                                price = priceDouble,
                                desc = desc,
                                category = category.takeIf { it.isNotBlank() }
                            )
                        } else {
                            partViewModel.addPart(
                                name = name,
                                brand = brand,
                                price = priceDouble,
                                desc = desc,
                                category = if (category.isBlank()) "Outros" else category
                            )
                        }
                        if (ok) onSaved()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Salvar") }
            }
        }
    }

    // --- Confirmação de exclusão ---
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Excluir peça") },
            text = { Text("Tem certeza que deseja excluir esta peça? Essa ação não pode ser desfeita.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Se seu deletePart retornar Boolean, você pode checar antes de fechar
                        partViewModel.deletePart(partId)
                        showDeleteDialog = false
                        onSaved() // volta para a tela anterior
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) { Text("Excluir") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Cancelar") }
            }
        )
    }
}
