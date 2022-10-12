package com.josue.dorset_mobileapp2.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.josue.dorset_mobileapp2.models.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun UserDao(): UserDao
}