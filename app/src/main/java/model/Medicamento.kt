package model

data class Medicamento(
    val id: String,
    val nome: String,
    val código_de_barras:String,
    val preco: String,
    val laboratorio: String,
    val apresentacao: String,
    val descricao:String,
    val img: String
)