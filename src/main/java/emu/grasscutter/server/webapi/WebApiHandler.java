package emu.grasscutter.server.webapi;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.server.webapi.dispatcher.DispatcherPool;
import emu.grasscutter.server.webapi.requestdata.RequestCheckState;
import emu.grasscutter.server.webapi.requestdata.RequestJson;
import emu.grasscutter.server.webapi.response.ResponseBuilder;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class WebApiHandler implements Handler {
    @Override
    public void handle(@NotNull Context context) {
        int contentLen = context.req.getContentLength();
        try {
            var inputStream = context.req.getInputStream();
            if(contentLen <= 0) {
                ResponseBuilder.buildInvalidRequest("Empty request").send(context);
                return;
            }

            byte[] buffer = new byte[0];

            if(inputStream.isReady()) {
                buffer = inputStream.readAllBytes();
            }

            String json = new String(buffer, StandardCharsets.UTF_8);
            for(int i = 0; i < json.length(); i++) {
                if(!Character.isISOControl(json.charAt(i))) {
                    continue;
                }

                ResponseBuilder.buildInvalidRequest("Invalid character.").send(context);
                return;
            }

            Gson gson = new Gson();
            var requestJson = gson.fromJson(json, RequestJson.class);
            var checkState = requestJson.checkRequest();
            if(checkState != RequestCheckState.SUCCESS) {
                switch (checkState) {
                    case REQUEST_TOKEN_MISSING -> ResponseBuilder.buildInvalidRequest("No token").send(context);
                    case REQUEST_TYPE_MISSING -> ResponseBuilder.buildInvalidRequest("No type").send(context);
                    case INVALID_TOKEN -> ResponseBuilder.buildInvalidRequest("Token mismatch").send(context);
                }
                return;
            }


            DispatcherPool.getInstance().get(requestJson.getRequestType()).dispatch(requestJson, context);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch(JsonParseException e) {
            ResponseBuilder.buildNormalError(e.getMessage(), null).send(context);
        }
        catch(Exception e) {
            ResponseBuilder.buildNormalError("Error when handling the request.", null).send(context);
            Grasscutter.getLogger().error(e.toString());
        }
    }
}
