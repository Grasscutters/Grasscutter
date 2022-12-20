package kcp.highway;

import kcp.highway.erasure.fec.Snmp;
import kcp.highway.internal.CodecOutputList;
import io.netty.buffer.ByteBuf;
import kcp.highway.threadPool.ITask;

import java.util.Queue;

/**
 * Created by JinMiao
 * 2018/9/11.
 */
public class ReadTask implements ITask {


    private final Ukcp ukcp;

    public ReadTask(Ukcp ukcp) {
        this.ukcp = ukcp;
    }


    @Override
    public void execute() {
        CodecOutputList<ByteBuf> bufList = null;
        Ukcp ukcp = this.ukcp;
        try {
            //查看连接状态
            if (!ukcp.isActive()) {
                return;
            }
            long current = System.currentTimeMillis();
            Queue<ByteBuf> recieveList = ukcp.getReadBuffer();
            int readCount =0;
            for (; ; ) {
                ByteBuf byteBuf = recieveList.poll();
                if (byteBuf == null) {
                    break;
                }
                readCount++;
                ukcp.input(byteBuf, current);
                byteBuf.release();
            }
            if (readCount==0) {
                return;
            }
            if(ukcp.isControlReadBufferSize()){
                ukcp.getReadBufferIncr().addAndGet(readCount);
            }
            long readBytes = 0;
            if (ukcp.isStream()) {
                int size =0;
                while (ukcp.canRecv()) {
                    if (bufList == null) {
                        bufList = CodecOutputList.newInstance();
                    }
                    ukcp.receive(bufList);
                    size= bufList.size();
                }
                for (int i = 0; i < size; i++) {
                    ByteBuf byteBuf = bufList.getUnsafe(i);
                    readBytes += byteBuf.readableBytes();
                    readBytebuf(byteBuf,current,ukcp);
                }
            } else {
                while (ukcp.canRecv()) {
                    ByteBuf recvBuf = ukcp.mergeReceive();
                    readBytes += recvBuf.readableBytes();
                    readBytebuf(recvBuf,current,ukcp);
                }
            }
            Snmp.snmp.BytesReceived.add(readBytes);
            //判断写事件
            if (!ukcp.getWriteBuffer().isEmpty()&& ukcp.canSend(false)) {
                ukcp.notifyWriteEvent();
            }
        } catch (Throwable e) {
            ukcp.internalClose();
            e.printStackTrace();
        } finally {
            release();
            if (bufList != null) {
                bufList.recycle();
            }
        }
    }


    private void readBytebuf(ByteBuf buf,long current,Ukcp ukcp) {
        ukcp.setLastRecieveTime(current);
        try {
            ukcp.getKcpListener().handleReceive(buf, ukcp);
        } catch (Throwable throwable) {
            ukcp.getKcpListener().handleException(throwable, ukcp);
        }finally {
            buf.release();
        }
    }

    public void release() {
        ukcp.getReadProcessing().set(false);
    }

}
