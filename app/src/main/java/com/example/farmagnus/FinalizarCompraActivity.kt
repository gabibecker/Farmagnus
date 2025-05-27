package com.example.farmagnus

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.farmagnus.databinding.ActivityFinalizarCompraBinding

class FinalizarCompraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinalizarCompraBinding
    private var step = 0  // 0 = Revisar, 1 = Endereço, 2 = Pagamento

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalizarCompraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botaoEntrar.setOnClickListener {
            if (step < 2) {
                step++
                updateUi()
            } else {
                finalizarCompra()
            }
        }

        binding.botaoVoltar.setOnClickListener {
            if (step > 0) {
                step--
                updateUi()
            }
        }
        updateUi()
    }

    private fun updateUi() {
        binding.LinearLayoutRevisar.visibility    = if (step == 0) View.VISIBLE else View.GONE
        binding.linearLayoutendereco.visibility   = if (step == 1) View.VISIBLE else View.GONE
        binding.linearLayoutPagamento.visibility  = if (step == 2) View.VISIBLE else View.GONE
        binding.toggleGroup.check(
            when (step) {
                0 -> R.id.btnRevisar
                1 -> R.id.btnEndereco
                else -> R.id.btnPagamento
            }
        )
        binding.btnRevisar.isEnabled   = step >= 0
        binding.btnEndereco.isEnabled  = step >= 1
        binding.btnPagamento.isEnabled = step >= 2

        binding.botaoVoltar.visibility = if (step == 0) View.GONE else View.VISIBLE

        binding.botaoEntrar.text = if (step < 2) "Continuar" else "Finalizar Compra"
    }

    private fun finalizarCompra() {
        startActivity(Intent(this, PedidoConfirmado::class.java))
    }
}
