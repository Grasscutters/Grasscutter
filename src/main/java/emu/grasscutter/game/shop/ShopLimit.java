package emu.grasscutter.game.shop;

import dev.morphia.annotations.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class ShopLimit {
    private int shopGoodId;
    private int hasBought;
    private int hasBoughtInPeriod = 0;
    private int nextRefreshTime = 0;
}
