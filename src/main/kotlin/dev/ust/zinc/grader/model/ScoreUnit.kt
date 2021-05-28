package dev.ust.zinc.grader.model

/**
 * Representation of a score, constituted by the score achieved by the submission and the maximum score.
 *
 * @property score The score achieved by the submission.
 * @property total The maximum achievable score.
 */
data class ScoreUnit(
    val score: Double,
    val total: Double
)