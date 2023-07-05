package src.commonMain.kotlin.com.example.sentrykmmbug

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Severity
import io.sentry.kotlin.multiplatform.Sentry
import io.sentry.kotlin.multiplatform.SentryLevel
import io.sentry.kotlin.multiplatform.protocol.Breadcrumb


/**
 * This allows us to write to our sentry log so that we have a lot more context into
 * what caused a crash. We where using the log writer from Kermit (which this basically is),
 * but it was causing lots of issues on iOS, so I opted to write a custom platform implementation
 * So we can easily port to iOS and write it in Swift instead.
 */
class SentryLogWriter(
    private val minSeverity: Severity,
    private val minCrashSeverity: Severity,
) : LogWriter() {
    init {
        check(minSeverity <= minCrashSeverity) {
            "minSeverity ($minSeverity) cannot be greater than minCrashSeverity ($minCrashSeverity)"
        }
    }

    override fun isLoggable(severity: Severity): Boolean = severity >= minSeverity

    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
        if (throwable == null) {

            Sentry.addBreadcrumb(
                Breadcrumb(
                    severity.toSentryLevel(),
                    message = message,
                    category = tag
                )
            )
        }
        if (throwable != null && severity >= minCrashSeverity) {
            Sentry.captureException(throwable)
        }
    }
}

fun Severity.toSentryLevel(): SentryLevel {
    return when (this) {
        Severity.Debug -> SentryLevel.DEBUG
        Severity.Info -> SentryLevel.INFO
        Severity.Warn -> SentryLevel.WARNING
        Severity.Error -> SentryLevel.ERROR
        Severity.Assert -> SentryLevel.FATAL
        else -> throw IllegalArgumentException("Unsupported severity level")
    }
}