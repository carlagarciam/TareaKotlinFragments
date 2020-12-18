package com.example.fragmentoscarla.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fragmentoscarla.database.entities.Asignaturas

@Dao
interface AsignaturasDao {
    @Query("Select * from asignaturas")
    fun getAsignaturas(): Array<Asignaturas>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(asignaturas: List<Asignaturas>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg asignaturas: Asignaturas)
}