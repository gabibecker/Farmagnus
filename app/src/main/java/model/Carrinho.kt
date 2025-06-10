package model

data class Carrinho(
    val id: String,
    val nome: String,
    val fabricante: String,
    val dosagem: String,
    val preco: String,
    val descricao:String,
    val imagemUrl: String? = null,
    var quantidade: Int
)
