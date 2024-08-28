package com.flexicode.testmecate.DataBase.DI

import android.content.Context
import androidx.room.Room
import com.flexicode.testmecate.DataBase.Interfaces.UserDao
import com.flexicode.testmecate.DataBase.MecateAppDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context): MecateAppDB =
        Room.databaseBuilder(context, MecateAppDB::class.java, "mecate_app_db")
            .fallbackToDestructiveMigrationFrom()
            .build()

    @Singleton
    @Provides
    fun provideTaskDao(mecateAppDB: MecateAppDB): UserDao = mecateAppDB.userDao()
}