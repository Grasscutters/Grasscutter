package emu.grasscutter.game.gacha;

import dev.morphia.annotations.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
public class PlayerGachaBannerInfo {
	@Getter @Setter private int totalPulls = 0;
	@Getter @Setter private int pity5 = 0;
	@Getter @Setter private int pity4 = 0;
	private int failedFeaturedItemPulls = 0;
	private int failedFeatured4ItemPulls = 0;
	private int pity5Pool1 = 0;
	private int pity5Pool2 = 0;
	private int pity4Pool1 = 0;
	private int pity4Pool2 = 0;

	@Getter @Setter private int failedChosenItemPulls = 0;
	@Getter @Setter private int wishItemId = 0;

	public void addTotalPulls(int amount) {
		this.totalPulls += amount;
	}

	public void addPity5(int amount) {
		this.pity5 += amount;
	}

	public void addPity4(int amount) {
		this.pity4 += amount;
	}

	public void addFailedChosenItemPulls(int amount) {
		failedChosenItemPulls += amount;
	}
	
	public int getFailedFeaturedItemPulls(int rarity) {
		return switch (rarity) {
			case 4 -> failedFeatured4ItemPulls;
			default -> failedFeaturedItemPulls;  // 5
		};
	}
	
	public void setFailedFeaturedItemPulls(int rarity, int amount) {
		switch (rarity) {
			case 4 -> failedFeatured4ItemPulls = amount;
			default -> failedFeaturedItemPulls = amount;  // 5
		};
	}
	
	public void addFailedFeaturedItemPulls(int rarity, int amount) {
		switch (rarity) {
			case 4 -> failedFeatured4ItemPulls += amount;
			default -> failedFeaturedItemPulls += amount;  // 5
		};
	}
	
	public int getPityPool(int rarity, int pool) {
		return switch (rarity) {
			case 4 -> switch (pool) {
				case 1 -> pity4Pool1;
				default -> pity4Pool2;
			};
			default -> switch (pool) {
				case 1 -> pity5Pool1;
				default -> pity5Pool2;
			};
		};
	}
	
	public void setPityPool(int rarity, int pool, int amount) {
		switch (rarity) {
			case 4:
				switch (pool) {
					case 1 -> pity4Pool1 = amount;
					default -> pity4Pool2 = amount;
				};
				break;
			case 5:
			default:
				switch (pool) {
					case 1 -> pity5Pool1 = amount;
					default -> pity5Pool2 = amount;
				};
				break;
		};
	}
	
	public void addPityPool(int rarity, int pool, int amount) {
		switch (rarity) {
			case 4:
				switch (pool) {
					case 1 -> pity4Pool1 += amount;
					default -> pity4Pool2 += amount;
				};
				break;
			case 5:
			default:
				switch (pool) {
					case 1 -> pity5Pool1 += amount;
					default -> pity5Pool2 += amount;
				};
				break;
		};
	}

	public void incPityAll() {
		pity4++;
		pity5++;
		pity4Pool1++;
		pity4Pool2++;
		pity5Pool1++;
		pity5Pool2++;
	}
}
