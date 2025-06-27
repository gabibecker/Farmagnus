package model

data class Carrinho(
    val id: String,
    val nome: String,
    val fabricante: String,
    val dosagem: String,
    val preco: String,
    val descricao: String,
    val imagemUrl: String?, // Pode ser nula se não houver imagem
    var quantidade: Int // **CRUCIAL: DEVE SER 'var' para permitir alterações na quantidade**
)
