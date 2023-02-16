package com.apmasquio.cadastrousuario.data.dao

import androidx.room.*
import com.apmasquio.cadastrousuario.data.models.User

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Insert
    fun save(vararg user: User)

    @Delete
    fun delete(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * FROM User WHERE id= :id")
    fun searchById(id: Long): User?
}