package com.example.fragmentoscarla.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.fragmentoscarla.database.entities.Profesores

@Dao
interface ProfesoresDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg profesores: Profesores)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(profesores: List<Profesores>)
}