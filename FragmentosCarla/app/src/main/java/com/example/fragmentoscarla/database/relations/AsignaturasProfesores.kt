package com.example.fragmentoscarla.database.relations

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.example.fragmentoscarla.database.entities.Asignaturas
import com.example.fragmentoscarla.database.entities.Profesores

data class AsignaturasProfesores(
    @Embedded var asignaturas: Asignaturas,

    @Relation(
        parentColumn = "asignaturasId",
        entity = Profesores::class,
        entityColumn = "profesoresId",
        associateBy = Junction(
            value = AsignaturasProfesoresCrossRef::class,
            parentColumn = "asignaturasId",
            entityColumn = "profesoresId"
        )
    )

    var profesores: List<Profesores>
)

@Entity(primaryKeys = ["asignaturasId", "profesoresId"])
data class AsignaturasProfesoresCrossRef(
    val asignaturasId: Int,
    val profesoresId: Int
)

