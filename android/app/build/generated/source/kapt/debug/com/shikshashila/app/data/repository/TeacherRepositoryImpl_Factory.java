package com.shikshashila.app.data.repository;

import com.google.gson.Gson;
import com.shikshashila.app.data.api.ShikshashilaApi;
import com.shikshashila.app.data.local.CacheDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class TeacherRepositoryImpl_Factory implements Factory<TeacherRepositoryImpl> {
  private final Provider<ShikshashilaApi> apiProvider;

  private final Provider<CacheDao> cacheDaoProvider;

  private final Provider<Gson> gsonProvider;

  public TeacherRepositoryImpl_Factory(Provider<ShikshashilaApi> apiProvider,
      Provider<CacheDao> cacheDaoProvider, Provider<Gson> gsonProvider) {
    this.apiProvider = apiProvider;
    this.cacheDaoProvider = cacheDaoProvider;
    this.gsonProvider = gsonProvider;
  }

  @Override
  public TeacherRepositoryImpl get() {
    return newInstance(apiProvider.get(), cacheDaoProvider.get(), gsonProvider.get());
  }

  public static TeacherRepositoryImpl_Factory create(Provider<ShikshashilaApi> apiProvider,
      Provider<CacheDao> cacheDaoProvider, Provider<Gson> gsonProvider) {
    return new TeacherRepositoryImpl_Factory(apiProvider, cacheDaoProvider, gsonProvider);
  }

  public static TeacherRepositoryImpl newInstance(ShikshashilaApi api, CacheDao cacheDao,
      Gson gson) {
    return new TeacherRepositoryImpl(api, cacheDao, gson);
  }
}
