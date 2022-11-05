package emu.grasscutter.server.webapi.response;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import emu.grasscutter.Grasscutter;
import io.javalin.http.Context;

import java.io.IOException;

public class Response {
    int retCode;
    boolean success;
    @SerializedName("info")
    String textInfo;

    Object data;



    int statusCode = 200;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setTextInfo(String textInfo) {
        this.textInfo = textInfo;
    }

    public void setData(Object additionalData) {
        this.data = additionalData;
    }

    public void send(Context context) {
        try {
            context.res.setContentType("application/json");
            context.res.setStatus(statusCode);
            context.res.getOutputStream().println(new Gson().toJson(this));
        }
        catch (IOException e) {
            Grasscutter.getLogger().error("Failed to send json.");
        }
    }
}
