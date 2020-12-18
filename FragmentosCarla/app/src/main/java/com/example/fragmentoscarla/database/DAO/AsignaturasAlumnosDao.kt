package com.example.fragmentoscarla.database.DAO

import androidx.room.*
import com.example.fragmentoscarla.database.relations.AsignaturasAlumnos
import com.example.fragmentoscarla.database.relations.AsignaturasAlumnosCrossRef

@Dao
interface AsignaturasAlumnosDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(join: AsignaturasAlumnosCrossRef)

    @Transaction
    @Query("SELECT * FROM asignaturas WHERE asignaturasId = :asignaturasId ORDER BY :asignaturasId ASC")
    fun getAlumnos(asignaturasId: Int): Array<AsignaturasAlumnos>
}