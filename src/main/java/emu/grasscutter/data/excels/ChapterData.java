package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@ResourceType(name = "ChapterExcelConfigData.json")
@Getter
@Setter // TODO: remove on next API break
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterData extends GameResource {
    // Why public? TODO: privatise next API break
    public static final Map<Integer, ChapterData> beginQuestChapterMap = new HashMap<>();
    public static final Map<Integer, ChapterData> endQuestChapterMap = new HashMap<>();

    @Getter(onMethod_ = @Override)
    int id;

    int beginQuestId;
    int endQuestId;
    int needPlayerLevel;

    @Override
    public void onLoad() {
        beginQuestChapterMap.put(beginQuestId, this);
        beginQuestChapterMap.put(endQuestId, this);
    }
}
