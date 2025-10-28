package com.autopartsx.autopartsapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.autopartsx.autopartsapp.viewmodel.PartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartFormScreen(
    partId: Int,
    partViewModel: PartViewModel,
    onSaved: () -> Unit
) {
    val editing = partId != -1
    val existing = if (editing) partViewModel.getPartById(partId) else null

    var name by remember { mutableStateOf(existing?.name ?: "") }
    var brand by remember { mutableStateOf(existing?.brand ?: "") }
    var priceTxt by remember { mutableStateOf(existing?.price?.toString() ?: "") }
    var desc by remember { mutableStateOf(existing?.description ?: "") }

    val msg by partViewModel.partMessage

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (editing) "Editar Peça" else "Nova Peça") }
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
                    Text(
                        msg ?: "",
                        color = MaterialTheme.colorScheme.primary
                    )
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

                // campo preço agora é só texto comum
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
                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        val priceDouble = priceTxt.toDoubleOrNull()
                        val ok = if (editing) {
                            partViewModel.updatePart(
                                id = partId,
                                name = name,
                                brand = brand,
                                price = priceDouble,
                                desc = desc
                            )
                        } else {
                            partViewModel.addPart(
                                name = name,
                                brand = brand,
                                price = priceDouble,
                                desc = desc
                            )
                        }
                        if (ok) {
                            onSaved()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Salvar")
                }
            }
        }
    }
}
