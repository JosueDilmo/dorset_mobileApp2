package com.josue.dorset_mobileapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import com.josue.dorset_mobileapp2.db.AppDatabase
import com.josue.dorset_mobileapp2.models.User

class DatabaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        Thread {
            dbOps()
        }.start()

    }

    private fun dbOps() {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-maps"
        ).build()

        val userDao = db.UserDao()

        //insert new user in db
        userDao.insertUser(User(1, "user name"))

        //insert user list
        val usersListToInsert = listOf(
            User(2, "username"),
            User(3, "user name 3"),
        )
        userDao.insertList(usersListToInsert)

        // Update user
        val userToUpdate = User(3, "Updated name 3")
        userDao.updateUser(userToUpdate)

        // Delete user
        val userToDelete = User(2, null)
        userDao.deleteUser(userToDelete)

        //getting all users
        val users: List<User> = userDao.getAll()
        users.map {
            Log.i("Database map", it.toString())
        }
    }
}