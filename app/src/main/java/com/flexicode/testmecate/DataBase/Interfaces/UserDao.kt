package com.flexicode.testmecate.DataBase.Interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flexicode.testmecate.Entity.CountryEntity
import com.flexicode.testmecate.Entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<CountryEntity>)

    @Query("SELECT * FROM user_tbl")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM country_tbl WHERE userId = :userId")
    suspend fun getCountriesByUserId(userId: Int): List<CountryEntity>
}