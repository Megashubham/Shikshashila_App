package com.shikshashila.app.di;

import com.shikshashila.app.data.api.ShikshashilaApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class NetworkModule_ProvideShikshashilaApiFactory implements Factory<ShikshashilaApi> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideShikshashilaApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public ShikshashilaApi get() {
    return provideShikshashilaApi(retrofitProvider.get());
  }

  public static NetworkModule_ProvideShikshashilaApiFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideShikshashilaApiFactory(retrofitProvider);
  }

  public static ShikshashilaApi provideShikshashilaApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideShikshashilaApi(retrofit));
  }
}
