package emu.grasscutter.command.parser;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

public final class PermissionUtil {

    private static final Pattern validCommandPermission = Pattern.compile("^$|^\\w+(\\.\\w+)?$");
    public static String ensurePermissionValid(String permissionString) {
        if (validCommandPermission.matcher(permissionString).matches()) {
            return permissionString;
        }
        throw new RuntimeException("The permission syntax is not valid.");
    }

    public static boolean hasPermission(String[] permissions, String required) {
        // for example:
        // player permissions: "server.*", "player.drop"
        // generated regex: /^$|^server\..*$|^player\.drop$/m
        // "" -> yes
        // "account" -> no
        // "server" -> no
        // "server.account" -> yes
        StringBuilder permissionPattern = new StringBuilder("^$"); // empty permission
        for (String permission: permissions) {
            permissionPattern.append("|^")
                    .append(permission.replace(".", "\\.").replace("*", ".*"))
                    .append("$");
        }

        return Pattern.compile(permissionPattern.toString(), CASE_INSENSITIVE).matcher(required).matches();
    }
}
