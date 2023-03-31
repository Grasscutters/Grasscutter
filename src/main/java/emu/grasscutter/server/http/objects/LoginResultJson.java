package emu.grasscutter.server.http.objects;

public class LoginResultJson {
    public String message;
    public int retcode;
    public VerifyData data = new VerifyData();

    public static class VerifyData {
        public VerifyAccountData account = new VerifyAccountData();
        public boolean device_grant_required = false;
        public String realname_operation = "NONE";
        public boolean realperson_required = false;
        public boolean safe_mobile_required = false;
    }

    public static class VerifyAccountData {
        public String uid;
        public String name = "";
        public String email = "";
        public String mobile = "";
        public String is_email_verify = "0";
        public String realname = "";
        public String identity_card = "";
        public String token;
        public String safe_mobile = "";
        public String facebook_name = "";
        public String twitter_name = "";
        public String game_center_name = "";
        public String google_name = "";
        public String apple_name = "";
        public String sony_name = "";
        public String tap_name = "";
        public String country = "US";
        public String reactivate_ticket = "";
        public String area_code = "**";
        public String device_grant_ticket = "";
    }
}
