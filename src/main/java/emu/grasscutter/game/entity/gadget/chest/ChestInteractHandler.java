package emu.grasscutter.game.entity.gadget.chest;

import emu.grasscutter.game.entity.gadget.GadgetChest;
import emu.grasscutter.game.player.Player;

public interface ChestInteractHandler {

    boolean isTwoStep();

    boolean onInteract(GadgetChest chest, Player player);
}
