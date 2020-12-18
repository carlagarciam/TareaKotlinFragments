package com.example.fragmentoscarla

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.fragmentoscarla.alumno.FragmentListaAlumno
import com.example.fragmentoscarla.database.entities.Alumnos
import com.example.fragmentoscarla.database.entities.Asignaturas
import com.example.fragmentoscarla.database.entities.Profesores
import com.example.fragmentoscarla.database.relations.AsignaturasAlumnos
import com.example.fragmentoscarla.database.relations.AsignaturasProfesores
import com.example.fragmentoscarla.profesor.FragmentProfesor
import com.example.fragmentoscarla.repository.DataRepository
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var dr = DataRepository(this)
        var br =
            BufferedReader(InputStreamReader(resources.openRawResource(R.raw.datos)))
        var readText = br.readLine()
        val readAll = StringBuilder()
        var cont = 0
        while (readText != null) {
            readAll.append(readText + "\n")
            readText = br.readLine()
        }
        readText = readAll.toString()
        br.close()
        val jObj = JSONObject(readText)
        val jArray = jObj.optJSONArray("asignaturas")
        var alumnosArr = ArrayList<Alumnos>()
        var profesoresArr = ArrayList<Profesores>()
        for (i in 0 until jArray.length()) {
            alumnosArr.clear()
            profesoresArr.clear()
            var asignatura = Asignaturas(i + 1, jArray.get(i).toString())
            val asignaturaTex = jArray.get(i).toString()
            val jsonArrayProf = jObj.optJSONArray("profesores")
            for (i in 0 until jsonArrayProf.length()) {
                val objeto = jsonArrayProf.getJSONObject(i)
                if (objeto.optString("asignatura").equals(asignaturaTex)) {
                    var codigoProfesor = objeto.optString("codigo")
                    var nombreProfesor = objeto.optString("nombre")
                    var apellidoProfesor = objeto.optString("apellido")
                    var profesor = Profesores(
                        codigoProfesor.toString().toInt(),
                        nombreProfesor.toString(),
                        apellidoProfesor.toString()
                    )
                    profesoresArr.add(profesor)
                }
            }
            val jsnAlumno = jObj.optJSONArray("alumnos")
            for (i in 0 until jsnAlumno.length()) {
                val objAlumno = jsnAlumno.getJSONObject(i)
                val jsonArrayAsignaturas = objAlumno.optJSONArray("Asignaturas")
                for (i in 0 until jsonArrayAsignaturas.length()){
                    val objetoAsignatura = jsonArrayAsignaturas[i].toString()
                    if (objetoAsignatura.equals(asignaturaTex)) {
                        var codigoAlumno = objAlumno.optString("codigo")
                        var nombreAlumno = objAlumno.optString("nombre")
                        var apellidoAlumno = objAlumno.optString("apellido")
                        var alumno = Alumnos(
                            codigoAlumno.toString().toInt(),
                            nombreAlumno.toString(),
                            apellidoAlumno.toString()
                        )
                        alumnosArr.add(alumno)
                    }
                    cont++
                }
            }
            var asignaturasAlumnos = AsignaturasAlumnos(asignatura, alumnosArr)
            var asignaturasProfesores = AsignaturasProfesores(asignatura, profesoresArr)
            dr.insertAsignaturasAlumnos(asignaturasAlumnos)
            dr.insertAsignaturasProfesores(asignaturasProfesores)
        }
        val spinner = findViewById<Spinner>(R.id.spinner)
        var pedidosGuardados = dr.getAsignaturas()
        var ArraySpinner = ArrayList<String>()
        for (items in pedidosGuardados) {
            ArraySpinner.add(items.nombre.toString())
        }
        spinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, ArraySpinner)
        if (spinner != null) {
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val asignaturaBundle = Bundle()
                    asignaturaBundle.putString("asignatura", spinner.selectedItem.toString())
                    var frameProfesor: FrameLayout? = findViewById(R.id.frameLayoutProfesor)
                    var frameAlumnos: FrameLayout? = findViewById(R.id.frameLayoutAlumno)
                    frameProfesor?.removeAllViewsInLayout()
                    frameAlumnos?.removeAllViewsInLayout()
                    var fragmentProfesor: FragmentProfesor? = FragmentProfesor.newInstance()
                    var fragmentAlumno: FragmentListaAlumno? = FragmentListaAlumno.newInstance()
                    fragmentProfesor!!.setArguments(asignaturaBundle)
                    fragmentAlumno!!.setArguments(asignaturaBundle)
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.add(R.id.frameLayoutProfesor, fragmentProfesor!!)
                    fragmentTransaction.add(R.id.frameLayoutAlumno, fragmentAlumno!!)
                    fragmentTransaction.commit()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }
}