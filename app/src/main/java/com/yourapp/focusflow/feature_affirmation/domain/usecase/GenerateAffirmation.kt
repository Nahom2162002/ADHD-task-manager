package com.yourapp.focusflow.feature_affirmation.domain.usecase

import javax.inject.Inject

/**
 * Provides immediate positive reinforcement.
 * For ADHD users, this "dopamine hit" helps bridge the gap between 
 * finishing a task and feeling the internal reward.
 */
class GenerateAffirmation @Inject constructor() {
    private val affirmations = listOf(
        "You're doing great!",
        "One step at a time—you've got this!",
        "Focused and productive. Well done!",
        "Progress is progress, no matter how small.",
        "Your future self is already thanking you!",
        "Task complete! Keep that momentum going.",
        "You're crushing it today! 🚀",
        "Small wins lead to big victories.",
        "That's one less thing on your mind!"
    )

    operator fun invoke(): String {
        return affirmations.random()
    }
}
