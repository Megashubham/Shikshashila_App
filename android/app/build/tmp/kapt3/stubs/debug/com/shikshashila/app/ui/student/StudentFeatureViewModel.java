package com.shikshashila.app.ui.student;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u0016J\u0006\u0010\u0018\u001a\u00020\u0016R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u001d\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00070\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010\u00a8\u0006\u0019"}, d2 = {"Lcom/shikshashila/app/ui/student/StudentFeatureViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/shikshashila/app/domain/repository/StudentRepository;", "(Lcom/shikshashila/app/domain/repository/StudentRepository;)V", "_homeworkState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/shikshashila/app/ui/student/FeatureState;", "Lcom/shikshashila/app/data/model/HomeworkResponse;", "_resultsState", "Lcom/shikshashila/app/data/model/ResultsResponse;", "_routineState", "Lcom/shikshashila/app/data/model/RoutineResponse;", "homeworkState", "Lkotlinx/coroutines/flow/StateFlow;", "getHomeworkState", "()Lkotlinx/coroutines/flow/StateFlow;", "resultsState", "getResultsState", "routineState", "getRoutineState", "fetchHomework", "", "fetchResults", "fetchRoutine", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class StudentFeatureViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.shikshashila.app.domain.repository.StudentRepository repository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.shikshashila.app.ui.student.FeatureState<com.shikshashila.app.data.model.RoutineResponse>> _routineState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.student.FeatureState<com.shikshashila.app.data.model.RoutineResponse>> routineState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.shikshashila.app.ui.student.FeatureState<com.shikshashila.app.data.model.ResultsResponse>> _resultsState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.student.FeatureState<com.shikshashila.app.data.model.ResultsResponse>> resultsState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.shikshashila.app.ui.student.FeatureState<com.shikshashila.app.data.model.HomeworkResponse>> _homeworkState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.student.FeatureState<com.shikshashila.app.data.model.HomeworkResponse>> homeworkState = null;
    
    @javax.inject.Inject
    public StudentFeatureViewModel(@org.jetbrains.annotations.NotNull
    com.shikshashila.app.domain.repository.StudentRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.student.FeatureState<com.shikshashila.app.data.model.RoutineResponse>> getRoutineState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.student.FeatureState<com.shikshashila.app.data.model.ResultsResponse>> getResultsState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.student.FeatureState<com.shikshashila.app.data.model.HomeworkResponse>> getHomeworkState() {
        return null;
    }
    
    public final void fetchRoutine() {
    }
    
    public final void fetchResults() {
    }
    
    public final void fetchHomework() {
    }
}