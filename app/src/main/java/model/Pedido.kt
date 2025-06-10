package model

data class Pedido(
    var id: String,
    var precoTotal: Float,
    var retirada: String,
    var formaPagamento: String,
    var dataPedido: String,
    var statusPedido: String,
    var endereco: String,
    var statusPagamento: String //o status do pagamento só é alterado quando a farmacia confirma pela web
    )