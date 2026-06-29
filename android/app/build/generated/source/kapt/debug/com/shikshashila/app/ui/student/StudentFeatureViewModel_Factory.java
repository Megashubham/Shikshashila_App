package com.shikshashila.app.ui.student;

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
public final class StudentFeatureViewModel_Factory implements Factory<StudentFeatureViewModel> {
  private final Provider<StudentRepository> repositoryProvider;

  public StudentFeatureViewModel_Factory(Provider<StudentRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public StudentFeatureViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static StudentFeatureViewModel_Factory create(
      Provider<StudentRepository> repositoryProvider) {
    return new StudentFeatureViewModel_Factory(repositoryProvider);
  }

  public static StudentFeatureViewModel newInstance(StudentRepository repository) {
    return new StudentFeatureViewModel(repository);
  }
}
