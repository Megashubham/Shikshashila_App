package com.shikshashila.app.data.repository;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ4\u0010\t\u001a\n\u0012\u0004\u0012\u0002H\u000b\u0018\u00010\n\"\u0006\b\u0000\u0010\u000b\u0018\u00012\u0006\u0010\f\u001a\u00020\rH\u0082H\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\"\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\nH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J\"\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\nH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0013J\"\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\nH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0019\u0010\u0013J\"\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\nH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u001c\u0010\u0013R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000f\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006\u001d"}, d2 = {"Lcom/shikshashila/app/data/repository/StudentRepositoryImpl;", "Lcom/shikshashila/app/domain/repository/StudentRepository;", "api", "Lcom/shikshashila/app/data/api/ShikshashilaApi;", "cacheDao", "Lcom/shikshashila/app/data/local/CacheDao;", "gson", "Lcom/google/gson/Gson;", "(Lcom/shikshashila/app/data/api/ShikshashilaApi;Lcom/shikshashila/app/data/local/CacheDao;Lcom/google/gson/Gson;)V", "fetchFromCache", "Lkotlin/Result;", "T", "key", "", "fetchFromCache-YNEx5aM", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDashboard", "Lcom/shikshashila/app/data/model/StudentDashboardData;", "getDashboard-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getHomework", "Lcom/shikshashila/app/data/model/HomeworkResponse;", "getHomework-IoAF18A", "getResults", "Lcom/shikshashila/app/data/model/ResultsResponse;", "getResults-IoAF18A", "getRoutine", "Lcom/shikshashila/app/data/model/RoutineResponse;", "getRoutine-IoAF18A", "app_debug"})
public final class StudentRepositoryImpl implements com.shikshashila.app.domain.repository.StudentRepository {
    @org.jetbrains.annotations.NotNull
    private final com.shikshashila.app.data.api.ShikshashilaApi api = null;
    @org.jetbrains.annotations.NotNull
    private final com.shikshashila.app.data.local.CacheDao cacheDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.gson.Gson gson = null;
    
    @javax.inject.Inject
    public StudentRepositoryImpl(@org.jetbrains.annotations.NotNull
    com.shikshashila.app.data.api.ShikshashilaApi api, @org.jetbrains.annotations.NotNull
    com.shikshashila.app.data.local.CacheDao cacheDao, @org.jetbrains.annotations.NotNull
    com.google.gson.Gson gson) {
        super();
    }
}