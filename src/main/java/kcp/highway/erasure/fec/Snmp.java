package kcp.highway.erasure.fec;

import java.util.concurrent.atomic.LongAdder;


/**
 * Created by JinMiao
 * 2018/8/29.
 */
public class Snmp {
    // bytes sent from upper level
    public LongAdder BytesSent = new LongAdder();
    // bytes received to upper level
    public LongAdder BytesReceived = new LongAdder();
    // max number of connections ever reached
    public LongAdder MaxConn = new LongAdder();
    // accumulated active open connections
    public LongAdder ActiveOpens = new LongAdder();
    // accumulated passive open connections
    public LongAdder PassiveOpens = new LongAdder();
    // current number of established connections
    public LongAdder CurrEstab = new LongAdder();
    // UDP read errors reported from net.PacketConn
    public LongAdder InErrs = new LongAdder();
    // checksum errors from CRC32
    public LongAdder InCsumErrors = new LongAdder();
    // packet iput errors reported from KCP
    public LongAdder KCPInErrors = new LongAdder();
    // incoming packets count
    public LongAdder InPkts = new LongAdder();
    // outgoing packets count
    public LongAdder OutPkts = new LongAdder();
    // incoming KCP segments
    public LongAdder InSegs = new LongAdder();
    // outgoing KCP segments
    public LongAdder OutSegs = new LongAdder();
    // UDP bytes received
    public LongAdder InBytes = new LongAdder();
    // UDP bytes sent
    public LongAdder OutBytes = new LongAdder();
    // accmulated retransmited segments
    public LongAdder RetransSegs = new LongAdder();
    // accmulated fast retransmitted segments
    public LongAdder FastRetransSegs = new LongAdder();
    // accmulated early retransmitted segments
    public LongAdder EarlyRetransSegs = new LongAdder();
    // number of segs infered as lost
    public LongAdder LostSegs = new LongAdder();
    // number of segs duplicated
    public LongAdder RepeatSegs = new LongAdder();
    // correct packets recovered from FEC
    public LongAdder FECRecovered = new LongAdder();
    // incorrect packets recovered from FEC
    public LongAdder FECErrs = new LongAdder();
    // 收到的 Data数量
    public LongAdder FECDataShards = new LongAdder();
    // 收到的 Parity数量
    public LongAdder FECParityShards = new LongAdder();
    // number of data shards that's not enough for recovery
    public LongAdder FECShortShards = new LongAdder();
    // number of data shards that's not enough for recovery
    public LongAdder FECRepeatDataShards = new LongAdder();

    public LongAdder getBytesSent() {
        return BytesSent;
    }

    public void setBytesSent(LongAdder bytesSent) {
        BytesSent = bytesSent;
    }

    public LongAdder getBytesReceived() {
        return BytesReceived;
    }

    public void setBytesReceived(LongAdder bytesReceived) {
        BytesReceived = bytesReceived;
    }

    public LongAdder getMaxConn() {
        return MaxConn;
    }

    public void setMaxConn(LongAdder maxConn) {
        MaxConn = maxConn;
    }

    public LongAdder getActiveOpens() {
        return ActiveOpens;
    }

    public void setActiveOpens(LongAdder activeOpens) {
        ActiveOpens = activeOpens;
    }

    public LongAdder getPassiveOpens() {
        return PassiveOpens;
    }

    public void setPassiveOpens(LongAdder passiveOpens) {
        PassiveOpens = passiveOpens;
    }

    public LongAdder getCurrEstab() {
        return CurrEstab;
    }

    public void setCurrEstab(LongAdder currEstab) {
        CurrEstab = currEstab;
    }

    public LongAdder getInErrs() {
        return InErrs;
    }

    public void setInErrs(LongAdder inErrs) {
        InErrs = inErrs;
    }

    public LongAdder getInCsumErrors() {
        return InCsumErrors;
    }

    public void setInCsumErrors(LongAdder inCsumErrors) {
        InCsumErrors = inCsumErrors;
    }

    public LongAdder getKCPInErrors() {
        return KCPInErrors;
    }

    public void setKCPInErrors(LongAdder KCPInErrors) {
        this.KCPInErrors = KCPInErrors;
    }

    public LongAdder getInPkts() {
        return InPkts;
    }

    public void setInPkts(LongAdder inPkts) {
        InPkts = inPkts;
    }

    public LongAdder getOutPkts() {
        return OutPkts;
    }

    public void setOutPkts(LongAdder outPkts) {
        OutPkts = outPkts;
    }

    public LongAdder getInSegs() {
        return InSegs;
    }

    public void setInSegs(LongAdder inSegs) {
        InSegs = inSegs;
    }

    public LongAdder getOutSegs() {
        return OutSegs;
    }

    public void setOutSegs(LongAdder outSegs) {
        OutSegs = outSegs;
    }

    public LongAdder getInBytes() {
        return InBytes;
    }

    public void setInBytes(LongAdder inBytes) {
        InBytes = inBytes;
    }

    public LongAdder getOutBytes() {
        return OutBytes;
    }

    public void setOutBytes(LongAdder outBytes) {
        OutBytes = outBytes;
    }

    public LongAdder getRetransSegs() {
        return RetransSegs;
    }

    public void setRetransSegs(LongAdder retransSegs) {
        RetransSegs = retransSegs;
    }

    public LongAdder getFastRetransSegs() {
        return FastRetransSegs;
    }

    public void setFastRetransSegs(LongAdder fastRetransSegs) {
        FastRetransSegs = fastRetransSegs;
    }

    public LongAdder getEarlyRetransSegs() {
        return EarlyRetransSegs;
    }

    public void setEarlyRetransSegs(LongAdder earlyRetransSegs) {
        EarlyRetransSegs = earlyRetransSegs;
    }

    public LongAdder getLostSegs() {
        return LostSegs;
    }

    public void setLostSegs(LongAdder lostSegs) {
        LostSegs = lostSegs;
    }

    public LongAdder getRepeatSegs() {
        return RepeatSegs;
    }

    public void setRepeatSegs(LongAdder repeatSegs) {
        RepeatSegs = repeatSegs;
    }

    public LongAdder getFECRecovered() {
        return FECRecovered;
    }

    public void setFECRecovered(LongAdder FECRecovered) {
        this.FECRecovered = FECRecovered;
    }

    public LongAdder getFECErrs() {
        return FECErrs;
    }

    public void setFECErrs(LongAdder FECErrs) {
        this.FECErrs = FECErrs;
    }

    public LongAdder getFECDataShards() {
        return FECDataShards;
    }

    public void setFECDataShards(LongAdder FECDataShards) {
        this.FECDataShards = FECDataShards;
    }

    public LongAdder getFECParityShards() {
        return FECParityShards;
    }

    public void setFECParityShards(LongAdder FECParityShards) {
        this.FECParityShards = FECParityShards;
    }

    public LongAdder getFECShortShards() {
        return FECShortShards;
    }

    public void setFECShortShards(LongAdder FECShortShards) {
        this.FECShortShards = FECShortShards;
    }

    public LongAdder getFECRepeatDataShards() {
        return FECRepeatDataShards;
    }

    public void setFECRepeatDataShards(LongAdder FECRepeatDataShards) {
        this.FECRepeatDataShards = FECRepeatDataShards;
    }

    public static Snmp getSnmp() {
        return snmp;
    }

    public static void setSnmp(Snmp snmp) {
        Snmp.snmp = snmp;
    }

    public static volatile Snmp snmp = new  Snmp();


    @Override
    public String toString() {
        return "Snmp{" +
                "BytesSent=" + BytesSent +
                ", BytesReceived=" + BytesReceived +
                ", MaxConn=" + MaxConn +
                ", ActiveOpens=" + ActiveOpens +
                ", PassiveOpens=" + PassiveOpens +
                ", CurrEstab=" + CurrEstab +
                ", InErrs=" + InErrs +
                ", InCsumErrors=" + InCsumErrors +
                ", KCPInErrors=" + KCPInErrors +
                ", 收到包=" + InPkts +
                ", 发送包=" + OutPkts +
                ", InSegs=" + InSegs +
                ", OutSegs=" + OutSegs +
                ", 收到字节=" + InBytes +
                ", 发送字节=" + OutBytes +
                ", 总共重发数=" + RetransSegs +
                ", 快速重发数=" + FastRetransSegs +
                ", 空闲快速重发数=" + EarlyRetransSegs +
                ", 超时重发数=" + LostSegs +
                ", 收到重复包数量=" + RepeatSegs +
                ", fec恢复数=" + FECRecovered +
                ", fec恢复错误数=" + FECErrs +
                ", 收到fecData数=" + FECDataShards +
                ", 收到fecParity数=" + FECParityShards +
                ", fec缓存冗余淘汰data包数=" + FECShortShards +
                ", fec收到重复的数据包=" + FECRepeatDataShards +
                '}';
    }

}
