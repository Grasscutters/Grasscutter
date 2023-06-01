package emu.grasscutter.utils.objects;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import emu.grasscutter.server.dispatch.IDispatcher;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

public interface FieldFetch {
    /**
     * Fetches the specified fields. Serializes them into a JSON object.
     *
     * @param fields The fields to fetch.
     * @return The JSON object containing the fields.
     */
    default JsonObject fetchFields(String... fields) {
        // Prepare field properties.
        var fieldValues = new JsonObject();
        var fieldMap = new HashMap<String, Field>();
        Arrays.stream(this.getClass().getDeclaredFields())
                .forEach(field -> fieldMap.put(field.getName(), field));

        // Find the values of all requested fields.
        for (var fieldName : fields) {
            try {
                var field = fieldMap.get(fieldName);
                if (field == null) fieldValues.add(fieldName, JsonNull.INSTANCE);
                else {
                    var wasAccessible = field.canAccess(this);
                    field.setAccessible(true);
                    fieldValues.add(fieldName, IDispatcher.JSON.toJsonTree(field.get(this)));
                    field.setAccessible(wasAccessible);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return fieldValues;
    }
}
