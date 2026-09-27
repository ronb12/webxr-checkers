import { neon } from '@neondatabase/serverless';

const sql = neon(process.env.DATABASE_URL);

export default async function handler(req, res) {
  if (req.method !== 'POST') return res.status(405).json({error:'Method not allowed'});
  const { deviceId, result } = req.body || {};
  const cleanDevice = String(deviceId || '').trim().slice(0,128);
  const cleanResult = String(result || '').toLowerCase();
  if (!cleanDevice || !['win','loss','draw'].includes(cleanResult)) return res.status(400).json({error:'Invalid result'});
  const rows = await sql`SELECT * FROM record_bradley_result(${cleanDevice}, ${cleanResult})`;
  if (!rows.length) return res.status(404).json({error:'Player not registered'});
  return res.status(200).json(rows[0]);
}
