package com.example.farmagnus

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.farmagnus.databinding.ActivityMedicamentoDetalhadoBinding
import com.seupacote.viewmodel.CarrinhoViewModel
import model.Carrinho

class MedicamentoDetalhado : AppCompatActivity() {
    private lateinit var binding: ActivityMedicamentoDetalhadoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicamentoDetalhadoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val carrinhoViewModel: CarrinhoViewModel by viewModels()

        val medicamentoASalvar = Carrinho(
            id = "123",
            nome = "Paracetamol",
            fabricante = "EMS",
            dosagem = "500mg",
            preco = "R$ 12,99",
            descricao = "Analgésico",
            imagemUrl = null,
            quantidade = 1
        ) //utilizado esse até receber os dados da api

        binding.botaoAdicionarCarrinho.setOnClickListener {
            carrinhoViewModel.adicionarMedicamento(medicamentoASalvar)
        }
    }
}
