package com.bradleyvirtualsolutions.checkers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import horizon.core.android.driver.coroutines.HorizonServiceConnection
import horizon.platform.grouppresence.GroupPresence
import horizon.platform.grouppresence.options.GroupPresenceOptions
import horizon.platform.grouppresence.options.InviteOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/** Meta Horizon social presence for Bradley Checkers multiplayer. */
class BradleyGroupPresence(private val context: Context) {
    companion object {
        const val APP_ID = "1285271641346248"
        const val DESTINATION = "BRADLEY_CHECKERS_MATCH"
        private const val TAG = "BradleyMetaPresence"
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val groupPresence = GroupPresence()

    fun initialize() = scope.launch {
        try {
            HorizonServiceConnection.connect(APP_ID, context)
            listenForJoinIntents()
            Log.i(TAG, "Meta Horizon Platform SDK ready")
        } catch (t: Throwable) { Log.e(TAG, "Platform SDK initialization failed", t) }
    }

    fun setMatch(roomCode: String, joinable: Boolean = true) = scope.launch {
        val room = roomCode.trim().uppercase().take(6)
        if (room.isBlank()) return@launch
        try {
            val options = GroupPresenceOptions.builder()
                .withDestinationApiName(DESTINATION)
                .withLobbySessionId(room)
                .withMatchSessionId(room)
                .withIsJoinable(joinable)
                .withDeeplinkMessageOverride("Join Bradley Checkers match $room")
                .build()
            groupPresence.set(options)
            Log.i(TAG, "Presence set: room=$room joinable=$joinable")
        } catch (t: Throwable) { Log.e(TAG, "Unable to set group presence", t) }
    }

    fun clear() = scope.launch {
        try { groupPresence.clear() } catch (t: Throwable) { Log.e(TAG, "Unable to clear presence", t) }
    }

    fun launchInvitePanel() = scope.launch {
        try { groupPresence.launchInvitePanel(InviteOptions.builder().build()) }
        catch (t: Throwable) { Log.e(TAG, "Unable to launch invite panel", t) }
    }

    private fun listenForJoinIntents() = scope.launch {
        try {
            groupPresence.joinIntentReceived().collect { details ->
                val room = details.lobbySessionId?.trim()?.uppercase()?.take(6)
                if (!room.isNullOrBlank()) launchWebMatch(room)
            }
        } catch (t: Throwable) { Log.e(TAG, "Join-intent listener stopped", t) }
    }

    private fun launchWebMatch(room: String) {
        val url = Uri.parse(BuildConfig.BRADLEY_WEB_URL).buildUpon()
            .appendQueryParameter("room", room)
            .appendQueryParameter("source", "meta-invite")
            .build()
        context.startActivity(Intent(Intent.ACTION_VIEW, url).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}
