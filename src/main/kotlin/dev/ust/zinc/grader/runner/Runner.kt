package dev.ust.zinc.grader.runner

import dev.ust.zinc.grader.pipeline.Pipeline
import dev.ust.zinc.grader.pipeline.StageResult

/**
 * The runner for a pipeline.
 *
 * The runner is responsible for managing all execution state of a pipeline.
 *
 * @property pipeline The pipeline which should be executed by this runner.
 */
class Runner(val pipeline: Pipeline) {

    /**
     * Diagnostics generated here are before the execution of runner
     */
    init {
        pipeline.forEach {
            it.putRunner(this)
        }
    }

    /**
     * Sequentially executes [pipeline] using [input] as the initial input to the pipeline.
     */
    fun execWithInput(input: StageResult): StageResult? {
        return try {
            pipeline.fold(input) { acc, stage -> stage.execute(acc) }
        } catch (tr: Throwable) {
            println(tr)
            null
        }
    }

}