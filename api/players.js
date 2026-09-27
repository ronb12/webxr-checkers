import { neon } from '@neondatabase/serverless';
const sql=neon(process.env.DATABASE_URL);
export default async function handler(req,res){
  if(req.method!=='GET') return res.status(405).json({error:'Method not allowed'});
  const deviceId=String(req.query?.deviceId||'').trim().slice(0,128);
  if(!deviceId) return res.status(400).json({error:'Device required'});
  const rows=await sql`SELECT player_name FROM players WHERE device_id<>${deviceId} ORDER BY lower(player_name) LIMIT 50`;
  return res.status(200).json(rows.map(r=>r.player_name));
}
