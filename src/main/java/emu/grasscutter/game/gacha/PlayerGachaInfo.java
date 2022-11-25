package emu.grasscutter.game.gacha;

import dev.morphia.annotations.Entity;

@Entity
public class PlayerGachaInfo {
    private PlayerGachaBannerInfo standardBanner;
    private PlayerGachaBannerInfo beginnerBanner;
    private PlayerGachaBannerInfo eventCharacterBanner;
    private PlayerGachaBannerInfo eventWeaponBanner;

    public PlayerGachaInfo() {
        this.standardBanner = new PlayerGachaBannerInfo();
        this.eventCharacterBanner = new PlayerGachaBannerInfo();
        this.eventWeaponBanner = new PlayerGachaBannerInfo();
    }

    public PlayerGachaBannerInfo getStandardBanner() {
        if (this.standardBanner == null) this.standardBanner = new PlayerGachaBannerInfo();
        return this.standardBanner;
    }

    public PlayerGachaBannerInfo getBeginnerBanner() {
        if (this.beginnerBanner == null) this.beginnerBanner = new PlayerGachaBannerInfo();
        return this.beginnerBanner;
    }

    public PlayerGachaBannerInfo getEventCharacterBanner() {
        if (this.eventCharacterBanner == null) this.eventCharacterBanner = new PlayerGachaBannerInfo();
        return this.eventCharacterBanner;
    }

    public PlayerGachaBannerInfo getEventWeaponBanner() {
        if (this.eventWeaponBanner == null) this.eventWeaponBanner = new PlayerGachaBannerInfo();
        return this.eventWeaponBanner;
    }

    public PlayerGachaBannerInfo getBannerInfo(GachaBanner banner) {
        return switch (banner.getBannerType()) {
            case STANDARD -> this.getStandardBanner();
            case BEGINNER -> this.getBeginnerBanner();
            case EVENT, CHARACTER, CHARACTER2 -> this.getEventCharacterBanner();
            case WEAPON -> this.getEventWeaponBanner();
        };
    }
}
