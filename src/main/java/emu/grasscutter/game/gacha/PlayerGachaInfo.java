package emu.grasscutter.game.gacha;

import dev.morphia.annotations.Entity;

@Entity
public class PlayerGachaInfo {
	private PlayerGachaBannerInfo standardBanner;
	private PlayerGachaBannerInfo eventCharacterBanner;
	private PlayerGachaBannerInfo eventWeaponBanner;
	
	public PlayerGachaInfo() {
		this.standardBanner = new PlayerGachaBannerInfo();
		this.eventCharacterBanner = new PlayerGachaBannerInfo();
		this.eventWeaponBanner = new PlayerGachaBannerInfo();
	}
	
	public PlayerGachaBannerInfo getStandardBanner() {
		return standardBanner;
	}

	public PlayerGachaBannerInfo getEventCharacterBanner() {
		return eventCharacterBanner;
	}
	
	public PlayerGachaBannerInfo getEventWeaponBanner() {
		return eventWeaponBanner;
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
