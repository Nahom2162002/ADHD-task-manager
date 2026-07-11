package com.yourapp.focusflow.di

import com.yourapp.focusflow.feature_achievement.data.repository.AchievementRepositoryImpl
import com.yourapp.focusflow.feature_achievement.domain.repository.AchievementRepository
import com.yourapp.focusflow.feature_focus.data.repository.FocusRepositoryImpl
import com.yourapp.focusflow.feature_focus.domain.repository.FocusRepository
import com.yourapp.focusflow.feature_schedule.data.remote.GeminiScheduleService
import com.yourapp.focusflow.feature_schedule.domain.repository.ScheduleAiService
import com.yourapp.focusflow.feature_task.data.repository.TaskRepositoryImpl
import com.yourapp.focusflow.feature_task.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository

    @Binds
    @Singleton
    abstract fun bindFocusRepository(impl: FocusRepositoryImpl): FocusRepository

    @Binds
    @Singleton
    abstract fun bindAchievementRepository(impl: AchievementRepositoryImpl): AchievementRepository

    @Binds
    @Singleton
    abstract fun bindScheduleAiService(impl: GeminiScheduleService): ScheduleAiService
}
