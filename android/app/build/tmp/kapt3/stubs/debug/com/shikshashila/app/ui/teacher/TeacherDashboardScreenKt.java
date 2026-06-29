package com.shikshashila.app.ui.teacher;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000@\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a3\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u0011H\u0007\u00f8\u0001\u0000\u001a$\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\n0\u0016H\u0007\u001a4\u0010\u0017\u001a\u00020\n2\b\b\u0002\u0010\u0018\u001a\u00020\u00192\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\n0\u00162\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\n0\u001bH\u0007\"\u0013\u0010\u0000\u001a\u00020\u0001X\u0082\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0013\u0010\u0003\u001a\u00020\u0001X\u0082\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0013\u0010\u0004\u001a\u00020\u0001X\u0082\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0013\u0010\u0005\u001a\u00020\u0001X\u0082\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0013\u0010\u0006\u001a\u00020\u0001X\u0082\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0013\u0010\u0007\u001a\u00020\u0001X\u0082\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0013\u0010\b\u001a\u00020\u0001X\u0082\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\n\u0002\u0010\u0002\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001c"}, d2 = {"BgFrom", "Landroidx/compose/ui/graphics/Color;", "J", "BgTo", "CardWhite", "Dark", "Muted", "Pink", "Purple", "StatCard", "", "modifier", "Landroidx/compose/ui/Modifier;", "title", "", "value", "gradient", "", "TeacherDashboardContent", "data", "Lcom/shikshashila/app/data/model/TeacherDashboardData;", "onNavigateTo", "Lkotlin/Function1;", "TeacherDashboardScreen", "viewModel", "Lcom/shikshashila/app/ui/teacher/TeacherViewModel;", "onLogout", "Lkotlin/Function0;", "app_debug"})
public final class TeacherDashboardScreenKt {
    private static final long Purple = 0L;
    private static final long Pink = 0L;
    private static final long Dark = 0L;
    private static final long Muted = 0L;
    private static final long BgFrom = 0L;
    private static final long BgTo = 0L;
    private static final long CardWhite = 0L;
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void TeacherDashboardScreen(@org.jetbrains.annotations.NotNull
    com.shikshashila.app.ui.teacher.TeacherViewModel viewModel, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateTo, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogout) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void TeacherDashboardContent(@org.jetbrains.annotations.NotNull
    com.shikshashila.app.data.model.TeacherDashboardData data, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateTo) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void StatCard(@org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String value, @org.jetbrains.annotations.NotNull
    java.util.List<androidx.compose.ui.graphics.Color> gradient) {
    }
}