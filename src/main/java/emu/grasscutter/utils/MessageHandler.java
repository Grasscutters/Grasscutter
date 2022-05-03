package emu.grasscutter.utils;

public class MessageHandler {
    private String message;

    public MessageHandler(){
        this.message = "";
    }

    public void append(String message){
        this.message += message;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
