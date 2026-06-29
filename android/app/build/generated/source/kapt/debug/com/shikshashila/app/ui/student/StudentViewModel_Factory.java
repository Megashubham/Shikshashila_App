package com.shikshashila.app.ui.student;

import com.shikshashila.app.domain.repository.AuthRepository;
import com.shikshashila.app.domain.repository.StudentRepository;
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
public final class StudentViewModel_Factory implements Factory<StudentViewModel> {
  private final Provider<StudentRepository> studentRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public StudentViewModel_Factory(Provider<StudentRepository> studentRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.studentRepositoryProvider = studentRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public StudentViewModel get() {
    return newInstance(studentRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static StudentViewModel_Factory create(
      Provider<StudentRepository> studentRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new StudentViewModel_Factory(studentRepositoryProvider, authRepositoryProvider);
  }

  public static StudentViewModel newInstance(StudentRepository studentRepository,
      AuthRepository authRepository) {
    return new StudentViewModel(studentRepository, authRepository);
  }
}
