# Bradley Checkers

**Bradley Checkers** is a cross-platform 3D checkers experience built for WebXR, Meta Quest, desktop browsers, phones, and tablets. It combines a championship-lounge presentation with traditional checkers gameplay, AI opponents, peer-to-peer online matches, persistent player identities, and a Neon-powered leaderboard.

## Features

- Immersive WebXR gameplay for Meta Quest
- Responsive desktop, phone, and tablet play
- Human vs. AI gameplay with selectable difficulty
- Peer-to-peer online multiplayer using WebRTC and PeerJS
- Captures, forced jumps, multi-jumps, and kings
- Quest controller interaction, haptic feedback, headset-relative locomotion, and snap turning
- Championship Lounge 3D environment with Bradley Checkers branding
- Persistent player/gamer names stored per browser or headset
- Neon Postgres leaderboard with cumulative wins, losses, draws, games, and score
- One database player record per persistent device identity to prevent duplicate score rows
- Privacy reminder instructing players to use a gamer name rather than their real name

## Technology

The client uses HTML, CSS, JavaScript, Three.js, and WebXR. Online matches use PeerJS/WebRTC. Server-side leaderboard endpoints run as Vercel Functions and connect securely to Neon Postgres with `@neondatabase/serverless`.

The database connection string is server-side only and is not exposed in the browser client.

## Scoring

Leaderboard scores are cumulative: a win is 3 points, a draw is 1 point, and a loss is 0 points. Existing players are updated instead of receiving duplicate leaderboard rows.

## Local Development

Install dependencies with `npm install`. Configure `DATABASE_URL` with a Neon Postgres connection string, then run `vercel dev` so the `/api` functions are available. WebXR immersive mode requires a compatible browser and secure HTTPS origin on a headset.

## API

- `POST /api/player` registers or retrieves a persistent player.
- `GET /api/leaderboard` returns the top leaderboard entries.
- `POST /api/result` adds a completed result to the existing player's cumulative statistics.

## Privacy

Players are instructed **not to enter their real name**. Bradley Checkers uses a player/gamer name for leaderboard display.

## Production

https://webxr-checkers.vercel.app

## Ownership

Created by **Ronell Bradley**  
Property of **Bradley Virtual Solutions, LLC**

© 2026 Bradley Virtual Solutions, LLC. All rights reserved.
