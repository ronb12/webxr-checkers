# Bradley Checkers — Meta Quest native invite bridge

This module is the native Quest-side integration plan/source for Meta Horizon Platform social travel.

Required Meta dashboard values before a signed build can function:
- Meta Horizon Application ID
- Destination API name: `BRADLEY_CHECKERS_MATCH`
- Uploaded immersive Quest binary associated with that app

Runtime contract with the existing game:
- `lobbySessionId` = Bradley Checkers six-character room code
- `matchSessionId` = same room code for the 1v1 match
- `isJoinable` = true while host is waiting / match has capacity
- Invite acceptance must route the received lobby ID back into the Bradley Checkers join flow.

Meta features to wire in the native immersive host:
1. Initialize Horizon Platform SDK with the Meta Application ID.
2. Set Group Presence destination/lobby/match and joinable state.
3. Get invitable users for the in-world player board.
4. Send direct Quick Invites or launch Meta's Invite to App panel.
5. Subscribe to join-intent / launch-intent notifications and forward the lobby ID to the game.

The existing WebXR/Neon player-ID invites remain the fallback when Meta Platform social APIs are unavailable.
