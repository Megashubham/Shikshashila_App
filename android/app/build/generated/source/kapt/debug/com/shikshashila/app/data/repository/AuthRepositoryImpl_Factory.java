package com.shikshashila.app.data.repository;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import com.google.gson.Gson;
import com.shikshashila.app.data.api.ShikshashilaApi;
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
public final class AuthRepositoryImpl_Factory implements Factory<AuthRepositoryImpl> {
  private final Provider<ShikshashilaApi> apiProvider;

  private final Provider<DataStore<Preferences>> dataStoreProvider;

  private final Provider<Gson> gsonProvider;

  public AuthRepositoryImpl_Factory(Provider<ShikshashilaApi> apiProvider,
      Provider<DataStore<Preferences>> dataStoreProvider, Provider<Gson> gsonProvider) {
    this.apiProvider = apiProvider;
    this.dataStoreProvider = dataStoreProvider;
    this.gsonProvider = gsonProvider;
  }

  @Override
  public AuthRepositoryImpl get() {
    return newInstance(apiProvider.get(), dataStoreProvider.get(), gsonProvider.get());
  }

  public static AuthRepositoryImpl_Factory create(Provider<ShikshashilaApi> apiProvider,
      Provider<DataStore<Preferences>> dataStoreProvider, Provider<Gson> gsonProvider) {
    return new AuthRepositoryImpl_Factory(apiProvider, dataStoreProvider, gsonProvider);
  }

  public static AuthRepositoryImpl newInstance(ShikshashilaApi api,
      DataStore<Preferences> dataStore, Gson gson) {
    return new AuthRepositoryImpl(api, dataStore, gson);
  }
}
