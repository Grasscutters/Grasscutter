package emu.grasscutter.utils.objects;

import lombok.Builder;
import lombok.Getter;

/** HTTP request object for handbook controls. */
@SuppressWarnings("FieldMayBeFinal")
public interface HandbookBody {
    @Builder
    class Response {
        private int status;
        private String message;
    }

    @Getter
    class GrantAvatar {
        private String player; // Parse into online player ID.
        private String avatar; // Parse into avatar ID.

        private int level = 90; // Range between 1 - 90.
        private int constellations = 6; // Range between 0 - 6.
        private int talentLevels = 10; // Range between 1 - 15.
    }

    @Getter
    class GiveItem {
        private String player; // Parse into online player ID.
        private String item; // Parse into item ID.

        private int amount = 1; // Range between 1 - Long.MAX_VALUE.
    }
}
