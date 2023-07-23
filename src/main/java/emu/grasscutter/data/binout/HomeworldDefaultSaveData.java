package emu.grasscutter.data.binout;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.game.world.Position;
import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HomeworldDefaultSaveData {

    @SerializedName(
            value = "KFHBFNPDJBE",
            alternate = {"PKACPHDGGEI", "AKOLOBLHDFK"})
    List<HomeBlock> homeBlockLists;

    @SerializedName(
            value = "IJNPADKGNKE",
            alternate = {"MINCKHBNING", "MBICDPDEKDM"})
    Position bornPos;

    @SerializedName(
            value = "IPIIGEMFLHK",
            alternate = {"EJJIOJKFKCO"})
    Position bornRot;

    @SerializedName(
            value = "HHOLBNPIHEM",
            alternate = {"CJAKHCIFHNP"})
    Position djinPos;

    @SerializedName(
            value = "KNHCJKHCOAN",
            alternate = {"AMDNOHPGKMI"})
    HomeFurniture mainhouse;

    @SerializedName(
            value = "NIHOJFEKFPG",
            alternate = {"BHCPEAOPIDC"})
    List<HomeFurniture> doorLists;

    @SerializedName(
            value = "EPGELGEFJFK",
            alternate = {"AABEPENIFLN"})
    List<HomeFurniture> stairLists;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class HomeBlock {

        @SerializedName(
                value = "FGIJCELCGFI",
                alternate = {"PGDPDIDJEEL", "ANICBLBOBKD"})
        int blockId;

        @SerializedName(
                value = "BEAPOFELABD",
                alternate = {"NCIMIKKFLOH"})
        List<HomeFurniture> furnitures;

        @SerializedName(
                value = "MLIODLGDFHJ",
                alternate = {"GJGNLIINBGB"})
        List<HomeFurniture> persistentFurnitures;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class HomeFurniture {

        @SerializedName(
                value = "ENHNGKJBJAB",
                alternate = {"KMAAJJHPNBA", "FFLCGFGGGND"})
        int id;

        @SerializedName(
                value = "NGIEEIOLPPO",
                alternate = {"JFKAHNCPDME", "BPCGGBKIAMG"})
        Position pos;
        // @SerializedName(value = "HEOCEHKEBFM", alternate = "LKCKOOGFDBM")
        Position rot;
    }
}
