package emu.grasscutter.permission;

public enum PermissionGroup {
    BANNED,
    GUEST,
    USER,
    MODERATOR,
    ADMIN;

    public int getNumber() {
        return ordinal() - 1;
    }

    public static PermissionGroup getGroupByName(String name) {
        for (PermissionGroup group : PermissionGroup.values()) {
            if (group.name().toLowerCase().equalsIgnoreCase(name)) {
                return group;
            }
        }
        return null;
    }

    public static PermissionGroup getGroupByNumber(int number) {
        for (PermissionGroup group : PermissionGroup.values()) {
            if (group.getNumber() == number) {
                return group;
            }
        }
        return null;
    }
}