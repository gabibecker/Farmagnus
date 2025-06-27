package com.seupacote.viewmodel // Verifique se o pacote está correto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import model.Carrinho
import android.util.Log // Adicione esta importação para usar Log

class CarrinhoViewModel : ViewModel() {
    private val _itensCarrinho = MutableLiveData<MutableList<Carrinho>>(mutableListOf())
    val itensCarrinho: LiveData<MutableList<Carrinho>> get() = _itensCarrinho

    init {
        Log.d("DEBUG_CARRINHO", "CarrinhoViewModel: Instância criada (init block).")
    }

    /**
     * Adiciona um medicamento ao carrinho. Se o medicamento já existe (mesmo ID),
     * incrementa sua quantidade. Caso contrário, adiciona-o como um novo item.
     * @param novoMedicamento O objeto Carrinho a ser adicionado ou cuja quantidade será incrementada.
     */
    fun adicionarMedicamento(novoMedicamento: Carrinho) {
        Log.d("DEBUG_CARRINHO", "CarrinhoViewModel: 'adicionarMedicamento' chamado para ID=${novoMedicamento.id}, Nome=${novoMedicamento.nome}")

        val currentList = _itensCarrinho.value ?: mutableListOf()

        val existingItem = currentList.find { it.id == novoMedicamento.id }

        if (existingItem != null) {
            existingItem.quantidade++
            Log.d("DEBUG_CARRINHO", "CarrinhoViewModel: Item '${novoMedicamento.nome}' já existe. Nova quantidade: ${existingItem.quantidade}")
        } else {
            currentList.add(novoMedicamento.copy(quantidade = 1))
            Log.d("DEBUG_CARRINHO", "CarrinhoViewModel: Adicionado novo item '${novoMedicamento.nome}'. Quantidade inicial: 1")
        }

        _itensCarrinho.value = currentList
        // --- PONTO DE DEBUG 2: VERIFIQUE SE ESTE LOG APARECE E QUAL O TAMANHO DA LISTA. ---
        Log.d("DEBUG_CARRINHO", "CarrinhoViewModel: LiveData '_itensCarrinho' atualizado. Total de itens APÓS adição: ${currentList.size}")
    }

    /**
     * Remove um medicamento do carrinho.
     * @param item O objeto Carrinho a ser removido.
     */
    fun removerMedicamento(item: Carrinho) {
        Log.d("DEBUG_CARRINHO", "CarrinhoViewModel: 'removerMedicamento' chamado para ID=${item.id}, Nome=${item.nome}")
        val currentList = _itensCarrinho.value?.toMutableList() ?: mutableListOf()
        currentList.remove(item)
        _itensCarrinho.value = currentList
        Log.d("DEBUG_CARRINHO", "CarrinhoViewModel: Item '${item.nome}' removido. Total de itens APÓS remoção: ${currentList.size}")
    }

    /**
     * Atualiza a quantidade de um item específico no carrinho.
     * Esta função é chamada pelo PedidoAdapter quando a quantidade é alterada.
     * @param itemToUpdate O item Carrinho cuja quantidade será atualizada.
     * @param newQuantity A nova quantidade para o item.
     */
    fun atualizarQuantidade(itemToUpdate: Carrinho, newQuantity: Int) {
        Log.d("DEBUG_CARRINHO", "CarrinhoViewModel: 'atualizarQuantidade' chamado para ID=${itemToUpdate.id}, Nome=${itemToUpdate.nome} para Qtd: $newQuantity")
        val currentList = _itensCarrinho.value?.toMutableList() ?: mutableListOf()
        val index = currentList.indexOfFirst { it.id == itemToUpdate.id }
        if (index != -1) {
            currentList[index].quantidade = newQuantity
            _itensCarrinho.value = currentList
            Log.d("DEBUG_CARRINHO", "CarrinhoViewModel: Quantidade de '${itemToUpdate.nome}' atualizada para ${newQuantity}. Total de itens: ${currentList.size}")
        } else {
            Log.w("DEBUG_CARRINHO", "CarrinhoViewModel: Item '${itemToUpdate.nome}' não encontrado para atualização de quantidade.")
        }
    }
}
