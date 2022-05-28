package emu.grasscutter.data.excels;

import java.util.Arrays;
import java.util.List;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

import emu.grasscutter.game.props.SceneType;

import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.common.ItemParamStringData;

@ResourceType(name = "RewardPreviewExcelConfigData.json", loadPriority = LoadPriority.HIGH)
public class RewardPreviewData extends GameResource {
	private int id;
	private ItemParamStringData[] previewItems;
	private ItemParamData[] previewItemsArray;
	
	@Override
	public int getId() {
		return this.id;
	}

	public ItemParamData[] getPreviewItems() {
		return previewItemsArray;
	}

	@Override
	public void onLoad() {
		if (this.previewItems != null && this.previewItems.length > 0) {
			this.previewItemsArray = Arrays.stream(this.previewItems)
					.filter(d -> d.getId() > 0 && d.getCount() != null && !d.getCount().isEmpty())
					.map(ItemParamStringData::toItemParamData)
					.toArray(size -> new ItemParamData[size]);
		} else {
			this.previewItemsArray = new ItemParamData[0];
		}
	}
}
