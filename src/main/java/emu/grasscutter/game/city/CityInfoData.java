package emu.grasscutter.game.city;

import dev.morphia.annotations.Entity;
import emu.grasscutter.net.proto.CityInfoOuterClass.CityInfo;
import lombok.*;

@Setter
@Getter
@Entity
public class CityInfoData {
    private int cityId;

    private int level = 1; // level of the city (include level SotS, level Frostbearing Trees, etc.)

    private int numCrystal = 0; // number of crystals in the city

    public CityInfoData(int cityId) {
        this.cityId = cityId;
    }

    public CityInfo toProto() {
        return CityInfo.newBuilder()
                .setCityId(cityId)
                .setLevel(level)
                .setCrystalNum(numCrystal)
                .build();
    }
}
