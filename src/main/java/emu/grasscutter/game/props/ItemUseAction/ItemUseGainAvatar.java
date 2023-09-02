package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.props.ItemUseOp;
import emu.grasscutter.game.systems.InventorySystem;
import java.util.Optional;

public class ItemUseGainAvatar extends ItemUseInt {
    private int level = 1;
    private int constellation = 0;

    public ItemUseGainAvatar(String[] useParam) {
        super(useParam);
        try {
            this.level = Integer.parseInt(useParam[1]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            this.constellation = Integer.parseInt(useParam[2]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_GAIN_AVATAR;
    }

    @Override
    public boolean useItem(UseItemParams params) {
        int haveConstellation =
                InventorySystem.checkPlayerAvatarConstellationLevel(params.player, this.i);
        if (haveConstellation == -2 || haveConstellation >= 6) {
            return false;
        } else if (haveConstellation == -1) {
            var avatar = new Avatar(this.i);
            avatar.setLevel(this.level);
            avatar.forceConstellationLevel(this.constellation);
            avatar.recalcStats();
            params.player.addAvatar(avatar);
            return true;
        } else {
            int itemId =
                    Optional.ofNullable(params.player.getAvatars().getAvatarById(this.i))
                            .map(Avatar::getSkillDepot)
                            .map(depot -> depot.getTalentCostItemId())
                            .orElse((this.i % 1000) + 100);
            return params.player.getInventory().addItem(itemId);
        }
    }
}
