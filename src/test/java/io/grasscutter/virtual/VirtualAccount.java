package io.grasscutter.virtual;

import emu.grasscutter.game.Account;

import java.util.Locale;

@SuppressWarnings("deprecation")
public final class VirtualAccount extends Account {
    public VirtualAccount() {
        super();

        this.setId("virtual_account");
        this.setUsername("virtual_account");
        this.setPassword("virtual_account");

        this.setReservedPlayerUid(10001);
        this.setEmail("virtual_account@grasscutter.io");
        this.setLocale(Locale.US);

        this.generateSessionKey();
        this.generateLoginToken();
    }
}
