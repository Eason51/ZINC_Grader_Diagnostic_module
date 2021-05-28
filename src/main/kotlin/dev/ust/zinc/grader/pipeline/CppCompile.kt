package dev.ust.zinc.grader.pipeline

import dev.ust.zinc.grader.diagnostics.*

/**
 * A stage which invokes the C++ compiler to compile some files.
 *
 * @property compiler The compiler to invoke.
 * @property inputs The list of input files to provide to the compiler.
 * @property flags The list of flags to provide to the compiler.
 * @property output The name of the output file.
 */
class CppCompile(
    val compiler: String,
    val inputs: List<String>,
    val flags: List<String>,
    val output: String
) : PipelineStage(StageResult.Volume::class, StageResult.ScoringResult::class){

    init{
        diagnoseInput()
    }

    override val cmd = "$compiler ${flags.joinToString(" ")} ${inputs.joinToString(" ")} -o $output"

    override fun execute(input: StageResult): StageResult {
        // dummy implementation; we assume it's a no-op
        println("CppCompile: Dummy Implementation")

        return StageResult.ScoringResult(input, emptyList())
    }

    /**
     * called in the constructor when the pipeline is first created
     */
    override fun diagnoseInput(){
        if(!diagnoseCompilerInput())
            emit(CppCompileDiagnostics.NoCompilerInput())

        if(!diagnoseCompilerFlags())
            emit(CppCompileDiagnostics.NoCompilerFlag())
    }

    /**
     * check if the input list is empty
     */
    private fun diagnoseCompilerInput(): Boolean {
        return inputs.isNotEmpty()
    }

    /**
     * check if the flag list is empty
     */
    private fun diagnoseCompilerFlags(): Boolean{
        return flags.isNotEmpty()
    }
}