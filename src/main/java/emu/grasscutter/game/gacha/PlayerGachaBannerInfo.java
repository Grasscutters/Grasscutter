package emu.grasscutter.game.gacha;

import dev.morphia.annotations.Entity;

@Entity
public class PlayerGachaBannerInfo {
    private int pity5 = 0;
    private int pity4 = 0;
    private int failedFeaturedItemPulls = 0;
    private int failedFeatured4ItemPulls = 0;
    private int pity5Pool1 = 0;
    private int pity5Pool2 = 0;
    private int pity4Pool1 = 0;
    private int pity4Pool2 = 0;

    private int failedChosenItemPulls = 0;
    private int wishItemId = 0;

    public int getPity5() {
        return this.pity5;
    }

    public void setPity5(int pity5) {
        this.pity5 = pity5;
    }

    public void addPity5(int amount) {
        this.pity5 += amount;
    }

    public int getPity4() {
        return this.pity4;
    }

    public void setPity4(int pity4) {
        this.pity4 = pity4;
    }

    public void addPity4(int amount) {
        this.pity4 += amount;
    }

    public int getWishItemId() {
        return this.wishItemId;
    }

    public void setWishItemId(int wishItemId) {
        this.wishItemId = wishItemId;
    }

    public int getFailedChosenItemPulls() {
        return this.failedChosenItemPulls;
    }

    public void setFailedChosenItemPulls(int amount) {
        this.failedChosenItemPulls = amount;
    }

    public void addFailedChosenItemPulls(int amount) {
        this.failedChosenItemPulls += amount;
    }

    public int getFailedFeaturedItemPulls(int rarity) {
        return switch (rarity) {
            case 4 -> this.failedFeatured4ItemPulls;
            default -> this.failedFeaturedItemPulls;  // 5
        };
    }

    public void setFailedFeaturedItemPulls(int rarity, int amount) {
        switch (rarity) {
            case 4 -> this.failedFeatured4ItemPulls = amount;
            default -> this.failedFeaturedItemPulls = amount;  // 5
        }
    }

    public void addFailedFeaturedItemPulls(int rarity, int amount) {
        switch (rarity) {
            case 4 -> this.failedFeatured4ItemPulls += amount;
            default -> this.failedFeaturedItemPulls += amount;  // 5
        }
    }

    public int getPityPool(int rarity, int pool) {
        return switch (rarity) {
            case 4 -> switch (pool) {
                case 1 -> this.pity4Pool1;
                default -> this.pity4Pool2;
            };
            default -> switch (pool) {
                case 1 -> this.pity5Pool1;
                default -> this.pity5Pool2;
            };
        };
    }

    public void setPityPool(int rarity, int pool, int amount) {
        switch (rarity) {
            case 4:
                switch (pool) {
                    case 1 -> this.pity4Pool1 = amount;
                    default -> this.pity4Pool2 = amount;
                }
                break;
            case 5:
            default:
                switch (pool) {
                    case 1 -> this.pity5Pool1 = amount;
                    default -> this.pity5Pool2 = amount;
                }
                break;
        }
    }

    public void addPityPool(int rarity, int pool, int amount) {
        switch (rarity) {
            case 4:
                switch (pool) {
                    case 1 -> this.pity4Pool1 += amount;
                    default -> this.pity4Pool2 += amount;
                }
                break;
            case 5:
            default:
                switch (pool) {
                    case 1 -> this.pity5Pool1 += amount;
                    default -> this.pity5Pool2 += amount;
                }
                break;
        }
    }

    public void incPityAll() {
        this.pity4++;
        this.pity5++;
        this.pity4Pool1++;
        this.pity4Pool2++;
        this.pity5Pool1++;
        this.pity5Pool2++;
    }
}
