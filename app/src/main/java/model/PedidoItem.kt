package model

data class PedidoItem(
    val pedidoId: String,
    val medicamentoId: String,
    val quantidade: Int,
    val precoUnidade: Float
)
