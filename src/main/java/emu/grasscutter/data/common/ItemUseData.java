package emu.grasscutter.data.common;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseData {
	private ItemUseOp useOp;
	private String[] useParam;

	public ItemUseOp getUseOp() {
	    if (useOp == null) {
	        useOp = ItemUseOp.ITEM_USE_NONE;
	    }
		return useOp;
	}

	public String[] getUseParam() {
		return useParam;
	}
}
