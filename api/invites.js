import { neon } from '@neondatabase/serverless';
const sql=neon(process.env.DATABASE_URL);
export default async function handler(req,res){
  const body=req.body||{};
  const deviceId=String(body.deviceId||req.query?.deviceId||'').trim().slice(0,128);
  if(!deviceId) return res.status(400).json({error:'Device required'});
  const me=await sql`SELECT id,player_name FROM players WHERE device_id=${deviceId} LIMIT 1`;
  if(!me.length) return res.status(403).json({error:'Registered player required'});
  if(req.method==='GET'){
    const rows=await sql`SELECT id,sender_name,room_code,created_at FROM game_invites WHERE recipient_name=${me[0].player_name} AND status='pending' AND created_at>now()-interval '10 minutes' ORDER BY created_at DESC LIMIT 1`;
    return res.status(200).json(rows[0]||null);
  }
  if(req.method==='POST'){
    const recipient=String(body.recipient||'').trim().slice(0,20);
    const roomCode=String(body.roomCode||'').trim().toUpperCase().replace(/[^A-Z0-9]/g,'').slice(0,6);
    if(!recipient||roomCode.length!==6) return res.status(400).json({error:'Recipient and room code required'});
    const target=await sql`SELECT player_name FROM players WHERE lower(player_name)=lower(${recipient}) LIMIT 1`;
    if(!target.length) return res.status(404).json({error:'Player not found'});
    if(target[0].player_name.toLowerCase()===me[0].player_name.toLowerCase()) return res.status(400).json({error:'Cannot invite yourself'});
    await sql`UPDATE game_invites SET status='expired' WHERE sender_name=${me[0].player_name} AND status='pending'`;
    const rows=await sql`INSERT INTO game_invites(sender_name,recipient_name,room_code,status) VALUES(${me[0].player_name},${target[0].player_name},${roomCode},'pending') RETURNING id,sender_name,recipient_name,room_code,status`;
    return res.status(200).json(rows[0]);
  }
  if(req.method==='PATCH'){
    const inviteId=Number(body.inviteId);
    const action=body.action==='accept'?'accepted':body.action==='decline'?'declined':null;
    if(!inviteId||!action) return res.status(400).json({error:'Invalid invite action'});
    const rows=await sql`UPDATE game_invites SET status=${action},responded_at=now() WHERE id=${inviteId} AND recipient_name=${me[0].player_name} AND status='pending' RETURNING id,sender_name,room_code,status`;
    if(!rows.length) return res.status(404).json({error:'Invite no longer available'});
    return res.status(200).json(rows[0]);
  }
  return res.status(405).json({error:'Method not allowed'});
}
