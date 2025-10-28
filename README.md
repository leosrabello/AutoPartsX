# AutoPartsX — Login e Register UI

Este projeto utiliza **Jetpack Compose** e **Material 3** para construir telas modernas e responsivas de Login e Registro para o aplicativo AutoPartsX.

## Estrutura das Telas

### LoginScreen e RegisterScreen
- Ambas as telas compartilham a mesma estrutura visual para manter consistência.
- A logo é exibida na parte superior, seguida por um `ElevatedCard` centralizado contendo os campos de entrada.
- O card utiliza `MaterialTheme.colorScheme.surface` para manter um estilo sóbrio e profissional.

### Componentes principais
- `Image`: exibe a logo do aplicativo.
- `ElevatedCard`: agrupa os campos de texto e botões, criando destaque visual.
- `OutlinedTextField`: usado para campos de entrada de texto (nome, email e senha).
- `Button`: executa ações de login ou cadastro.
- `TextButton`: navegação entre as telas de Login e Registro.
- `Spacer`: controla o espaçamento entre os elementos da UI.

### Estilo
- A logo foi dimensionada para 200dp.
- O card tem cantos arredondados e leve elevação.
- Os títulos "Entrar" e "Criar Conta" utilizam `FontWeight.Bold`.
- O layout utiliza `Column` e `Box` para alinhamento e centralização dos elementos.
- As cores seguem o tema global definido pelo `MaterialTheme`.

## Navegação
- `onLoginSuccess` e `onRegisterSuccess` são callbacks para navegação após login ou cadastro bem-sucedidos.
- `onGoRegister` e `onBackLogin` permitem alternar entre as telas.

## Organização
- `LoginScreen.kt` e `RegisterScreen.kt` estão no pacote `ui.screens`.
- A logo está armazenada em `res/drawable/logo_autopartsx.png`.
- O `AuthViewModel` gerencia a autenticação e mensagens de feedback.

## Requisitos
- Android Studio Flamingo ou superior
- Kotlin e Jetpack Compose configurados
- Material 3 habilitado no projeto

## Execução
- Rodar o app no emulador ou dispositivo físico.
- As telas podem ser acessadas conforme a navegação implementada no app.
