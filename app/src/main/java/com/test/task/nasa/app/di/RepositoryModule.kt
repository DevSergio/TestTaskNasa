package com.test.task.nasa.app.di

import com.test.task.nasa.app.data.remote.NasaRemoteSource
import com.test.task.nasa.app.repository.INasaRepository
import com.test.task.nasa.app.repository.NasaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideMainRepository(
        remoteSource: NasaRemoteSource
    ): INasaRepository {
        return NasaRepository(remoteSource)
    }
}