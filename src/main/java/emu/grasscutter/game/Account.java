package emu.grasscutter.game;

import static emu.grasscutter.config.Configuration.*;

import dev.morphia.annotations.*;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.utils.*;
import java.util.*;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

@Entity(value = "accounts", useDiscriminator = false)
public class Account {
    @Setter @Getter @Id private String id;

    @Setter
    @Getter
    @Indexed(options = @IndexOptions(unique = true))
    @Collation(locale = "simple", caseLevel = true)
    private String username;

    @Setter @Getter private String password; // Unused for now

    private int reservedPlayerId;
    @Setter private String email;

    @Setter @Getter private String token;
    @Getter private String sessionKey; // Session token for dispatch server
    /** -- GETTER -- The collection of a player's permissions. */
    @Getter private final List<String> permissions;

    @Setter @Getter private Locale locale;

    @Setter @Getter private String banReason;
    @Setter @Getter private int banEndTime;
    @Setter @Getter private int banStartTime;
    private boolean isBanned;

    @Deprecated
    public Account() {
        this.permissions = new ArrayList<>();
        this.locale = LANGUAGE;
    }

    public static boolean permissionMatchesWildcard(String wildcard, String[] permissionParts) {
        String[] wildcardParts = wildcard.split("\\.");
        if (permissionParts.length
                < wildcardParts.length) { // A longer wildcard can never match a shorter permission
            return false;
        }
        for (int i = 0; i < wildcardParts.length; i++) {
            switch (wildcardParts[i]) {
                case "**": // Recursing match
                    return true;
                case "*": // Match only one layer
                    if (i >= (permissionParts.length - 1)) {
                        return true;
                    }
                    break;
                default: // This layer isn't a wildcard, it needs to match exactly
                    if (!wildcardParts[i].equals(permissionParts[i])) {
                        return false;
                    }
            }
        }
        // At this point the wildcard will have matched every layer, but if it is shorter then the
        // permission then this is not a match at this point (no **).
        return (wildcardParts.length == permissionParts.length);
    }

    public int getReservedPlayerUid() {
        return this.reservedPlayerId;
    }

    public void setReservedPlayerUid(int playerId) {
        this.reservedPlayerId = playerId;
    }

    public String getEmail() {
        if (email != null && !email.isEmpty()) {
            return email;
        } else {
            // As of game version 3.5+, only the email is displayed to a user.
            return this.getUsername() + "@" + ACCOUNT.playerEmail;
        }
    }

    public String generateSessionKey() {
        this.sessionKey = Utils.bytesToHex(Crypto.createSessionKey(32));
        this.save();
        return this.sessionKey;
    }

    public boolean isBanned() {
        if (banEndTime > 0 && banEndTime < System.currentTimeMillis() / 1000) {
            this.isBanned = false;
            this.banEndTime = 0;
            this.banStartTime = 0;
            this.banReason = null;
            save();
        }

        return isBanned;
    }

    public void setBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    public boolean addPermission(String permission) {
        if (this.permissions.contains(permission)) return false;
        this.permissions.add(permission);
        return true;
    }

    public boolean hasPermission(String permission) {
        if (permission.isEmpty()) return true;
        if (this.permissions.contains("*") && this.permissions.size() == 1) return true;

        // Add default permissions if it doesn't exist
        List<String> permissions =
                Stream.of(this.permissions, Arrays.asList(ACCOUNT.defaultPermissions))
                        .flatMap(Collection::stream)
                        .distinct()
                        .toList();

        if (permissions.contains(permission)) return true;

        String[] permissionParts = permission.split("\\.");
        for (String p : permissions) {
            if (p.startsWith("-") && permissionMatchesWildcard(p.substring(1), permissionParts))
                return false;
            if (permissionMatchesWildcard(p, permissionParts)) return true;
        }

        return permissions.contains("*");
    }

    public boolean removePermission(String permission) {
        return this.permissions.remove(permission);
    }

    public void clearPermission() {
        this.permissions.clear();
    }

    // TODO make unique
    public String generateLoginToken() {
        this.token = Utils.bytesToHex(Crypto.createSessionKey(32));
        this.save();
        return this.token;
    }

    public void save() {
        DatabaseHelper.saveAccount(this);
    }

    @PreLoad
    public void onLoad(Document document) {
        // Grant the superuser permissions to accounts created before the permissions update
        if (!document.containsKey("permissions")) {
            this.addPermission("*");
        }

        // Set account default language to server default language
        if (!document.containsKey("locale")) {
            this.locale = LANGUAGE;
        }
    }

    @Override
    public String toString() {
        return "Account ID: %s; Username: %s".formatted(this.id, this.username);
    }
}
