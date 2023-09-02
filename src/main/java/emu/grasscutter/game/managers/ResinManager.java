package emu.grasscutter.game.managers;

import static emu.grasscutter.config.Configuration.GAME_OPTIONS;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.*;
import emu.grasscutter.game.props.*;
import emu.grasscutter.net.proto.RetcodeOuterClass;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.Utils;

public class ResinManager extends BasePlayerManager {
    public static final int MAX_RESIN_BUYING_COUNT = 6;
    public static final int AMOUNT_TO_ADD = 60;
    public static final int[] HCOIN_NUM_TO_BUY_RESIN = new int[] {50, 100, 100, 150, 200, 200};

    public ResinManager(Player player) {
        super(player);
    }

    /********************
     * Change resin.
     ********************/
    public synchronized boolean useResin(int amount) {
        // Check if resin enabled.
        if (!GAME_OPTIONS.resinOptions.resinUsage) {
            return true;
        }

        int currentResin = this.player.getProperty(PlayerProperty.PROP_PLAYER_RESIN);

        // Check if the player has sufficient resin.
        if (currentResin < amount) {
            return false;
        }

        // Deduct the resin from the player.
        int newResin = currentResin - amount;
        this.player.setProperty(PlayerProperty.PROP_PLAYER_RESIN, newResin);

        // Check if this has taken the player under the recharge cap,
        // starting the recharging process.
        if (this.player.getNextResinRefresh() == 0 && newResin < GAME_OPTIONS.resinOptions.cap) {
            int currentTime = Utils.getCurrentSeconds();
            this.player.setNextResinRefresh(currentTime + GAME_OPTIONS.resinOptions.rechargeTime);
        }

        // Send packets.
        this.player.sendPacket(new PacketResinChangeNotify(this.player));

        // Battle Pass trigger
        this.player
                .getBattlePassManager()
                .triggerMission(
                        WatcherTriggerType.TRIGGER_COST_MATERIAL, 106, amount); // Resin item id = 106

        return true;
    }

    public synchronized boolean useCondensedResin(int amount) {
        // Don't deduct if resin disabled.
        if (!GAME_OPTIONS.resinOptions.resinUsage) return true;
        return this.player.getInventory().payItem(220007, amount);
    }

    public synchronized void addResin(int amount) {
        // Check if resin enabled.
        if (!GAME_OPTIONS.resinOptions.resinUsage) {
            return;
        }

        // Add resin.
        int currentResin = this.player.getProperty(PlayerProperty.PROP_PLAYER_RESIN);
        int newResin = currentResin + amount;
        this.player.setProperty(PlayerProperty.PROP_PLAYER_RESIN, newResin);

        // Stop recharging if player is now at or over the cap.
        if (newResin >= GAME_OPTIONS.resinOptions.cap) {
            this.player.setNextResinRefresh(0);
        }

        // Send packets.
        this.player.sendPacket(new PacketResinChangeNotify(this.player));
    }

    /********************
     * Recharge resin.
     ********************/
    public synchronized void rechargeResin() {
        // Check if resin enabled.
        if (!GAME_OPTIONS.resinOptions.resinUsage) {
            return;
        }

        int currentResin = this.player.getProperty(PlayerProperty.PROP_PLAYER_RESIN);
        int currentTime = Utils.getCurrentSeconds();

        // Make sure we are currently in "recharging mode".
        // This is denoted by Player.nextResinRefresh being greater than 0.
        if (this.player.getNextResinRefresh() <= 0) {
            return;
        }

        // Determine if we actually need to recharge yet.
        if (currentTime < this.player.getNextResinRefresh()) {
            return;
        }

        // Calculate how much resin we need to refill and update player.
        // Note that this can be more than one in case the player
        // logged off with uncapped resin and is now logging in again.
        int recharge =
                1
                        + (int)
                                ((currentTime - this.player.getNextResinRefresh())
                                        / GAME_OPTIONS.resinOptions.rechargeTime);
        int newResin = Math.min(GAME_OPTIONS.resinOptions.cap, currentResin + recharge);
        int resinChange = newResin - currentResin;

        this.player.setProperty(PlayerProperty.PROP_PLAYER_RESIN, newResin);

        // Calculate next recharge time.
        // Set to zero to disable recharge (because on/over cap.)
        if (newResin >= GAME_OPTIONS.resinOptions.cap) {
            this.player.setNextResinRefresh(0);
        } else {
            int nextRecharge =
                    this.player.getNextResinRefresh() + resinChange * GAME_OPTIONS.resinOptions.rechargeTime;
            this.player.setNextResinRefresh(nextRecharge);
        }

        // Send packets.
        this.player.sendPacket(new PacketResinChangeNotify(this.player));
    }

    /********************
     * Player login.
     ********************/
    public synchronized void onPlayerLogin() {
        // If resin usage is disabled, set resin to cap.
        if (!GAME_OPTIONS.resinOptions.resinUsage) {
            this.player.setProperty(PlayerProperty.PROP_PLAYER_RESIN, GAME_OPTIONS.resinOptions.cap);
            this.player.setNextResinRefresh(0);
        }

        // In case server administrators change the resin cap while players are capped,
        // we need to restart recharging here.
        int currentResin = this.player.getProperty(PlayerProperty.PROP_PLAYER_RESIN);
        int currentTime = Utils.getCurrentSeconds();

        if (currentResin < GAME_OPTIONS.resinOptions.cap && this.player.getNextResinRefresh() == 0) {
            this.player.setNextResinRefresh(currentTime + GAME_OPTIONS.resinOptions.rechargeTime);
        }

        // Send initial notifications on logon.
        this.player.sendPacket(new PacketResinChangeNotify(this.player));
    }

    public int buy() {
        if (this.player.getResinBuyCount() >= MAX_RESIN_BUYING_COUNT) {
            return RetcodeOuterClass.Retcode.RET_RESIN_BOUGHT_COUNT_EXCEEDED_VALUE;
        }

        var res =
                this.player
                        .getInventory()
                        .payItem(201, HCOIN_NUM_TO_BUY_RESIN[this.player.getResinBuyCount()]);
        if (!res) {
            return RetcodeOuterClass.Retcode.RET_HCOIN_NOT_ENOUGH_VALUE;
        }

        this.player.setResinBuyCount(this.player.getResinBuyCount() + 1);
        this.player.setProperty(PlayerProperty.PROP_PLAYER_WAIT_SUB_HCOIN, 0);
        this.addResin(AMOUNT_TO_ADD);
        this.player.sendPacket(
                new PacketItemAddHintNotify(new GameItem(106, AMOUNT_TO_ADD), ActionReason.BuyResin));

        return 0;
    }
}
