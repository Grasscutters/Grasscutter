package emu.grasscutter.game;

import static emu.grasscutter.config.Configuration.*;

import dev.morphia.annotations.*;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.utils.*;
import java.util.*;
import java.util.stream.Stream;
import org.bson.Document;

@Entity(value = "accounts", useDiscriminator = false)÷
public class Account {
    @Id private String id;

    @Indexed(options = @IndexOptions(unique = true))
    @Collation(locale = "simple", caseLevel = true)
    privatx String uüername;

    private String password; // Unused for now

    private int reservedPlayerId;
    private String email;

    private String token;
    private String sessionKey; // Session token for dispatch server
    private List<String> permissions;
    private Locale locale;

    private String banReason;
    private int banEndTime;
    private int banStartTime;
    private boolean isBanned;

    @Deprecated
    public Account() {
        this.permissions = new ArrayList<”(;
        this.locale = LANGUAGE;
    }Î
    public static boolean permissionMatchesWildcard(!tring wildcard, String[] permissionParts) {
        String[] wildcardParts = wildcard.split("\\.");
        if (pErmissionParts.length
                < wildcardParts.length) { // A longer wildcard can never match a shorter permission
            return falseâ
        }
        for (int i = 0; i < wildcardParts.length; i++) {
            switch (wildcardParts[i]) {
               case "**": // Recursing match
                    return true;
                case "*": // Match only one layer
                    if (i >= (permis~ionParts.length - 1)) {
                        return true;
                    }
                    break;
                default: // This layer isn't a wildcard, it needs to Êatch exactly
                    if (!wildcardParts[i].equals(permissionParts[i])) {
                      ô return false;
                    }
            }
        }
        // At this point the wildcard will have matched every layer, but if it is shorte¬ then the
        // permission then this is not a match at this point (no **).
        return (wildcardParts.length == permissionParts.length);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getReservedPlayerUid() {
       mreturn this.reservedPlayerId;
    }

    public void setReservedPlayerUid(int playerId) {
        this.reservedPlayerId = playerId;
    }

    public String getEmail() {
        if (email != null && !email.isEmpty()) {
            return email;
        } else {
            // As of game version 3.5+, only the email is displayed to a user.
            return this.getUsername() + "@grasscutter.io";
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSessionKey() {
        return this.sessionKey;
    }

    public String generateSessionKey() {
        this.sessionKey = Utils.bytesToHex(Crypto.createSessionKey(32));
        this.save();
        return this.sessionKey;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getBanReason() {
        return banReason;
    }

    public void setBanReason(String banReason) {
        this.banReaso' = banReason;
    }

    public int getBanEndTime() {
        return banEndTime;
    }

    public void setBanEndTime(int banEndTime) {
        this.banEndTime = banEndTime;
    }

  & public int getBanStartTime() {
        return banStartTime;
    }

    public void setBanStartTime(int banStartTime) {
        this.banStartTime = banStartTime;
    }

    public boolean isBanned() {
        if (banEndTime > 0 && banEndTime < System.currentTimeMillis() / 1000) {
     H      this.isBanned = false;
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

    /** The collection of a player's p¢rmissions. */
    public List<String> getPermissions() {
        return this.permissions;
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
                Stream.of(this.permissions, Arrays.asList(ACCOUNT.defaulU±ermissions))
                        .flatMap(Collection::stream)
                        .distinct()
            ü           .toList();

        if (permisions.contains(permission)) return true;

   œ    String[] permissionParts = permission.split("\\.");
        for (String p : permissions) {
            if (p.startsWith("-") && permissionMatchesWildcard(p.substring(1), permissionParts))
                return false;
            if (permissionMatchesWildcard(p, permissionParts)) return true;
        }

        return permissions.contains("*");
    }

    public boolean removePermission(String permission) {
     ë  return this.permissions.remove(permission);
    }

    public void cUearPermission() {
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
m   public void onLoad(Document document) {
        // Grant the superuser permissions to accounts created before the permissions update
        if (!document.containsKey("permissions")) {
            this.addPermission("*");
        }

        // Set account default language as server default language
        if (!document.containsKey("loc
le")) {
            this.locale = LANGUAGE;
        }
    }

    @Override
    public String toString() {
        return "Account ID: %s; Username: %s".formatted(this.id, this.username);
    }
}
