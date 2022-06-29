package emu.grasscutter.game.entity.gadget;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.EntityItem;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.GatherGadgetInfoOuterClass.GatherGadgetInfo;
import emu.grasscutter.net.proto.InteractTypeOuterClass.InteractType;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;
import emu.grasscutter.server.packet.send.PacketGadgetInteractRsp;
import emu.grasscutter.utils.Position;
import emu.grasscutter.utils.Utils;

public class GadgetGatherObject extends GadgetContent {
	private int itemId;
	private boolean isForbidGuest;
	
	public GadgetGatherObject(EntityGadget gadget) {
		super(gadget);
		
		if (gadget.getSpawnEntry() != null) {
			this.itemId = gadget.getSpawnEntry().getGatherItemId();
		}
	}
	
	public int getItemId() {
		return this.itemId;
	}

	public boolean isForbidGuest() {
		return isForbidGuest;
	}

	public boolean onInteract(Player player, GadgetInteractReq req) {
		// Sanity check
		ItemData itemData = GameData.getItemDataMap().get(getItemId());
		if (itemData == null) {
			return false;
		}

		GameItem item = new GameItem(itemData, 1);
		player.getInventory().addItem(item, ActionReason.Gather);
		
		getGadget().getScene().broadcastPacket(new PacketGadgetInteractRsp(getGadget(), InteractType.INTERACT_TYPE_GATHER));

		return true;
	}

	public void onBuildProto(SceneGadgetInfo.Builder gadgetInfo) {
		GatherGadgetInfo gatherGadgetInfo = GatherGadgetInfo.newBuilder()
				.setItemId(this.getItemId())
				.setIsForbidGuest(this.isForbidGuest())
				.build();

		gadgetInfo.setGatherGadget(gatherGadgetInfo);
	}

	public void dropItems(Player player) {
		Scene scene = getGadget().getScene();
		int times = Utils.randomRange(1,2);
		
        for (int i = 0 ; i < times ; i++) {
            EntityItem item = new EntityItem(
            		scene,
            		player,
                    GameData.getItemDataMap().get(itemId),
                    new Position(
                            getGadget().getPosition().getX() + (float)Utils.randomRange(1,5) / 5,
                            getGadget().getPosition().getY() + 2f,
                            getGadget().getPosition().getZ() + (float)Utils.randomRange(1,5) / 5
                    ),
                    1,
                    true);
            
            scene.addEntity(item);
        }
        
        scene.killEntity(this.getGadget(), player.getTeamManager().getCurrentAvatarEntity().getId());
        // Todo: add record
	}
}
