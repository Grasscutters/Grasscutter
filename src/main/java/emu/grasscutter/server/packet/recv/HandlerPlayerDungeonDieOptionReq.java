package emu.grasscutter.server.packet.recv;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.player.TeamManager;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketWorldPlayerReviveRsp;

@Opcodes(PacketOpcodes.DungeonDieOptionReq)
public class HandlerPlayerDungeonDieOptionReq extends PacketHandler{

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        session.getPlayer().getTeamManager().respawnTeam();
    }
    //public static final int DungeonDieOptionReq = 975;
    //public static final int DungeonDieOptionRsp = 948;

    //public static final int DungeonPlayerDieReq = 981;
    //public static final int DungeonPlayerDieRsp = 905;

    //public static final int DungeonRestartReq = 961;
    //public static final int DungeonRestartResultNotify = 940;
    //public static final int DungeonRestartRsp = 929;


    /**
      DungeonDieOptionReq -> 放弃挑战 and 从门复活
      DungeonPlayerDieReq -> None
     */

}
