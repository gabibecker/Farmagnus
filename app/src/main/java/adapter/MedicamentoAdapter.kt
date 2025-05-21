package adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.farmagnus.R
import model.Medicamento

class MedicamentoAdapter(
    private val items: List<Medicamento>,
    private val onClick: (Medicamento) -> Unit
) : RecyclerView.Adapter<MedicamentoAdapter.MedViewHolder>() {

    inner class MedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val img = view.findViewById<ImageView>(R.id.imgMedicamento)
        private val nome = view.findViewById<TextView>(R.id.tvNome)
        private val fab = view.findViewById<TextView>(R.id.tvFabricante)
        private val dose = view.findViewById<TextView>(R.id.tvDosagem)
        private val preco = view.findViewById<TextView>(R.id.tvPreco)

        fun bind(m: Medicamento) {
            nome.text = m.nome
            fab.text = m.fabricante
            dose.text = m.dosagem
            preco.text = m.preco

            m.imagemUrl?.let {
                Glide.with(img.context)
                    .load(it)
                    .placeholder(R.color.white)
                    .into(img)
            }

            itemView.setOnClickListener { onClick(m) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medicamento, parent, false)
        return MedViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}