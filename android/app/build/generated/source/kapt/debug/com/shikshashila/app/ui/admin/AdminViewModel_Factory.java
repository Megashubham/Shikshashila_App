package com.shikshashila.app.ui.admin;

import com.shikshashila.app.domain.repository.AdminRepository;
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
public final class AdminViewModel_Factory implements Factory<AdminViewModel> {
  private final Provider<AdminRepository> adminRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public AdminViewModel_Factory(Provider<AdminRepository> adminRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.adminRepositoryProvider = adminRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public AdminViewModel get() {
    return newInstance(adminRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static AdminViewModel_Factory create(Provider<AdminRepository> adminRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new AdminViewModel_Factory(adminRepositoryProvider, authRepositoryProvider);
  }

  public static AdminViewModel newInstance(AdminRepository adminRepository,
      AuthRepository authRepository) {
    return new AdminViewModel(adminRepository, authRepository);
  }
}
