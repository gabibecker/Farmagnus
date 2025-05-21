package model

data class Medicamento(
    val nome: String,
    val fabricante: String,
    val dosagem: String,
    val preco: String,
    val imagemUrl: String? = null
)