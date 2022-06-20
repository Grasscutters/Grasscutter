package emu.grasscutter.data.binout;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.utils.Position;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HomeworldDefaultSaveData {

    @SerializedName(value = "KFHBFNPDJBE", alternate = "PKACPHDGGEI")
    List<HomeBlock> homeBlockLists;
    @SerializedName(value = "IJNPADKGNKE", alternate = "MINCKHBNING")
    Position bornPos;
    @SerializedName("IPIIGEMFLHK")
    Position bornRot;
    @SerializedName("HHOLBNPIHEM")
    Position djinPos;
    @SerializedName("KNHCJKHCOAN")
    HomeFurniture mainhouse;

    @SerializedName("NIHOJFEKFPG")
    List<HomeFurniture> doorLists;
    @SerializedName("EPGELGEFJFK")
    List<HomeFurniture> stairLists;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class HomeBlock {

        @SerializedName(value = "FGIJCELCGFI", alternate = "PGDPDIDJEEL")
        int blockId;

        @SerializedName(value = "BEAPOFELABD", alternate = "MLIODLGDFHJ")
        List<HomeFurniture> furnitures;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class HomeFurniture {

        @SerializedName(value = "ENHNGKJBJAB", alternate = "KMAAJJHPNBA")
        int id;
        @SerializedName(value = "NGIEEIOLPPO", alternate = "JFKAHNCPDME")
        Position pos;
        //@SerializedName(value = "HEOCEHKEBFM", alternate = "LKCKOOGFDBM")
        Position rot;
    }
}
