package emu.grasscutter.game.player;

import dev.morphia.annotations.Entity;
import emu.grasscutter.net.proto.BirthdayOuterClass.Birthday;

@Entity
public class PlayerBirthday {
    private int day;
    private int month;

    public PlayerBirthday() {
        this.day = 0;
        this.month = 0;
    }

    public PlayerBirthday(int day, int month) {
        this.day = day;
        this.month = month;
    }

    public PlayerBirthday set(PlayerBirthday birth) {
        this.day = birth.day;
        this.month = birth.month;

        return this;
    }

    public PlayerBirthday set(int d, int m) {
        this.day = d;
        this.month = m;

        return this;
    }

    public PlayerBirthday setDay(int value) {
        this.day = value;
        return this;
    }

    public PlayerBirthday setMonth(int value) {
        this.month = value;
        return this;
    }

    public int getDay() {
        return this.day;
    }

    public int getMonth() {
        return this.month;
    }

    public Birthday toProto() {
        return Birthday.newBuilder()
            .setDay(this.getDay())
            .setMonth(this.getMonth())
            .build();
    }

    public Birthday.Builder getFilledProtoWhenNotEmpty() {
        if (this.getDay() > 0) {
            return Birthday.newBuilder()
                .setDay(this.getDay())
                .setMonth(this.getMonth());
        }

        return Birthday.newBuilder();
    }
}