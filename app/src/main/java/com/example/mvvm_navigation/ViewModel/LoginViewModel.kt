package com.example.mvvm_navigation.ViewModel

import Modelo.Usuario
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

//    private val _email = MutableLiveData<String>() //Este pertenece a esta clase
//    val email : LiveData<String> = _email          //Atributo que usamos para la suscripción
//
//    private val _password = MutableLiveData<String>() //Este pertenece a esta clase
//    val password : LiveData<String> = _password          //Atributo que usamos para la suscripción
//
//    private val _check = MutableLiveData<Boolean>() //Este pertenece a esta clase
//    val estadoCheck : LiveData<Boolean> = _check         //Atributo que usamos para la suscripción


//    private val _isLoginEnable = MutableLiveData<Boolean>()
//    val isLoginEnable : LiveData<Boolean> = _isLoginEnable

    private val _usuarios = MutableLiveData<ArrayList<Usuario>>()
    val usuarios : LiveData<ArrayList<Usuario>> = _usuarios


    fun add(us:Usuario){
        //Esto es necesario porque solo con el add no se consigue un cambio de estado y no funciona el observe.
        val currentList = ArrayList(_usuarios.value ?: emptyList())
        currentList.add(us)
        _usuarios.value = currentList
    }

//    fun onEmailCambiado(email:String){
//        _email.value = email
//    }
//
//    fun onPassCambiado(pass:String){
//        _password.value = pass
//    }
//
//    fun onRegistroCambiado(email:String, pass:String){
//        _email.value = email
//        _password.value = pass
//        _isLoginEnable.value = enableLogin(email,pass)
//    }
//
//    fun onCheckCambiado(ch:Boolean){
//        _check.value = ch
//    }
//
//    fun enableLogin(email:String, pass:String):Boolean{
//        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && pass.length > 4
//    }
}