package com.yourapp.focusflow.feature_affirmation.domain.usecase

import javax.inject.Inject

class GenerateAffirmation @Inject constructor() {
    operator fun invoke(): String = "You’ve got this."
}
