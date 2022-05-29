package emu.grasscutter.server.packet.recv;

import java.util.HashMap;

import emu.grasscutter.data.GameData;
import emu.grasscutter.game.entity.EntityItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.VectorOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.net.proto.HitCollisionOuterClass.HitCollision;
import emu.grasscutter.utils.AutoRecycleHashMap;
import emu.grasscutter.utils.Position;

@Opcodes(PacketOpcodes.HitTreeNotify)
public class HandlerHitTreeNotify extends PacketHandler {
    private final static int RECORD_EXPIRED_TIME = 1000*60*5;
    private final AutoRecycleHashMap<Integer,HashMap<Integer,HitTreeRecord>> hitRecord = new AutoRecycleHashMap<>(RECORD_EXPIRED_TIME);
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        HitCollision hit = HitCollision.parseFrom(payload);
        VectorOuterClass.Vector hitPosition = hit.getHitPoint();
        int woodType = hit.getHitColliderTypeValue();
        if(woodType>=1 && woodType<=12) {// is a available wood type
            Player player = session.getPlayer();
            Scene scene = player.getScene();
            int uid = player.getUid();
            synchronized (hitRecord) {
                if (!hitRecord.containsKey(uid)) {
                    hitRecord.put(uid, new HashMap<>());
                }
                int itemId = 101300 + woodType;
                int positionHash = hit.getHitPoint().hashCode();
                HashMap<Integer, HitTreeRecord> currentRecord = hitRecord.get(uid);
                if (currentRecord.containsKey(positionHash)) {
                    long currentTime = System.currentTimeMillis();
                    HitTreeRecord record = currentRecord.get(positionHash);
                    if (currentTime - record.time > RECORD_EXPIRED_TIME) {// fresh wood after 5 min
                        record.times = 1;
                        record.time = currentTime;
                    } else {
                        if (record.times >= 3) {// too many wood they have deforested, no more wood dropped!
                            return;
                        } else {
                            record.times++;
                        }
                    }
                } else {
                    currentRecord.put(positionHash, new HitTreeRecord());
                }
                EntityItem entity = new EntityItem(scene,
                        null,
                        GameData.getItemDataMap().get(itemId),
                        new Position(hitPosition.getX(),hitPosition.getY(),hitPosition.getZ()),
                        1,
                        false);
                scene.addEntity(entity);
            }
        }
        // unknown wood type
    }
    private static class HitTreeRecord{
        short times = 1;
        long time = System.currentTimeMillis();
    }
}