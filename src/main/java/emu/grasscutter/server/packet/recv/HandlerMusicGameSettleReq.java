package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.props.WatcherTriggerType;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.MusicGameSettleReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketMusicGameSettleRsp;

@Opcodes(PacketOpcodes.MusicGameSettleReq)
public class HandlerMusicGameSettleReq extends PacketHandler {

	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		var req = MusicGameSettleReqOuterClass.MusicGameSettleReq.parseFrom(payload);

        session.getPlayer().getActivityManager().triggerWatcher(
            WatcherTriggerType.TRIGGER_FLEUR_FAIR_MUSIC_GAME_REACH_SCORE,
            String.valueOf(req.getMusicBasicId()),
            String.valueOf(req.getScore())
            );


		//session.send(new PacketMusicGameSettleRsp(req.getMusicBasicId()));
		session.send(new PacketMusicGameSettleRsp(req.getMusicBasicId()));
	}

}
