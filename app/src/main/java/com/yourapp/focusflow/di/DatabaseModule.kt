package com.yourapp.focusflow.di

import android.content.Context
import androidx.room.Room
import com.yourapp.focusflow.feature_task.data.local.FocusFlowDatabase
import com.yourapp.focusflow.feature_task.data.local.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): FocusFlowDatabase =
        Room.databaseBuilder(
            context,
            FocusFlowDatabase::class.java,
            "focus_flow.db",
        ).build()

    @Provides
    fun provideTaskDao(database: FocusFlowDatabase): TaskDao = database.taskDao()
}
