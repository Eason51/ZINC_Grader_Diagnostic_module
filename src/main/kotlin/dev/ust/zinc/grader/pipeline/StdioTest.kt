package dev.ust.zinc.grader.pipeline

import dev.ust.zinc.grader.diagnostics.*
import dev.ust.zinc.grader.runner.Runner

/**
 * A stage which checks the student program with a given output.
 *
 * @property id Test case ID. Constraint: All IDs must be unique within a pipeline.
 * @property file File to execute.
 * @property stdin Standard input for the target program. Constraint: Mutually exclusive with [file_stdin].
 * @property file_stdin File to read for standard input. Constraint: Mutually exclusive with [stdin].
 * @property expected Expected output for the target program. Constraint: Mutually exclusive with [file_expected].
 * @property file_expected File to read for expected output. Constraint: Mutually exclusive with [expected].
 * @property visibility The visibility of the test case.
 * @property args Arguments to provide to the test program.
 * @property score The score of this test case. Constraint: Greater than 0, must not be [Double.POSITIVE_INFINITY],
 * [Double.NEGATIVE_INFINITY], or [Double.NaN].
 */
class StdioTest(
    val id: Int,
    val file: String,
    val stdin: String?,
    val file_stdin: String?,
    val expected: String?,
    val file_expected: String?,
    val visibility: Visibility,
    val args: List<String>?,
    val score: Double?
) : PipelineStage(StageResult.ScoringResult::class, StageResult.Score::class) {

    init{
        diagnoseInput()
    }

    companion object{
        private val idList: ArrayList<Int> = ArrayList<Int>()
    }

    enum class Visibility {
        ALWAYS_VISIBLE, ALWAYS_HIDDEN, VISIBLE_AFTER_GRADING, VISIBLE_AFTER_GRADING_IF_FAILED
    }

    override val cmd = ""

    override fun execute(input: StageResult): StageResult {

        println("StdioTest: Dummy Implementation.")

        val stageResult = input as StageResult.ScoringResult
        return StageResult.ScoringResult(stageResult.result, stageResult.score)
    }

    /**
     * executed in the constructor
     */
    override fun diagnoseInput() {
        if(!diagnoseId())
            emit(StdioTestDiagnostics.NonUniqueId())

        if(!diagnoseMutualExclusion(stdin, file_stdin))
            emit(StdioTestDiagnostics.MutualExclusionViolation("stdin", "file_stdin"))

        if(!diagnoseMutualExclusion(expected, file_expected))
            emit(StdioTestDiagnostics.MutualExclusionViolation("expected", "file_expected"))

        if(!diagnoseScore(score))
            emit(StdioTestDiagnostics.InvalidScore(score))
    }

    /**
     * Diagnose whether current [id] is unique
     */
    private fun diagnoseId(): Boolean{
        idList.forEach{
            if(it == id)
                return false
        }
        idList.add(id)
        return true
    }

    /**
     * Diagnose whether two properties fulfil mutual exclusion constraint
     */
    private fun diagnoseMutualExclusion(property1: Any?, property2: Any?): Boolean{
        if(property1 == null)
        {
            if(property2 == null)
                return false
        }
        else
        {
            if(property2 != null)
                return false
        }
        return true
    }

    /**
     * Diagnose whether [score] is valid
     */
    private fun diagnoseScore(score: Double?): Boolean{
        if(score == null || score <= 0 || score in listOf(
                Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NaN
            ))
            return false
        return true
    }

}