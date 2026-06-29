package com.shikshashila.app.ui.admin;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0006\u001a$\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\n0\u000eH\u0007\u001a4\u0010\u0010\u001a\u00020\n2\b\b\u0002\u0010\u0011\u001a\u00020\u00122\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\n0\u000e2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\u0014H\u0007\u001a;\u0010\u0015\u001a\u00020\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\u001dH\u0007\u00f8\u0001\u0000\u001aE\u0010\u001e\u001a\u00020\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020\u00012\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\n0\u0014H\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b!\u0010\"\"\u0013\u0010\u0000\u001a\u00020\u0001X\u0082\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0013\u0010\u0003\u001a\u00020\u0001X\u0082\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0013\u0010\u0004\u001a\u00020\u0001X\u0082\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0013\u0010\u0005\u001a\u00020\u0001X\u0082\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0013\u0010\u0006\u001a\u00020\u0001X\u0082\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0013\u0010\u0007\u001a\u00020\u0001X\u0082\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0013\u0010\b\u001a\u00020\u0001X\u0082\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\n\u0002\u0010\u0002\u0082\u0002\u000b\n\u0002\b\u0019\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006#"}, d2 = {"BgFrom", "Landroidx/compose/ui/graphics/Color;", "J", "BgTo", "CardWhite", "Dark", "Muted", "Pink", "Purple", "AdminDashboardContent", "", "data", "Lcom/shikshashila/app/data/model/AdminDashboardData;", "onNavigateTo", "Lkotlin/Function1;", "", "AdminDashboardScreen", "viewModel", "Lcom/shikshashila/app/ui/admin/AdminViewModel;", "onLogout", "Lkotlin/Function0;", "AdminStatCard", "modifier", "Landroidx/compose/ui/Modifier;", "title", "value", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "gradient", "", "ModuleButton", "color", "onClick", "ModuleButton-42QJj7c", "(Landroidx/compose/ui/Modifier;Ljava/lang/String;Landroidx/compose/ui/graphics/vector/ImageVector;JLkotlin/jvm/functions/Function0;)V", "app_debug"})
public final class AdminDashboardScreenKt {
    private static final long Purple = 0L;
    private static final long Pink = 0L;
    private static final long Dark = 0L;
    private static final long Muted = 0L;
    private static final long BgFrom = 0L;
    private static final long BgTo = 0L;
    private static final long CardWhite = 0L;
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void AdminDashboardScreen(@org.jetbrains.annotations.NotNull
    com.shikshashila.app.ui.admin.AdminViewModel viewModel, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateTo, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogout) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AdminDashboardContent(@org.jetbrains.annotations.NotNull
    com.shikshashila.app.data.model.AdminDashboardData data, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateTo) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AdminStatCard(@org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String value, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.graphics.vector.ImageVector icon, @org.jetbrains.annotations.NotNull
    java.util.List<androidx.compose.ui.graphics.Color> gradient) {
    }
}