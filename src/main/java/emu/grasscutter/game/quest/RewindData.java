package emu.grasscutter.game.quest;

import java.util.List;
import lombok.*;

@Data
public class RewindData {
    AvatarData avatar;
    List<Npc> npcs;

    @Data
    public static class AvatarData {
        @Getter private String pos;
    }

    @Data
    public static class Npc {
        private String script;
        private int room_id;
        private int data_index;
        private int id;
        private String pos;
        private int scene_id;
        private String alias;
    }
}
