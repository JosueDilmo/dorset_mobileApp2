package com.josue.dorset_mobileapp2.db

import androidx.room.*
import com.josue.dorset_mobileapp2.models.User

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Insert
    fun insertUser(user: User)

    @Insert
    fun insertList(user: List<User>)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)
}