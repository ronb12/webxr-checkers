import { neon } from '@neondatabase/serverless';

const sql = neon(process.env.DATABASE_URL);

export default async function handler(req, res) {
  if (req.method !== 'POST') return res.status(405).json({error:'Method not allowed'});
  const { deviceId, name } = req.body || {};
  const cleanDevice = String(deviceId || '').trim().slice(0,128);
  const cleanName = String(name || '').trim().replace(/[^a-zA-Z0-9 _-]/g,'').slice(0,20);
  if (!cleanDevice || !cleanName) return res.status(400).json({error:'Player name and device are required'});
  const rows = await sql`SELECT * FROM register_bradley_player(${cleanDevice}, ${cleanName})`;
  return res.status(200).json(rows[0]);
}
