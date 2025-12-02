

import Modelo.Usuario
import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lint.kotlin.metadata.Visibility
import androidx.navigation.NavHostController
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.ViewModel.LoginViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun Login(navController: NavHostController, loginViewModel: LoginViewModel) {
    Column(
//        Modifier
//            .fillMaxSize()
//            .padding(8.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Header()
        Body(navController, loginViewModel)
        Listado(loginViewModel)
        Footer()
    }
}

@Composable
fun ImageLogo() {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "logo",
            modifier = Modifier.size(180.dp).fillMaxSize()
        )
    }
}

@SuppressLint("ContextCastToActivity")
@Composable
fun Header() {
    Column( modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End ) {
        val activity = LocalContext.current as Activity
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "close app",
            modifier = Modifier.clickable { activity.finish() })
    }
}

@SuppressLint("ContextCastToActivity")
@Composable
//fun CerrarSesion(mod:Modifier){
fun CerrarSesion(){
    val activity = LocalContext.current as Activity
    Button(modifier = Modifier.fillMaxWidth(), onClick = { activity.finish() }) {
        Text(text = "Cerrar aplicación")
    }
}


@Composable
fun Footer() {
    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalDivider(
            Modifier
                .background(Color(0xFFF9F9F9))
                .height(1.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(24.dp))
    }
}

@Composable
fun Body(navController: NavHostController, loginViewModel: LoginViewModel) {
    val almacenPersonas by loginViewModel.usuarios.observeAsState(initial = ArrayList<Usuario>())
    val context = LocalContext.current

    //Opción A)
    //val email:String by loginViewModel.email.observeAsState(initial = "")
    //val password:String by loginViewModel.password.observeAsState(initial = "")
    //val estadoCheck:Boolean by loginViewModel.estadoCheck.observeAsState(initial = false)
    //val isLoginEnable: Boolean by loginViewModel.isLoginEnable.observeAsState(initial = false)

    //Opción B)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var estadoCheck by remember { mutableStateOf(false) }
    var us by remember { mutableStateOf(Usuario("", "", false)) }
    val isLoginEnable by remember { mutableStateOf(false) }


    Column() {
        ImageLogo()
        Spacer(modifier = Modifier.size(12.dp))
        email = EmailSencillo()
        Spacer(modifier = Modifier.size(4.dp))
        password = PasswordSencillo()
        Spacer(modifier = Modifier.size(16.dp))
        estadoCheck = MiCheckBoxTexto("Recuérdame", estadoCheck, true)
        Spacer(modifier = Modifier.size(4.dp))
        us = Usuario(email, password, estadoCheck)
        Log.e("Fernando",us.toString())
        LoginButton2(loginViewModel, navController, us)
        Spacer(modifier = Modifier.size(16.dp))
        LoginDivider()
        CerrarSesion()
    }
}

@Composable
fun PasswordSencillo():String {
    var showPassword by remember { mutableStateOf(value = false) }
    var password by remember { mutableStateOf("") }

    TextField(
        value = password,
        onValueChange = { password = it },
        label = {Text("Introduce una clave")},
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFFAFAFA)),
        placeholder = { Text("Password") },
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFFB2B2B2),
            unfocusedTextColor = Color(0xFFB2B2B2),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            if (showPassword) {
                IconButton(onClick = { showPassword = false }) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = "hide_password"
                    )
                }
            } else {
                IconButton(
                    onClick = { showPassword = true }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = "hide_password"
                    )
                }
            }
        },
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )
    Log.w("Fernando","Final del Password Sencillo: $password")
    return password
}

@Composable
//fun EmailSencillo(loginViewModel: LoginViewModel):String {
fun EmailSencillo():String {
    //val email:String by loginViewModel.email.observeAsState(initial = "")
    var emailTxt by remember { mutableStateOf("") }
    TextField(
        value = emailTxt,
        onValueChange = {
            //loginViewModel.onEmailCambiado(it)
            emailTxt = it
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFFAFAFA)),
        placeholder = { Text(text = "Email") },
        label = {Text("Introduce un correo")},
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFFB2B2B2),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        )
    )
    Log.w("Fernando","Final del EmailSencillo: $emailTxt")
   return emailTxt
}


@Composable
fun MiCheckBoxTexto(texto: String, vainicial:Boolean, habilitado:Boolean):Boolean {
    var estado by remember { mutableStateOf(vainicial) }
    var estadoEnable by remember { mutableStateOf(habilitado) }
    Row() {
        Checkbox(
            checked = estado,
            enabled = estadoEnable,
            onCheckedChange = { estado = !estado })
//        Spacer(modifier = Modifier.width(8.dp).height(2.dp))
        Text(texto, modifier = Modifier.padding(vertical = 12.dp))
    }
    return estado
}


@Composable
fun LoginDivider() {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        HorizontalDivider(
            Modifier
                .background(Color(0xFFF9F9F9))
                .height(1.dp)
                .weight(1f)
        )
        Text(
            text = "Opcional",
            modifier = Modifier.padding(horizontal = 18.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB5B5B5)
        )
        HorizontalDivider(
            Modifier
                .background(Color(0xFFF9F9F9))
                .height(1.dp)
                .weight(1f)
        )
    }
}
//
//@Composable
//fun LoginButton(onClickActionIzaskub: () -> Unit) {
//
//    Button(
//        onClick = {
//            onClickActionIzaskub()
//        },
//        enabled = true,
//        modifier = Modifier.fillMaxWidth(),
//        colors = ButtonDefaults.buttonColors(
//            contentColor = Color.White,
//            disabledContentColor = Color.White
//        )
//    ) {
//        Text(text = "Iniciar sesión")
//    }
//}


@Composable
fun LoginButton2(loginViewModel: LoginViewModel, navController: NavHostController, us:Usuario) {
    //Opción A)
//    val email:String by loginViewModel.email.observeAsState(initial = "")
//    val password:String by loginViewModel.password.observeAsState(initial = "")
//    val estadoCheck:Boolean by loginViewModel.estadoCheck.observeAsState(initial = false)

    //val almacenPersonas by loginViewModel.usuarios.observeAsState(initial = ArrayList<Usuario>())

    Button(
        onClick = {
            loginViewModel.add(us)
            navController.navigate(Rutas.Pantalla2)
        },
        enabled = true,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            disabledContentColor = Color.White
        )
    ) {
        Text(text = "Almacenar e ir a ventana 2")
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Password(password: String, onTextChanged: (String) -> Unit) {
    var showPassword by remember { mutableStateOf(value = false) }
    TextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        label = {Text("Introduce una clave")},
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFFAFAFA)),
        placeholder = { Text("Password") },
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFFB2B2B2),
            unfocusedTextColor = Color(0xFFB2B2B2),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            if (showPassword) {
                IconButton(onClick = { showPassword = false }) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = "hide_password"
                    )
                }
            } else {
                IconButton(
                    onClick = { showPassword = true }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = "hide_password"
                    )
                }
            }
        },
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )
}


@Composable
fun Email(email: String, onTextChanged: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = {
            onTextChanged(it)
                        },
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFFAFAFA)),
        placeholder = { Text(text = "Email") },
        label = {Text("Introduce un correo")},
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFFB2B2B2),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        )
    )
}

//@Composable
//fun MiCheckBoxObjeto(info: CheckInfo, onCheckChanged: (Boolean) -> Unit) {
//    Row() {
//        Checkbox(
//            checked = info.selected,
//            enabled = info.enable,
//            onCheckedChange = {
//                info.onCheckedChange(!info.selected)
//                onCheckChanged(it)
//            })
//        Text(info.title, modifier = Modifier.padding(vertical = 12.dp))
//    }
//}

@Composable
fun Listado(loginViewModel: LoginViewModel) {
    val almacenPersonas by loginViewModel.usuarios.observeAsState(ArrayList<Usuario>())
    Column(modifier = Modifier
        .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ){
//        Text(text = "Hola esto es la pantalla 2")
        //Text(text = "Valor del arrayList: ${almacenPersonas.toString()}")
        LazyColumn {
            items(almacenPersonas.size) {
                Text(text = almacenPersonas[it].email + " - " + almacenPersonas[it].estadoCheck.toString() + "")
            }
        }
    }
}

