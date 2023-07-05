package com.example.sentrykmmbug

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.platformLogWriter
import io.sentry.kotlin.multiplatform.Context
import io.sentry.kotlin.multiplatform.Sentry
import src.commonMain.kotlin.com.example.sentrykmmbug.SentryLogWriter

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

