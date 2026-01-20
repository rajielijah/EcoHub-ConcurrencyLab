package com.ecohub.concurrencylab.ui.device;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0010\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\b\u0010\u001c\u001a\u00020\u0016H\u0002J\u0010\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u0018H\u0002J\u001e\u0010\u001f\u001a\u00020\u00162\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u0018H\u0082@\u00a2\u0006\u0002\u0010#J\b\u0010$\u001a\u00020\u0016H\u0002J\u000e\u0010%\u001a\u00020\u00162\u0006\u0010&\u001a\u00020\'J\u0010\u0010(\u001a\u00020\u00162\u0006\u0010)\u001a\u00020\u0018H\u0002J\b\u0010*\u001a\u00020\u0016H\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\f0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006+"}, d2 = {"Lcom/ecohub/concurrencylab/ui/device/DeviceViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/ecohub/concurrencylab/data/repository/DeviceRepository;", "uiPreferences", "Lcom/ecohub/concurrencylab/ui/preferences/UiPreferences;", "(Lcom/ecohub/concurrencylab/data/repository/DeviceRepository;Lcom/ecohub/concurrencylab/ui/preferences/UiPreferences;)V", "_effects", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/ecohub/concurrencylab/ui/device/DeviceUiEffect;", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/ecohub/concurrencylab/ui/device/DeviceUiState;", "effects", "Lkotlinx/coroutines/flow/SharedFlow;", "getEffects", "()Lkotlinx/coroutines/flow/SharedFlow;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "adjustCurrentTemperature", "", "delta", "", "emitSnackbar", "message", "", "forceOverwriteFromConflict", "formatTemperature", "value", "handleConflict", "conflict", "Lcom/ecohub/concurrencylab/data/error/ConflictException;", "userTemp", "(Lcom/ecohub/concurrencylab/data/error/ConflictException;DLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeDeviceState", "onIntent", "intent", "Lcom/ecohub/concurrencylab/ui/device/DeviceIntent;", "submitTemperature", "newTemp", "submitTemperatureFromInput", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class DeviceViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.ecohub.concurrencylab.data.repository.DeviceRepository repository = null;
    @org.jetbrains.annotations.NotNull
    private final com.ecohub.concurrencylab.ui.preferences.UiPreferences uiPreferences = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.ecohub.concurrencylab.ui.device.DeviceUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.ecohub.concurrencylab.ui.device.DeviceUiState> uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.ecohub.concurrencylab.ui.device.DeviceUiEffect> _effects = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.SharedFlow<com.ecohub.concurrencylab.ui.device.DeviceUiEffect> effects = null;
    
    @javax.inject.Inject
    public DeviceViewModel(@org.jetbrains.annotations.NotNull
    com.ecohub.concurrencylab.data.repository.DeviceRepository repository, @org.jetbrains.annotations.NotNull
    com.ecohub.concurrencylab.ui.preferences.UiPreferences uiPreferences) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.ecohub.concurrencylab.ui.device.DeviceUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.SharedFlow<com.ecohub.concurrencylab.ui.device.DeviceUiEffect> getEffects() {
        return null;
    }
    
    public final void onIntent(@org.jetbrains.annotations.NotNull
    com.ecohub.concurrencylab.ui.device.DeviceIntent intent) {
    }
    
    private final void observeDeviceState() {
    }
    
    private final void submitTemperature(double newTemp) {
    }
    
    private final void submitTemperatureFromInput() {
    }
    
    private final void adjustCurrentTemperature(double delta) {
    }
    
    private final java.lang.Object handleConflict(com.ecohub.concurrencylab.data.error.ConflictException conflict, double userTemp, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void forceOverwriteFromConflict() {
    }
    
    private final void emitSnackbar(java.lang.String message) {
    }
    
    private final java.lang.String formatTemperature(double value) {
        return null;
    }
}