package com.bradleyvirtualsolutions.checkers

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle

/**
 * Quest launch/deep-link entry point. The immersive WebXR experience remains hosted at
 * BRADLEY_WEB_URL; Meta join intents are translated into its existing ?room=XXXXXX flow.
 * The Horizon Platform SDK adapter is intentionally activated only when META_APP_ID is set.
 */
class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forwardToBradleyCheckers(intent)
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        forwardToBradleyCheckers(intent)
    }
    private fun forwardToBradleyCheckers(intent: Intent) {
        val room = intent.data?.getQueryParameter("room")?.uppercase()?.take(6)
        val url = Uri.parse(BuildConfig.BRADLEY_WEB_URL).buildUpon().apply {
            if (!room.isNullOrBlank()) appendQueryParameter("room", room)
        }.build()
        startActivity(Intent(Intent.ACTION_VIEW, url).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        finish()
    }
}
