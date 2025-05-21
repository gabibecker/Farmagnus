package model

data class PedidoItem(
    val nomeMedicamento: String,
    val cms: String,
    val dosagem: String,
    val preco: String,
    var quantidade: Int,
    val imagemUrl: String
)