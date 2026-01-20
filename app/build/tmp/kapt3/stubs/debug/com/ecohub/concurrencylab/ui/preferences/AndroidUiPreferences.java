package com.ecohub.concurrencylab.ui.preferences;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tH\u0016R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/ecohub/concurrencylab/ui/preferences/AndroidUiPreferences;", "Lcom/ecohub/concurrencylab/ui/preferences/UiPreferences;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "isCollaborativeModeEnabled", "", "setCollaborativeModeEnabled", "", "enabled", "app_debug"})
public final class AndroidUiPreferences implements com.ecohub.concurrencylab.ui.preferences.UiPreferences {
    private final android.content.SharedPreferences prefs = null;
    
    public AndroidUiPreferences(@org.jetbrains.annotations.NotNull
    android.app.Application application) {
        super();
    }
    
    @java.lang.Override
    public boolean isCollaborativeModeEnabled() {
        return false;
    }
    
    @java.lang.Override
    public void setCollaborativeModeEnabled(boolean enabled) {
    }
}