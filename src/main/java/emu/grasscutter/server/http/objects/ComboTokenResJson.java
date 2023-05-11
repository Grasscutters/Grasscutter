package emu.grasscutter.server.http.objects;

public class ComboTokenResJson {
    public String message;
    public int retcode;
    public LoginData data = new LoginData();

    public static class LoginData {
        public int account_type = 1;
        public boolean heartbeat;
        public String combo_id;
        public String combo_token;
        public String open_id;
        public String data = "{\"guest\":false}";
        public String fatigue_remind = null; // ?
    }
}
