
* * *

**Navigation, ViewModel y Formularios en Jetpack Compose**
========================================================================

**1️⃣ Navigation (Navegación entre pantallas)**
-----------------------------------------------

En Jetpack Compose se utiliza `NavHost` y `composable` para definir las rutas y pantallas de la app:

```kotlin
val navController = rememberNavController()

NavHost(navController = navController, startDestination = Rutas.Login) {
    composable(Rutas.Login) {
        Login(navController, lvm)
    }
    composable(Rutas.Pantalla2) {
        Pant2(navController, lvm)
    }
}
```

*   `NavHost`: Contenedor principal de la navegación.
*   `composable(route)`: Define cada pantalla a la que se puede navegar.
*   `navController.navigate(Ruta)`: Permite cambiar de pantalla.
*   Se pueden pasar **objetos o datos** entre pantallas usando `ViewModel` compartido.

💡 **Tip:** Mantener un solo `NavController` en la raíz de la app y pasarlo a los composables que lo necesiten.

* * *

**2️⃣ ViewModel y LiveData**
----------------------------

`ViewModel` se utiliza para **almacenar y gestionar datos de UI** de forma persistente mientras la actividad o fragmento exista.

```kotlin
class LoginViewModel : ViewModel() {
    private val _usuarios = MutableLiveData<ArrayList<Usuario>>()
    val usuarios: LiveData<ArrayList<Usuario>> = _usuarios

    fun add(usuario: Usuario) {
        val currentList = ArrayList(_usuarios.value ?: emptyList())
        currentList.add(usuario)
        _usuarios.value = currentList
    }
}
```

*   `_usuarios` es **privado** para evitar que se modifique directamente desde la UI.
*   `usuarios` es **LiveData pública**, que la UI puede observar.
*   Cada vez que actualizas `_usuarios.value`, la UI que observa este `LiveData` se refresca automáticamente.

💡 **Tip:** Evitar usar `var` directamente en la UI si los datos deberían persistir o compartirse entre pantallas. Usar `ViewModel`.

* * *

**3️⃣ Formularios en Jetpack Compose**
--------------------------------------

Para crear formularios con `TextField`, `Checkbox` y botones:

```kotlin
@Composable
fun Body(navController: NavHostController, loginViewModel: LoginViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var estadoCheck by remember { mutableStateOf(false) }
    
    val usuario = Usuario(email, password, estadoCheck)

    Column {
        EmailSencillo(email) { email = it }
        PasswordSencillo(password) { password = it }
        MiCheckBoxTexto("Recuérdame", estadoCheck) { estadoCheck = it }
        LoginButton2(loginViewModel, navController, usuario)
    }
}
```

*   **TextField**: Para capturar texto.
*   **Checkbox**: Para opciones booleanas.
*   **Button**: Para enviar datos.

### **TextField con manejo de estado**

```kotlin
@Composable
fun EmailSencillo(value: String, onValueChanged: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        placeholder = { Text("Email") }
    )
}
```

*   `value`: valor actual del TextField.
*   `onValueChange`: función que actualiza el estado cuando cambia el texto.
*   Se recomienda **delegar el estado a ViewModel** si quieres que los datos sobrevivan cambios de configuración o se compartan entre pantallas.

* * *

**4️⃣ Recuperar datos y mostrarlos en LazyColumn**
--------------------------------------------------

Para listar datos que vienen del `ViewModel`:

```kotlin
@Composable
fun Listado(loginViewModel: LoginViewModel) {
    val usuarios by loginViewModel.usuarios.observeAsState(emptyList())

    LazyColumn {
        items(usuarios.size) { index ->
            val user = usuarios[index]
            Text("${user.email} - ${user.estadoCheck}")
        }
    }
}
```

*   `observeAsState()`: Convierte `LiveData` en estado Compose para que la UI se actualice automáticamente.
*   `LazyColumn` es un **recycler view optimizado en Compose**.
*   Cada elemento se renderiza dentro del bloque `items`.

💡 **Tip:** Siempre usar un estado inicial vacío para evitar errores con `null` al observar LiveData.

* * *

**5️⃣ Flujo general del ejemplo**
-----------------------------------

1.  Usuario abre la app → `MainActivity`.
2.  Pantalla inicial → **Login**.
3.  Usuario ingresa **email, password y checkbox**.
4.  Al presionar **LoginButton2**, se crea un objeto `Usuario` y se **agrega al ViewModel**.
5.  Navega a **Pantalla 2** usando `navController.navigate(Rutas.Pantalla2)`.
6.  Pantalla 2 observa el LiveData del ViewModel y muestra la lista en **LazyColumn**.
7.  Si se agrega un nuevo usuario, la lista se actualiza automáticamente.

* * *

**6️⃣ Buenas prácticas y recomendaciones**
------------------------------------------

*   Separar lógica de UI y datos usando **ViewModel**.
*   Usar `LiveData` o `StateFlow` para comunicar datos desde ViewModel a Compose.
*   Evitar manejar lógica de negocio directamente en composables.
*   Para formularios, delegar el estado a **ViewModel** si se necesita persistencia.
*   Para navigation, mantener rutas definidas en un objeto `Rutas` para no usar strings "hardcodeados".

* * *

