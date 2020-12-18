package com.example.fragmentoscarla.profesor

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmentoscarla.model.profesor

import com.example.fragmentoscarla.R
import com.example.fragmentoscarla.repository.DataRepository

class FragmentProfesor : Fragment() {
    var itemSeleccionado: profesor? = null
    var thiscontext: Context? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.profesor_fragment, container, false)
        val asignatura = arguments!!.getString("asignatura")
        val recyclerViewLista: RecyclerView =
            v.findViewById<View>(R.id.recyclerviewlista) as RecyclerView
        thiscontext = container?.getContext();
        var dataRepository = DataRepository(thiscontext!!)
        var numeroAsignatura: Int
        if (asignatura.equals("BBDD")) {
            numeroAsignatura = 2
        } else {
            numeroAsignatura = 1
        }
        var profesoresGuardados = dataRepository.getProfesorOne(numeroAsignatura)
        var profesor = profesoresGuardados.get(0).profesores
        var items = ArrayList<profesor>()
        for (i in 0..profesor.size - 1) {
            items.add(
                profesor(
                    profesor.get(i).nombre.toString(),
                    profesor.get(i).apellido.toString()
                )
            )
        }

        val adapter = AdapterProfesor(items) { item ->
            itemSeleccionado = item

        }
        recyclerViewLista.setAdapter(adapter)
        recyclerViewLista.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
        )

        return v
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentProfesor().apply {}
    }
}