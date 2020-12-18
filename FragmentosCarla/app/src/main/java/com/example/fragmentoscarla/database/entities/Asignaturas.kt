package com.example.fragmentoscarla.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Asignaturas(
    @PrimaryKey val asignaturasId: Int,
    val nombre: String?
)