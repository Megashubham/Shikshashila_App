package com.shikshashila.app.ui.auth;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0019\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2 = {"Lcom/shikshashila/app/ui/auth/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/shikshashila/app/domain/repository/AuthRepository;", "(Lcom/shikshashila/app/domain/repository/AuthRepository;)V", "savedUser", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/shikshashila/app/data/model/User;", "getSavedUser", "()Lkotlinx/coroutines/flow/StateFlow;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.data.model.User> savedUser = null;
    
    @javax.inject.Inject
    public MainViewModel(@org.jetbrains.annotations.NotNull
    com.shikshashila.app.domain.repository.AuthRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.shikshashila.app.data.model.User> getSavedUser() {
        return null;
    }
}