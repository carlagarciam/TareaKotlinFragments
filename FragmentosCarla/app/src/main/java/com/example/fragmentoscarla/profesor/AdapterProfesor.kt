package com.example.fragmentoscarla.profesor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmentoscarla.R
import com.example.fragmentoscarla.model.profesor


class AdapterProfesor(var items: ArrayList<profesor>, private val listener: (profesor) -> Unit) :
    RecyclerView.Adapter<AdapterProfesor.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.listar_datos, parent, false)
        val viewHolder = ViewHolder(v)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position])
        holder.itemView.setOnClickListener { listener(items[position]) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(cliente: profesor) {
            val textViewNombre = itemView.findViewById<TextView>(R.id.textViewNombre)
            textViewNombre.text = "Profesor : " + cliente.nombre + "----" + cliente.apellido
        }
    }
}