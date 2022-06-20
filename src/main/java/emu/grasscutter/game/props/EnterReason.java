package emu.grasscutter.game.props;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public enum EnterReason {
    None(0),
    Login(1),
    DungeonReplay(11),
    DungeonReviveOnWaypoint(12),
    DungeonEnter(13),
    DungeonQuit(14),
    Gm(21),
    QuestRollback(31),
    Revival(32),
    PersonalScene(41),
    TransPoint(42),
    ClientTransmit(43),
    ForceDragBack(44),
    TeamKick(51),
    TeamJoin(52),
    TeamBack(53),
    Muip(54),
    DungeonInviteAccept(55),
    Lua(56),
    ActivityLoadTerrain(57),
    HostFromSingleToMp(58),
    MpPlay(59),
    AnchorPoint(60),
    LuaSkipUi(61),
    ReloadTerrain(62),
    DraftTransfer(63),
    EnterHome(64),
    ExitHome(65),
    ChangeHomeModule(66),
    Gallery(67),
    HomeSceneJump(68),
    HideAndSeek(69);

    private final int value;
    private static final Int2ObjectMap<EnterReason> map = new Int2ObjectOpenHashMap<>();
    private static final Map<String, EnterReason> stringMap = new HashMap<>();

    static {
        Stream.of(values()).forEach(e -> {
            map.put(e.getValue(), e);
            stringMap.put(e.name(), e);
        });
    }

    EnterReason(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static EnterReason getTypeByValue(int value) {
        return map.getOrDefault(value, None);
    }

    public static EnterReason getTypeByName(String name) {
        return stringMap.getOrDefault(name, None);
    }
}
