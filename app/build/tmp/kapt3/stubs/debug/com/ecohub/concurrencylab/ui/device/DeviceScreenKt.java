package com.ecohub.concurrencylab.ui.device;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000J\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\t\u001a,\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0006H\u0003\u001a,\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0003\u001a,\u0010\r\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\u00062\u0006\u0010\u0011\u001a\u00020\u0012H\u0007\u001a\b\u0010\u0013\u001a\u00020\u0001H\u0003\u001aB\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u00032\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00010\u00062\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0003\u001aK\u0010\u001b\u001a\u00020\u00012\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u001f\u001a\u00020\u00032\u0006\u0010 \u001a\u00020\u00032\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u00010\u0006H\u0003\u00a2\u0006\u0002\u0010\"\u001a\u0017\u0010#\u001a\u00020\u00162\b\u0010$\u001a\u0004\u0018\u00010\u001dH\u0002\u00a2\u0006\u0002\u0010%\u00a8\u0006&"}, d2 = {"CollaborativeModeCard", "", "enabled", "", "enabledInteraction", "onEnabledChanged", "Lkotlin/Function1;", "ConflictDialog", "state", "Lcom/ecohub/concurrencylab/ui/device/ConflictDialogState;", "onKeepTechnician", "Lkotlin/Function0;", "onOverwrite", "DeviceScreen", "Lcom/ecohub/concurrencylab/ui/device/DeviceUiState;", "onIntent", "Lcom/ecohub/concurrencylab/ui/device/DeviceIntent;", "snackbarHostState", "Landroidx/compose/material3/SnackbarHostState;", "ScreenHeader", "TemperatureControlsCard", "temperatureInput", "", "loading", "isUpdating", "onTemperatureInputChanged", "onUpdateClicked", "TemperatureHeroCard", "temperature", "", "versionLabel", "canIncrement", "canDecrement", "onAdjustClicked", "(Ljava/lang/Double;Ljava/lang/String;ZZZLkotlin/jvm/functions/Function1;)V", "formatTemp", "value", "(Ljava/lang/Double;)Ljava/lang/String;", "app_debug"})
public final class DeviceScreenKt {
    
    @androidx.compose.runtime.Composable
    public static final void DeviceScreen(@org.jetbrains.annotations.NotNull
    com.ecohub.concurrencylab.ui.device.DeviceUiState state, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.ecohub.concurrencylab.ui.device.DeviceIntent, kotlin.Unit> onIntent, @org.jetbrains.annotations.NotNull
    androidx.compose.material3.SnackbarHostState snackbarHostState) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void ScreenHeader() {
    }
    
    @androidx.compose.runtime.Composable
    private static final void TemperatureHeroCard(java.lang.Double temperature, java.lang.String versionLabel, boolean loading, boolean canIncrement, boolean canDecrement, kotlin.jvm.functions.Function1<? super java.lang.Double, kotlin.Unit> onAdjustClicked) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void TemperatureControlsCard(java.lang.String temperatureInput, boolean loading, boolean isUpdating, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onTemperatureInputChanged, kotlin.jvm.functions.Function0<kotlin.Unit> onUpdateClicked) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void CollaborativeModeCard(boolean enabled, boolean enabledInteraction, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onEnabledChanged) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void ConflictDialog(com.ecohub.concurrencylab.ui.device.ConflictDialogState state, kotlin.jvm.functions.Function0<kotlin.Unit> onKeepTechnician, kotlin.jvm.functions.Function0<kotlin.Unit> onOverwrite) {
    }
    
    private static final java.lang.String formatTemp(java.lang.Double value) {
        return null;
    }
}