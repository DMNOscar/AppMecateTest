package com.flexicode.testmecate.DataBase.Repository

import com.flexicode.testmecate.DataBase.Interfaces.UserDao
import com.flexicode.testmecate.Entity.CountryEntity
import com.flexicode.testmecate.Entity.UserEntity
import javax.inject.Inject

class MecateRepository @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun insertUser(user: UserEntity): Long {
        return userDao.insertUser(user)
    }

    suspend fun insertCountries(countries: List<CountryEntity>) = userDao.insertCountries(countries)

    suspend fun getAllUsers(): List<UserEntity> = userDao.getAllUsers()

    suspend fun getCountriesByUserId(userId: Int): List<CountryEntity> {
        return userDao.getCountriesByUserId(userId)
    }

}