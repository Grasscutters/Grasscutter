package emu.grasscutter.scripts.serializer;

import java.util.*;

public interface Serializer {

    <T> List<T> toList(Class<T> type, Object obj);

    <T> T toObject(Class<T> type, Object obj);

    <T> Map<String, T> toMap(Class<T> type, Object obj);
}
