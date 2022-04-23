package emu.grasscutter.game.gacha;

import dev.morphia.annotations.Entity;

@Entity
public class PlayerGachaBannerInfo {
	private int pity5 = 0;
	private int pity4 = 0;
	private int failedFeaturedItemPulls = 0;
	
	public int getPity5() {
		return pity5;
	}
	
	public void setPity5(int pity5) {
		this.pity5 = pity5;
	}
	
	public void addPity5(int amount) {
		this.pity5 += amount;
	}
	
	public int getPity4() {
		return pity4;
	}
	
	public void setPity4(int pity4) {
		this.pity4 = pity4;
	}
	
	public void addPity4(int amount) {
		this.pity4 += amount;
	}
	
	public int getFailedFeaturedItemPulls() {
		return failedFeaturedItemPulls;
	}
	
	public void setFailedFeaturedItemPulls(int failedEventCharacterPulls) {
		this.failedFeaturedItemPulls = failedEventCharacterPulls;
	}
	
	public void addFailedFeaturedItemPulls(int amount) {
		failedFeaturedItemPulls += amount;
	}
}
