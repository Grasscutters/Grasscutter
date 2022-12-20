package kcp.highway.erasure.fec;

/**
 * Created by JinMiao
 * 2018/6/6.
 */
public class Fec {
    public static int
            fecHeaderSize      = 6,
            fecDataSize = 2,
            fecHeaderSizePlus2 = fecHeaderSize + fecDataSize, // plus 2B data size
            typeData           = 0xf1,
            typeParity = 0xf2;

}
