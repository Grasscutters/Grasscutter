package emu.grasscutter.game.entity.gadget;

import emu.grasscutter.game.dungeons.challenge.DungeonChallenge;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.InterOpTypeOuterClass;
import emu.grasscutter.net.proto.InteractTypeOuterClass.InteractType;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;
import emu.grasscutter.server.packet.send.PacketGadgetInteractRsp;

public class GadgetRewardStatue extends GadgetContent {
	
	public GadgetRewardStatue(EntityGadget gadget) {
		super(gadget);
	}

	public boolean onInteract(Player player, InterOpTypeOuterClass.InterOpType opType) {
		if (player.getScene().getChallenge() != null && player.getScene().getChallenge() instanceof DungeonChallenge dungeonChallenge) {
			dungeonChallenge.getStatueDrops(player);
		}
		
		player.sendPacket(new PacketGadgetInteractRsp(getGadget(), InteractType.INTERACT_TYPE_OPEN_STATUE));
		
		return false;
	}

	public void onBuildProto(SceneGadgetInfo.Builder gadgetInfo) {
		
	}
}
