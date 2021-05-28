package dev.ust.zinc.grader.pipeline

import dev.ust.zinc.grader.diagnostics.*
import dev.ust.zinc.grader.model.ScoreUnit
import kotlin.reflect.cast

/**
 * A stage which aggregates the score of all previous stages, and performs clipping and normalization.
 *
 * Note that normalization is performed _after_ clipping.
 *
 * @property normalization The score to normalize to; If `null`, implies not to normalize the score.
 * @property clipMin Minimum value the score can be; If `null`, implies that the minimum is unbounded.
 * @property clipMax Maximum value the score can be; If `null`, implies that the maximum is unbounded.
 */
class Score(
    val normalization: Double?,
    val clipMin: Double?,
    val clipMax: Double?
) : PipelineStage(StageResult.ScoringResult::class, StageResult.Score::class) {

    init{
        diagnoseInput()
    }

    override val cmd = ""

    override fun execute(input: StageResult): StageResult {
        val res = inType.cast(input) as StageResult.ScoringResult

        val clipRange = (clipMin ?: Double.NEGATIVE_INFINITY)..(clipMax ?: Double.POSITIVE_INFINITY)

        val score = res.score.sumByDouble { it.score }.coerceIn(clipRange)
        val total = res.score.sumByDouble { it.total }.coerceIn(clipRange)

        val normScore = normalization?.let { score / total * it } ?: score
        val normTotal = normalization ?: total

        if(!diagnoseCurrentScore(normScore, normTotal))
            emit(ScoreDiagnostics.ScoreOutOfRange(normScore))

        return StageResult.Score(ScoreUnit(normScore, normTotal))

    }

    override fun diagnoseInput(){
        if(!diagnoseScoreRange())
            emit(ScoreDiagnostics.InvalidRange(clipMin, clipMax))
    }

    /**
     * check whether the supplied range is valid
     */
    private fun diagnoseScoreRange(): Boolean{
        if(clipMin == null || clipMax == null)
            return true
        return (clipMin <= clipMax)
    }

    /**
     * Check whether the current score is within a valid range
     */
    private fun diagnoseCurrentScore(curr: Double, max: Double): Boolean{
        return (curr !in 0.0..max)
    }
}