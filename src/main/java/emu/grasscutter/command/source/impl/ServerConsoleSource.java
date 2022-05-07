package emu.grasscutter.command.source.impl;

import emu.grasscutter.command.source.BaseCommandSource;

public class ServerConsoleSource extends BaseCommandSource {
    @Override
    public String getPermission() {
        return "*";
    }
}
