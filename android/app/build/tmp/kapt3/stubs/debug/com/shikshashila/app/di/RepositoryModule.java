package com.shikshashila.app.di;

@dagger.Module
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\'J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\'J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\'\u00a8\u0006\u0013"}, d2 = {"Lcom/shikshashila/app/di/RepositoryModule;", "", "()V", "bindAdminRepository", "Lcom/shikshashila/app/domain/repository/AdminRepository;", "adminRepositoryImpl", "Lcom/shikshashila/app/data/repository/AdminRepositoryImpl;", "bindAuthRepository", "Lcom/shikshashila/app/domain/repository/AuthRepository;", "authRepositoryImpl", "Lcom/shikshashila/app/data/repository/AuthRepositoryImpl;", "bindStudentRepository", "Lcom/shikshashila/app/domain/repository/StudentRepository;", "studentRepositoryImpl", "Lcom/shikshashila/app/data/repository/StudentRepositoryImpl;", "bindTeacherRepository", "Lcom/shikshashila/app/domain/repository/TeacherRepository;", "teacherRepositoryImpl", "Lcom/shikshashila/app/data/repository/TeacherRepositoryImpl;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract class RepositoryModule {
    
    public RepositoryModule() {
        super();
    }
    
    @dagger.Binds
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public abstract com.shikshashila.app.domain.repository.AuthRepository bindAuthRepository(@org.jetbrains.annotations.NotNull
    com.shikshashila.app.data.repository.AuthRepositoryImpl authRepositoryImpl);
    
    @dagger.Binds
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public abstract com.shikshashila.app.domain.repository.StudentRepository bindStudentRepository(@org.jetbrains.annotations.NotNull
    com.shikshashila.app.data.repository.StudentRepositoryImpl studentRepositoryImpl);
    
    @dagger.Binds
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public abstract com.shikshashila.app.domain.repository.TeacherRepository bindTeacherRepository(@org.jetbrains.annotations.NotNull
    com.shikshashila.app.data.repository.TeacherRepositoryImpl teacherRepositoryImpl);
    
    @dagger.Binds
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public abstract com.shikshashila.app.domain.repository.AdminRepository bindAdminRepository(@org.jetbrains.annotations.NotNull
    com.shikshashila.app.data.repository.AdminRepositoryImpl adminRepositoryImpl);
}