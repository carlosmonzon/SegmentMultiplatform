package com.monzon.analytics

class Analytics : IAnalytics {
    override fun track(name: String, properties: Map<Any?, *>?, options: Map<Any?, *>?) {
        TODO("Not yet implemented")
    }

    override fun identify(userId: String, traits: Map<Any?, *>?, options: Map<Any?, *>?) {
        TODO("Not yet implemented")
    }

    override fun alias(userId: String, options: Map<Any?, *>?) {
        TODO("Not yet implemented")
    }

    override fun screen(screenTitle: String, properties: Map<Any?, *>?, options: Map<Any?, *>?) {
        TODO("Not yet implemented")
    }

    override fun group(groupId: String, traits: Map<Any?, *>?, options: Map<Any?, *>?) {
        TODO("Not yet implemented")
    }

    override fun reset() {
        TODO("Not yet implemented")
    }

}