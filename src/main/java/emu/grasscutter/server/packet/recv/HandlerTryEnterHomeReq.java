package emu.grasscutter.server.packet.recv;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.TryEnterHomeReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketTryEnterHomeRsp;
import emu.grasscutter.utils.Position;

@Opcodes(PacketOpcodes.TryEnterHomeReq)
public class HandlerTryEnterHomeReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        TryEnterHomeReqOuterClass.TryEnterHomeReq req =
                TryEnterHomeReqOuterClass.TryEnterHomeReq.parseFrom(payload);

        if (req.getTargetUid() != session.getPlayer().getUid()) {
            // I hope that tomorrow there will be a hero who can support multiplayer mode and write code like a poem
            session.send(new PacketTryEnterHomeRsp());
            return;
        }

        // Hardcoded for now
        switch (session.getPlayer().getCurrentRealmId()) {
            case 1:
                session.getPlayer().getWorld().transferPlayerToScene(
                        session.getPlayer(),
                        2001,
                        new Position(839, 319, 137)
                );
                break;

            case 2:
                session.getPlayer().getWorld().transferPlayerToScene(
                        session.getPlayer(),
                        2002,
                        new Position(605, 444, 554)
                );
                break;

            case 3:
                session.getPlayer().getWorld().transferPlayerToScene(
                        session.getPlayer(),
                        2003,
                        new Position(511, 229, 605)
                );
                break;

            case 4:
                session.getPlayer().getWorld().transferPlayerToScene(
                        session.getPlayer(),
                        2004,
                        new Position(239, 187, 536)
                );
                break;

            default:
                session.send(new PacketTryEnterHomeRsp());
                return;
        }


        session.send(new PacketTryEnterHomeRsp(req.getTargetUid()));
    }
}
