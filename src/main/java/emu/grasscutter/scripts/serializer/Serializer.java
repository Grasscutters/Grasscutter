package emu.grasscutter.scripts.serializer;

import java.util.List;

public interface Serializer {

    <T> List<T> toList(Class<T> type, Object obj);

    <T> T toObject(Class<T> type, Object obj);
}
