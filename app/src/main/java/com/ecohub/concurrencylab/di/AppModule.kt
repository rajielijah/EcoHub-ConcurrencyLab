package com.ecohub.concurrencylab.di

import android.app.Application
import android.content.Context
import com.ecohub.concurrencylab.data.repository.DeviceRepository
import com.ecohub.concurrencylab.data.repository.FakeDeviceRepository
import com.ecohub.concurrencylab.data.repository.LatencyProvider
import com.ecohub.concurrencylab.data.repository.NoLatency
import com.ecohub.concurrencylab.data.repository.RandomLatency
import com.ecohub.concurrencylab.ui.preferences.AndroidUiPreferences
import com.ecohub.concurrencylab.ui.preferences.UiPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLatencyProvider(): LatencyProvider = RandomLatency(maxMillis = 2_000)

    @Provides
    @Singleton
    fun provideDeviceRepository(
        latencyProvider: LatencyProvider
    ): DeviceRepository =
        FakeDeviceRepository(
            latencyProvider = latencyProvider
        )

    @Provides
    @Singleton
    fun provideUiPreferences(
        @ApplicationContext context: Context
    ): UiPreferences =
        AndroidUiPreferences(context as Application)


}
