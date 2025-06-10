package com.example.farmagnus

import adapter.MedicamentoAdapter
import adapter.PedidoAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.farmagnus.databinding.ActivityMenuInicialBinding
import model.Carrinho
import model.Medicamento

class MenuInicialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuInicialBinding
    private lateinit var adapter: PedidoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMenuInicialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        config()

        binding.botaoFinalizarCompra.setOnClickListener {
            val intent = Intent(this, FinalizarCompraActivity::class.java)
            startActivity(intent)
        }

        binding.LinearLayoutInicio.visibility = View.VISIBLE
        binding.LinearLayoutCarrinho.visibility = View.GONE
        binding.LinearLayoutPerfil.visibility = View.GONE

        menuinicial()
    }

    private fun config() {
        binding.navigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    //menu inicial
                    binding.LinearLayoutInicio.visibility = View.VISIBLE
                    binding.LinearLayoutCarrinho.visibility = View.GONE
                    binding.LinearLayoutPerfil.visibility = View.GONE
                    menuinicial()
                    true
                }
                R.id.nav_cart -> {
                    //carrinho
                    binding.LinearLayoutInicio.visibility = View.GONE
                    binding.LinearLayoutCarrinho.visibility = View.VISIBLE
                    binding.LinearLayoutPerfil.visibility = View.GONE
                    carrinho()
                    true
                }
                R.id.nav_profile -> {
                    //perfil
                    binding.LinearLayoutInicio.visibility = View.GONE
                    binding.LinearLayoutCarrinho.visibility = View.GONE
                    binding.LinearLayoutPerfil.visibility = View.VISIBLE
                    true
                }
                else -> false
            }
        }
    }

    private fun menuinicial() {
        val listaMedicamentos = listOf(
            Medicamento(
                "1",
                "Paracetamol",
                "EMS",
                "500mg – 20 comprimidos",
                "R$ 12,99",
                "dor de cabeca"
            )
        ) //recebe da api a lista depois

        binding.rvMedicamentos.layoutManager = GridLayoutManager(this, 2)
        binding.rvMedicamentos.adapter = MedicamentoAdapter(listaMedicamentos) { med ->
            val intent = Intent(this, MedicamentoDetalhado::class.java)
            intent.putExtra("ID_MED", med.id)
            startActivity(intent)
        }
    }

    private fun carrinho() {
        val itensPedido = mutableListOf(
            Carrinho(
                "1",
                "Paracetamol",
                "EMS",
                "500mg – 20 comprimidos",
                "R$12,00",
                "dor de cabeca",
                "imagem",
                1
            )
        ) //adicionar da api a lista depois

        adapter = PedidoAdapter(
            itensPedido,
            onDeleteClick = { position ->
                itensPedido.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, itensPedido.size)
            },
            onQuantityChanged = { position, newQuantity ->
                itensPedido[position].quantidade = newQuantity
                adapter.notifyItemChanged(position)
            },
            onItemClick = { position ->
                val intent = Intent(this, MedicamentoDetalhado::class.java)
                intent.putExtra("ID_MED", itensPedido[position].id)
                startActivity(intent)
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}
