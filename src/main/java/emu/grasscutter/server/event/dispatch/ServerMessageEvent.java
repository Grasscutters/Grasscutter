package emu.grasscutter.server.event.dispatch;

import com.google.gson.*;
import emu.grasscutter.server.dispatch.IDispatcher;
import emu.grasscutter.server.event.Event;
import java.util.Base64;
import lombok.*;
import org.java_websocket.WebSocket;

@Getter
@RequiredArgsConstructor
public final class ServerMessageEvent extends Event {
    /**
     * Invokes the event.
     *
     * @param client The client that sent the message.
     * @param object The message.
     */
    public static void invoke(WebSocket client, JsonElement object) {
        var message = IDispatcher.decode(object);
        var isBinary = message.get("binary").getAsBoolean();
        var data = Base64.getDecoder().decode(message.get("data").getAsString());

        // Create the event and invoke it.
        new ServerMessageEvent(client, isBinary, data).call();
    }

    private final WebSocket client;
    private final boolean isBinary;
    private final byte[] message;

    /**
     * @return The message as a string.
     */
    public String asString() {
        if (this.isBinary)
            throw new UnsupportedOperationException("Cannot convert binary message to string.");
        return new String(this.message);
    }

    /**
     * @return The message as a JSON object.
     */
    public JsonObject asJson() {
        return IDispatcher.JSON.fromJson(this.asString(), JsonObject.class);
    }

    /**
     * @return The message as a JSON object. The type is specified.
     */
    public <T> T asJson(Class<T> type) {
        return IDispatcher.JSON.fromJson(this.asString(), type);
    }
}
