package emu.grasscutter.game.shop;

import java.util.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShopTable {
    private int shopId;
    private List<ShopInfo> items = new ArrayList<>();
}
