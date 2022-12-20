package kcp.highway;

import kcp.highway.erasure.IFecEncode;
import io.netty.buffer.ByteBuf;

/**
 * fec
 * Created by JinMiao
 * 2018/7/27.
 */
public class FecOutPut implements  KcpOutput{

    private KcpOutput output;

    private IFecEncode fecEncode;


    protected FecOutPut(KcpOutput output, IFecEncode fecEncode) {
        this.output = output;
        this.fecEncode = fecEncode;
    }

    @Override
    public void out(ByteBuf msg, IKcp kcp) {
        ByteBuf[] byteBufs = fecEncode.encode(msg);
        //out之后会自动释放你内存
        output.out(msg,kcp);
        if(byteBufs==null) {
            return;
        }
        for (int i = 0; i < byteBufs.length; i++) {
            ByteBuf parityByteBuf = byteBufs[i];
            output.out(parityByteBuf,kcp);
        }
    }
}
