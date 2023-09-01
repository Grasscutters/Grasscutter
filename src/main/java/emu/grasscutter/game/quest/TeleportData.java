package emu.grasscutter.game.quest;

import lombok.Data;

import java.util.List;

@Data
public class TeleportData {
    List<TransmitPoint> transmit_points;
    List<Npc> npcs;
    List<Gadget> gadgets;

    @Data
    public static class TransmitPoint {
        private int point_id;
        private int scene_id;
        private String pos;
    }

    @Data
    public static class Npc {
        private int data_index;
        private int room_id;
        private int scene_id;
        private int id;
        private String alias;
        private String script;
        private String pos;
    }

    @Data
    public static class Gadget {
        private int id;
        private String pos;
    }
}
