package emu.grasscutter.server.webapi.response;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import emu.grasscutter.Grasscutter;
import io.javalin.http.Context;

import java.io.IOException;

public class Response
{
    boolean success;
    @SerializedName("info")
    String textInfo;

    String state;

    Object additionalData;

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public void setTextInfo(String textInfo)
    {
        this.textInfo = textInfo;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }
    public void setAdditionalData(Object additionalData)
    {
        this.additionalData = additionalData;
    }

    public void send(Context context)
    {
        try
        {
            context.res.getOutputStream().println(new Gson().toJson(this));
        }
        catch (IOException e)
        {
            Grasscutter.getLogger().error("Failed to send json.");
        }
    }
}
