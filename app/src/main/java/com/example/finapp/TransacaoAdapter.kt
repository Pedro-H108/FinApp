package com.example.finapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat

class TransacaoAdapter(
    private val context: Context,
    private val lista: List<Transacao>
) : BaseAdapter() {

    override fun getCount(): Int = lista.size
    override fun getItem(position: Int): Any = lista[position]
    override fun getItemId(position: Int): Long = lista[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_transacao, parent, false)

        val transacao = lista[position]

        val imagem = view.findViewById<ImageView>(R.id.imageTipo)
        val descricao = view.findViewById<TextView>(R.id.textDescricao)
        val valor = view.findViewById<TextView>(R.id.textValor)

        descricao.text = transacao.descricao
        valor.text = "R$ %.2f".format(transacao.valor)

        if (transacao.tipo == "Crédito") {
            imagem.setImageResource(android.R.drawable.arrow_up_float)
            valor.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark))
        } else {
            imagem.setImageResource(android.R.drawable.arrow_down_float)
            valor.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
        }

        return view
    }
}
