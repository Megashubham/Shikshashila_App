package com.shikshashila.app.data.repository;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ4\u0010\t\u001a\n\u0012\u0004\u0012\u0002H\u000b\u0018\u00010\n\"\u0006\b\u0000\u0010\u000b\u0018\u00012\u0006\u0010\f\u001a\u00020\rH\u0082H\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\"\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\nH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J,\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\n2\b\u0010\u0016\u001a\u0004\u0018\u00010\rH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0017\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000f\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006\u0018"}, d2 = {"Lcom/shikshashila/app/data/repository/AdminRepositoryImpl;", "Lcom/shikshashila/app/domain/repository/AdminRepository;", "api", "Lcom/shikshashila/app/data/api/ShikshashilaApi;", "cacheDao", "Lcom/shikshashila/app/data/local/CacheDao;", "gson", "Lcom/google/gson/Gson;", "(Lcom/shikshashila/app/data/api/ShikshashilaApi;Lcom/shikshashila/app/data/local/CacheDao;Lcom/google/gson/Gson;)V", "fetchFromCache", "Lkotlin/Result;", "T", "key", "", "fetchFromCache-YNEx5aM", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDashboard", "Lcom/shikshashila/app/data/model/AdminDashboardData;", "getDashboard-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFeesReport", "Lcom/shikshashila/app/data/model/FeesReportData;", "session", "getFeesReport-gIAlu-s", "app_debug"})
public final class AdminRepositoryImpl implements com.shikshashila.app.domain.repository.AdminRepository {
    @org.jetbrains.annotations.NotNull
    private final com.shikshashila.app.data.api.ShikshashilaApi api = null;
    @org.jetbrains.annotations.NotNull
    private final com.shikshashila.app.data.local.CacheDao cacheDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.gson.Gson gson = null;
    
    @javax.inject.Inject
    public AdminRepositoryImpl(@org.jetbrains.annotations.NotNull
    com.shikshashila.app.data.api.ShikshashilaApi api, @org.jetbrains.annotations.NotNull
    com.shikshashila.app.data.local.CacheDao cacheDao, @org.jetbrains.annotations.NotNull
    com.google.gson.Gson gson) {
        super();
    }
}