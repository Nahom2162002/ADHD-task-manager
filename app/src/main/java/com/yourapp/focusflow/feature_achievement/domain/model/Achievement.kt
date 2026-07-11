package com.yourapp.focusflow.feature_achievement.domain.model

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val iconResId: Int,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null
)
