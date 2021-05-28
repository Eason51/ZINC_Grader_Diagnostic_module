package dev.ust.zinc.grader.diagnostics

/*
* Diagnostics generated from within CppCompile class
* */
sealed class CppCompileDiagnostics{

    class NoCompilerInput(
        internalMsg: String = "CPPCOMPILE_INPUTS",
        msg: String = "no input provided to the compiler"
    ): Error(internalMsg, msg)

    class NoCompilerFlag(
        internalMsg: String = "CPPCOMPILE_FLAGS",
        msg: String = "no flags provided to the compiler"
    ):Warning(internalMsg, msg)
}




