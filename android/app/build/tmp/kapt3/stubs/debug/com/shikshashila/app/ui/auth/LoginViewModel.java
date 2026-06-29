package com.shikshashila.app.ui.auth;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fJ\u0006\u0010\u0011\u001a\u00020\rR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/shikshashila/app/ui/auth/LoginViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/shikshashila/app/domain/repository/AuthRepository;", "(Lcom/shikshashila/app/domain/repository/AuthRepository;)V", "_authState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/shikshashila/app/ui/auth/AuthState;", "authState", "Lkotlinx/coroutines/flow/StateFlow;", "getAuthState", "()Lkotlinx/coroutines/flow/StateFlow;", "login", "", "loginId", "", "password", "resetState", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class LoginViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.shikshashila.app.domain.repository.AuthRepository repository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.shikshashila.app.ui.auth.AuthState> _authState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.auth.AuthState> authState = null;
    
    @javax.inject.Inject
    public LoginViewModel(@org.jetbrains.annotations.NotNull
    com.shikshashila.app.domain.repository.AuthRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.ui.auth.AuthState> getAuthState() {
        return null;
    }
    
    public final void login(@org.jetbrains.annotations.NotNull
    java.lang.String loginId, @org.jetbrains.annotations.NotNull
    java.lang.String password) {
    }
    
    public final void resetState() {
    }
}