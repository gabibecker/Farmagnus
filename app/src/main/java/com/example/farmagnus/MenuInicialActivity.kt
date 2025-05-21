package com.example.farmagnus
import adapter.MedicamentoAdapter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.farmagnus.adapter.PedidoDetalhadoAdapter
import com.example.farmagnus.databinding.ActivityMenuInicialBinding
import model.Medicamento
import model.PedidoItem

class MenuInicialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuInicialBinding
    private lateinit var adapter: PedidoDetalhadoAdapter

    private val itensPedido = mutableListOf<PedidoItem>()
    private val dadosMenuInicial = listOf(
        Medicamento("Paracetamol", "EMS", "500mg – 20 comprimidos", "R$ 12,99"),
        Medicamento("Paracetamol", "EMS", "500mg – 20 comprimidos", "R$ 12,99"),
        Medicamento("Paracetamol", "EMS", "500mg – 20 comprimidos", "R$ 12,99"),
        Medicamento("Paracetamol", "EMS", "500mg – 20 comprimidos", "R$ 12,99"),
        Medicamento("Paracetamol", "EMS", "500mg – 20 comprimidos", "R$ 12,99"),
        Medicamento("Paracetamol", "EMS", "500mg – 20 comprimidos", "R$ 12,99"),
        Medicamento("Paracetamol", "EMS", "500mg – 20 comprimidos", "R$ 12,99"),
        Medicamento("Paracetamol", "EMS", "500mg – 20 comprimidos", "R$ 12,99"),
        Medicamento("Paracetamol", "EMS", "500mg – 20 comprimidos", "R$ 12,99")
    )

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

        binding.navigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    binding.LinearLayoutInicio.visibility = View.VISIBLE
                    binding.LinearLayoutCarrinho.visibility = View.GONE
                    binding.LinearLayoutPerfil.visibility = View.GONE
                    true
                }
                R.id.nav_cart -> {
                    binding.LinearLayoutInicio.visibility = View.GONE
                    binding.LinearLayoutCarrinho.visibility = View.VISIBLE
                    binding.LinearLayoutPerfil.visibility = View.GONE
                    true
                }
                R.id.nav_profile -> {
                    binding.LinearLayoutInicio.visibility = View.GONE
                    binding.LinearLayoutCarrinho.visibility = View.GONE
                    binding.LinearLayoutPerfil.visibility = View.VISIBLE
                    true
                }
                else -> false
            }
        }

        binding.rvMedicamentos.layoutManager = GridLayoutManager(this, 2)
        binding.rvMedicamentos.adapter = MedicamentoAdapter(dadosMenuInicial) { med ->
            Toast.makeText(this, "Clicou em ${med.nome}", Toast.LENGTH_SHORT).show()
        }

        itensPedido.addAll(listOf(
            PedidoItem(
                nomeMedicamento = "Paracetamol",
                cms = "EMS",
                dosagem = "500mg - 20 comprimidos",
                preco = "R$ 12,99",
                quantidade = 1,
                imagemUrl = "https://exemplo.com/image1.png"
            ),
            PedidoItem(
                nomeMedicamento = "Paracetamol",
                cms = "EMS",
                dosagem = "500mg - 20 comprimidos",
                preco = "R$ 12,99",
                quantidade = 1,
                imagemUrl = "https://exemplo.com/image2.png"
            ),
            PedidoItem(
                nomeMedicamento = "Paracetamol",
                cms = "EMS",
                dosagem = "500mg - 20 comprimidos",
                preco = "R$ 12,99",
                quantidade = 1,
                imagemUrl = "https://exemplo.com/image3.png"
            ),
            PedidoItem(
                nomeMedicamento = "Paracetamol",
                cms = "EMS",
                dosagem = "500mg - 20 comprimidos",
                preco = "R$ 12,99",
                quantidade = 1,
                imagemUrl = "https://exemplo.com/image3.png"
            ),
            PedidoItem(
                nomeMedicamento = "Paracetamol",
                cms = "EMS",
                dosagem = "500mg - 20 comprimidos",
                preco = "R$ 12,99",
                quantidade = 1,
                imagemUrl = "https://exemplo.com/image3.png"
            ),
            PedidoItem(
                nomeMedicamento = "Paracetamol",
                cms = "EMS",
                dosagem = "500mg - 20 comprimidos",
                preco = "R$ 12,99",
                quantidade = 1,
                imagemUrl = "https://exemplo.com/image3.png"
            ),
            PedidoItem(
                nomeMedicamento = "Paracetamol",
                cms = "EMS",
                dosagem = "500mg - 20 comprimidos",
                preco = "R$ 12,99",
                quantidade = 1,
                imagemUrl = "https://exemplo.com/image3.png"
            ),
            PedidoItem(
                nomeMedicamento = "Paracetamol",
                cms = "EMS",
                dosagem = "500mg - 20 comprimidos",
                preco = "R$ 12,99",
                quantidade = 1,
                imagemUrl = "https://exemplo.com/image3.png"
            ),
            PedidoItem(
                nomeMedicamento = "Paracetamol",
                cms = "EMS",
                dosagem = "500mg - 20 comprimidos",
                preco = "R$ 12,99",
                quantidade = 1,
                imagemUrl = "https://exemplo.com/image3.png"
            ),
            PedidoItem(
                nomeMedicamento = "Paracetamol",
                cms = "EMS",
                dosagem = "500mg - 20 comprimidos",
                preco = "R$ 12,99",
                quantidade = 1,
                imagemUrl = "https://exemplo.com/image3.png"
            )

        ))


        adapter = PedidoDetalhadoAdapter(itensPedido,
            onDeleteClick = { position ->
                itensPedido.removeAt(position)
                adapter.notifyItemRemoved(position)
            },
            onQuantityChanged = { position, newQuantity ->
                // ARRUMAR DEPOIS
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter


        binding.LinearLayoutInicio.visibility = View.VISIBLE
        binding.LinearLayoutCarrinho.visibility = View.GONE
        binding.LinearLayoutPerfil.visibility = View.GONE
    }
}
