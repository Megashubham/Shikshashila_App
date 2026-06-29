package com.shikshashila.app.ui.teacher;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010!\u001a\u00020\"2\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u00122\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u0012J\u001e\u0010%\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\u00122\u0006\u0010&\u001a\u00020\u0012J\u0006\u0010\'\u001a\u00020\"J\u0016\u0010(\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\u0012J\u0006\u0010)\u001a\u00020\"J\u0006\u0010*\u001a\u00020\"J\u000e\u0010+\u001a\u00020\"2\u0006\u0010,\u001a\u00020-R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001d\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u001d\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00070\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00070\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u001d\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00070\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0016R\u001d\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00070\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0016\u00a8\u0006."}, d2 = {"Lcom/shikshashila/app/ui/teacher/TeacherFeatureViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/shikshashila/app/domain/repository/TeacherRepository;", "(Lcom/shikshashila/app/domain/repository/TeacherRepository;)V", "_assignmentsState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/shikshashila/app/ui/teacher/TeacherFeatureState;", "Lcom/shikshashila/app/data/model/TeacherAssignmentsResponse;", "_attendanceState", "Lcom/shikshashila/app/data/model/TeacherAttendanceResponse;", "_classesState", "Lcom/shikshashila/app/data/model/TeacherClassesResponse;", "_routineState", "Lcom/shikshashila/app/data/model/TeacherRoutineResponse;", "_studentsListState", "Lcom/shikshashila/app/data/model/TeacherStudentsListResponse;", "_submitState", "", "assignmentsState", "Lkotlinx/coroutines/flow/StateFlow;", "getAssignmentsState", "()Lkotlinx/coroutines/flow/StateFlow;", "attendanceState", "getAttendanceState", "classesState", "getClassesState", "routineState", "getRoutineState", "studentsListState", "getStudentsListState", "submitState", "getSubmitState", "fetchAssignments", "", "classId", "sectionId", "fetchAttendance", "date", "fetchClasses", "fetchStudentsList", "fetchTeacherRoutine", "resetSubmitState", "submitAttendance", "request", "Lcom/shikshashila/app/data/model/SubmitAttendanceRequest;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class TeacherFeatureViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.shikshashila.app.domain.repository.TeacherRepository repository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.shikshashila.app.ui.teacher.TeacherFeatureState<com.shikshashila.app.data.model.TeacherClassesResponse>> _classesState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.teacher.TeacherFeatureState<com.shikshashila.app.data.model.TeacherClassesResponse>> classesState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.shikshashila.app.ui.teacher.TeacherFeatureState<com.shikshashila.app.data.model.TeacherAttendanceResponse>> _attendanceState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.teacher.TeacherFeatureState<com.shikshashila.app.data.model.TeacherAttendanceResponse>> attendanceState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.shikshashila.app.ui.teacher.TeacherFeatureState<com.shikshashila.app.data.model.TeacherAssignmentsResponse>> _assignmentsState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.teacher.TeacherFeatureState<com.shikshashila.app.data.model.TeacherAssignmentsResponse>> assignmentsState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.shikshashila.app.ui.teacher.TeacherFeatureState<java.lang.String>> _submitState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.teacher.TeacherFeatureState<java.lang.String>> submitState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.shikshashila.app.ui.teacher.TeacherFeatureState<com.shikshashila.app.data.model.TeacherRoutineResponse>> _routineState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.teacher.TeacherFeatureState<com.shikshashila.app.data.model.TeacherRoutineResponse>> routineState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.shikshashila.app.ui.teacher.TeacherFeatureState<com.shikshashila.app.data.model.TeacherStudentsListResponse>> _studentsListState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.teacher.TeacherFeatureState<com.shikshashila.app.data.model.TeacherStudentsListResponse>> studentsListState = null;
    
    @javax.inject.Inject
    public TeacherFeatureViewModel(@org.jetbrains.annotations.NotNull
    com.shikshashila.app.domain.repository.TeacherRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.teacher.TeacherFeatureState<com.shikshashila.app.data.model.TeacherClassesResponse>> getClassesState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.teacher.TeacherFeatureState<com.shikshashila.app.data.model.TeacherAttendanceResponse>> getAttendanceState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.teacher.TeacherFeatureState<com.shikshashila.app.data.model.TeacherAssignmentsResponse>> getAssignmentsState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.teacher.TeacherFeatureState<java.lang.String>> getSubmitState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.teacher.TeacherFeatureState<com.shikshashila.app.data.model.TeacherRoutineResponse>> getRoutineState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.teacher.TeacherFeatureState<com.shikshashila.app.data.model.TeacherStudentsListResponse>> getStudentsListState() {
        return null;
    }
    
    public final void fetchClasses() {
    }
    
    public final void fetchAttendance(@org.jetbrains.annotations.NotNull
    java.lang.String classId, @org.jetbrains.annotations.NotNull
    java.lang.String sectionId, @org.jetbrains.annotations.NotNull
    java.lang.String date) {
    }
    
    public final void submitAttendance(@org.jetbrains.annotations.NotNull
    com.shikshashila.app.data.model.SubmitAttendanceRequest request) {
    }
    
    public final void resetSubmitState() {
    }
    
    public final void fetchAssignments(@org.jetbrains.annotations.Nullable
    java.lang.String classId, @org.jetbrains.annotations.Nullable
    java.lang.String sectionId) {
    }
    
    public final void fetchTeacherRoutine() {
    }
    
    public final void fetchStudentsList(@org.jetbrains.annotations.NotNull
    java.lang.String classId, @org.jetbrains.annotations.NotNull
    java.lang.String sectionId) {
    }
}