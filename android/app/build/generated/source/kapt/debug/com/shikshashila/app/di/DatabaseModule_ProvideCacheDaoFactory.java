package com.shikshashila.app.di;

import com.shikshashila.app.data.local.CacheDao;
import com.shikshashila.app.data.local.ShikshashilaDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DatabaseModule_ProvideCacheDaoFactory implements Factory<CacheDao> {
  private final Provider<ShikshashilaDatabase> databaseProvider;

  public DatabaseModule_ProvideCacheDaoFactory(Provider<ShikshashilaDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public CacheDao get() {
    return provideCacheDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideCacheDaoFactory create(
      Provider<ShikshashilaDatabase> databaseProvider) {
    return new DatabaseModule_ProvideCacheDaoFactory(databaseProvider);
  }

  public static CacheDao provideCacheDao(ShikshashilaDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCacheDao(database));
  }
}
