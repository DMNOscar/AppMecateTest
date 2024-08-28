package com.flexicode.testmecate.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flexicode.testmecate.DataBase.Interfaces.UserDao
import com.flexicode.testmecate.Entity.CountryEntity
import com.flexicode.testmecate.Entity.UserEntity

@Database(entities = [UserEntity::class, CountryEntity::class], version = 1, exportSchema = false)
abstract class MecateAppDB : RoomDatabase() {
    abstract fun userDao(): UserDao
}