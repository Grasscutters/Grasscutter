package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ResourceType(name = "ChapterExcelConfigData.json")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterData extends GameResource {
    int id;
    int beginQuestId;
    int endQuestId;
    int needPlayerLevel;

    public static final Map<Integer, ChapterData> beginQuestChapterMap = new HashMap<>();
    public static final Map<Integer, ChapterData> endQuestChapterMap = new HashMap<>();

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void onLoad() {
        beginQuestChapterMap.put(beginQuestId, this);
        beginQuestChapterMap.put(endQuestId, this);
    }
}
