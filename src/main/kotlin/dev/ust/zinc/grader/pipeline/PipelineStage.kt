package dev.ust.zinc.grader.pipeline

import kotlin.reflect.KClass
import dev.ust.zinc.grader.diagnostics.*
import dev.ust.zinc.grader.runner.Runner

/**
 * A stage of a pipeline which performs a single task.
 *
 * @property inType The input type accepted by this class.
 * @property outType The output type emitted by this class.
 */
abstract class PipelineStage(
    val inType: KClass<out StageResult>,
    val outType: KClass<out StageResult>
): StageEmit {

    private lateinit var _runner: Runner
    protected val runner: Runner get() = _runner

    /**
     * The command to be executed by this class.
     */
    abstract val cmd: String

    /**
     * Executes this stage using the given [input].
     */
    abstract fun execute(input: StageResult): StageResult

    fun putRunner(runner: Runner) {
        this._runner = runner
    }

    /**
     * Diagnose a [PipelineStage] before the [runner] starts execution
     */
    protected abstract fun diagnoseInput()

}
