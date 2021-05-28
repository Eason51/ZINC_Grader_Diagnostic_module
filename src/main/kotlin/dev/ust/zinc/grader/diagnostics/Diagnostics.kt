package dev.ust.zinc.grader.diagnostics
import kotlinx.serialization.*
import kotlinx.serialization.json.Json


/**
 * The most superclass for holding serializable information of a diagnostic
 * @property internalMsg message for developers to quickly identify the Diagnostic class
 * @property message message for users to identify the problem
 * @property type severity of the diagnostic
 * @property location location where the diagnostic is issued
 */
@Serializable
open class Diagnostics(
    val internalMsg: String,
    val message: String,
    val type: DiagnosticType? = null,
    val location: Location? = null,
){
    enum class DiagnosticType{
        WARNING, ERROR
    }

    /**
     * @property stageName the pipeline stage where the diagnostic is issued
     * @property methodName the method where the diagnostic is issued
     * @property testCaseId in which test case the diagnostic is issued
     */
    @Serializable
    data class Location(
        val stageName: String? = null,
        val methodName: String? = null,
        val testCaseId: Int? = null
    )
}

/**
 * automatically severity initialized diagnostics *
 */
open class Error(
    internalMsg: String,
    message: String,
    location: Location? = null
): Diagnostics(internalMsg, message, DiagnosticType.ERROR, location)

open class Warning(
    internalMsg: String,
    message: String,
    location: Location? = null
): Diagnostics(internalMsg, message, DiagnosticType.WARNING, location)

/**
 * location must be provided for this kind of diagnostics,
 * currently used by Emit interface to add additional location information
 * for emitting diagnostics
 */
open class LocationSpecificDiagnostics(
    internalMsg: String,
    message: String,
    location: Location,
    errorType: DiagnosticType? = null
): Diagnostics(internalMsg, message, errorType, location){
    constructor(diag: Diagnostics, location: Location):
            this(diag.internalMsg, diag.message, location, diag.type)
}







