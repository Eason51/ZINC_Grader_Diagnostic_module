package dev.ust.zinc.grader.diagnostics

/**
 * Diagnostics generated from class StdioTest
 */
sealed class StdioTestDiagnostics{

    class NonUniqueId(
        internalMsg: String = "STDIOTEST_UNIQUE_ID",
        msg: String = "Given test case id is not unique",
    ): Error(internalMsg, msg)

    class MutualExclusionViolation(
        property1: String?,
        property2: String?,
        internalMsg: String = "STDIOTEST_MUTEX_PROPERTY",
        msg: String = "Violate Mutual Exclusion constraints," +
                " ${property1}, ${property2}",
    ): Error(internalMsg, msg)

    class InvalidScore(
        score: Double?,
        internalMsg: String = "STDIOTTEST_INVALID_SCORE",
        msg: String = "Score is invalid, ${score}",
    ): Error(internalMsg, msg)
}