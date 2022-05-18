package emu.grasscutter.game.entity.gadget;

import java.util.Random;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.InteractTypeOuterClass.InteractType;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;
import emu.grasscutter.scripts.constants.ScriptGadgetState;
import emu.grasscutter.server.packet.send.PacketGadgetInteractRsp;

public class GadgetChest extends GadgetContent {
	
	public GadgetChest(EntityGadget gadget) {
		super(gadget);
	}

	public boolean onInteract(Player player) {
		var chestRewardMap = getGadget().getScene().getWorld().getServer().getWorldDataManager().getChestRewardMap();
		var chestReward = chestRewardMap.get(getGadget().getGadgetData().getJsonName());
		if (chestReward == null) {
			Grasscutter.getLogger().warn("Could not found the config of this type of Chests {}", getGadget().getGadgetData().getJsonName());
			return true;
		}

		player.earnExp(chestReward.getAdvExp());
		player.getInventory().addItem(201, chestReward.getResin());

		var mora = chestReward.getMora() * (1 + (player.getWorldLevel() - 1) * 0.5);
		player.getInventory().addItem(202, (int)mora);

		for(int i=0;i<chestReward.getContent().size();i++){
			getGadget().getScene().addItemEntity(chestReward.getContent().get(i).getItemId(), chestReward.getContent().get(i).getCount(), getGadget());
		}

		var random = new Random(System.currentTimeMillis());
		for(int i=0;i<chestReward.getRandomCount();i++){
			var index = random.nextInt(chestReward.getRandomContent().size());
			var item = chestReward.getRandomContent().get(index);
			getGadget().getScene().addItemEntity(item.getItemId(), item.getCount(), getGadget());
		}
		
		getGadget().updateState(ScriptGadgetState.ChestOpened);
		player.sendPacket(new PacketGadgetInteractRsp(getGadget(), InteractType.INTERACT_OPEN_CHEST));
		
		return true;
	}

	public void onBuildProto(SceneGadgetInfo.Builder gadgetInfo) {

	}
}
