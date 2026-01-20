package com.ecohub.concurrencylab.ui.device;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Bc\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010 \u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0018J\t\u0010!\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0007H\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\fH\u00c6\u0003J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003Jl\u0010(\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010)J\u0013\u0010*\u001a\u00020\u00032\b\u0010+\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010,\u001a\u00020-H\u00d6\u0001J\t\u0010.\u001a\u00020\u0007H\u00d6\u0001R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\u0019\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u001c\u001a\u00020\u00078F\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\u001bR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001b\u00a8\u0006/"}, d2 = {"Lcom/ecohub/concurrencylab/ui/device/DeviceUiState;", "", "loading", "", "temperature", "", "versionLabel", "", "temperatureInput", "collaborativeMode", "isUpdating", "conflictDialog", "Lcom/ecohub/concurrencylab/ui/device/ConflictDialogState;", "canIncrement", "canDecrement", "(ZLjava/lang/Double;Ljava/lang/String;Ljava/lang/String;ZZLcom/ecohub/concurrencylab/ui/device/ConflictDialogState;ZZ)V", "getCanDecrement", "()Z", "getCanIncrement", "getCollaborativeMode", "getConflictDialog", "()Lcom/ecohub/concurrencylab/ui/device/ConflictDialogState;", "getLoading", "getTemperature", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getTemperatureInput", "()Ljava/lang/String;", "temperatureText", "getTemperatureText", "getVersionLabel", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(ZLjava/lang/Double;Ljava/lang/String;Ljava/lang/String;ZZLcom/ecohub/concurrencylab/ui/device/ConflictDialogState;ZZ)Lcom/ecohub/concurrencylab/ui/device/DeviceUiState;", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class DeviceUiState {
    private final boolean loading = false;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Double temperature = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String versionLabel = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String temperatureInput = null;
    private final boolean collaborativeMode = false;
    private final boolean isUpdating = false;
    @org.jetbrains.annotations.Nullable
    private final com.ecohub.concurrencylab.ui.device.ConflictDialogState conflictDialog = null;
    private final boolean canIncrement = false;
    private final boolean canDecrement = false;
    
    public DeviceUiState(boolean loading, @org.jetbrains.annotations.Nullable
    java.lang.Double temperature, @org.jetbrains.annotations.NotNull
    java.lang.String versionLabel, @org.jetbrains.annotations.NotNull
    java.lang.String temperatureInput, boolean collaborativeMode, boolean isUpdating, @org.jetbrains.annotations.Nullable
    com.ecohub.concurrencylab.ui.device.ConflictDialogState conflictDialog, boolean canIncrement, boolean canDecrement) {
        super();
    }
    
    public final boolean getLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Double getTemperature() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getVersionLabel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getTemperatureInput() {
        return null;
    }
    
    public final boolean getCollaborativeMode() {
        return false;
    }
    
    public final boolean isUpdating() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.ecohub.concurrencylab.ui.device.ConflictDialogState getConflictDialog() {
        return null;
    }
    
    public final boolean getCanIncrement() {
        return false;
    }
    
    public final boolean getCanDecrement() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getTemperatureText() {
        return null;
    }
    
    public DeviceUiState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Double component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component4() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final boolean component6() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.ecohub.concurrencylab.ui.device.ConflictDialogState component7() {
        return null;
    }
    
    public final boolean component8() {
        return false;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.ecohub.concurrencylab.ui.device.DeviceUiState copy(boolean loading, @org.jetbrains.annotations.Nullable
    java.lang.Double temperature, @org.jetbrains.annotations.NotNull
    java.lang.String versionLabel, @org.jetbrains.annotations.NotNull
    java.lang.String temperatureInput, boolean collaborativeMode, boolean isUpdating, @org.jetbrains.annotations.Nullable
    com.ecohub.concurrencylab.ui.device.ConflictDialogState conflictDialog, boolean canIncrement, boolean canDecrement) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
}