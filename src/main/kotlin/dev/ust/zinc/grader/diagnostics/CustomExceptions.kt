package dev.ust.zinc.grader.diagnostics

sealed class CustomExceptions{

    /**
     * Exceptions thrown from within a StageResult class
     */
    class StageResultException(msg: String? = null): Exception(msg){}

    /**
     * Exceptions thrown from within a PipelineStage
     */
    class StageException(msg: String? = null): Exception(msg) {}
}