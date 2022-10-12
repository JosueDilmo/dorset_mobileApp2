package com.josue.dorset_mobileapp2.models

import androidx.room.Entity
import androidx.room.Relation

@Entity
class UserWithLocation {
    lateinit var user: User
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    lateinit var locations: List<Location>
}