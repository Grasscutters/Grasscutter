package emu.grasscutter.server.dispatch.json;

public class RegisterAccount {
    public String username;
    public String password;
    public String password_confirmation;

    @Override
    public String toString() {
        return "RegisterAccount{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", password_confirm='" + password_confirmation + '\'' +
                '}';
    }
}
