package com.shikshashila.app.ui.teacher;

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
public final class TeacherFeatureViewModel_Factory implements Factory<TeacherFeatureViewModel> {
  private final Provider<TeacherRepository> repositoryProvider;

  public TeacherFeatureViewModel_Factory(Provider<TeacherRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public TeacherFeatureViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static TeacherFeatureViewModel_Factory create(
      Provider<TeacherRepository> repositoryProvider) {
    return new TeacherFeatureViewModel_Factory(repositoryProvider);
  }

  public static TeacherFeatureViewModel newInstance(TeacherRepository repository) {
    return new TeacherFeatureViewModel(repository);
  }
}
