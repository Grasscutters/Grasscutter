package emu.grasscutter.game.quest;

import lombok.Data;
import lombok.Getter;

import java.util.List;
@Data
public class RewindData {
    AvatarData avatar;
    List<Npc> npcs;

    @Data
    public static class AvatarData {
        @Getter private String pos;
    }

    @Data
    private static class Npc {
        private String script;
        private int room_id;
        private int data_index;
        private int id;
        private String pos;
        private int scene_id;
        private String alias;
    }
}

