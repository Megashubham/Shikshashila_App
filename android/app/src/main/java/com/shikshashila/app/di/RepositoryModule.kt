package com.shikshashila.app.di

import com.shikshashila.app.data.repository.AuthRepositoryImpl
import com.shikshashila.app.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindStudentRepository(
        studentRepositoryImpl: com.shikshashila.app.data.repository.StudentRepositoryImpl
    ): com.shikshashila.app.domain.repository.StudentRepository

    @Binds
    @Singleton
    abstract fun bindTeacherRepository(
        teacherRepositoryImpl: com.shikshashila.app.data.repository.TeacherRepositoryImpl
    ): com.shikshashila.app.domain.repository.TeacherRepository

    @Binds
    @Singleton
    abstract fun bindAdminRepository(
        adminRepositoryImpl: com.shikshashila.app.data.repository.AdminRepositoryImpl
    ): com.shikshashila.app.domain.repository.AdminRepository
}
