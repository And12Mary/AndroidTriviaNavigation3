package com.example.android.navigation.db

import androidx.room.*
import com.example.android.navigation.db.UserEntity

@Dao
interface UserDao {


    @Query("SELECT * FROM userinfo ORDER BY id DESC")
    fun getAllUserInfo(): List<UserEntity>?


    @Insert
    fun insertUser(user: UserEntity?)

    @Delete
    fun deleteUser(user: UserEntity?)

    @Update
    fun updateUser(user: UserEntity?)

}