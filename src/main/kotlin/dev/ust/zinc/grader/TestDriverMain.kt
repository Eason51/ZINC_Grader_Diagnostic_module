package dev.ust.zinc.grader

import dev.ust.zinc.grader.pipeline.CppCompile
import dev.ust.zinc.grader.pipeline.Score
import dev.ust.zinc.grader.pipeline.StageResult
import dev.ust.zinc.grader.pipeline.StdioTest
import dev.ust.zinc.grader.runner.Runner

fun main() {

    try
    {
        val pipeline = listOf(
            CppCompile(
                compiler = "g++",
                inputs = listOf("main.cpp"),
                flags = listOf("-std=c++11"),
                output = "main.exe"
            ),
            StdioTest(
                1,
                "a.out",
                "",
                null,
                "haha",
                "hehe",
                StdioTest.Visibility.ALWAYS_VISIBLE,
                null,
                1.0
            ),
            StdioTest(
                2,
                "a.out",
                "",
                null,
                "",
                null,
                StdioTest.Visibility.ALWAYS_VISIBLE,
                null,
                1.0
            ),
            StdioTest(
                3,
                "a.out",
                "",
                null,
                "",
                null,
                StdioTest.Visibility.ALWAYS_VISIBLE,
                null,
                1.0
            ),
            StdioTest(
                4,
                "a.out",
                "",
                null,
                "",
                null,
                StdioTest.Visibility.ALWAYS_VISIBLE,
                null,
                1.0
            ),
            Score(
                normalization = 100.0,
                clipMin = null,
                clipMax = null
            )
        )

        val runner = Runner(pipeline)

        val result = runner.execWithInput(StageResult.Volume("abc123"))
        println(result)
    }
    catch(e: Throwable)
    {
        println(e)
    }

}
