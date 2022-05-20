package emu.grasscutter.game.entity.gadget;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.BossChestInfoOuterClass.BossChestInfo;
import emu.grasscutter.net.proto.InterOpTypeOuterClass;
import emu.grasscutter.net.proto.InteractTypeOuterClass;
import emu.grasscutter.net.proto.InteractTypeOuterClass.InteractType;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;
import emu.grasscutter.scripts.constants.ScriptGadgetState;
import emu.grasscutter.server.packet.send.PacketGadgetInteractRsp;

import static emu.grasscutter.net.proto.InterOpTypeOuterClass.InterOpType.INTER_OP_START;

public class GadgetChest extends GadgetContent {
	
	public GadgetChest(EntityGadget gadget) {
		super(gadget);
	}

	public boolean onInteract(Player player, InterOpTypeOuterClass.InterOpType opType) {
		var chestInteractHandlerMap = getGadget().getScene().getWorld().getServer().getWorldDataManager().getChestInteractHandlerMap();
		var handler = chestInteractHandlerMap.get(getGadget().getGadgetData().getJsonName());
		if(handler == null){
			Grasscutter.getLogger().warn("Could not found the handler of this type of Chests {}", getGadget().getGadgetData().getJsonName());
			return false;
		}

		if(opType == INTER_OP_START && handler.isTwoStep()){
			player.sendPacket(new PacketGadgetInteractRsp(getGadget(), InteractType.INTERACT_OPEN_CHEST, INTER_OP_START));
			return false;
		}else{
			var success = handler.onInteract(this, player);
			if (!success){
				return false;
			}

			getGadget().updateState(ScriptGadgetState.ChestOpened);
			player.sendPacket(new PacketGadgetInteractRsp(this.getGadget(), InteractTypeOuterClass.InteractType.INTERACT_OPEN_CHEST));
			return true;
		}
	}

	public void onBuildProto(SceneGadgetInfo.Builder gadgetInfo) {
		if(getGadget().getMetaGadget() == null){
			return;
		}

		var bossChest = getGadget().getMetaGadget().boss_chest;
		if(bossChest != null){
			var players = getGadget().getScene().getPlayers().stream().map(Player::getUid).toList();

			gadgetInfo.setBossChest(BossChestInfo.newBuilder()
					.setMonsterConfigId(bossChest.monster_config_id)
					.setResin(bossChest.resin)
					.addAllQualifyUidList(players)
					.addAllRemainUidList(players)
					.build());
		}

	}
}
