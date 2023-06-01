package emu.grasscutter.game.entity.gadget.chest;

import emu.grasscutter.game.entity.gadget.GadgetChest;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.ChestReward;
import emu.grasscutter.server.event.player.PlayerOpenChestEvent;
import java.util.Random;

public class NormalChestInteractHandler implements ChestInteractHandler {
    private final ChestReward chestReward;

    public NormalChestInteractHandler(ChestReward rewardData) {
        this.chestReward = rewardData;
    }

    @Override
    public boolean isTwoStep() {
        return false;
    }

    @Override
    public boolean onInteract(GadgetChest chest, Player player) {
        // Invoke open chest event.
        var event = new PlayerOpenChestEvent(player, chest, this.chestReward);
        event.call();
        if (event.isCanceled()) return true;

        player.earnExp(chestReward.getAdvExp());
        player.getInventory().addItem(201, chestReward.getResin());

        var mora = chestReward.getMora() * (1 + (player.getWorldLevel() - 1) * 0.5);
        player.getInventory().addItem(202, (int) mora);

        for (int i = 0; i < chestReward.getContent().size(); i++) {
            chest
                    .getGadget()
                    .getScene()
                    .addItemEntity(
                            chestReward.getContent().get(i).getItemId(),
                            chestReward.getContent().get(i).getCount(),
                            chest.getGadget());
        }

        var random = new Random(System.currentTimeMillis());
        for (int i = 0; i < chestReward.getRandomCount(); i++) {
            var index = random.nextInt(chestReward.getRandomContent().size());
            var item = chestReward.getRandomContent().get(index);
            chest
                    .getGadget()
                    .getScene()
                    .addItemEntity(item.getItemId(), item.getCount(), chest.getGadget());
        }

        return true;
    }
}
