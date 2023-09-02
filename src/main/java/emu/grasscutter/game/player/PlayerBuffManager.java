package emu.grasscutter.game.player;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.data.excels.BuffData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.net.proto.ServerBuffChangeNotifyOuterClass.ServerBuffChangeNotify.ServerBuffChangeType;
import emu.grasscutter.net.proto.ServerBuffOuterClass.ServerBuff;
import emu.grasscutter.server.packet.send.PacketServerBuffChangeNotify;
import it.unimi.dsi.fastutil.ints.*;
import java.util.*;
import lombok.Getter;

public final class PlayerBuffManager extends BasePlayerManager {
    private final List<PlayerBuff> pendingBuffs;
    private final Int2ObjectMap<PlayerBuff> buffs; // Server buffs
    private int nextBuffUid;

    public PlayerBuffManager(Player player) {
        super(player);

        this.buffs = new Int2ObjectOpenHashMap<>();
        this.pendingBuffs = new ArrayList<>();
    }

    /**
     * Gets a new uid for a server buff
     *
     * @return New integer buff uid
     */
    private int getNextBuffUid() {
        return ++nextBuffUid;
    }

    /**
     * Returns true if the player has a buff with this group id
     *
     * @param groupId Buff group id
     * @return True if a buff with this group id exists
     */
    public synchronized boolean hasBuff(int groupId) {
        return this.buffs.containsKey(groupId);
    }

    /** Clears all player buffs */
    public synchronized void clearBuffs() {
        // Remove from player
        getPlayer()
                .sendPacket(
                        new PacketServerBuffChangeNotify(
                                getPlayer(),
                                ServerBuffChangeType.SERVER_BUFF_CHANGE_TYPE_DEL_SERVER_BUFF,
                                this.buffs.values()));

        // Clear
        this.buffs.clear();
    }

    /**
     * Adds a server buff to the player.
     *
     * @param buffId Server buff id
     * @return True if a buff was added
     */
    public boolean addBuff(int buffId) {
        return addBuff(buffId, -1f);
    }

    /**
     * Adds a server buff to the player.
     *
     * @param buffId Server buff id
     * @param duration Duration of the buff in seconds. Set to 0 for an infinite buff.
     * @return True if a buff was added
     */
    public synchronized boolean addBuff(int buffId, float duration) {
        return addBuff(buffId, duration, null);
    }

    /**
     * Adds a server buff to the player.
     *
     * @param buffId Server buff id
     * @param duration Duration of the buff in seconds. Set to 0 for an infinite buff.
     * @param target Target avatar
     * @return True if a buff was added
     */
    public synchronized boolean addBuff(int buffId, float duration, Avatar target) {
        // Get buff excel data
        var buffData = GameData.getBuffDataMap().get(buffId);
        if (buffData == null) return false;

        // Perform onAdded actions
        var success =
                Optional.ofNullable(GameData.getAbilityData(buffData.getAbilityName()))
                        .map(data -> data.modifiers.get(buffData.getModifierName()))
                        .map(modifier -> modifier.onAdded)
                        .map(
                                onAdded -> {
                                    var shouldHeal = false;
                                    for (var ability : onAdded) {
                                        if (Objects.requireNonNull(ability.type) == AbilityModifierAction.Type.HealHP) {
                                            if (target == null) continue;

                                            var maxHp = target.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
                                            var amount =
                                                    ability.amount.get() + ability.amountByTargetMaxHPRatio.get() * maxHp;

                                            target.getAsEntity().heal(amount);
                                            shouldHeal = true;
                                        }
                                    }

                                    return shouldHeal;
                                })
                        .orElse(false);

        // Set duration
        if (duration < 0f) {
            duration = buffData.getTime();
        }

        // Don't add buff if duration is equal or less than 0
        if (duration <= 0) {
            return success;
        }

        // Clear previous buff if it exists
        this.removeBuff(buffData.getGroupId());

        // Create and store buff
        PlayerBuff buff = new PlayerBuff(getNextBuffUid(), buffData, duration);
        this.buffs.put(buff.getGroupId(), buff);

        // Packet
        getPlayer()
                .sendPacket(
                        new PacketServerBuffChangeNotify(
                                getPlayer(), ServerBuffChangeType.SERVER_BUFF_CHANGE_TYPE_ADD_SERVER_BUFF, buff));

        return true;
    }

    /**
     * Removes a buff by its group id
     *
     * @param buffGroupId Server buff group id
     * @return True if a buff was remove
     */
    public synchronized boolean removeBuff(int buffGroupId) {
        PlayerBuff buff = this.buffs.remove(buffGroupId);

        if (buff != null) {
            getPlayer()
                    .sendPacket(
                            new PacketServerBuffChangeNotify(
                                    getPlayer(), ServerBuffChangeType.SERVER_BUFF_CHANGE_TYPE_DEL_SERVER_BUFF, buff));
            return true;
        }

        return false;
    }

    public synchronized void onTick() {
        // Skip if no buffs
        if (this.buffs.isEmpty()) return;

        long currentTime = System.currentTimeMillis();

        // Add to pending buffs to remove if buff has expired
        this.buffs
                .values()
                .removeIf(
                        buff -> {
                            if (currentTime <= buff.getEndTime()) return false;
                            this.pendingBuffs.add(buff);
                            return true;
                        });

        if (this.pendingBuffs.size() > 0) {
            // Send packet
            getPlayer()
                    .sendPacket(
                            new PacketServerBuffChangeNotify(
                                    getPlayer(),
                                    ServerBuffChangeType.SERVER_BUFF_CHANGE_TYPE_DEL_SERVER_BUFF,
                                    this.pendingBuffs));
            this.pendingBuffs.clear();
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
