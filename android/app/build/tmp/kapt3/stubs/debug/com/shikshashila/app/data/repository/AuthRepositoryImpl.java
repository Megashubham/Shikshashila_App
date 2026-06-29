package com.shikshashila.app.data.repository;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\u0010\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000fH\u0016J2\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u00122\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\fH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0011\u0010\u0017\u001a\u00020\u0018H\u0096@\u00f8\u0001\u0002\u00a2\u0006\u0002\u0010\u0019R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000f\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006\u001a"}, d2 = {"Lcom/shikshashila/app/data/repository/AuthRepositoryImpl;", "Lcom/shikshashila/app/domain/repository/AuthRepository;", "api", "Lcom/shikshashila/app/data/api/ShikshashilaApi;", "dataStore", "Landroidx/datastore/core/DataStore;", "Landroidx/datastore/preferences/core/Preferences;", "gson", "Lcom/google/gson/Gson;", "(Lcom/shikshashila/app/data/api/ShikshashilaApi;Landroidx/datastore/core/DataStore;Lcom/google/gson/Gson;)V", "jwtKey", "Landroidx/datastore/preferences/core/Preferences$Key;", "", "userKey", "getSavedUser", "Lkotlinx/coroutines/flow/Flow;", "Lcom/shikshashila/app/data/model/User;", "login", "Lkotlin/Result;", "loginId", "password", "login-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class AuthRepositoryImpl implements com.shikshashila.app.domain.repository.AuthRepository {
    @org.jetbrains.annotations.NotNull
    private final com.shikshashila.app.data.api.ShikshashilaApi api = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> dataStore = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> jwtKey = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> userKey = null;
    
    @javax.inject.Inject
    public AuthRepositoryImpl(@org.jetbrains.annotations.NotNull
    com.shikshashila.app.data.api.ShikshashilaApi api, @org.jetbrains.annotations.NotNull
    androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> dataStore, @org.jetbrains.annotations.NotNull
    com.google.gson.Gson gson) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object logout(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public kotlinx.coroutines.flow.Flow<com.shikshashila.app.data.model.User> getSavedUser() {
        return null;
    }
}