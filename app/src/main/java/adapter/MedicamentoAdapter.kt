package adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.farmagnus.R
import com.example.farmagnus.MedicamentoDetalhado
import model.Medicamento

class MedicamentoAdapter(
    private val items: MutableList<Medicamento>,  // Lista de Medicamentos
    private val onClick: (Medicamento) -> Unit  // Click handler para o item
) : RecyclerView.Adapter<MedicamentoAdapter.MedViewHolder>() {

    inner class MedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val img = view.findViewById<ImageView>(R.id.imgMedicamento)
        private val nome = view.findViewById<TextView>(R.id.tvNome)
        private val fab = view.findViewById<TextView>(R.id.tvFabricante)
        private val dose = view.findViewById<TextView>(R.id.tvDosagem)
        private val preco = view.findViewById<TextView>(R.id.tvPreco)

        fun bind(m: Medicamento) {
            nome.text = m.nome
            fab.text = m.laboratorio
            dose.text = m.descricao
            preco.text = m.preco

            // Usando Glide para carregar a imagem (se disponível)
            m.img?.let {
                Glide.with(img.context)
                    .load(it)
                    .placeholder(R.color.white)  // Placeholder enquanto a imagem carrega
                    .into(img)
            }

            // Ao clicar, envia os dados do medicamento para a tela de detalhes
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, MedicamentoDetalhado::class.java)
                intent.putExtra("ID_MED", m.id)  // Passa o ID do medicamento
                intent.putExtra("NOME", m.nome)  // Passa o nome do medicamento
                intent.putExtra("FABRICANTE", m.laboratorio)  // Passa o fabricante
                intent.putExtra("DOSAGEM", m.descricao)  // Passa a descrição
                intent.putExtra("PRECO", m.preco)  // Passa o preço
                intent.putExtra("IMAGEM", m.img)  // Passa a URL da imagem
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medicamento, parent, false)
        return MedViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedViewHolder, position: Int) {
        holder.bind(items[position])  // Passa o medicamento para o view holder
    }

    override fun getItemCount(): Int = items.size  // Retorna o número de itens na lista
}
