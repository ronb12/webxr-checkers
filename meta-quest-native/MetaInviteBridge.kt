package com.bradleyvirtualsolutions.checkers

/**
 * Bradley Checkers Meta social bridge specification.
 *
 * The actual Horizon SDK calls belong in the immersive Quest host after the
 * Meta application ID/destination are provisioned. Keeping the room code as
 * lobby + match ID lets a Meta invite land in the existing PeerJS match.
 */
data class BradleyPresence(
    val destinationApiName: String = "BRADLEY_CHECKERS_MATCH",
    val lobbySessionId: String,
    val matchSessionId: String = lobbySessionId,
    val isJoinable: Boolean = true
)

data class MetaInvitablePlayer(val id: String, val alias: String)

interface MetaInviteBridge {
    suspend fun initialize(applicationId: String)
    suspend fun setPresence(presence: BradleyPresence)
    suspend fun clearPresence()
    suspend fun getInvitableUsers(): List<MetaInvitablePlayer>
    suspend fun sendInvites(userIds: List<String>)
    suspend fun launchInvitePanel()
    fun onJoinIntent(callback: (lobbySessionId: String) -> Unit)
}
