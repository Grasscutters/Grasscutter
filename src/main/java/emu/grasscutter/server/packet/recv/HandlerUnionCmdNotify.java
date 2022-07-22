package emu.grasscutter.server.packet.recv;

import static emu.grasscutter.config.Configuration.GAME_INFO;
import static emu.grasscutter.config.Configuration.SERVER;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.UnionCmdNotifyOuterClass.UnionCmdNotify;
import emu.grasscutter.net.proto.UnionCmdOuterClass.UnionCmd;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.Grasscutter.ServerDebugMode;

@Opcodes(PacketOpcodes.UnionCmdNotify)
public class HandlerUnionCmdNotify extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        UnionCmdNotify req = UnionCmdNotify.parseFrom(payload);
        for (UnionCmd cmd : req.getCmdListList()) {
            int cmdOpcode = cmd.getMessageId();
            byte[] cmdPayload = cmd.getBody().toByteArray();
            if (GAME_INFO.logPackets == ServerDebugMode.WHITELIST && SERVER.debugWhitelist.contains(cmd.getMessageId())) {
                session.logPacket("RECV in Union", cmdOpcode, cmdPayload);
            } else if (GAME_INFO.logPackets ==  ServerDebugMode.BLACKLIST && !SERVER.debugBlacklist.contains(cmd.getMessageId())) {
                session.logPacket("RECV in Union", cmdOpcode, cmdPayload);
            }
            //debugLevel ALL ignores UnionCmdNotify, so we will also ignore the contained opcodes
            session.getServer().getPacketHandler().handle(session, cmd.getMessageId(), EMPTY_BYTE_ARRAY, cmd.getBody().toByteArray());
        }

        // Update
        session.getPlayer().getCombatInvokeHandler().update(session.getPlayer());
        session.getPlayer().getAbilityInvokeHandler().update(session.getPlayer());

        // Handle attack results last
        while (!session.getPlayer().getAttackResults().isEmpty()) {
            session.getPlayer().getScene().handleAttack(session.getPlayer().getAttackResults().poll());
        }
    }
}
