package ru.itmo.potatocoder228.androidlab2024.ui.theme

import kotlinx.serialization.Serializable

class Credentials {

    @Volatile
    private var login = ""

    @Volatile
    private var password = ""

    private val token_prefix = "Bearer "

    @Volatile
    private var token = token_prefix

    public fun setToken(token: String){
        this.token = token_prefix + token
    }

    public fun getToken():String{
        return token
    }

    public fun setLogin(login: String) {
        this.login = login
    }

    public fun setPassword(password: String) {
        this.password = password
    }

    public fun getLogin(): String {
        return login
    }

    public fun getPassword(): String {
        return password
    }

    public fun clear(){
        login = ""
        password = ""
        token = token_prefix
    }

}