package com.shikshashila.app.ui.teacher;

import com.shikshashila.app.domain.repository.AuthRepository;
import com.shikshashila.app.domain.repository.TeacherRepository;
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
public final class TeacherViewModel_Factory implements Factory<TeacherViewModel> {
  private final Provider<TeacherRepository> teacherRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public TeacherViewModel_Factory(Provider<TeacherRepository> teacherRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.teacherRepositoryProvider = teacherRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public TeacherViewModel get() {
    return newInstance(teacherRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static TeacherViewModel_Factory create(
      Provider<TeacherRepository> teacherRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new TeacherViewModel_Factory(teacherRepositoryProvider, authRepositoryProvider);
  }

  public static TeacherViewModel newInstance(TeacherRepository teacherRepository,
      AuthRepository authRepository) {
    return new TeacherViewModel(teacherRepository, authRepository);
  }
}
