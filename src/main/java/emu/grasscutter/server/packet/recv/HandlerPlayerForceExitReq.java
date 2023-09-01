package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.PlayerForceExitReq)
public class HandlerPlayerForceExitReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        // Client should auto disconnect right now
        session.send(new BasePacket(PacketOpcodes.PlayerForceExitRsp));
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000); // disconnect after 1 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                session.close();
                super.run();
            }
        }.start();
    }
}
