package com.josue.dorset_mobileapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update

class DatabaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        Thread {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-maps"
            ).build()

            val userDao = db.UserDao()

            //insert user in db
            userDao.insertUser(User(1,"user name"))

            //inser user list
            val usersListToInsert = listOf(
            User(2,"username"),
            User(3,"user name 3"),
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

        }.start()
    }
}

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun UserDao(): UserDao
}

@Entity
data class User(val uid: Int, val uname: String?){
    @PrimaryKey var id: Int = uid
    var name: String? = uname

    override fun toString(): String {
        return "$name ($id)"
    }

}

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