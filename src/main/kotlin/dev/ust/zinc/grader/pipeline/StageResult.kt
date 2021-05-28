package dev.ust.zinc.grader.pipeline

import dev.ust.zinc.grader.model.ScoreUnit
import dev.ust.zinc.grader.diagnostics.*

/**
 * The result of a pipeline stage.
 *
 * This class is used to abstract the possible results of a pipeline stage into a set of enumerable types.
 */
sealed class StageResult: PipelineEmit{

    /**
     * A [StageResult] which represents a Docker volume.
     *
     * @property id The ID of the volume.
     */
    data class Volume(val id: String) : StageResult()

    /**
     * A [StageResult] which represents a [StageResult] with a list of [ScoreUnit] aggregated up to this point.
     *
     * @property result The [StageResult] of the stage.
     * @property score The list of [ScoreUnit] aggregated up to this point of the pipeline.
     */
    data class ScoringResult(val result: StageResult, val score: List<ScoreUnit>) : StageResult()

    /**
     * A [StageResult] which represents a single [ScoreUnit].
     *
     * @property score The accumulated score represented as a [ScoreUnit].
     */
    data class Score(val score: ScoreUnit) : StageResult(){
        init{
            if(score.score !in 0.0..score.total)
                emit(PipelineDiagnostics.ScoreStageDiagnostics.ScoreOutOfRange(score.score))
        }
    }
}