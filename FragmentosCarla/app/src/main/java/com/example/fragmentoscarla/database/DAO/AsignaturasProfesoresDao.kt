package com.example.fragmentoscarla.database.DAO

import androidx.room.*
import com.example.fragmentoscarla.database.relations.AsignaturasProfesores
import com.example.fragmentoscarla.database.relations.AsignaturasProfesoresCrossRef

@Dao
interface AsignaturasProfesoresDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(join: AsignaturasProfesoresCrossRef)

    @Transaction
    @Query("SELECT * FROM asignaturas WHERE asignaturasId = :asignaturasId ORDER BY :asignaturasId ASC")
    fun getProfesorOne(asignaturasId: Int): Array<AsignaturasProfesores>

}