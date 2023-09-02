package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import java.util.*;
import lombok.Getter;

@Getter
@ResourceType(name = "ChapterExcelConfigData.json")
public class ChapterData extends GameResource {
    @Getter private static final Map<Integer, ChapterData> beginQuestChapterMap = new HashMap<>();
    @Getter private static final Map<Integer, ChapterData> endQuestChapterMap = new HashMap<>();

    @Getter(onMethod_ = @Override)
    private int id;

    private int beginQuestId;
    private int endQuestId;
    private int needPlayerLevel;

    @Override
    public void onLoad() {
        beginQuestChapterMap.put(beginQuestId, this);
        endQuestChapterMap.put(endQuestId, this);
    }
}
