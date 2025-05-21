package com.example.farmagnus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.farmagnus.R
import model.PedidoItem

class PedidoDetalhadoAdapter(
    private val items: MutableList<PedidoItem>,
    private val onDeleteClick: (position: Int) -> Unit,
    private val onQuantityChanged: (position: Int, newQuantity: Int) -> Unit
) : RecyclerView.Adapter<PedidoDetalhadoAdapter.PedidoViewHolder>() {

    inner class PedidoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagemMedicamento: ImageButton = itemView.findViewById(R.id.ImagemMedicamento)
        val nomeMedicamento: TextView = itemView.findViewById(R.id.nomeMedicamento)
        val cms: TextView = itemView.findViewById(R.id.cms)
        val descricao: TextView = itemView.findViewById(R.id.dosagem)
        val preco: TextView = itemView.findViewById(R.id.preco)
        val quantidade: TextView = itemView.findViewById(R.id.quantidade)
        val botaoMais: ImageButton = itemView.findViewById(R.id.botaoMais)
        val botaoMenos: ImageButton = itemView.findViewById(R.id.botaoMenos)
        val lixeira: ImageButton = itemView.findViewById(R.id.lixeira)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pedido_detalhado, parent, false)
        return PedidoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val item = items[position]

        Glide.with(holder.imagemMedicamento.context)
            .load(item.imagemUrl)
            .into(holder.imagemMedicamento)

        holder.nomeMedicamento.text = item.nomeMedicamento
        holder.cms.text = item.cms
        holder.descricao.text = item.dosagem
        holder.preco.text = item.preco
        holder.quantidade.text = item.quantidade.toString()

        holder.lixeira.setOnClickListener {
            onDeleteClick(position)
        }

        holder.botaoMais.setOnClickListener {
            item.quantidade++
            notifyItemChanged(position)
            onQuantityChanged(position, item.quantidade)
        }

        holder.botaoMenos.setOnClickListener {
            if (item.quantidade > 1) {
                item.quantidade--
                notifyItemChanged(position)
                onQuantityChanged(position, item.quantidade)
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
