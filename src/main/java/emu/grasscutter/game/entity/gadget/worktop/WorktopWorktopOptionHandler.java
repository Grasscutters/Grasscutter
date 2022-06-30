package emu.grasscutter.game.entity.gadget.worktop;

import emu.grasscutter.game.entity.gadget.GadgetWorktop;
import emu.grasscutter.game.player.Player;

public interface WorktopWorktopOptionHandler {
    boolean onSelectWorktopOption(Player player, GadgetWorktop gadgetWorktop,int option);
}
