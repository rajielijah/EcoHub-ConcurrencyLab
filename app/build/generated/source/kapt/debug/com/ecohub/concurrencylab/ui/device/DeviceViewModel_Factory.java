package com.ecohub.concurrencylab.ui.device;

import com.ecohub.concurrencylab.data.repository.DeviceRepository;
import com.ecohub.concurrencylab.ui.preferences.UiPreferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DeviceViewModel_Factory implements Factory<DeviceViewModel> {
  private final Provider<DeviceRepository> repositoryProvider;

  private final Provider<UiPreferences> uiPreferencesProvider;

  public DeviceViewModel_Factory(Provider<DeviceRepository> repositoryProvider,
      Provider<UiPreferences> uiPreferencesProvider) {
    this.repositoryProvider = repositoryProvider;
    this.uiPreferencesProvider = uiPreferencesProvider;
  }

  @Override
  public DeviceViewModel get() {
    return newInstance(repositoryProvider.get(), uiPreferencesProvider.get());
  }

  public static DeviceViewModel_Factory create(Provider<DeviceRepository> repositoryProvider,
      Provider<UiPreferences> uiPreferencesProvider) {
    return new DeviceViewModel_Factory(repositoryProvider, uiPreferencesProvider);
  }

  public static DeviceViewModel newInstance(DeviceRepository repository,
      UiPreferences uiPreferences) {
    return new DeviceViewModel(repository, uiPreferences);
  }
}
