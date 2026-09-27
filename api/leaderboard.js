import { neon } from '@neondatabase/serverless';

const sql = neon(process.env.DATABASE_URL);

export default async function handler(req, res) {
  if (req.method !== 'GET') return res.status(405).json({error:'Method not allowed'});
  const rows = await sql`
    SELECT player_name, wins, losses, draws, games_played, score
    FROM players
    ORDER BY score DESC, wins DESC, games_played ASC, updated_at ASC
    LIMIT 10
  `;
  res.setHeader('Cache-Control','no-store');
  return res.status(200).json(rows);
}
