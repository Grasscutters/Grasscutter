package emu.grasscutter.utils.objects;

import lombok.*;

/** HTTP request object for handbook controls. */
@SuppressWarnings("FieldMayBeFinal")
public interface HandbookBody {
    @Builder
    @Getter
    class Response {
        private int status;
        private String message;
    }

    enum Action {
        GRANT_AVATAR,
        GIVE_ITEM,
        TELEPORT_TO,
        SPAWN_ENTITY
    }

    @Getter
    class GrantAvatar {
        private String player; // Parse into online player ID.
        private String playerToken; // Parse into session token.
        private String avatar; // Parse into avatar ID.

        private int level = 90; // Range between 1 - 90.
        private int constellations = 6; // Range between 0 - 6.
        private int talentLevels = 10; // Range between 1 - 15.
    }

    @Getter
    class GiveItem {
        private String player; // Parse into online player ID.
        private String playerToken; // Parse into session token.
        private String item; // Parse into item ID.

        private long amount = 1; // Range between 1 - Long.MAX_VALUE.
    }

    @Getter
    class TeleportTo {
        private String player; // Parse into online player ID.
        private String playerToken; // Parse into session token.
        private String scene; // Parse into a scene ID.
    }

    @Getter
    class SpawnEntity {
        private String player; // Parse into online player ID.
        private String playerToken; // Parse into session token.
        private String entity; // Parse into entity ID.

        private long amount = 1; // Range between 1 - Long.MAX_VALUE.
        private int level = 1; // Range between 1 - 200.
    }
}
