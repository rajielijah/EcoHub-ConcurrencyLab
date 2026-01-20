package com.ecohub.concurrencylab.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u0004H\u0096@\u00a2\u0006\u0002\u0010\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/ecohub/concurrencylab/data/repository/NoLatency;", "Lcom/ecohub/concurrencylab/data/repository/LatencyProvider;", "()V", "delay", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class NoLatency implements com.ecohub.concurrencylab.data.repository.LatencyProvider {
    @org.jetbrains.annotations.NotNull
    public static final com.ecohub.concurrencylab.data.repository.NoLatency INSTANCE = null;
    
    private NoLatency() {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object delay(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}