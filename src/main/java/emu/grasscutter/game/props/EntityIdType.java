package emu.grasscutter.game.props;

import java.util.*;

public enum EntityIdType {
    AVATAR(0x01),
    MONSTER(0x02),
    NPC(0x03),
    GADGET(0x04),
    REGION(0x05),
    WEAPON(0x06),
    TEAM(0x09),
    MPLEVEL(0x0b);

    private final int id;

    private static final Map<Integer, EntityType> map = new HashMap<>();

    static {
        map.put(EntityIdType.AVATAR.getId(), EntityType.Avatar);
        map.put(EntityIdType.MONSTER.getId(), EntityType.Monster);
        map.put(EntityIdType.NPC.getId(), EntityType.NPC);
        map.put(EntityIdType.GADGET.getId(), EntityType.Gadget);
        map.put(EntityIdType.REGION.getId(), EntityType.Region);
        map.put(EntityIdType.WEAPON.getId(), EntityType.Equip);
        map.put(EntityIdType.TEAM.getId(), EntityType.Team);
        map.put(EntityIdType.MPLEVEL.getId(), EntityType.MPLevel);
    }

    EntityIdType(int id) {
        this.id = id;
    }

    public static EntityType toEntityType(int entityId) {
        return map.getOrDefault(entityId, EntityType.None);
    }

    public int getId() {
        return id;
    }
}
