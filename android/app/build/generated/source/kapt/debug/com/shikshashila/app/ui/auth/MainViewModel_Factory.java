package com.shikshashila.app.ui.auth;

import com.shikshashila.app.domain.repository.AuthRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class MainViewModel_Factory implements Factory<MainViewModel> {
  private final Provider<AuthRepository> repositoryProvider;

  public MainViewModel_Factory(Provider<AuthRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public MainViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static MainViewModel_Factory create(Provider<AuthRepository> repositoryProvider) {
    return new MainViewModel_Factory(repositoryProvider);
  }

  public static MainViewModel newInstance(AuthRepository repository) {
    return new MainViewModel(repository);
  }
}
