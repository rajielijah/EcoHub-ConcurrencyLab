package com.ecohub.concurrencylab.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000e\u0018\u0000 *2\u00020\u0001:\u0001*B7\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\u000e\u0010\u001c\u001a\u00020\u001dH\u0082@\u00a2\u0006\u0002\u0010\u001eJ\u0010\u0010\u001f\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u0005H\u0002J\u0006\u0010!\u001a\u00020\u001dJ\u0010\u0010\"\u001a\u00020\u00052\u0006\u0010#\u001a\u00020\u0005H\u0002J\u0016\u0010$\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020\u0005H\u0096@\u00a2\u0006\u0002\u0010&J\u001e\u0010\'\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u0003H\u0096@\u00a2\u0006\u0002\u0010)R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006+"}, d2 = {"Lcom/ecohub/concurrencylab/data/repository/FakeDeviceRepository;", "Lcom/ecohub/concurrencylab/data/repository/DeviceRepository;", "technicianIntervalMillis", "", "minTemp", "", "maxTemp", "scope", "Lkotlinx/coroutines/CoroutineScope;", "latencyProvider", "Lcom/ecohub/concurrencylab/data/repository/LatencyProvider;", "(JDDLkotlinx/coroutines/CoroutineScope;Lcom/ecohub/concurrencylab/data/repository/LatencyProvider;)V", "_deviceState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/ecohub/concurrencylab/data/model/DeviceState;", "deviceState", "Lkotlinx/coroutines/flow/StateFlow;", "getDeviceState", "()Lkotlinx/coroutines/flow/StateFlow;", "maxTemperature", "getMaxTemperature", "()D", "minTemperature", "getMinTemperature", "mutex", "Lkotlinx/coroutines/sync/Mutex;", "technicianJob", "Lkotlinx/coroutines/Job;", "advanceTechnicianReading", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clampTemperature", "value", "close", "computeNextTechnicianTemperature", "current", "forceUpdate", "newTemp", "(DLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setTemperature", "expectedVersion", "(DJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public final class FakeDeviceRepository implements com.ecohub.concurrencylab.data.repository.DeviceRepository {
    private final long technicianIntervalMillis = 0L;
    private final double minTemp = 0.0;
    private final double maxTemp = 0.0;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull
    private final com.ecohub.concurrencylab.data.repository.LatencyProvider latencyProvider = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.sync.Mutex mutex = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.ecohub.concurrencylab.data.model.DeviceState> _deviceState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.ecohub.concurrencylab.data.model.DeviceState> deviceState = null;
    private final double minTemperature = 0.0;
    private final double maxTemperature = 0.0;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.Job technicianJob = null;
    private static final long TECHNICIAN_INTERVAL_MILLIS = 5000L;
    private static final double TECHNICIAN_STEP = 0.5;
    private static final double MIN_TEMP = 5.0;
    private static final double MAX_TEMP = 30.0;
    private static final double DEFAULT_TEMP = 20.0;
    @org.jetbrains.annotations.NotNull
    public static final com.ecohub.concurrencylab.data.repository.FakeDeviceRepository.Companion Companion = null;
    
    public FakeDeviceRepository(long technicianIntervalMillis, double minTemp, double maxTemp, @org.jetbrains.annotations.NotNull
    kotlinx.coroutines.CoroutineScope scope, @org.jetbrains.annotations.NotNull
    com.ecohub.concurrencylab.data.repository.LatencyProvider latencyProvider) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public kotlinx.coroutines.flow.StateFlow<com.ecohub.concurrencylab.data.model.DeviceState> getDeviceState() {
        return null;
    }
    
    @java.lang.Override
    public double getMinTemperature() {
        return 0.0;
    }
    
    @java.lang.Override
    public double getMaxTemperature() {
        return 0.0;
    }
    
    private final double clampTemperature(double value) {
        return 0.0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object setTemperature(double newTemp, long expectedVersion, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object forceUpdate(double newTemp, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object advanceTechnicianReading(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final double computeNextTechnicianTemperature(double current) {
        return 0.0;
    }
    
    public final void close() {
    }
    
    public FakeDeviceRepository() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/ecohub/concurrencylab/data/repository/FakeDeviceRepository$Companion;", "", "()V", "DEFAULT_TEMP", "", "MAX_TEMP", "MIN_TEMP", "TECHNICIAN_INTERVAL_MILLIS", "", "TECHNICIAN_STEP", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}