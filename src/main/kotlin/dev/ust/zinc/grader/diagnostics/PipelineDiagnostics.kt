package dev.ust.zinc.grader.diagnostics


/*
* Diagnostics generated from the pipeline (the output of pipeline stages)
* */
sealed class PipelineDiagnostics{

    /*
    * Diagnostics generated from the output of Score Stage
    * */
    sealed class ScoreStageDiagnostics{

        class ScoreOutOfRange(
            score: Double,
            internalMsg: String = "SCORE_RESULT_RANGE",
            msg: String = "Current Score $score is invalid, negative/more than total"
        ): Warning(internalMsg, msg)
    }
}