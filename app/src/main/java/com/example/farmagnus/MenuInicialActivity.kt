package com.example.farmagnus

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.farmagnus.databinding.ActivityMenuInicialBinding
import model.Carrinho
import model.Medicamento
import adapter.MedicamentoAdapter
import adapter.PedidoAdapter

class MenuInicialActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMenuInicialBinding
    private lateinit var adapter: PedidoAdapter

    // Lista estática do carrinho
    private val itensCarrinho: MutableList<Carrinho> = mutableListOf(
        Carrinho("1", "Paracetamol", "EMS", "500mg – 20 comprimidos", "R$12,00", "dor de cabeça", "laborat", 1),
        Carrinho("2", "Ibuprofeno", "EMS", "400mg – 20 comprimidos", "R$15,00", "dor muscular", "laborat", 1)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuInicialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Carregar dados do SharedPreferences para o perfil
        carregarDadosPerfil()

        // Configura o RecyclerView do carrinho
        adapter = PedidoAdapter(
            itensCarrinho,
            onDeleteClick = { position ->
                // Remove o item do carrinho
                itensCarrinho.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, itensCarrinho.size)
                atualizarCarrinho() // Atualiza o total
            },
            onQuantityChanged = { position, newQuantity ->
                // Atualiza a quantidade do item
                itensCarrinho[position].quantidade = newQuantity
                adapter.notifyItemChanged(position)
                atualizarCarrinho() // Atualiza o total
            },
            onItemClick = { position ->
                // Navega para a tela de detalhes do medicamento
                val intent = Intent(this, MedicamentoDetalhado::class.java)
                intent.putExtra("ID_MED", itensCarrinho[position].id)
                startActivity(intent)
            }
        )

        binding.recyclerView.layoutManager = GridLayoutManager(this, 1) // Alteração: 1 coluna
        binding.recyclerView.adapter = adapter

        // Chama a função de configuração do menu de navegação
        config()

        // Exemplo de como configurar o botão de finalizar compra
        binding.botaoFinalizarCompra.setOnClickListener {
            val intent = Intent(this, FinalizarCompraActivity::class.java)
            startActivity(intent)
        }

        // Exibe a tela inicial e oculta o carrinho e perfil
        binding.LinearLayoutInicio.visibility = View.VISIBLE
        binding.LinearLayoutCarrinho.visibility = View.GONE
        binding.LinearLayoutPerfil.visibility = View.GONE

        // Carrega a lista de medicamentos
        menuinicial()

        // Atualiza a UI do carrinho
        atualizarCarrinho()
    }

    private fun config() {
        binding.navigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    binding.LinearLayoutInicio.visibility = View.VISIBLE
                    binding.LinearLayoutCarrinho.visibility = View.GONE
                    binding.LinearLayoutPerfil.visibility = View.GONE
                    menuinicial()
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
    }

    private fun menuinicial() {
        try {
            val listaMedicamentos = mutableListOf(
                Medicamento("1", "Paracetamol", "EMS", "500mg – 20 comprimidos", "R$12,00", "dor de cabeça", "laborat", ""),
                Medicamento("2", "Ibuprofeno", "EMS", "400mg – 20 comprimidos", "R$15,00", "dor muscular", "laborat", ""),
            )

            binding.rvMedicamentos.layoutManager = GridLayoutManager(this@MenuInicialActivity, 2) // 2 colunas
            binding.rvMedicamentos.adapter = MedicamentoAdapter(listaMedicamentos) { med ->
                // Adiciona o medicamento ao carrinho
                val medicamentoCarrinho = Carrinho(
                    id = med.id,
                    nome = med.nome,
                    fabricante = med.laboratorio,
                    dosagem = med.descricao,
                    preco = med.preco,
                    descricao = "Medicamento",
                    imagemUrl = med.img,
                    quantidade = 1
                )
                // Adiciona o medicamento à lista do carrinho
                itensCarrinho.add(medicamentoCarrinho)
                adapter.notifyItemInserted(itensCarrinho.size - 1) // Notifica a inserção do item
                atualizarCarrinho() // Atualiza o total do carrinho
                Toast.makeText(this, "${med.nome} adicionado ao carrinho", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Erro ao carregar medicamentos", Toast.LENGTH_SHORT).show()
        }
    }

    // Função para atualizar a interface com os itens do carrinho
    private fun atualizarCarrinho() {
        // Calcula o valor total do carrinho
        val total = itensCarrinho.sumByDouble {
            it.preco.replace("R$", "").replace(",", ".").toDoubleOrNull() ?: 0.0
        }
        binding.valortotal.text = "Total: R$%.2f".format(total)
        adapter.notifyDataSetChanged() // Atualiza o RecyclerView com a lista de itens
    }

    private fun carregarDadosPerfil() {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)

        // Recupera os valores armazenados no SharedPreferences
        val nome = sharedPreferences.getString("nome", "Nome não disponível")
        val cpf = sharedPreferences.getString("cpf", "CPF não disponível")
        val email = sharedPreferences.getString("email", "E-mail não disponível")
        val telefone = sharedPreferences.getString("telefone", "Telefone não disponível")

        // Atualiza os campos EditText com os dados recuperados
        binding.campoNome.editText?.setText(nome ?: "")
        binding.campoCPF.editText?.setText(cpf ?: "")
        binding.campoEmail.editText?.setText(email ?: "")
        binding.campoTelefone.editText?.setText(telefone ?: "")
    }

}
