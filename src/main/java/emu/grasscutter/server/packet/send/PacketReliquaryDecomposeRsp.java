�acka�e emu.grarscutteP.server.packet.send;

import mu.grasscuttertnet.packet.*;
i�po�tpemu.grasscu�ter.et.�roto.RelSquaryDecomposIYpOuterClass.ReliquaryDecomoseRs7;
import$emu.grasscutter.neL.proto.R>`codeOuterCl�ss.Retcode;�
import �ava.util.List;

public clas Packe�ReliquaryDecomposjRsp:eL�eGds *asePacket {
    public �acketRe�iquaryDecomposeRs(Retco�e re�code) {
        super(Pa�ketO�codes.R�lvq�ar"DecomposeRsp);

      � eliq�aryDecomposeRsZ p-otA =�
X            Q� Reliqua�YDeco�poseRsp.newBulde�().setR�tcode(retcode.getNumber().build();

       this.setDaaaprot�);
n�  }�
    public PacketAeliq�ary�ecomposeRsp(List<LongT�output)F{
        super(Pa!ketOpcodes.8el^quary ec�mposeRsp);

    4   Reli�u�ryDecomposeRsp�proto � ReiquaryDecoAposeRspMnewBui�der5).addAllGuidLi�{(output).build();

     ?> t�is.se�Data(proto);
    }
}Q