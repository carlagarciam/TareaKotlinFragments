package com.example.fragmentoscarla.repository

import android.content.Context
import android.os.AsyncTask
import com.example.fragmentjsonbbdd.database.AppDatabase
import com.example.fragmentoscarla.database.DAO.*
import com.example.fragmentoscarla.database.entities.Asignaturas
import com.example.fragmentoscarla.database.relations.AsignaturasAlumnos
import com.example.fragmentoscarla.database.relations.AsignaturasAlumnosCrossRef
import com.example.fragmentoscarla.database.relations.AsignaturasProfesores
import com.example.fragmentoscarla.database.relations.AsignaturasProfesoresCrossRef

class DataRepository(context: Context) {
    private val asignaturasDao: AsignaturasDao? = AppDatabase.getInstance(context)?.asignaturasDao()
    private val profesoresDao: ProfesoresDao? = AppDatabase.getInstance(context)?.profesoresDao()
    private val alumnosDao: AlumnosDao? = AppDatabase.getInstance(context)?.alumnosDao()
    private val asignaturasProfesoresDao: AsignaturasProfesoresDao? = AppDatabase.getInstance(
        context
    )?.profesoresAsignaturasDao()
    private val asignaturasAlumnosDao: AsignaturasAlumnosDao? =
        AppDatabase.getInstance(context)?.alumnosAsignaturasDao()


    fun insertAsignaturasAlumnos(asignaturasAlumnos: AsignaturasAlumnos): Int {
        if (asignaturasDao != null && alumnosDao != null && asignaturasAlumnosDao != null) {
            return InsertAsignaturasAlumnosAsyncTask(
                asignaturasDao,
                alumnosDao,
                asignaturasAlumnosDao
            ).execute(asignaturasAlumnos).get()
        }
        return -1
    }


    fun insertAsignaturasProfesores(asignaturasProfesores: AsignaturasProfesores): Int {
        if (asignaturasDao != null && profesoresDao != null && asignaturasProfesoresDao != null) {
            return InsertAsignaturasProfesoresAsyncTask(
                profesoresDao,
                asignaturasProfesoresDao
            ).execute(asignaturasProfesores).get()
        }
        return -1
    }


    private class InsertAsignaturasProfesoresAsyncTask(
        private val profesoresDao: ProfesoresDao,
        private val asignaturasProfesoresDao: AsignaturasProfesoresDao
    ) : AsyncTask<AsignaturasProfesores, Void, Int>() {
        override fun doInBackground(vararg asignaturasProfesores: AsignaturasProfesores?): Int {
            try {
                for (asignaturasProfesores in asignaturasProfesores) {
                    if (asignaturasProfesores != null) {
                        profesoresDao.insertAll(asignaturasProfesores.profesores)
                        for (profesores in asignaturasProfesores.profesores) {
                            asignaturasProfesoresDao.insert(
                                AsignaturasProfesoresCrossRef(
                                    asignaturasProfesores.asignaturas.asignaturasId,
                                    profesores.profesoresId
                                )
                            )
                        }
                    }
                }

                return 0
            } catch (exception: Exception) {
                return -1
            }
        }
    }

    private class InsertAsignaturasAlumnosAsyncTask(
        private val asignaturasDao: AsignaturasDao,
        private val alumnosDao: AlumnosDao,
        private val asignaturasAlumnosDao: AsignaturasAlumnosDao
    ) : AsyncTask<AsignaturasAlumnos, Void, Int>() {
        override fun doInBackground(vararg asignaturasAlumnos: AsignaturasAlumnos?): Int {
            try {
                for (asignaturasAlumnos in asignaturasAlumnos) {
                    if (asignaturasAlumnos != null) {
                        asignaturasDao.insertAll(asignaturasAlumnos.asignaturas)
                        alumnosDao.insertAll(asignaturasAlumnos.alumnos)
                        for (alumnos in asignaturasAlumnos.alumnos) {
                            asignaturasAlumnosDao.insert(
                                AsignaturasAlumnosCrossRef(
                                    asignaturasAlumnos.asignaturas.asignaturasId,
                                    alumnos.alumnosId
                                )
                            )
                        }
                    }
                }

                return 0
            } catch (exception: Exception) {
                return -1
            }
        }
    }

    fun getAsignaturas(): Array<Asignaturas> {
        return GetAsignaturas(asignaturasDao!!).execute().get()
    }

    fun getAlumnos(idAsignatura: Int): Array<AsignaturasAlumnos> {
        return GetAlumnos(asignaturasAlumnosDao!!, idAsignatura).execute().get()
    }

    fun getProfesorOne(idAsignatura: Int): Array<AsignaturasProfesores> {
        return GetProfesorOne(asignaturasProfesoresDao!!, idAsignatura).execute().get()
    }

    private class GetAlumnos(
        private val asignaturasAlumnosDao: AsignaturasAlumnosDao,
        private val idAsignatura: Int
    ) : AsyncTask<Void, Void, Array<AsignaturasAlumnos>>() {
        override fun doInBackground(vararg params: Void?): Array<AsignaturasAlumnos> {
            return asignaturasAlumnosDao.getAlumnos(idAsignatura)
        }
    }

    private class GetAsignaturas(private val asignaturasDao: AsignaturasDao) :
        AsyncTask<Void, Void, Array<Asignaturas>>() {
        override fun doInBackground(vararg params: Void?): Array<Asignaturas> {
            return asignaturasDao.getAsignaturas()
        }
    }

    private class GetProfesorOne(
        private val asignaturasProfesoresDao: AsignaturasProfesoresDao,
        private val idAsignatura: Int
    ) : AsyncTask<Void, Void, Array<AsignaturasProfesores>>() {
        override fun doInBackground(vararg params: Void?): Array<AsignaturasProfesores> {
            return asignaturasProfesoresDao.getProfesorOne(idAsignatura)
        }
    }


}