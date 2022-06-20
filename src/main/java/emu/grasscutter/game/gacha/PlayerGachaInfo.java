package emu.grasscutter.game.gacha;

import dev.morphia.annotations.Entity;

@Entity
public class PlayerGachaInfo {
    private final PlayerGachaBannerInfo standardBanner;
    private final PlayerGachaBannerInfo eventCharacterBanner;
    private final PlayerGachaBannerInfo eventWeaponBanner;

    public PlayerGachaInfo() {
        this.standardBanner = new PlayerGachaBannerInfo();
        this.eventCharacterBanner = new PlayerGachaBannerInfo();
        this.eventWeaponBanner = new PlayerGachaBannerInfo();
    }

    public PlayerGachaBannerInfo getStandardBanner() {
        return this.standardBanner;
    }

    public PlayerGachaBannerInfo getEventCharacterBanner() {
        return this.eventCharacterBanner;
    }

    public PlayerGachaBannerInfo getEventWeaponBanner() {
        return this.eventWeaponBanner;
    }

    public PlayerGachaBannerInfo getBannerInfo(GachaBanner banner) {
        switch (banner.getBannerType()) {
            case EVENT:
                return this.eventCharacterBanner;
            case WEAPON:
                return this.eventWeaponBanner;
            case STANDARD:
            default:
                return this.standardBanner;
        }
    }
}
