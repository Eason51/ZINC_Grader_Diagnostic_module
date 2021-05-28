package dev.ust.zinc.grader.diagnostics

/*
* Diagnostics generated from within Score class
* */
sealed class ScoreDiagnostics {

    class InvalidRange(
        min: Double?,
        max: Double?,
        internalMsg: String = "SCORE_RANGE",
        msg: String = "Score range [$min, $max] is invalid"
    ): Error(internalMsg, msg)

    class ScoreOutOfRange(
        score: Double,
        internalMsg: String = "CURRENT_SCORE_VALIDITY",
        msg: String = "Current Score $score is invalid, negative/more than total"
    ): Warning(internalMsg, msg)
}