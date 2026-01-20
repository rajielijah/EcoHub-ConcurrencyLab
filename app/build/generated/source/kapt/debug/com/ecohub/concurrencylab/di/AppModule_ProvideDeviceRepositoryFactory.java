package com.ecohub.concurrencylab.di;

import com.ecohub.concurrencylab.data.repository.DeviceRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
  @Override
  public DeviceRepository get() {
    return provideDeviceRepository();
  }

  public static AppModule_ProvideDeviceRepositoryFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DeviceRepository provideDeviceRepository() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideDeviceRepository());
  }

  private static final class InstanceHolder {
    private static final AppModule_ProvideDeviceRepositoryFactory INSTANCE = new AppModule_ProvideDeviceRepositoryFactory();
  }
}
