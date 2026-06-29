package com.shikshashila.app.di

import android.content.Context
import androidx.room.Room
import com.shikshashila.app.data.local.CacheDao
import com.shikshashila.app.data.local.ShikshashilaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ShikshashilaDatabase {
        return Room.databaseBuilder(
            context,
            ShikshashilaDatabase::class.java,
            "shikshashila_db"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    fun provideCacheDao(database: ShikshashilaDatabase): CacheDao {
        return database.cacheDao()
    }
}
