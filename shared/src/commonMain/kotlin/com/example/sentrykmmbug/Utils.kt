package com.example.sentrykmmbug

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.platformLogWriter
import io.sentry.kotlin.multiplatform.Context
import io.sentry.kotlin.multiplatform.Sentry
import src.commonMain.kotlin.com.example.sentrykmmbug.SentryLogWriter

class Utils {
    public fun setupSentry(context: Context? = null, isDebug: Boolean, releaseVersion: String) {
        if (context != null) {
            Sentry.init(context) {
                it.attachScreenshot = true
                it.dsn = "REPLACE ME"
                it.debug = isDebug
                it.environment = if (isDebug) "debug" else "production"
                it.release = releaseVersion
                it.attachViewHierarchy = true
            }
        } else {
            Sentry.init {
                it.attachScreenshot = true
                it.dsn = "REPLACE ME"
                it.debug = isDebug
                it.environment = if (isDebug) "debug" else "production"
                it.release = releaseVersion
            }
        }
        setupLogger()
    }


    private fun setupLogger() {

        Logger.setLogWriters(platformLogWriter())
        // if we have a SentryLogWriter lets add it.
        Logger.addLogWriter(
            SentryLogWriter(
                Severity.Verbose,
                Severity.Warn
            )
        )

    }


    public fun logError() {
        Logger.d("Tag") { "This should be a breadcumb" }

        try {
            throw Exception("I'm a crash")
        } catch (e: Throwable) {
            Logger.e(e) { "some message" }
        }
    }

    public fun crash() {
        Logger.d("Tag") { "This should be a breadcumb" }
        throw Exception("I'm a crash")
    }
}