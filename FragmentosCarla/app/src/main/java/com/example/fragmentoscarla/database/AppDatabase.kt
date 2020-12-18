package com.example.fragmentjsonbbdd.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fragmentoscarla.database.DAO.*
import com.example.fragmentoscarla.database.entities.Alumnos
import com.example.fragmentoscarla.database.entities.Asignaturas
import com.example.fragmentoscarla.database.entities.Profesores
import com.example.fragmentoscarla.database.relations.AsignaturasAlumnosCrossRef
import com.example.fragmentoscarla.database.relations.AsignaturasProfesoresCrossRef

@Database(
    entities = [Asignaturas::class, Profesores::class, Alumnos::class, AsignaturasProfesoresCrossRef::class, AsignaturasAlumnosCrossRef::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun asignaturasDao(): AsignaturasDao
    abstract fun profesoresDao(): ProfesoresDao
    abstract fun alumnosDao(): AlumnosDao
    abstract fun profesoresAsignaturasDao(): AsignaturasProfesoresDao
    abstract fun alumnosAsignaturasDao(): AsignaturasAlumnosDao
    companion object {
        private const val DATABASE_NAME = "actividadFragmentos"
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }
            return INSTANCE
        }


    }
}