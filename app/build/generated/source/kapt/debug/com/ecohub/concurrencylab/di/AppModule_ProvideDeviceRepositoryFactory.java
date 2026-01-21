package com.ecohub.concurrencylab.di;

import com.ecohub.concurrencylab.data.repository.DeviceRepository;
import com.ecohub.concurrencylab.data.repository.LatencyProvider;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class AppModule_ProvideDeviceRepositoryFactory implements Factory<DeviceRepository> {
  private final Provider<LatencyProvider> latencyProvider;

  public AppModule_ProvideDeviceRepositoryFactory(Provider<LatencyProvider> latencyProvider) {
    this.latencyProvider = latencyProvider;
  }

  @Override
  public DeviceRepository get() {
    return provideDeviceRepository(latencyProvider.get());
  }

  public static AppModule_ProvideDeviceRepositoryFactory create(
      Provider<LatencyProvider> latencyProvider) {
    return new AppModule_ProvideDeviceRepositoryFactory(latencyProvider);
  }

  public static DeviceRepository provideDeviceRepository(LatencyProvider latencyProvider) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideDeviceRepository(latencyProvider));
  }
}
