package com.ecohub.concurrencylab.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\bH\u00a6@\u00a2\u0006\u0002\u0010\u0010J\u001e\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u0013H\u00a6@\u00a2\u0006\u0002\u0010\u0014R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\n\u00a8\u0006\u0015"}, d2 = {"Lcom/ecohub/concurrencylab/data/repository/DeviceRepository;", "", "deviceState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/ecohub/concurrencylab/data/model/DeviceState;", "getDeviceState", "()Lkotlinx/coroutines/flow/StateFlow;", "maxTemperature", "", "getMaxTemperature", "()D", "minTemperature", "getMinTemperature", "forceUpdate", "", "newTemp", "(DLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setTemperature", "expectedVersion", "", "(DJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface DeviceRepository {
    
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.StateFlow<com.ecohub.concurrencylab.data.model.DeviceState> getDeviceState();
    
    public abstract double getMinTemperature();
    
    public abstract double getMaxTemperature();
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object setTemperature(double newTemp, long expectedVersion, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object forceUpdate(double newTemp, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}