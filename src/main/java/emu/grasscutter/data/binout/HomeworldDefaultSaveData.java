package emu.grasscutter.data.binout;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.game.world.Position;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HomeworldDefaultSaveData {

    @SerializedName(
            value = "homeBlockLists",
            alternate = {"PKACPHDGGEI", "AKOLOBLHDFK", "KFHBFNPDJBE"})
    List<HomeBlock> homeBlockLists;

    @SerializedName(
            value = "bornPos",
            alternate = {"MINCKHBNING", "MBICDPDEKDM", "IJNPADKGNKE"})
    Position bornPos;

    @SerializedName(
            value = "bornRot",
            alternate = {"EJJIOJKFKCO", "IPIIGEMFLHK"})
    Position bornRot;

    @SerializedName(
            value = "djinPos",
            alternate = {"CJAKHCIFHNP", "HHOLBNPIHEM"})
    Position djinPos;

    @SerializedName(
            value = "mainhouse",
            alternate = {"AMDNOHPGKMI", "KNHCJKHCOAN"})
    HomeFurniture mainhouse;

    @SerializedName(
            value = "doorLists",
            alternate = {"BHCPEAOPIDC", "NIHOJFEKFPG"})
    List<HomeFurniture> doorLists;

    @SerializedName(
            value = "stairLists",
            alternate = {"AABEPENIFLN", "EPGELGEFJFK"})
    List<HomeFurniture> stairLists;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class HomeBlock {

        @SerializedName(
                value = "blockId",
                alternate = {"PGDPDIDJEEL", "ANICBLBOBKD", "FGIJCELCGFI"})
        int blockId;

        @SerializedName(
                value = "furnitures",
                alternate = {"NCIMIKKFLOH", "BEAPOFELABD"})
        List<HomeFurniture> furnitures;

        @SerializedName(
                value = "persistentFurnitures",
                alternate = {"GJGNLIINBGB", "MLIODLGDFHJ"})
        List<HomeFurniture> persistentFurnitures;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class HomeFurniture {

        @SerializedName(
                value = "id",
                alternate = {"KMAAJJHPNBA", "FFLCGFGGGND", "ENHNGKJBJAB"})
        int id;

        @SerializedName(
                value = "pos",
                alternate = {"JFKAHNCPDME", "BPCGGBKIAMG", "NGIEEIOLPPO"})
        Position pos;

        @SerializedName(
                value = "rot",
                alternate = {"LKCKOOGFDBM", "HEOCEHKEBFM"})
        Position rot;
    }
}
