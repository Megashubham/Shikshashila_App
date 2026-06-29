package com.shikshashila.app.ui.admin;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0013\u001a\u00020\u0014J\u0012\u0010\u0015\u001a\u00020\u00142\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0017J\u0006\u0010\u0018\u001a\u00020\u0014R\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010\u00a8\u0006\u0019"}, d2 = {"Lcom/shikshashila/app/ui/admin/AdminViewModel;", "Landroidx/lifecycle/ViewModel;", "adminRepository", "Lcom/shikshashila/app/domain/repository/AdminRepository;", "authRepository", "Lcom/shikshashila/app/domain/repository/AuthRepository;", "(Lcom/shikshashila/app/domain/repository/AdminRepository;Lcom/shikshashila/app/domain/repository/AuthRepository;)V", "_dashboardState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/shikshashila/app/ui/admin/AdminState;", "Lcom/shikshashila/app/data/model/AdminDashboardData;", "_feesReportState", "Lcom/shikshashila/app/data/model/FeesReportData;", "dashboardState", "Lkotlinx/coroutines/flow/StateFlow;", "getDashboardState", "()Lkotlinx/coroutines/flow/StateFlow;", "feesReportState", "getFeesReportState", "fetchDashboard", "", "fetchFeesReport", "session", "", "logout", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class AdminViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.shikshashila.app.domain.repository.AdminRepository adminRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.shikshashila.app.domain.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.shikshashila.app.ui.admin.AdminState<com.shikshashila.app.data.model.AdminDashboardData>> _dashboardState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.admin.AdminState<com.shikshashila.app.data.model.AdminDashboardData>> dashboardState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.shikshashila.app.ui.admin.AdminState<com.shikshashila.app.data.model.FeesReportData>> _feesReportState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.admin.AdminState<com.shikshashila.app.data.model.FeesReportData>> feesReportState = null;
    
    @javax.inject.Inject
    public AdminViewModel(@org.jetbrains.annotations.NotNull
    com.shikshashila.app.domain.repository.AdminRepository adminRepository, @org.jetbrains.annotations.NotNull
    com.shikshashila.app.domain.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.admin.AdminState<com.shikshashila.app.data.model.AdminDashboardData>> getDashboardState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.admin.AdminState<com.shikshashila.app.data.model.FeesReportData>> getFeesReportState() {
        return null;
    }
    
    public final void fetchDashboard() {
    }
    
    public final void fetchFeesReport(@org.jetbrains.annotations.Nullable
    java.lang.String session) {
    }
    
    public final void logout() {
    }
}