package com.example.warroom.models

import com.example.warroom.R

enum class ChallengeEnum {
    TALK,
    SPORT,
    INTELLECTUAL;

    fun getTitleResId() : Int {
        return when(this) {
            TALK -> {
                R.string.challenge_talk_title
            }
            SPORT -> {
                R.string.challenge_sport_title
            }
            INTELLECTUAL -> {
                R.string.challenge_intellectual_title
            }
        }
    }
}