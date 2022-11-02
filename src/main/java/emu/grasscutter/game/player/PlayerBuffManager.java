package emu.grasscutter.game.player;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.BuffData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.net.proto.ServerBuffChangeNotifyOuterClass.ServerBuffChangeNotify.ServerBuffChangeType;
import emu.grasscutter.net.proto.ServerBuffOuterClass.ServerBuff;
import emu.grasscutter.server.packet.send.PacketServerBuffChangeNotify;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.val;

@Getter(AccessLevel.PRIVATE)
public class PlayerBuffManager extends BasePlayerManager {
    private int nextBuffUid;

    private final List<PlayerBuff> pendingBuffs;
    private final Int2ObjectMap<PlayerBuff> buffs; // Server buffs

    public PlayerBuffManager(Player player) {
        super(player);
        this.buffs = new Int2ObjectOpenHashMap<>();
        this.pendingBuffs = new LinkedList<>();
    }

    /**
     * Gets a new uid for a server buff
     * @return New integer buff uid
     */
    private int getNextBuffUid() {
        return ++nextBuffUid;
    }

    /**
     * Returns true if the player has a buff with this group id
     * @param groupId Buff group id
     * @return True if a buff with this group id exists
     */
    public synchronized boolean hasBuff(int groupId) {
        return this.getBuffs().containsKey(groupId);
    }

    /**
     * Clears all player buffs
     */
    public synchronized void clearBuffs() {
        // Remove from player
        getPlayer().sendPacket(
            new PacketServerBuffChangeNotify(getPlayer(), ServerBuffChangeType.SERVER_BUFF_CHANGE_TYPE_DEL_SERVER_BUFF, getBuffs().values())
        );

        // Clear
        getBuffs().clear();
    }

    /**
     * Adds a server buff to the player.
     * @param buffId Server buff id
     * @return True if a buff was added
     */
    public boolean addBuff(int buffId) {
        return addBuff(buffId, -1f);
    }

    /**
     * Adds a server buff to the player.
     * @param buffId Server buff id
     * @param duration Duration of the buff in seconds. Set to 0 for an infinite buff.
     * @return True if a buff was added
     */
    public synchronized boolean addBuff(int buffId, float duration) {
        return addBuff(buffId, duration, null);
    }

    /**
     * Adds a server buff to the player.
     * @param buffId Server buff id
     * @param duration Duration of the buff in seconds. Set to 0 for an infinite buff.
     * @param target Target avatar
     * @return True if a buff was added
     */
    public synchronized boolean addBuff(int buffId, float duration, Avatar target) {
        // Get buff excel data
        BuffData buffData = GameData.getBuffDataMap().get(buffId);
        if (buffData == null) return false;

        boolean success = false;

        // Perform onAdded actions
        success |= Optional.ofNullable(GameData.getAbilityData(buffData.getAbilityName()))
            .map(data -> data.modifiers.get(buffData.getModifierName()))
            .map(modifier -> modifier.onAdded)
            .map(onAdded -> {
                System.out.println("onAdded exists");
                boolean s = false;
                for (var a: onAdded) {
                    switch (a.type) {
                        case HealHP:
                            System.out.println("Attempting heal");
                            if (target == null) break;
                            float maxHp = target.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
                            float amount = a.amount.get() + a.amountByTargetMaxHPRatio.get() * maxHp;
                            target.getAsEntity().heal(amount);
                            s = true;
                            System.out.printf("Healed {}", amount);
                            break;
                        default:
                            break;
                    }
                }
                return s;
            })
            .orElse(false);
        System.out.println("Oh no");

        // Set duration
        if (duration < 0f) {
            duration = buffData.getTime();
        }

        // Dont add buff if duration is equal or less than 0
        if (duration <= 0) {
            return success;
        }

        // Clear previous buff if it exists
        if (this.hasBuff(buffData.getGroupId())) {
            this.removeBuff(buffData.getGroupId());
        }

        // Create and store buff
        PlayerBuff buff = new PlayerBuff(getNextBuffUid(), buffData, duration);
        getBuffs().put(buff.getGroupId(), buff);

        // Packet
        getPlayer().sendPacket(new PacketServerBuffChangeNotify(getPlayer(), ServerBuffChangeType.SERVER_BUFF_CHANGE_TYPE_ADD_SERVER_BUFF, buff));

        return true;
    }

    /**
     * Removes a buff by its group id
     * @param buffGroupId Server buff group id
     * @return True if a buff was remove
     */
    public synchronized boolean removeBuff(int buffGroupId) {
        PlayerBuff buff = this.getBuffs().get(buffGroupId);

        if (buff != null) {
            getPlayer().sendPacket(
                new PacketServerBuffChangeNotify(getPlayer(), ServerBuffChangeType.SERVER_BUFF_CHANGE_TYPE_DEL_SERVER_BUFF, buff)
            );
            return true;
        }

        return false;
    }

    public synchronized void onTick() {
        // Skip if no buffs
        if (getBuffs().size() == 0) return;

        long currentTime = System.currentTimeMillis();

        // Add to pending buffs to remove if buff has expired
        for (PlayerBuff buff : getBuffs().values()) {
            if (currentTime > buff.getEndTime()) {
                this.getPendingBuffs().add(buff);
            }
        }

        if (this.getPendingBuffs().size() > 0) {
            // Send packet
            getPlayer().sendPacket(
                new PacketServerBuffChangeNotify(getPlayer(), ServerBuffChangeType.SERVER_BUFF_CHANGE_TYPE_DEL_SERVER_BUFF, this.pendingBuffs)
            );

            // Remove buff from player buff map
            for (PlayerBuff buff : this.getPendingBuffs()) {
                getBuffs().remove(buff.getGroupId());
            }
            this.getPendingBuffs().clear();
        }
    }

    @Getter
    public static class PlayerBuff {
        private final int uid;
        private final BuffData buffData;
        private final long endTime;

        public PlayerBuff(int uid, BuffData buffData, float duration) {
            this.uid = uid;
            this.buffData = buffData;
            this.endTime = System.currentTimeMillis() + ((long) duration * 1000);
        }

        public int getGroupId() {
            return getBuffData().getGroupId();
        }

        public ServerBuff toProto() {
            return ServerBuff.newBuilder()
                .setServerBuffUid(this.getUid())
                .setServerBuffId(this.getBuffData().getId())
                .setServerBuffType(this.getBuffData().getServerBuffType().getValue())
                .setInstancedModifierId(1)
                .build();
        }
    }
}
