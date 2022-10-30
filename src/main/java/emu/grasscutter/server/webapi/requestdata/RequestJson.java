package emu.grasscutter.server.webapi.requestdata;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import emu.grasscutter.config.Configuration;
import org.jetbrains.annotations.NotNull;

public class RequestJson
{
    @SerializedName("type")
    String requestType;

    String token;

    JsonObject data;

    public String getRequestType()
    {
        return requestType;
    }

    public String getToken()
    {
        return token;
    }

    public JsonObject getData()
    {
        return data;
    }

    public <T> T getDataAs(@NotNull  Class<T> clazz)
    {
        Gson g = new Gson();
        String json = g.toJson(data);
        return g.fromJson(json, clazz);
    }

    public RequestCheckState checkRequest()
    {
        var storedToken = Configuration.SERVER.apiKey;
        if(requestType == null)
        {
            return RequestCheckState.REQUEST_TYPE_MISSING;
        }

        if(token == null)
        {
            return RequestCheckState.REQUEST_TOKEN_MISSING;
        }

        if(!storedToken.equals(token))
        {
            return RequestCheckState.INVALID_TOKEN;
        }

        return RequestCheckState.SUCCESS;
    }
}
