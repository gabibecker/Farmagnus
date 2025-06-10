package com.seupacote.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import model.Carrinho
import model.Medicamento

class CarrinhoViewModel : ViewModel() {

    private val _itensCarrinho = MutableLiveData<MutableList<Carrinho>>(mutableListOf())

    fun adicionarMedicamento(c: Carrinho) {
        val listaAtual = _itensCarrinho.value ?: mutableListOf()
        listaAtual.add(c)
        _itensCarrinho.value = listaAtual
    }
}
