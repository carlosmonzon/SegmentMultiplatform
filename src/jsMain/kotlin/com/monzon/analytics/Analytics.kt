package com.monzon.analytics

actual class Analytics {
    actual companion object {
        actual fun setupWithConfiguration(configuration: Configuration): Analytics {
            TODO("Not yet implemented")
        }

        actual fun shared(context: Any?): Analytics {
            TODO("Not yet implemented")
        }
    }

    actual fun alias(userId: String, options: Map<Any?, *>?) {
        TODO("Not yet implemented")
    }

    actual fun track(name: String, properties: Map<Any?, *>?, options: Map<Any?, *>?) {
        TODO("Not yet implemented")
    }

    actual fun identify(userId: String, traits: Map<Any?, *>?, options: Map<Any?, *>?) {
        TODO("Not yet implemented")
    }

    actual fun screen(
        screenTitle: String,
        properties: Map<Any?, *>?,
        options: Map<Any?, *>?
    ) {
        TODO("Not yet implemented")
    }

    actual fun group(groupId: String, traits: Map<Any?, *>?, options: Map<Any?, *>?) {
        TODO("Not yet implemented")
    }

    actual fun reset() {
        TODO("Not yet implemented")
    }
}