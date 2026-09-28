# Meta Horizon dashboard setup

Create/configure the Bradley Checkers app in Meta Horizon Developer Dashboard.

## Destination
- Display name: Bradley Checkers Match
- API name: `BRADLEY_CHECKERS_MATCH`
- Deep-link message: multiplayer match

## Social travel
Enable Destinations, Group Presence, Rosters, Invite to App, and Quick Invites.
The native host should mark the player joinable only while the 1v1 session has an open seat.

## Required private value
Set the Meta Application ID in the native build configuration. Do not commit credentials or secrets to Git.

## Release prerequisite
Upload an immersive Quest binary for the app. Meta's Invite to App receive flow requires an uploaded binary.
