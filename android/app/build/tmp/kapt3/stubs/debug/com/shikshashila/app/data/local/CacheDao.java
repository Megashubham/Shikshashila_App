package com.shikshashila.app.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u0011\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J\u001b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ\u0019\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\f\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\r"}, d2 = {"Lcom/shikshashila/app/data/local/CacheDao;", "", "clearAllCache", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCache", "Lcom/shikshashila/app/data/local/CacheEntity;", "key", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertCache", "cache", "(Lcom/shikshashila/app/data/local/CacheEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao
public abstract interface CacheDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertCache(@org.jetbrains.annotations.NotNull
    com.shikshashila.app.data.local.CacheEntity cache, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM api_cache WHERE cacheKey = :key LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getCache(@org.jetbrains.annotations.NotNull
    java.lang.String key, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.shikshashila.app.data.local.CacheEntity> $completion);
    
    @androidx.room.Query(value = "DELETE FROM api_cache")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object clearAllCache(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}