package com.example.finapp

data class Transacao(
    val id: Int = 0,
    val descricao: String,
    val valor: Double,
    val tipo: String // "Crédito" ou "Débito"
)