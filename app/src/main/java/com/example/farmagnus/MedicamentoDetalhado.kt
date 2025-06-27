package com.example.farmagnus

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.farmagnus.databinding.ActivityMedicamentoDetalhadoBinding
import com.seupacote.viewmodel.CarrinhoViewModel // VERIFIQUE ESTE PACOTE NOVAMENTE
import model.Carrinho
import android.util.Log // Adicione esta importação para usar Log

class MedicamentoDetalhado : AppCompatActivity() {
    private lateinit var binding: ActivityMedicamentoDetalhadoBinding

    // ViewModel responsável por manipular o carrinho
    private val carrinhoViewModel: CarrinhoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicamentoDetalhadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("DEBUG_CARRINHO", "MedicamentoDetalhado: onCreate chamado.")

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recupera os dados passados pela Intent
        val id = intent.getStringExtra("ID_MED") ?: ""
        val nome = intent.getStringExtra("NOME") ?: ""
        val fabricante = intent.getStringExtra("FABRICANTE") ?: ""
        val descricao = intent.getStringExtra("DOSAGEM") ?: ""
        val preco = intent.getStringExtra("PRECO") ?: ""
        val imagemUrl = intent.getStringExtra("IMAGEM")

        Log.d("DEBUG_CARRINHO", "MedicamentoDetalhado: Dados recebidos da Intent: ID=$id, Nome=$nome")

        // Exibe os dados na interface
        binding.textNomeMedicamento.text = nome
        binding.textLaboratorio.text = fabricante
        binding.textQuantidade.text = descricao
        binding.textValor.text = preco

        binding.botaoFinalizarCompra.setOnClickListener {
            // Cria o objeto Carrinho AQUI
            val medicamentoASalvar = Carrinho(
                id = id,
                nome = nome,
                fabricante = fabricante,
                dosagem = descricao,
                preco = preco,
                descricao = "Analgésico",
                imagemUrl = imagemUrl,
                quantidade = 1 // Sempre inicia com 1 ao adicionar pela primeira vez
            )

            // --- PONTO DE DEBUG 1: VERIFIQUE SE ESTE LOG APARECE E QUAIS SÃO OS DADOS. ---
            Log.d("DEBUG_CARRINHO", "MedicamentoDetalhado: Botão 'Adicionar ao Carrinho' clicado.")
            Log.d("DEBUG_CARRINHO", "  Item a ser adicionado: ID=${medicamentoASalvar.id}, Nome=${medicamentoASalvar.nome}, Preço=${medicamentoASalvar.preco}, Qtd=${medicamentoASalvar.quantidade}")


            // Adiciona o medicamento ao carrinho usando o ViewModel
            carrinhoViewModel.adicionarMedicamento(medicamentoASalvar)

            Toast.makeText(this, "${medicamentoASalvar.nome} adicionado ao carrinho!", Toast.LENGTH_SHORT).show()
        }
    }
}
