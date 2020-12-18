package com.example.fragmentoscarla.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fragmentoscarla.database.entities.Alumnos

@Dao
interface AlumnosDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg alumnos: Alumnos)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(alumnos: List<Alumnos>)

    @Query("SELECT * FROM alumnos WHERE alumnosId = :alumnoId ORDER BY :alumnoId ASC")
    fun getAlumnoOne(alumnoId: Int): Array<Alumnos>
}