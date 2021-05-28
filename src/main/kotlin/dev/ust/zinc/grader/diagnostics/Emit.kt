package dev.ust.zinc.grader.diagnostics

import dev.ust.zinc.grader.pipeline.StdioTest
import kotlinx.serialization.*
import kotlinx.serialization.json.Json

/**
 * implemented by any class that wish to emit diagnostics,
 * issue a diagnostic by invoking the emit method
 */
interface Emit{
    fun emit(diag: Diagnostics){
        throw Exception(Json.encodeToString(diag))
    }
}

class GraderException(
    val diag: Diagnostics
): Exception()

/**
 * implemented by any pipeline stages that wish to emit diagnostics,
 * location information about the stage is automatically generated
 */
interface StageEmit: Emit{

    fun getClassName() = this.javaClass.kotlin.simpleName

    override fun emit(diag: Diagnostics){

        //get the method name of where the Diagonstic has been generated
        var methodName: String? = null
        for(i in Thread.currentThread().stackTrace.indices)
        {
            val name: String = Thread.currentThread().stackTrace[i].methodName
            if( name != "getStackTrace" && name != "emit" && name != "serialize")
            {
                methodName = name
                break
            }
        }

        var testCaseId: Int? = null
        if(this is StdioTest)
        {
            testCaseId = this.id
        }

        val location: Diagnostics.Location = Diagnostics.Location(
            diag.location?.stageName ?: getClassName(),
            diag.location?.methodName ?: methodName,
            diag.location?.testCaseId ?: testCaseId
        )
        val serializedDiagnostic: Diagnostics = LocationSpecificDiagnostics(diag, location)

        throw CustomExceptions.StageException(Json.encodeToString(serializedDiagnostic))
    }
}

/**
 * implemented by any StageResult class that wish to emit diagnostics,
 * location information about the specific StageResult name is automatically
 * generated
 */
interface PipelineEmit: Emit{

    fun getClassName() = this.javaClass.kotlin.simpleName

    override fun emit(diag: Diagnostics){
        val location: Diagnostics.Location = Diagnostics.Location(
            diag.location?.stageName ?: getClassName(),
            diag.location?.methodName,
            diag.location?.testCaseId
        )

        val serializedDiagnostic: Diagnostics = LocationSpecificDiagnostics(diag, location)

        throw CustomExceptions.StageResultException(Json.encodeToString(serializedDiagnostic))
    }
}
