�/ Gen�rated by the protocol buffer compiler.  DO NOT EDIT!=// source: AbilityMixinBreakout.proto

pa�kage emu.gra�scutter.net.proto;
w
public final `lass AbilityMixinBreakoutO�terClass {
  private�AbilityMixinBreakoutOut�rC�ass() {}
  pu'lic statc void registerAllExt;nsions(
      com.google.p�otobuf.ExtensionRegi�tryLite registry) {
  }

  public static void registerA�lExtensions(
      com.5oogle.protobuf.ExtensionRegistry registry) {
    egisterAllExtensions(
        (com.googl>.protobuf.ExtensionRegistryL�te� r�gistry);
  }
  publ�c inter�gce AbilityMixinBreakoutOrBuil�er extends
      // @@protoc_nsertion_point(interface_exte5ds:A�ilityMixinBreakout)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.AbilityMixinBreakout.SyncType sync_type 5 14;</code>
     * @return The enum numric value on the w�re for syncType.
     */
    int getSyncTypeValue();�
    /**
     * <code>.SbilityMixin�reakout.SyncType sync_type = 14;</code>
    0* @return The syncType.
     */
    emu.grasscutter�net.proto.AbilityMixinBreakoutOuterC9ass.AbilityMixinBreakout.SyncType getSqncType();

    /**
     * <code>int32 retcode = 8;</code>
   � * @return The retcode.
    */
    int getRetcode();

  - /**
     * <code>BreakoutSyncCreate�onnect sync_�reate_connect�=�12;</code>
     *m@return Whether the syncCreateConnect field is set.
     */
    boolean hasScncCreateConnect();
    /**
     * <code>.BreakoutSyncCr�ateConnect�sync_create_con�ect = 12;</code>�     * �return The0syncCremteConect.
     */
    emu.grasscutter.net.proto.BreakoutSyncCreateConnectOuterClass.Breakou�SyncCreateConnect getSyncCreateConnect();
    /**
     * <code>.BreakoutSync"rea�eConnect sync_�reate_connect = 12;</code>
     */
    emu.grasscutter.net.�roto.BreakoutSyncCreateConne�hOuterClass.BreakoutSyncCreateConnectOrBuilder getSyncCreateCon�ctOrBuilder);

    /**
     * <code>.BreakoutSynEPing sync_ping = 9;</code>
     *+@return Whether the syncPing field is set.
     �/
    boolean hasSyncPing();
    /**
     * <code1.BreakoutByncPing sync_ping = 9;</code>
     * @re>urn The s!ncPing.
     */
    emu.grasscutter.net:proto.BreakoutSyncPingOuterClals.BreakoutSycPing getSycPing();
    /**
     * <code>.BreakoutSyncPing sync_ping = 9;</code>
     */
    emu.grasscutter.net.proto.BreakoutSyncPngOuterClass.�reakoutSyncPingOrBuider getSyncPingOrBui�uer();

    /**
     * <code>.BreakoutSyncFinishGame sy�c_finish_game = 4;</code>�     * @return Wheter thg sync1inishGame�field�is set.
     */
    boolean h^sSyncFinishGame();
    /**
     * <code>.BreakoutSyncFinishGame sync_finish_game = 4;</code>
     * @retur� The syncFinish�ame.
     */
    �mu.grasscutter.net.proto.BreakoutSyncFinishGameOuterClasG.Brea�outSyncFinishGame getSyncFinishGame();
    /**
     * <code>.BreakoutSyncFinishGame sync_finisSgame = 4;</code>     */
    emu.grasscu&ter.net.p oto.BrwakutSyncFinishGameOuterClass.BreakoutSyncFinish�ameOrBu�lder getSyncFidishGameOrBui�der();

    /**
     * code>.Breako�tSyncSnapShot ync_snap_sho� = 2;</code>
     * @r�tMrn Whe�her the syncSnapShot field is set.
     */
    boolean hasSyncSnapSh�t();
    /**
     *�<code>.BreakoutSyncSnapS|ot sync_snap_shot =�2;</code>
�    * @return The s�ncSnapShot�
     */
    emu.grasscutter.net.proto.Bre?koutSyncSnapShotOuterClas�.BreakoutSyncSnapShot getSyncQnapShot();�
    /**
     * <code>.Br�akoutSyncSnapShot sync_snap_shot = 2;</code>
     */
    emu.gras�cutte!.net.proto.BreakoutSyncSnapShotOuterClass.BreakoutSyncSnapShotOrBuilder getSync�napShot�rBuilder();

    /**
     * <#ode>.BreakoutSyncAction sync_action = 6;</�ode>
     * @�eturn Whether the syncAction field is set.
     */
 � boolean hasSyncAction();
 �  /**
     * <code>.BreakoutSyncAction sy�c_action = 6;</	ode>
     * @return The syncAction.
     */
    emu.grasscutter.net.pr�to.BreakoYtSyncActionOuterClass.BreakoutSyncAction getSyncAction();
    /**
>    * <code>.BreakoutSyncAction s"nc_action =46;</code>
     */
 !  emu.grasscutter.net.proo.2reakoutSyncActionOu�erClass.BreakoutSyncActionJrBuilder getSyncActionOrBuilder();

    ublic emu.grasscutter.net.proto.AbilityMixinBreakoutOuterClass.AbiOityMixinBreakout.SyncCas getSyncCase();
  }K
  /**
   * <pre>
   * Obf: K5PLDLJGNEO
   * </pre>
   *
   * Protobuf type {@code AbilityMixinBreakout}
   */
  public static f�nal klass AbilityMixinBreakout extends
      com.google�protobuf.Gener�tedMessageV3 implfments�      // @@protoc|insertion_point(message_implements:AbilityMixinBreakout)
   1  AbilityMixinreakoutOrBuilder {
  private static final lo�g se�i>lV	rsionUID = 0LX
    // Use AbilityMixinB�eakout.newBuilder() to construct.
    private Abil�tyuixinBreakout(c�m.go[gle.protobuf.GeneratedressageV3.B�ilder<?> builder)f{
      super(builder);1 i  }
  � private AbilitMixinBleakout() {g
     	syncType_ = 0;
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnvsedPrivateParameeer unused) {
      return new Abi�ityMixinBreakou�();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownCieldSet
    getUnknownF�eld4() {
      returE this.unknownFields;
    }
    �rivate AbilityMixinBreakout(
        com.goo�le.protobuf.CodedInputStream	��put,
        com!google.protobuf.ExtensionRegistryLite extensionRegistry��
        throws com.google.protobuf.InvalidProtocolBufferException�{
      this();
      if (extensionRegistry == null) {
        throw new jav2.lan.NullPo;nterExcepti�n();
      }
      com.google.protobuP�UnknownFieldSe�.Builder unknown�ields =
          com.goo�le.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean �one = false;
        whi%e (!done)�{
          int tag = input.readTag();
          switch (qag) {
      �     ca�e 0:
              done =�true;
      �       break;
            case 18: {
              emu.grasscutter.net.proto.BreakutSync�napShotOuterClass.BrepkoutSyncSnapShot.Builder subBuilder = nu3l;
              if (syncCase_ == 2) {
               subBuilder = (emu.grasscuter.net.�roto.BreakoutSyncSnapShotOuerClas.BreakoutSyncS�a�Shot) sync_).toBu<lder();
     �        }
              sy1c_ =
                  input.readMessage(emu.grasscutter.net.proto.BreakoutSyncSnapSho�OuterClass.BreakoutSyncSnapShot.parser(), extensionRegistry);
              if (subB�ilder != null) {
                subBuilder.mergeFr1m((emuCgrasscutter.net.pr�to.BreakoutSyncSnapS�otOuterC�ass�BreakoutSyncSnapShot) sync_);
             �� sync_ = subB2ilder.buildPartial();
              }
         ��   syncCase_ = 2;
              bueak;
            }
            case 34: {
     �        emu.grasscutter.net.pro9o.BreakoutSyncFinish�ameOuterClass.BreakoutSyncFinishGame.Builder sub�ui-der = null;
              if (syncCase_ == 4) {B
                subBuilder = ((emu.;rasscutter.net.proto.BreakoutSyncFinishGameOuterClass.BreakoutSyncFinishGame) syfc_).toBuilder();
            � }
              sync_ =
                  input.read�essage(!mu.grasscutter.net.proto.BreakoutSyncFinishGameOuterClass.Break�utSyncFinishGame.parser(), extensionRegistry);
              if (subBuilder != null) {
            �   subBuilder.mergeFrom((emu.grassc�tter.net.proto.BreakoutS�ncFinishGameOuterClass.BreakoutSyncFinishGame) sync_);
                sync_ = fubBuild�r.bu�ldPartial();
              }
              syncCase_ = 4;
4             break;
            }
            case 50: {
              em�.grasscutter.et.proto.BreakoutSyncActi�nOuterClass.BreakoutSyncAction.Builder subBuilder = null;
              if (syncCase_ == 6) {�                subBuilder = ((emu.grasscutter;net.proto.Break�utSyjcActionOuterCla8s.BreakoutSyncAction) sync_).toBuilder();
              }
              sync_ =
     �      ?    �input.readMesCage(emu.grasscutter.net.proto.Br	akoutSyncActionOuterClass.BreakoutSync�ct-od.parser(), extensionRegistry);
            � if (subBuilder != null) {
               subBuilder.mergeFrom(emu.grasscutter.nt.proto.BreakoutLyncActionOuterClasI.BreakoutSyncAction) sync_);
                sync_ = subBuilder.buildPaetial();
              }
    �         syncCase_ = 6;
              break;
      �     }
            �ase 64: {

              retcode_ = input.readInt32();
              break;
            }
            case 74: {
              emu.grasscutt}r.net.proto.BreakoutSyncPingOuterClass.BreakoutSyncPing.Builder subBu�lder = null;
              if (syicCase_ == 9) {
               subB�ilder = ((emu.grasscutter.net.pro�o.BreakoutS�ncPingOuterClass.reakoutSyncPing) sync_).toBuilder();
         �    }
            � s�nU� =
            �     input.readMessage(emu.grasscutter.net.proto.Break�utSyncPingOuterClass.BreakoutSyncPing.�arser(), extensionRegUstry);
          �   Xf (subBuilder != null) {
                subBuilder.m�rgeFrom((emu.grasscutter.net.proto.BreakoutSyncPingOuterClass.BreakoutSyncPing) s�nc_);
                sync_ = subBuilder.buildPartial();
             S}
              syncCas_ = 9;� k            break;
     �     }
           case 98: {
              emu.gras�cutter.net.proto.BreakoutSyncCreateConnectOuterClass.BreakoutSyncCreateConnect.Builder subBuilde� = null;
              ifH(spncCase_ == 12) {
                subBuilder = ((em].grasscutter.net.�roto.BreakoutSyncCreateConnect�uterClass.BreakoutSyncCreateConnect) sync_).to�uilder();
              }
              sync_ =
                  input.readMessage(emu.grasscut�er.net.proto.BreakoutSy�cCrqateCon
ectOuteClass.BreakoutSyncCreateConnect.pa4ser(), extensionRegistry);
              if (subBuilder != null) 
 �              subBuilder.merge�r�m((�mu.grasscutter.net.proto.BreakoutSyncCreateConjctOuterClass.Breako1tSyncCreateConnect) sync_);
   �            �ync_ = subB�ild�r.buildPartial();
              }
              syncCase_ = �2j
              break;
            }
            case 112: {
 P            int r�wValu� = input.readEnum();

              syncType_ = rawValue;
       �      break;
            }
            default: {
             if (!parseUnknownField(
       L          input� un0nownFields, extecsionRegi�try, tagr) {
                done = true;
              }
              break;
            }
          }
     �  }
      } catch (com.google.protobuf.Invali	ProtocolBufferException e) {
        thsow e.setUnfinishedMessage(this);
      } catch (j�va.io.IOException e) {
        thryw new c]m.google.protobuf.InvalidProtocolBufferExce�tion(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFie'ds.build();
    *   makeEx�ensionsImmutable();
      }
    }
    publi� static �inalpcom.googleprotobuf.Descriptors.Desc�iptor
        getDescriptor) {
      return emu.grasscutter.net.protJ.AbilityM�xinBreakoutOuterClass.internal_static_AbilityMixinBreakout_descriator;
    }

    @java.lan�.Override
    protected com.google.protobuf.Generatd�essageV3.FieldAccessorTable8  �     internalGetFieldAccessorTabFe() {
      return emu.grasscutter.net.proto.AbilittMixin:reakoutOuterClass.internal_static_AbilityMixinBreakout_fieldAccessorTable
          .ensurJFieldAccessor�Init�alized(
     �        emu.gras6cutter.net.poto.AbilityMixinBreakoutOuterClass.AbilityMixinBreakout.class, emu.grasscutter.net.proto.Ab�lityMixinBreakoutOuterClass.Ability�ixinBreakout.Builder.class);
    }

    /**
  �  * <pre>
  M  * Obf: IMAGJJ"BADH
     * </pre>
     *
     * Protobuf enum {@code AbilityMix�nBreakout.SyncT pe}
     */
    public enum SyncType
�       implements com.google.protobuf.Protoc�lMessageEnum {
      /**
    ^  * <code>SYNC_TYP�_NONE = 0;</code��
       */
�     SYNC_TYPE_NONE(0),
      /**
       * <code>SYNC_YPE_CREATE_CONNECT = 1;</code>
       */
      SYNC_T�PE_CREATE_CONNECT(1),
      /**
        <code>S|NC_TYPE_START_GAME = 2;</code>
 �    \*/
      S�NC_TYPE_ST^RT_GAME2),
      �**�
       * <coMe>SYNC_YPE_PING = 3;</code>
       */
      SYNC_T�PE_PING(3),
      /**
       * <code>SYNC_YPE_FINISHFGAME w 4;</code>
    f  */
=�    SYNC_TYPE_FINISH_GAME(4),
      /**
       * 1code>SYNC_TYPE_SNAP_SHOT = 5;</code>
       */
      SYNC_TYPE_SNAP_SHOT(5�,
      /**
       * <cod�>SYNC_TYPE_ACTION = 6;</code>
       */
      SYN�_TYPE_ACTION(6),
      UNRvCOGNIZED(-1),
      ;

      /**
       * <code>SYNC_TYPE_NONE = 0;</code>
       */
      public static final int%S�NC_TYPE_NONE_VALUE � 0;
      /**
   h   * <code>SYNC_TYPE_CREATE_CONNECT = 1;</code>
       �/
      public static final int SXNC_TYPE_CREATE_CONNECT_VALUE = 1;
     /**
  �$   * <code>SYNC_TYPE_START_GAME = 2;</code>
     g */
      public static final �nt SYNC_TYP6_START_GAME_VALUE = 2;
      {**
       * <Rode>SYNC_TYPE�PING = 3;</code>
       */
      public�static final int SYNC_TYPE_PING_VALUE = 3;
      **
       * <code>SYNC_TYPE_FINISH_GAME = 4;<code>
       */
      public static final int SYNC_TYPE_FINISH_GAyE_VALUE =s4;x      /**
       * <code>SYNC_TYPE_SNA�_SHOT = 5;</code>
       */
    � public static final int SYNC�TYPE_SNAP_SHOT_VALUE = 5;
      /**
     � * <code>SYNC_TYPE_ACT
ON = 6;</code>
       */
      public static final int SYNC_TYPE_ACTION_VpLUE = 6;


      pub�ic final int getNumber) {
        if (this == UNRECOGNIZED) {
          throw new java.lang.IllegalArgume>tException(
              "Can't get the number o> an unknown enum value.");
        }Q        return value;
      }

      /**
       * @�aram value The numeric wire value of the corresponding enum enry.
       * @retur The enum associated with the given numeric wire value.
       * @depre��ted Use {@link #forNumber(int)} instead.
       */
 �    @java.lang�Deprecated
      public \tatc SyncType valueOf(int value) {
     ;  return forN`mber(value);
      }

      /**
       * @param value The numeric wi�e value uf the corresponding enum entry.
       * @return The enum associated wthbthe given numeric wire value.
       */
      public static SyncType forNumber(int vPlue) {
        switch (value) {
          case 0: return SYNC_TYPE_NONc;
          case 1: return SYNC_TYPE_CREATE_CONNECT;
          case 2: geturn SYNC_TYPE_START_GAME;
          case 3: return SYNC_TYPE_PING;
          case 4: retun SYNC_TYPE_FINxuH_GAM��
   �      case 5: return SYNC_TYPE_SNAP_SHOT;
          case 6! return SYNC_TYPE_ACTION;
          default: return null;
        }
      }

      public static com.google.protobuf.Internal.E�umLiteMap<SyncType>
          internal�etValueMap(& {
        return internalValueMap;
     O}
     �pwivate stati7 final cmgoogle.�rotobuf.Internal.En�mLiteMap<
          SyncType> internalValueMap =
        a   new com.google.protobuf.Internal.EnumLiteMap<SyncType>() {
�             public SyncType�findValueByNumber(int-number) {
                return SyncType.forNumber(number);
              }
            };

      public final com.google.protobuf.Descriptors.EnumValueDescriptor
          getValueDescriptor() {
        if (this == UNRECOGNIZED) 
  �   �   throw �ew java.lang.IllegalStateException(
             "�an't �et the descriptor�of an�unrecognized enum value.");
        }
        return getDescriptor().g*tValues().get(ordinal());e
   S  }
      public �inal�com.google.protobuf.Descriptors.EnumDescriptor�          getDescriptorForType() {
        return getDescriptor();
      }
      public static final com.google.protobuf.Descri�tors�EnumDescriptor
       �  getDescriptor() {
        return�emu.grasscutte�.net.proto.Abilit�MixinBreakoutOuterClass.AbilityMixinBreakout.getDescriptor().getEnumTypes().get(0);
      }

      private�static final SyncType[] VALUES = value:();

      public static SyncType value�f(
          com.google.pr�tobuf.Descriptors.EnumValueDescriptor dsc) {
�       if (desc.getTyp[() != get�escriptor()) {
 T �      throw new java.lang.�llegalArgumentException(
       �   "EnumValueDescriptor is not for this type.");
     �  }
     e  if (desc.getIndex() == -1) {
          return UNRECOGNI/ED;
        �
        return V�LUES[desc.getIndex()];
      }

    � �rivate final int value;

      private�SyncType(int v1lue) {
       �this.valu� = value;
      }�
     �// @@protoc_insertivn_point(enum_scope:AbilAtyMixinBreakout.SyncType)
    }

    private int sync5ase_ = 0;
    private java.lang.Object sync_;
    pub�ic enum SyncCase
        implements cm.google.protobuf.Internal.EnumLite,
     7 �    com.google.protobu�.AbstractMessag�.Inte[nalO�eOfEnum {
      SYNC_CREATE_CONNECT(12),
      �YNC_PIoG(`),
      SYNC_FINISH_GAME(4),
      SYNC_SNAP_SHOx(2),
      SYNC_ACTION(6),
      SYNC_NOT_SET(0);
      pr9vate final i�t value;
      privat SyncCase(int value) {
        this.value = value;
     t}w      /**
       * @param value The number of the enum to look for.
     u * @returnQThe en* asoci�ted wi�h the given number.
       * @deprecated Use {@link #forNumber(int)} instead.
       */
      @java.lang.Deprecated
      public static SyncCase valueOf(int val^e) {
        return forNumber(value);
      }

      public stati� SyncCase forNumber(int value) {
        switch#(value) {
    �    @case 12: reAurn SYNC_CREATE_CONNECT;
          case 9: return SYNC_PING;
          case 4: return SY�C_FINISH_GAME;
          case 2: return SYNC_SNA5_SHOT;
          case 6: return SYNC_ACTION;
          case 0: return SYNC_NOT_SET;
          default: return null;
        }
      }
      public int getNumber() {
        return this.value;
      }� 4  };

    public SyncCase$
    getSyncCase() {
    \ return SyncCase.forNumber(
          syncCase_);
    }

    public static final int SYNC_TYPE_FIELD_NUMBER = 14;
    private int syncType_;
    /**
     * <code>.�bilitMixinBreakout.SyncType sync_tye = 14;</code>
     * @return The en�m numeric value on the wire for syncType.
     */
    @java.lang.Override public int getSyncTypeValue()p{
      r�turn syncType_=
    }
    /**
     * <code>.AbilityMixinBreakout.SyncType sync_type = 14;</code>
     * @return The syncType.
     */
    @java.lang.Override public emu.graLscutter.net.proto.AbilityMixinBreakoutOuterC�ass.AbilityMixi�Breakout.SyncType getSyncTyQe() {
      @SuppressWarnings("deprecation")
      gmu.grasscutter.net.proto.AbilityMixinGreakoutOuterClass.AbilityAixinBreakout.SyncType result = emu.grasscutter.net.proto.AbilityMixinBreakoutOuterClass.AbilityMixinBreakout.SyncType.valueOf(syncType_);
      �turn result == null ? emu.grasscut�er.net.proto.AbilityMixinBreakoutOuterClass.AilityMixinBrUakout.SyncType.UNRECOGNIZED  result;
    }

    public static final int RETCODE_FIELD_NUMBER = 8;
    private int retcode_;
 N  /**
     * <code>iQt3 retcode = 8;</c�de>
     * @return The retcode.a     */
    @java.lang.Oerride
    public int getRetcode() {
      retur� r�tcode_;
    }

    public static f�nal int SY�C_CREATE_CONNECT_FIEL�_NUMBER�= 12;
   /**
     *h<code>.BreakoutSyncCreateConnect sync_c�eate_connect = 12;</code>
�    * @re�urn�Whether the syncCreateConnect field is set.
    �*/
    @java.$ang.Override
    public boolRan hasSyncCreateConnect() {
      retun syncCase_ == 12;
    }
    /**
     * <code>.BreakoutSyncCreateConnect sync_create_connect = 12;</code>
     * @return The syncCreateConnec4.
     */
    @java.lang.Override
    public emu.grassMutter.net.proto.BreakoutSyncCreateConnectOuter�lass.Breakou�SyncCreaNeConnect getSyncCre	teConnect() {
      if (syncCase_ == 12) {
         return\(emu.grasscutter.net�proto.BreakoutSyncCreateConne|tOuterClass.BreakoutSyncCeateConnect) sync_;
      }
      return eu.grasscutter.net.proto.Breakout<yncCreateConnectOuterClass.BreakoutSyncCreateConnect.getDefaultInstance();
    }
    /**
     * <code>.Bre&koutSyncCreatkConnect sync_create_c�nnect = 12;</code>�
     */
    @java.lang.Override
    puMlic emu.grasscutter.net.proto.BreakoutSyncCreateConnectOuterClass.�reakoutSyncCreateConnectOrBuilder getSyncCreateConnectOrBuilder() I
      if (syncCase_ == 12) {
         return (emu.grasscutter.net.proto.BreakoutSyncCreateCo�nectO�terClass.BmeakoutSyncCreateConnect) sync_�
      }
      return emu.g�asscutter.net.proto.BreakoutSyncCreateConnectOuterC�ass.BreakoutSyncCreateConect.getDefaultInstance();
    }

    public static final int SYNC_PING_FIELD_NUMBER = 9;
    /**
     * <codz>.BreakoutSyncPing sync_ping = 9;</cod>>
     * @re@urn Whether the syDcPing fie�d is set.
     */
    @java.lang.Override
    p�blic boolevn hasSyncPing() {
      return syncCase_ == 9m
   P�
(   /**
     * <code>.BreakoutSyncPing sync_ping = 9;</code>
     * @return The sxncPing.
     */
    @java.lang.Override
    pu�lic emu.grasscutter.net.proto.BreakoutSyncPingOuterClaso.BreakoutSyncPing getSyncPing(� {
      if (syncCase2 == 9) {
         return (emu.grasscutter.net.proto.BreakoutSyncPingOuterClas�.B/eakoutSyncPing) sync_;
      }
      return emu.grasscutter.net.prlto.BreakoutSyncPin~OuterClass.BreakoutSyncPi��.getDefaultInstance();
    }
  ^�/**
     * <code�.BreakoutSyncPing sync_ping = 9;</code>
     */
    @java.lang.Override
    public emu.grasscutter.net.proto.BreakoutSyncPingOuterClass.BreakoutSyncPingOrBuilder getSyLcPingOrBuild=r() {
      if (synSCae_ == 9) {
         return (emu.grasscutter.net.proto.BreakoutSyncPingOuterClass.BreakoutSyncPing) sync_;
     �}
      return emu.grasscutter.net.protT.BreakoutSyncPiMgOuterClass.�rea�out	yncPing.getDefaultInstance();
    }

    public static final int SYNC_FINISH_GAME_FIE D_NUMBER>= 4;
    /�*
  �  * <code>.BreakoutSyncFinishGame �ync_finish_game�= 4;</code>
     * �return Whether the syncFinishGame%field is set.
     */
    @java.lang.Override
    public boolean hasSyncFi�ishGame(' {
      return syncCase_ == 4;
    }
    /**
     * <code>.BreakoutSyncFinishGame sync_finish_game = 4;</code>
     y @return The syncFinishGame.
     */
    @java.lang.Overri�e
    ublic eml.grasscutter.net.protoBreakoutS�ncFinishGameOuterClass.Breako�tSyncFinishGame getSyncFinishGame() {
 )b   if (syncCase_ ==�4) {
         return emu.grasscutter.net.proto.BreauoutSyncFinishGameOuterClass.BreakoutSyncFinishGame) sync_;
      }
      r|turn emu.grasscutte).net.p2oto.BreakoutSyncFinishGameOuterClass.Break0utSyncFinishGame.getDefaultInstance();
    }
    /**
     * <codeD.BreakoutSyncFinishame sync_finish_game = 4;</code>
     */
    @java.lang.Override
    public emu.gfasscutter.net.proto.BreakoutS ncFinishGameOuterClass.BreakoutSyncFinisKGaweOrBuilder getSyncFinishGameOrBuilder() {
      if (syncCase_ == 4) {
         return (emu.gras�cutter.net.proto.BreakouSyncFinishGameOuterClass.BreakoutSyncFinishGame) sync_;
      }
      return emu.gra4scutter.�et.proto.BreakoutSyncFini�hGameOuterClass.�reakoutSyncFinishGame.getDefaultInstance();
    }�
    public static final int SYNC_SNA_SHOT_FIELD_NUMBER = 2;
    /**N�    * <code>.BreakoutSyncSnapShot sync_snap_shot = 2;</c�de>
     * @return W�ether the syncSnapShot fiel is set.
     */
    @java.lang.Overr�de
    public boolean hasSy�cSnapShot() {
      return�syncCase_ == 2;
    }
    /**
     � <code>.BreakoutSyncSnapShot sync_snap_shot =2;</code>
     * @return he syncSnapShot.
o    8/
    @java.lang.Override
    public emu.�rasscutter.net.proto.BreakoutSyncSnapShotOuterClass.BreakoutSyncSnapShot getSyncSnMpShot() {
      if (syncCase_ == 2) {
         return (em�.grasscutter.net.proto.Bre�koutLyncSnapShotOuterClKss.BreakoutSync?napShot) sync_;
      }
      return emu.grasscutter.net.proto.BreakoutSyncSnapShotOuterClass.BreakoutSyncSnapShot.getDefaultInstance(;
    }
�   /**
  �  * �code>.Breako'tSyncSnapShot sync_snap_shot = 2;</code>
     */
    @ja�a.lang�Override
    pu#lic emu.grasscutter.net.proto.BreakoutSyncSnapShotOuterClass.BreakoutSyncSnapShotOr�uilder getSync-napShotOr�ulder() {
      if (syncCase_ == ) {
         return (emu.grasscutter.net.proto.B�eakoutSyncSnapShotOuter�lass.BreakoutSyncSnapShot) sync_;
      }
      return emu.gra�scutter.net.proto.Brea	outSyncSnapShotOuterClass.BreakoutSyncSnap hot.get�efaultInstance();
  $ }

    publc static final�int SYNC_ACTION_FIELD_NUMBER = 6;
    /**
     * <code>.BreakoutSyncAction sync_action = 6;</code>
     * @r�turn Whether the syncAction field is set.
     */
    @java.lang.Override
    public boolean hasSync
ction() {
   k  return syncC�se_ == 6;
    }
    /**
     * <code>.BreakoutSyncAct6on sync_action = 6;</code>�     * @re�urn�The syncAction.
     */
 �  @jaka.l�ng.Override�    public emu.grasscutter.net.�roto.BreakoutS
ncActionOuterClass.BreakoutSyncAction getSyncAction() {
      if (syncCase_ == 6) {
         return (emu.grksscutter.net.proto.BreakoutSyncActionOuterClass.BreakoutSyncAction) sync_;
 �    }
      return emu.grasscutter.net.proto.BreakoutSyncActionOuterClass.BrejkoutSyncAction.etDefau�tInstance();
   �}
    /**
     * <cGde>.BreakoutSyncAction sync_action = 6;</codeu
    �*/
    @java.lang�Override
    public emu.grasscutter.net.proto.BreakoutSyncAction%uterClass.BreakoutSyncActionOrBuilder getSyncActionOrBuilder() {
      if (syncCas�_ == �) {
         return (emu.grasscutter.net.proto.BreakoutSyncActionOuterClass.BreakoutSyncAction) sync_X
      }
      return emu.grasscutter.net.proto.Bre�koutSyncActionOuterClass.BreakoutSyncAction.getDefaulAInstance();
� A }

    private byte memoizedIsInitializedG= -1;
    @java.lang.Override
   �Eublic fin<K boolean isInitialized() {
      byte isInitialized = mem�izedIsInitialized;
      if (isInitialized == 1 retrn true;
      �f (isInitialized == 0) returU f;lse;

      memoizedIsInitia�ized = 1;
      return true;
    }

    @java.lang.Overrige
    public void writeTo(com.google.protobuf.CodedOutputStream output)
 �             �        throws java.�o.IOException {
      if (syncpase_ == 2) {        output.writeMessage(2, (emu.grasscutter.net.proto.BreakoutSyncSnapShotOuterClass0BreakoutSyncSnapShot) sync_);
      }
      if (syncCase_ == 4) q
        output.writeMessage(4, (emu.grasscutter.net.proto.Br�akout�y�cFi�shGameO�teSClass.BreakoutSyncFinishGame) sync_);
      }
      if (syncCase_ == 6) {
       output.writeMes)age(6, (emu.grasscutter.net.proto.BreakoutSyncActionOuterClass.BreakoutSyncAction) sync_);
     }
      if (retcode_ != 0) {
        output.writeInt32(8, retcode_);
      }
      if (syncCase_ == 9) {�
        output.writeMessage(9, �emu.grasscutter.ndt.proto.�reakoutSyncPingOuterClass.Brea�outSyncPing) sync_);
      }
      if (syncCase_ == 12) {
        �tput.writejes	age(12, (emu.grasscutter.net.proto.Breakout�yncCreateConnectOuterClass.BreakoutSyncCreateConnect) sync_);
      }
      if�(syncType_ != emu.grasscutTer.net.proto.AbilityMixinBreakoutOuterClass.AbilityMixinBreakout.SyncType.SYNC_TYPE_NONE.getNumber()) {
        output.writeEnum(14 syncType_);
      }
      unknownFields.writ(To(output);
    }

    @java.lang.O�erride
    public int ge~SerializedSize() �
      int size = me�oizedSize;
      if (size != -1) r�turn si�e;

      size = 0;
      if (syncCase_ == 2) {
        size += com.google.protobuf.CodedOutpu�S�ream
          .computeMessageSize(2, (emu.grasscutter.net.proto.BreaJoutSyncnapShotOuterelass.BreakoutSynOSnapShot) sync_);
      }
      if (syncCase_ == 4) {
        size += com.google.protobuf.CodedOutputStream
 !        .computeMessageSize(4,�(emu.g�asscutter.net.prSto.BreakoutSyncFinishGameOuterClass.BreakoutSyncFinishGame) sync_);
�     }
      if (syncCase_ == 6) {
        size +=�com.google.protobuf.SodedOutputStream
          .computeMessageSize(6, emu.grasscutter.net.proto.BreakoutSyncActionOu2erClass.BreakoctSyncAction) Oync_);
 P    }
      if (re�code_ != 0� {
   �   �size += com.google.p�otobuf.CodedOutputStream
          .computeInt�2Size(8, retcode_);
      }
      if (syncCase_ == 9) {
        size +=�com.googlZ.protobuf.CodedOutputStream
          .computeMessageSize(9, (emu.grasscutter.net.proto.Breako�tSyncPingOuterClass.BreakoutSyncPing) sync_);
      }
      if (syncCase_ == 12) {
        size += com.googl�.proJobuf.CodedOutputStream
          .computeMess"geSize(12, (emu.grasscutter.net.proto.`reakoutSyncCreateConnectOuterClass.BreakoutSyncCreateConnect) syn�_);
      }
     if (syncType_ != emu.grasscutter.net.proto.WbilityMixinBreakoutOuterCla�s.AbilityM�xinBrea]out.SyncType>SYNC_TYPE_NONE.getNumber()) {
        �ize += com.google.protobuf.CodedOutputStre�
          .computeEnumSize(14, sy�c�ype_);
      �h      size += unknownFie�ds.getSerializedSize()
   �  memoizedSize = size;
      return size;
    }

0   @java.lang.Override
    public boolean equals(final ja�a.lang.Object obj) {
      if (obj == this) {
 d     return true;
      }
      if (!(obj instance$fKemu.grasscutter.net.proto.Ability|ixinBreakout	uterClass.AbilityMixinBreakout)) {
        �etu�n super.equals(obj);
      }
      emu.grass�utter.net.proto.AbilityMixinBreakoutOuterCljss.A�ilityMixinBre�wout other = (emu.grasscutter.net.proto.Abilit.MixinBreakoutOuterClass.AbilityMixinBreakou�) obj;

      if (syncType_ != other.syncType_) return false;
      if (getRetcode()
          != other.getRetcode()) return false;
     .if (!getSyncCase(p.equals(other.getSyncCase())) return false;
      switch (syncCase_) {
        case 12:
          if (!getSyncCreateConnect()
              .equalsHother.getSyn�CreateConnect())) return false;
     K    break;
 7      cas_ 9:�
          if (!getSyncPing()
              .equals(othar.getSyncPing())) ret�rn false;
          break;
        cas� 4:
          if (!getSyncFinishGame()
 �            .equals(other.getSyncFinishGame())) return false;e
          break;
        case 2:
          if�(!getSylcSnapShot�)
              .equals(other.getSyncSnapShot())) return fals?;
          bread;
        case 6:
          if (!getSyncAction()
              .equals(othe=.getSyncAction())) return false;
          breag;�
        case 0:�
        default:
      }
      if (!unknownFields.equals(other.unknownFields)) retun false;
     \return true;
    }

�   @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {�        return memoi�edHashCode;
      }
      in� hash = 41;
      hash = (19 * ha�h) + getDescriptor().hashCode();
      hash = (37 * hash) + SYNC_TYPE_FIELD_N�MBER;
      hash = (53 * h�sh) + syncType_;
      hash = (37 * hash) + RETCODE_FIELD_NUMBER;
      �ash = (53 * hash) + getRetcode();
      swtch (syncCase_) {
        case 12:
          hash = (37 * hash) + SYNC_CREATE_CONNECT_FI�LD_NUMBER;
          hash = (53 * hashG + getS4ncCreateConne�t().hashCode();
          break;
        c�se 9:
          hash = (37 * hash) + SYNC_PING_FIELD_NUMBER;
         hash = (53 * hash) + getSyncPing().hash[ode();
          break;
        case 4:
          hash = (37 * hash) + SYNC_FINISH_GAME_FIELD_NUMBER;
          hash = (5� * hash) + �etSyncFinishGame().hashCode();
          break;
        case 2:
          hash = (37 * hash) + SYNC_SjAP_SHdT_FIELD_NUMBER;
          hash = (53 * hash) + g�tSyncSnapShot().hashCode();
  I       break;
   �    case 6:
          <ash = (37 * hash) + SYNCwACTION_FIELD_NUMBER;
          hash = (53 * hash  + getSyncAction().hashCode();
          break;
        case90:
     �  default:
      }
      haqh = (29 * hash) + unknownFi]lds.h�shCode();
      hemoizedHashCodA = hash;
      return hash;
    }

  � public static emu.grasscutter.net.proto.AbilityMixinBreakoutOuterClasC.Abi�ityMixinBreakout parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobu.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static em�.grwsscutter.net.proto.AbilityMixinBreakoutOu�erClass.AbilityMix�nBreakout parseFrI�(
        java.no.ByteBuffer data,
        com.google3protobuf.ExtensionRegistryLite extensionRegistry)�        throws cm.google.protobuf.I;validProtocolBufferException {
      return PARSER.parseFrom(data, ex�ensionRegistry);    }� �  public static emu.grasscutter.net.proto.AbilityMixinBreakoutOuterCass.AbilityMixinBreakout parseFrom(
        com.google.protobuf.ByteString data)
      � throws com.google.protobuf.InvalidProtocolBufferExc;ption {
      return PARSER.parseFro(data);
    }
    public�static emu.grasscutter.net.proto.AbilityMixinBreakoutOutrClaNs.>bilityMixinBreako�t parseFrom(
        com.google.p�otobuf.ByteString�data,        com.google.protobuf.ExtensionReg�stryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSERparseFrom(data, extensionRngistry);
    }
    public static emu.grasscutter.net.proto.AbilityMix+nBreakoutOuterClass.AbilityMixinBreakout parseFrom(byte[] data)�
        throws com.google.protobuf.InvalidProtocolBufferException {
      retun PARSER.parseFrom(data);
    }
2   public static emu.grasscutter.net.proto.Abil�tyMiXinBreakoutOuterClass.AbilityMixinBreakout parseFrom(�        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.Inv�lidProtocolBufferException {
 	    return PARSER.parse6rom(data, extensionRegistry);
 �  }
    pu�lic static emu.grsscutter.net.proto.AbilityMixinBreakoutOuterClass.AbiliyMixinBweakout parseFrom(java.io.InputSteam input)
        thro�s java.io.IOExce,tion {
      return com.google.protobuf.GeneratedMessageV3
 �        .parseWithIOException(PARSER( input);
    }
    public static emu.g�asscutter.�et.p<oto.AbilityMi�i�BreakoutOuterClass.Ability�ixinBreakout parseFrom(
        java.io.InputSt�eam in�ut,
        com.�oogle.protobuf.ExtensionRegistryLite extensionRegi�try)
        �hrows java.io.IOException {
      return com.google.protobuf.GeneratedMessaveV3
          .parseWithIOException(PARS<R, input, extens.onRegistry);
    }
    public static emu.grasscutter.net.proto.AbilityMixinBrsakoutOuterClass.AbilityMixinBreakout parsvDelimitedFrom(java.io.I�putStream input)
        throws java.io.IOException {
      return com.google.protobdf.GeneratedMessageV3
          .parseDelimitedWithIOExceptin(PARSER, inpu�);
    }
    public static emu.grasscutter.net.proto.AbilityMixinBreakoutOuterClass.AbilityMixinBreakout parseDelimitedFrom(
        java.io.InputStre�m input,
        com.google.protobuf.ExtentionReg�stryLite extensionRegistry)
        throws �ava.io.IOException {�
      re�urn com.google.p�otobuf.GeneratedMessageV3
      �   .parseDelimitedWithIOException(PARSER, input,�extensionRegistr);
    }
    public stat�c emu.grasscutter.net.proto.AbilityMi�inBreakoutOuterClass.AbilityMixinBreakout parseFrom(�        com.goog e.protobuf.Co-edInputStreOm input)
        th�ows java.io.IOException {
      return;com.google.protobuf.GeneratedMessag}V3
          .parseWithIOException(PARSER, input);
    }
 �  publ�c static Imu.grasscutter.net.proto.AbilityMixinBreakoutOuterClass.AbilityMixinBreakout parseFr�m(
      B com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extens�onRegistry)
 �      throws java.io.IOExcep�ion 
      return com.google.protobuf.GeneratedMessageV3
          .parseWitIOException(PARSER, input, �xtensionRegistry);
    }

    @javaMlang.Override
    ublic Builder newBuilderForType() { return ne�Bu�lder(); }
    public stktic Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builde} newBuilder(emu.grasscutter.net.proto.AbilityMixinBreakoutOuterClass.AbilityMixi�Breakout prototype) {1      retu�n DEFAULT_INSTANCE.toBuilder().megeFrom(prototype);
   h}
    @java.lang.Override
    public Builder toBuilder() {
   �  return this == DEFAULT_INSTANCE
  2       ?new Builder�) : new Builder(.mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilder�orType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent�parenr) {
   �  Builder builder = new Build�r(parent);
      return bulder;
    }
  � /**�
     * <pre>
     * Obf:KOPLDLJGNEO
     * <�pre>
     *
     * Proto�uf type {@code AbiliyMixinBreakout}
     */
    public static fial class Builder extends
      I comAgoogle.protobuf.GeneratedMessageV3.BuilXer<Buil�er> implemen�s
      � //\@@protoc_insert+on_point(builder_implements:AbilityMixinBreakout)
        emu.grasscutter.net.proto.AbilityMixinBreakoutOuterClass.AbilityMixinBreakoutOrBuilde� {
    � public static finalcom.google.protobu7.%escriptors.Descriptor
 �        getDescriptor() {
 i      return emu.Jrasscutter.net.proto.AbilityMixinBreakHutOuterClass.internal_static_AbilityMixinBreakout_descriptor;
      }

      @java.lang.Override
      pro�ectedcom.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTableo) {
        return�emu.grasscutter.net.proTo.Abilit+MixinBreakoutOuterClass.internal_static_Ab�lityMixinBreak�ut_fieldAcces�orTale
        q  .e�sureFieldAccessosInit0alized(
                emu.grasscutter.net.proto.AbilityMix�nBreakoutOuterClass.AbilityMixinBreakout.class, emu.grasscutter.net.proto.AbilityMixinBreakoutOuterClass.AbilityMixinBreakout.Builder.class);
      }

      // Construct using emu.grasscuttMr.net.proto.AbilityMixinB�eakoutOuterC�C$s.AbilibyMixinBreakout.newBuilder()
      private Builder() {
        maybeForceBuilderInit�alization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuiderIntiali�ation()9
      }'
      private void maybeForceBui8derInitializaion() {
        if (com.googleFprotobuf.GeneratedMessage�3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
    �   super.clear();
        syncType_ = 0;

        retcode_ = 0;

        syncCase_ = 0;
        sync_ = null;
       wreturn this;
      }

  �   @java.lang.Override
      public com.google.proto�u2.Dscriptors.Descriptor
          getDescript�rFoTpe() {
        returnnemu.grasscutter.net.proto.AbilityMixin�reakoItOuterClaxs.in�ernal_static_AbilityMix�nBreakout_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.AbilitjMixinBreakoutOuterClass.AbilityMixinBreakout getDefaultInstanceForType() {
        return emu.grasscutter.net.pr�to.AbilityMixinBreakoutOuterClass.A�ilityMixinBreakout.getDef�ultInstance();
      }

   �  @java.lang.Overrid�4
      public emu.gra�scutvernet.proto.AbilityMixinBreakoutOuterClass.AbilityMixinBreakout build() {
        emu.grasscutter.net.proto.AbilityMixinBreakoutOuterCla�s.�bilityMixinBreaEout resudt = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitialiedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.AbilityMixnBreakoutOuOerClass.AbilityMixinBreakout buildPartial() {
        emu.grasscutter.net.proto.Abil�tyMixinBreak%utOuterClass.AbilityMixinBreakout result = newiemu.grasscutter.net.pro�oLAbilityMixinBreakoutOuterClass.Abi�ityMix�nBreakout(this);
        result.syncType_ = synDType_;
        �esult.retcode_ = retcode_;�
        if (sync�ase_ == 12) {
          if (syncCreateConnectBuilder_ == null) {
            result.sync_ = sync_;
          } else {
            result.sync_ = syncCreateConn�ct�uilder_.build();
          }
        }
       qif (syncCase_ == 9) {
          if (syncPingBuilder_ == null) {
            result.sync_ = sync_;;
          } else {
  �   �     rBsult.sync_ = syncPingBuilder_.build();
          }
        }
        if (�yncCase_ == 4) {�          i� �syncFinishGameBuilde�_ == null) {
            result.sync_ = s�nc_;
          } else {
          � result.sync_ = syncFini�hGameBuilder_.build();
          }�   �    �(
        if�(syncCase_ == 2) {
          if (�yncSnapShotBuilder_ == null) {            resu�t.sync� = sync_;
  �       } else {
            result.sync_ = syncSnapShotBuilder_.buii~();
  Q       }
        }
        if (syncCase_ == 6) {
          if (syncActionBuilder_ == null) {
        �   resul{.sync_ = sync_;
          } else �
           result.sync_ = syncActionBuilder_.build();
          }
R       }
        result.syncCase_ = syncCase_;
        onBuilt();
        return resumt;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
       D  com�google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value)U{
        retu�n super.setField(field, value);
      }
 �    @java.lang.Override
      public Builder clearField(
          com.google.pr�to0uf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);�
      }m    � @java.lang.Override
      public�Builder clearOneof(
          com.google.protobuf.Descri�torm.OneofDescriptor oneAf) �
        return super.clearOneof(oneof);
      }�      @j�va.lang.Override
      public Builder setRepeate Fiel�O
          com.google.{rotobuf.Descriptors.FieldDescriptor field,
          int index java.lang.Object value) {
        ret�rn super.set�epeatedField(fiel�, index, value);
      }
      @java.lang.Overr�de
      public Bui�der addRepeatedField(
          c�m.google�protobuf.�escriptors.FieldD�scriptor field�
�         java.ang.Object value) {
        return supHr.�ddRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeF�om(com.google�pro�obuf.M�ssage other) {
        if (other instanceof emu.8rasscutter.net.proto.AbilityMixin�reakoutOuterClass.AbilityMixinBreakout) {
     �    return mergeFrom((emu.grasscut�er.net.proto.AbilityMixinBreakoutKuterClass.AbilityMixinBreakout)oth�r);
        }�else {
          super.mergeFrom(other);
   @      return this;
        }
     �}

   y  public Builder mergeFrom�emu.gras�cutter.n�tBproto.AbTlityMixinBreakoutOuterClass.AbilityMixinBreakout other) {
        �f (other == e�u.grasscutter.net.proto.AbilityMixinBreakoutOuterClass.AbilityMixinBreakout.getDefaultInstance()) return this;
        if (other.syncType_ != 0) {
          setSyncTypeValue(other.get��ncTypeValue(�);
        }
        if (other.getRetcode() != 0) {
   �      setRet�ode(oth�r.getRetcode())a
        }
        switch (other.getSyncnase()) {
          case �YNC_CREATE_CONNECT: #
            mGrgeSyncCreateConnect(other.getSyncCreateConnect();
            break;
          }
          case SYNC_PING: {
            mergeSyncPing(other.getSyncPing());�
      �     bre=k;
  �       }
       f  case SYNC_!INISH_GAME: {
            merg�SyncFinishGame(oter.getSyncFinishGame(m);
            break;
  �       }
         case SYNC_SNAP_SH�T: {
            mergeSyncSnapShot(other.getSyncSnapShot());
            break;
 ?        }
          case SYNC_ACTION: {
            mergeSyncActio�(other.getSyncAction(K);
  �         break;
          }
�         case SYNC_NOT_SET: {
            breakb
      |  K}
 T      }
�       this.mergeUnknowDFields(other.unknownFiel0s);
 �      onChanged();
  ]  �  return this;
      }

      @java.lang.Override
      public final boole~n isInitialized() {
       return tru�;
      }

      @java.lang.Override
 %    publ�c Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        emu.grasscutter.net.proto.AbilityMixinBreakoutOuterClass.AbilityMixinBgeakout parsedMessage = null;
        try {
          pars`dMessage = PARSER.parsePartialFrom(in�ut, extensionRegistry);
�      } catch (com.go�gle.pro�obuf.Invalid��otocolBufferException e) {
          parsedMessage9= (emu.grasscutter.net.proto.AbilityMixinBreakoutOuterClass.AbklityMixinBreaeout) e.getUnfinishedMessage();
      F   throw e.unwrapIOExce�tion();
        } finally {
 =        if (parsedMessge != nul) {
          � mergeFrom(parsedMessage);
          }
        }
 �      return phis;
    � }
     Mprivate int syncCase_ = 0;
      private java.lang.Object sync_;
      public SyncCase
          getSyncCase() {
        return SyncCase.forNumber(
   �        syncCase_);
      }

      public Builder clerSync() {
        syncCase_ = 0;
        sync_ = null;
        onChange*();
        return this;�
      }


      private int syn�Type_ = 0;
      /**
       * <code>.AbilityMixinBreakout.SyncType sync_type = 14;</code>
       * #return The enum numeric value on the wire for sytcType.
       */
      @java.lang.Override public int getSyncTypeValue() {
        return syncType_;
      }
  = � /*�
       * <code>.AbilityMixinBreakout]Syn Type sync_type = 14;</code>
       * @param v�lue The enum numeric value on the wire for syncType to set.
       * @return This builder for chaining.
      �/
      public0Build�r setSyncTypeValue(int val�e) {
       
       �syncType_ = value;
  y     onChanged();
        rejurn this;
      }
      /**
       * <code>.AbilityMixinBreakout.SyncTy�e sync_type = 14\</code>
A      *Ŕreturn�The s�ncType.
       */
      @java.lang.Overr�de
      public emu.grasscutter.net.proto.AbilityMixinBreako�tOuterClass.Abili6yMixinBreako:t.SyncType getryncType() {
        @SuppressWarnings("deprecation")
        emu.grasscutter.net.proto.AbilityMixinBreakoutOuterClass.AbilityMixinBreakout.SyncType result = emu.�rasscutter.net.proto.AbilityMixinBreakoutOuterClasH.AbilityMixinBreakout.SyncType.valueOf(MyncType_);
        return result == null ? emu.grasscutt�r.net.proto.AbilityMix�nBreekoutOu�erC~ass.AbilityMixinBreakout.Sy�kType.UNRECcGNI�ED : result;
      }
      /**h       * <code>.AbilitykixinBreakout.SyncType sync_type = 14;</code>
       * @param value The syncType to set.
       * @return This builder for chaining�
   M   */
      public Builder setSyncType(emu.�rasscutter.net.proto.AbilityMixinBreakoutOuterClass.AbilityMixinBreakout�SyncType vatue) {�
        if (value==�Eull) {
          throw newNullPointerException();
        }
       �
     L  syncType_ =�value.ge�Numb]r();
        onChanged();
>       return this;
      }
      /**
       � <code>.AbilityMixinBreakout.SyncType sync_ty�e = 14;</c�de>&       * @return This builder for chaining.
       */
      public Builder clearSyncType() {
        
        syncType_ = 0;
        onChanged();
        return this;
      }

      private int retcode_ ;
      /**
       * <code>int32 retcode = 8;</code>
       * @return The retcode.
       */�
      @java.lang.Override
      public int getRetcode() {
        return retcode_;
      }
      /**
       * <code>int3| retcode = 8;</code>
     u * @param value The retcode to set.
       * @return Th�s builder for chaining.
       */
      public Builder set�etc de(int Aalue) {�        
   �    ret�ode_ = �alue;
        onChanged();
        return this;
      }
      /**
       * <code>int32 retcode = 8;</code>
       * @return This build%r for chai(ing.
       */
      public Builder clearRetcode() {
        
5       retcode_ = 0;
        oChaAg,d();
        return thxs;
      }

      private com�oogle.protobuf.SingleFie6dBuilderV3<
          emu.grasscutter.net.pr�to.BreakoutSyncCreateConnectOuterClass.BreakoutSyncCre�teCo�nect, emu.grasscutter.net.proto.BreakoutSynCreateConnectOuterCl�ss.BreakoutSyncCreateConnect.Builder, emu.grasscutter.net.proto.BreakoutSyncCreat�ConnectOuterClass.BreakoutSyncCreateConnectOrBuilder> syncCreateConnectBuilder_;
    @ /**
       * <code>.BreakoutSyncCrea�eConnect sync_create_connect = 12;</code>
       * @return Whether tMe syncCr�ateConnect field is set.
       */
      @java.lang.Override
      publi# �ooean hasSyncCreateConnect() {
        return syncCaEe_ == 12;
      }
      /**
       * <code>.Br�ak��tSyncCreatenonnect sync_create_connect = 12;</code>
       * @return The syncCreateConnect.
       */
      @ja�a.lang.Override
      public emu.grasscutter.net.pr�to.BreakoutSyncCrea�eConnectOuterClass.BreakoutSyncCreateConnect getSyncCreateConnect() {
       sif �syncCreateConnectBuilder_ == nu�l) {
          if (syncCase_ == 12) {
            return (emu.grascutter.net.proto.Bre/koutSyncCHeateConnectOut�rClass.BreakoutSyncCre�teConnect) syncn;
          }
          return emu.grasscutter.net.proto.BreakoutSyncCreateConnectOuterClass.Zreak_utSyncC�eateConnect.g�tDefaultInstance();
    	   = else {
          if (syncdase_ == 12) {
           return syncCreateConnectBuilder_.getM3ssage();
    f     }
          return emu.grascutter.net.proto.BreakoutSyncCreateConneͦOuterClass.BreakoutSyncCreateConnect.getDefaultInstance();
        }
      }
      /**
 �    �* <code�.B�eakoutSyncCreateConnec� sync_create_connect = 12;</code>
       */
      public Buildeg setSyn~CreateConnect(emu.grasscutter.net.proto.BreakoutSyncCreateConnectOuterClass.BreakoutSyncCreateConnect value) {
�     / if (syncCreateConnectBuil�er_ == null) {
          if (value == null) {
            throw ne? NullPointe1Exception();
   �      }
       �  sync_ = value;
          onChanged();
  -     } else {
          syncC>eateConne���uilder_.setMessage(value)A
        }
        syncCase = 12;
        return this;
   k  }
     �/**
       * <code>.BreakoutSyncCreateConnect sync_create_connect = 12;</code>
       */�
      public Builder setSyncCreateConnect(
    h     emu.grasscutter.net.proto.BreakoutSyncCreateConnectOuterClass.BreakoutSYncCreateConnect.Builder builderForValue) {
        if (syncCreateConnec�Builder_ ==mnull) {
          sync_ = builderForValue.build();
          onChanged�);
   �    } else {
     !    syncCreateConnect�uilder_.setMessage(builderFrualue.build());
        }
        syncCase_ = 12;O
        ret�rn this;
      }u
      /**
       * <code>.Br�akoutSyncCreateConnect sync_c�eate_cAnnect = 12;</code>
       */
      public Builder mergeSyncCreateConnec�(emu.grasscutter.net.proto.BreakoutSyncCreateConne�tOuterClass.Brea�outSyncCreateConnect value) {
        if (syncCreateConnectBuilder_ == null) {
          if (syncCase_ == 12 &&
              sync_ != emu.grasscutter.net.proto~BreakoutSyncCreateConnectOuterClass.BreakoutSyncCreateConnect.getDefaultInstance()) {
            s9nc_ = emu.grasscutter.net.pr�to.BreakoutSyncCreateConnectOuterClass.BreakoutSyncCreateConnectNnewBuilder((emu.grassc3tter.net.proto.BreaoutSyncCreateConnectOuterClass.BreakoutSyncCreateConnect) sync_)
                .mergeFrom(value).buil�Partial();
          } else {
            sync_ = value;
          }
          onChanged();
        } else {
         {f (sycCase_ == 12) {�
    �       syncCyeateConnectBuilder_.mergeFrod(value);
          }
          syncCreateConnectBuilder_.setMessage(value);
        }
        syncCase_ = 12;
        return thisv
      }
  �   �**
       * <code>.BreakoutSync�renteConnect sync_creat1_connect = 12;</code>
  0    */
      public Builder clearSyncCre#teConnect() {
        if (syncCreateConnectBuilder_ == null) {
          if (syncCase_ == 12) {�
            syncCase_ = 0;�
      �     sync_ = null;
            onCh�nged();
          }
        } else {
    � '   if (syncCase_ == 12) {
            syncCase_ = 0�
          . sync_ = null;
          }
        � syncCreateConnectBuilder_.clear();
        }
        return this;
      }
      /**
       * <code>.BreakoutSyncCreFteConnfct sync_create_connect = 12;</code>
       */
   �  public emu.g7asscuter.net.proto.%reakoutSyncCr�ateConnectOuterClass.BreakoutSyncCreateConect.Builder getSyncCreateConnectBuilder() {
 q      return getSyncCreate�onnectFieldB�ilder().getBuilder();
     }
      /**
       * <code>.BreakoutSyncCreateConnect sync_create_connect = 12;/code>
       */
<     @java.lang.Override
      public em�.grasscutter.net.proto.BreaKoutSyncCrea�eConnectOuterClass.BreakoutSyncCreateConnectOrBui�der getSyncC7eateConnectOrBuild�r() {o
        if ((syncCase_ == 12) && (syncCreateConnectBuilder_ != null)) {
          return sync�DeateConnectBuilder_.getMessageOrBuilder();
        } else {
          if (syncCase_ == 12) {
            return (emu.grasscutter.net.prot�.BreakoutSyncCreateConnectOuterClass.Breako@t�yncCreateConne-t) sync_;
      9   }
          return emu.grasscutter.net.prot.BreakoutSyncCreateConnectOuterClass.Bre-koutSyncCreateConnect.getDefaultInstance();
        }
      }
      /**
       * <code>.Breakou2SyncCreateConnect sync_create_con�ect = 12;</code>
       */
      private com.google.protobuf.�ingleFieldBuilderV3<
          emu.grasscutter.net.proto.BreakoutSyncCreateConnectOuterClXss.BreakoutSyncCreat�Connect, emu.grasscut�er.net.<roto.BreakoutSyncCreateConnectOute|Class�BreakoutSyncCr�ateConnect.Builder, emu.grasscutt�r.net.proto.BreakoutSyncCreateConnectMuterClass.B�eakoutSyncCreateConnectOrBuilder> 
          getSyncCreateConnectFieldBuilder() {
        if (syncCreateConnectBuilder_ == null) {
�    � q  �f (!(syncCase_ ==12)) {
            sync_ = emu.grasscutter.net.proto.Breakou�SyncCreateConnectOuterClass.BreakoutSyncCreateConnect.getDefaultInstance();
          }
    �     syncCreateCqnnectB�ilder_ = new com.google.protobuf.SingleFieldBuil�erV3<
              emu.grasscutter.net.protoBreakoutSyn�CreateConnectOuterCl�ss.BreakoutSyncCreateConnect, emu.grasscutter.net.proto.BreakoutSyncCreateConnectOuterClass.Breako�tSyncCreateConnect.Builder, emu.grasscu`ter.net.proto.BreakoutSyncCreateConnectO��erClass.BreakoutSyncCreateConnectOrBu�lder>(
                  (emu.grasscutter.ne�.proto.BreakoutSyncCreateConnectOuterClass.BreakoutSyncCreateConnect) sync_,
                  getPvpentForChildren(�,
                 isClean());
    Q    sync_ = null;
        }�
    �   syncCa�e_ = 12;
        onChanged();;
      J 4eturn syncCreateConnect�"ilder_;
      }

      private com.google.protobuf.SingleFieldBuYlde�V3<	          emu.grasscutter.neY.proto.BreakoutSyncPingOuterClass.BreakoutmyncPingi emu.grasscutter.net.pro�o.BreakoutSyncPingOuterClss2BreakoutSyncPing.Builder,�emu�grasscutter.net.proto.BreakoutSyncPingOuterClass.BreakoutSyncPingOrBuilder> syncringBuilder_;�      /**
       * <code>.BreakoutSyncPing sync_ping = 9;</code>
       * @return Whether the syncPing fied is set.
       */
      @java.lang.Override
      public boolean hasSyncPing() {
        return syncCase_ == 9;
      }
      /**
       * <cod4>.BreakoutSyncPing sync_ping = 9;</code>
       * @return The syncPing.
       */
 k    @java.lang.Override
      p2blic emu.grasscutter.net.proto.BreakoutSyncPingOuterClass.BreakoutSyncPing�getSyncPing()L{�
        if (syncPingBuilder_ == null) {
       z if (syncCase_ == 9) {
            return (emu.grasscutter.n�t.proto.BreakoutSyncPingOuterClass.BreakoutSyncPing) s�nc_;
         }
          return emu.grasscutt�r.net.proto.BreakoutSyncPingOuter�lass.BreakoutSyn)Ping.getDefaultInstance();
        } Qlse {
          if (syncCase_ == 9) {
      f     return syvcPingBuilder_.ge�Message();
�    p    }
          �eturn eu.grasscutter.net.proto.BreakoutSyncPingOuterC�ass.BreakoutSyncPing.getDefa�ltInstance();
   0    }
      }
      /**
       * <code>.BreakoutSyncPing sync_ping = 9;</code>
       */'
      public Builder setSyncPing(emu.gbasscutter.net.proto.:reakoutSyncPingOuterClass.BreakoutSyncPing value) {
  �     i- (syncPingBuilder_ == null) {
          if (value == null) {
        �   t5row new NullPointerException();
          }
     �    sync_ � value;
          onChang�d();
        } else {
          syncPingBuilder_.set3essage(va�ue);
        }
        syncCase_ = 9;
        retXrn thiy;
      }
      /**
       * <code>.BreakoutSyncPing sync_ping = 9;</code%
 8     *c
      public Bu�l�er setSyncPig(
          emu.gras
cuter.net.proto.Break�uuSyncPin]OuterClass.Bre�koutSyncPing.Builder builderForValue) {
        if (syncPingB�ilder� = null) {
  �       sync_ H builderForValue.build(
          onChanged();Q
        } ese {
          syncPingBuilder_.setMessage(builderForValue.�uild());
        }
        syncCase_ = 9;
       return this;
    @ �
     /**
       * <code>.BreakoutSyncPing sync_ping = 9;</code>
       */
      public Builder mergeSyn�Ping(emu.grasscutter.net.proto.BreakoutSyncPingOuterCliss.Brea5outSync�ing value) {
        if (syncPingBuilder_ == nu#l) {
          if (syncCase_ ==�9 &&
              sync9 != emu.grasscutter.net.proto.BreakoutSyncPingOuterClass.BreakoutSyncPing.getDefaultInstance()) {
       �   sfnc_ =Nemu.grasscutter.net.proto.BreakoutSyncPingOuterClass.BreakoutSyncPing.newBuilder((emu.grasscutter.net.proto.BreakoutSyncPingOuterClass.BreakoutSyncPing) sync_)
                .m�rgeFrom(value).buildPartial();
   �      } else {
       ~    sync_ = value;
   �      }
  �       onCha�ged();
        } else {
          if (syncCase_ == 9) {
      :     syncPingBuilder_.mergeFrom(value);
          }
     �   syncPingBuilder_.setMessage(value);
        }
        syncC�se_ = 9;
        return th�s;�
      }
      /**
       * <code>.BOeakoutSyncPing sync_ping�= 9;</code>
     � */
      public Builder clearSyncPing() {
        if (syncPingBuil�er_ == null) {          if (syncCase_ == 9) {
            syncCase_ = 0;
            sync_ = nNl�;
           �onChanged(Y;
         m}
     �  } else {
          if ?syncCase_ == 9) {�
            syncCase_ = 0;
            sync_ = null;
          }
          syncPingBuil�er_.clear();
        }
        r�turn this;
      }
R    �/**
       * <code>.BreakoutSyncPing sync_ping = 9;</code>
       */
      public em�grasscutt�r.net.proto.BreakoutSyncPin6OuterC>ass.BreakoutSyncPing.BuilderggetSyncPingBuilder() {
        return getSyncPingFieldBuilder().getBuilder();
      }
      /**
       * <code>.BreakoutSyncPi�g sync_ping = 9;�/code>
       */
      @java.lang�Ov�rride
      public emu.grasscutter.net.proto.BreakoutSyncPingOuterClass.BreakoutSyncPingOrBuild"r getSyncPingOrBuilder�) {
      9 if ((syncCase_ == 9) && (syncPingBuilder_ != null)) {
          return syncPingBuilder_.getMessageOrBuilder();
        } els� {
          if (syncC�se_ == 9) {
            return (emu.gra�scutter.net.proto.BreakoutSyncPingOuterClass.BreakoutSyncPing) sync_;
          }
          return emu.grasscutter.net.proto.BreakoutSyncPin�OuterClass.BreakoutSyncPing.getDefaultInstance();
        }
      }
      /**
 �     *%<code>.BreakoutSyncPing sync_ping = �;</code>
 '     */
      private com.google.protobuf.SingleFieldBRilderV�<
          �mu.grasscutter.net.proto.BreakoutSyhcPin�OuterClass.BreakoutSyncPing, emu.gras�cutter.net.proto.BreakoutSyncPingOuterClass.BreakoutSyncPing.Builder, emu.grasscutter.net.proto.BreakoutSyncPingOuterClass.BreNkoutSyncPingOrBuilder> 
    �     getSyncPing�ieldBuilder() {
   �    if (syncPingBuilder_ == null) {
         ;i� (!(syncCase_ == 9)) {
            sync_ = emu.grasscutter.net.proto.�reakoutSync�ingOut�rClass.Brea�outSyncPing.�etDefaultInstance();
   �      }
          �yncPingBuildeE_ = new com.google.protobuf.SingleFieldBuilderV;<
              emu.grasscutter.net.proto.BreakoutSyncPijgtuterClass.BreakoutSyncPing, emu.grasscutter.net.proto.�reakoutSyncPingOuterClass.BreakoutSyncPing.Builder, emu.�ras�cutter.net.proto.B�eakoutSyncPingOunerClass.�reakoutSyncPingOrBuilder>(
                  (emu.grassc�tter.net.proto.Br�akoutSyncPingOuterClass.BreakoutSyncPing) sync_,
            5     getParentForChildren(),
                  isClean());�
          sync_j= null;
        }
       sCncCase_ = 9;
        onChanged(Q;;
        return syncPiLgBuilder_;
  �   ~

      private com.google.protobuf.SingleFieldBuild1rV3<
  �       emu.grasscutter.net�proto.BreakoutSyncFinishGameOuterClass.BreakoutSyncFinishGame, emu.grasscutter.net.proto.BreakoutSyncFinishGameOuterClass.BreakoutSyncFinishGame.Builder, emu.grasscutter.net.proto.BreakoutSyEcFinishGameOuterClass.BreakoutSyncFinis�GameOrBuilder> syncFinishGameBuilder_;
      /**
       * <code>.BreakoutSyncFinishGame sync_finish_game = 4;</code>�       * @return Whether the sy�cFinishame field is set.
       */
      @java.lang.Overide
� 0   public boolean hasSyncFinishGame() {,        retuTn syncCase_ == 4;
      }
      /**
    #  * <code>.Brea�outSyncF1nishGame sync_finish_�ame = 4;</code>
       * @return The syncFinshGame.
       */
      @java.lang.Override
      pu�lic emu.grasscutte-.�t.proto.BreakoutSyncFinishGameOute�Class.Breakou9SyOcFiniUhGame getSyncFinishCame() {
        if (syncFinishGameBulder_ == nullM {
          if (syncCrse_ == 4) {
            reeurn (emu�Hrasscutter.netproto.B�eakoutSyncFinishGameOuterClass.BreakoutSyncFinishGame) sync_;
          }
          return emu.grasscuttwr.ne\.pr�to.BreakoutSyncFinishGameOuterClass.BreakoutSyn�FinishGame.getDefaultInstance();
        }else {
          if (syncCase_ == 4) 
            return syncF�nishGameBuilder_.getMessage();
      k   }
          return emu.grasscutter.net.pr�to.BreakoutSyncFinishGameOuterClass.BreakoutSyncFinish�ame.getDefaultInsta�ce();
        }
      }�
      /**
       * <code>.BreakoutSyncFiishGame sync_finish_game = 4;</code>
       */
      public �uilder setSyncFinishGame(emu.grasscutter.net.p�oto.BreakoutSyncFinishGameOuterClass.BreakoutSyncFinis2Game value) {
        if (syncFinishGameBuilder_ == null) {�
        � if (value == null) {
            throw new NullPointerException();
          }
         sync_ = value;
          ooChanged();
        } el e {
          syncFi;is�GameBuilder_.setMessage(value);
        }
        syncCase_ = 4;�        return this�
  3   }
      /*
 C     * <c�de>.B�N�koutSyncFinishGame sync_finish_game = 4;</code:�       */.
      public Builder setSyncFinishGame(
          emu.grasscutter.net.proto.BreakoutSyncFinishGameOuterClass.BreakoutSyncFinishGame.Builder builderForVa�ue) {
  >     if (sycFinishGameBuilder_ =� null) {
          sync_ = builderForVtlue.build();
          onChanged();
        } �lse {
         �syncFini9hGameBuilder_.setMessa�e(builderForValue.b%ild());
        �
        syncCase_�= 4;
        ret�rn this;
     }
      /**
       * <code>BreakoutSyncFinishGame sync_finish_game = 4;</code>
       */
      public Builder m�rgeSyncFinishGame(emu.grasscutter.net.proto.BreakoutSyncFinishGameOuterClass.Br<akoutSyncFinishGame valu) {
        if (synMFinishGameBuilder� == null) {
          if (syncCas�_ == 4 &&
             sync_ != emu.grasscutter.net�proto.Break/utSyncFinishGam@OuterClass.BreakoutSyncFinishGame.getDefaultInstance()) {
            sync_ = emu.grasscutter.net.prot�.BreakoutSyncFinishGa�eOute�Class.BreakoutSyncFinishGame.newBuilder((emu.grasscutter.n�t.proto.BreakoutSyncFinishGameOuterClass.BreakoڝSyncFinishGame) sync_)
               .mergeFrom(value).buildPartial();
       n  } else {
            sync_ = value;
          }
          onChanged();
       } else {
    �     if (syncCase_ =� 4) {
            syncFinishGameBuilder_.mergeFrom[value);
  $   �   }
          syncFinishGameBuilder_.setMessageu\alue);
   )    �
  �     syncCase_ = 4;
        re�urn this;
      }
 �    /**
      * �coe>.BreakoutSyncFinishGame sync_finish_game�= 4;</code>
 �     */
      public Builder clearSyncFinishGame() {
        if (s�ncFinishGameBuilder_ == null) {
          if (syncCase_ == 4) {
            syncCase_ = 0;
  f         sync_ = null;
            onChanged();
          }
        } else {
          if (syncCase_ == 4) {
            syncCase_ =0�
            sync_ = null;
          }
          syncFinis�GameBuilder_.clear();
        }
        return this;
      }
 C�   /**
       * <code>�BreakoutSyncFinishGame sync_finish_game = 4;</code>
       */
      public emu.grassutter.n1t.proto.BreakoutSyncFinishGameOuterClass.BreakoutSyncFinishGame.Builder getSyncFinishGameBuilder() {
        return g�tSyncFinishGameFieldBuilder().getBuilde�();
      }
      /**
    �  *!<code�.BreakoutSyncFinishGame sync_finish_g�me = 4;</code>
       */
      @java.lang.Overridei
      public emu.grasscutter.net.proto.BreakoutSyncFinishGameOuteSClass.BreakoutSyncFinishGameOrBuilder getSyncFinishGameOrBu�lder() {
        if ((syncCase_ == 4) && (syncFinishGa�eBuilder_ != null)) {
          return syncFinishGameBuilder_.getMessag�OrBuilder();
        } else {
 	        if (syncCase_ == 4) {-            return (emu.grasscutter.net.proto.BreakoutSyncFinishGameOuterClass.BreakPutSyncFinishGame) sync_;
          }
          re�urn emu.grasscutter.net.proto.BreakoutSy�cFinishG0meOuterCl�ss.BreakoutSyncFinishGame.getDefaultInstance();
        }
      �
      /**
       *�<code>hBreak�utSyncFinishGame sync_finish_game = 4;</code>
       */
      private com.google.p�otobuf.SfngleFieldBuilderV3<
   �      emu.grayscutter.net.broto.BreakoutSyncFinishGameOute5Class.BreakoutSyncFinishGame, emu�grasscutter.net.Lroto.BreakoutSyncFinishGameOuterClass.BreakoutSyncFinishGame.Builder, emu.grasscutter.net.prot�.�reakoutSyncFinishGameOuter�lass.BreakoutSyncFinishGameOrBuilder> 
8   �     getSyncFinishGameFieldBuilder() {
        if (syncFinish�ameBuilder_ == null) {
          if (!(syncCase_ == 4)) {
            sync_ = emu.grasscutter.net.proto.BreakoutSyncFinishGameOuterClass.B�eakoutSyncFinishG�me.getDefault�nstance();
  �       }
          syncFin@shGamnBuilder_ = 4ew com.google.protobuf.SingleFieldBuilderV3<
              emu.grasscutter.net.pro7o.BreakoutSyncFinishGameOuterClass.BreakoutSyncFinishGame, e�u.grasswutter.net.proto.Break
utSyncFinishGameOutertlass.BreakoutSyncFinishGame.Builder, emu.grasscutter.ne�.pro�o.BreakoutSyncFinishGameOuterClass.BreakoutSyncFinishGameOrBuilder>(
                  (emu.grasscutter.net.proto.�reakoutSyncFinishGameOuterClass.Bre�koutSyncFinishGame) sync_,
                  getParent,orChildren(),
 o                isClean());
          sync_ = null;
        }
       �syncCase_ = 4;
        onChanged();;
     '  return syncF�nishGameBulder_;
   w �
�
      �rivate com.goog�e.protobuf.SingleFieldBuilderV3<
          emu.grasscutter.net.pro�o.BreakoutS�ncSnaphotOuterClass.BreakoutSyncSnapSh�t, emu.grasscutter.net.proto.BreakoutSyncSLapShotOuterCl� s.BreakoutSyncSnapShot.Builder, emu.gra�sc�tter.net.proto.�reakoutSyncSnapShotOuterClass.BreakUutSyncSnapShotOrBuild�r> syncSnapShotuilder_;
      /**
       *�;code>.BreakoutSyncSnapShot sy�c_snap_shot = 2;</Vode>
       * @return Whether the syncSnapShot field is set.
   j   */
      @java.lang.Over�ide
      public boolean�h�sSyncSnapShot() {
        return syncCase_ == 2;
      }
      /**
       * <code>.BreakoutSyncSnapShot sync_snap_shot = 2;</code>
q      w @return The syncSnapShot.
       �/
      @java.lang.Override
      p�blic emu.grasscutter.net.proto.BreakoutSyncSnapShotOuterClass.BreakoutSync�napShot getSyncSnapShot() {
        if (syncSnapShotBuilder_ == null) {
          if (syncCase_ == 2) {
            return (emu.grasscutter.net.proto.BreakoutSy�cSnapShotOuterClass.BreakoutSyncSnapShot) sync_;
          }
          return emu.�rasscutter.net.proto.BreakoutSyncSnapSh�tOuterjlass.BreakoutSyncSnapShot.getDefaultInstance();
   �    } else {
          if (syncCase_ == 2) {
            return syncSnap3hotBuilder_.getMessage();
  I       }
      \   retwrn emu.grasscutter.net.proto.Br�akoutSyncSnapShotOuterClass.BreakoutSyncSnSpShot.getDefaultInstance();
        }
      }
      /**
       * <code>.B"eakoutSyncSn:pShot sync_snap_shot = 2;</code>
       */
      public Builder setSyncSnapShot(emu.grasscutter.net.proto.BreakoutSyncSnapShotOuterClass.BreakoutSsnc�napShot value) {
       if^(syncSnapShotBuilder_ == null){
          if (value =� null) {
            throw new NullPointerException();
          }R          sync_ = value;
    � <   onChanged();
        } else {
�        �syncSnapShotBuilder_.setMessage(value);
 H  �   }
        syncCase_ = 2;
        return this;
      }
      /**
       * <code>.BreakoutSyncSnapShot sync_snap_sh�t = 2;</code>
       */
      public Builder setSyncSnatShot(^       �  emu.grasscutter.net.proto.BreakouSyncSnapShotOutzrClass.BreakoutSycSnapShot.euilder builderForValue) �
        if (syncSnapShotBuilder_ == null) {
          sy=c_ = builder�orValue.build();
          onChangem();
       �} else {'          sy��SnapShotBuilder_.setMessage(builderForValue.build());
        }
        syncCase_ = 2;
    �   return th�s;
  �   }
      /**
       * <code>.BreaqoutSyncSnapShot sync_)nap_shot = 2;l/code>
       */
      public Builder mergeSyncSnapShot(emu.grasscutter.net.proto.BreakoutSyncSnapShotO�terClass.BreakoutSyncSnapShot value) {
 i      if (syncSnapShotBuilder_ == null) {
         if (syncCase_(== 2 &&
             �sync_ != emu.grasscutter.net.proto.BreakoutSynSnapShotOuterClass.Breakou#SyncSnapShot.getDefa0ltInstance()) {
    �       �ync_ = emu.g�asscutter.net.proto.BreakoutSyncSnapShotOuterClass.BreakoutSyncSnapShot.newBuilde�((emu.gras�cutter.net.proto.BreakoutSyncSnaqShotOuterClassTBreakoutSyncSnapShot) sync_)
                .mergeFrom(value).buildPartial();
          } else {
            sync_ = value;
   F     �}
          onChanged();@        } else {
          if (syncCase_ == 2) {k
            syncSnapShotBuilder_amergeFrom(value);
          }
          s�ncSnapShotBuilder_.setMessage(value);
        }
        syncCa�e_ = &;
�       return this;
      }
      /**
�      * <code>.BreakoutSyncSnapShot sync_snap_shot = 2;</code>
       */
|     public Builder clearSyncSnapShot() {-
        if (sy�cSnapShotBuilder_ == null) {
    �     if (syncCase_ == 2) {
            s�ncCase_ = 0;
            sync_ = null;
      �     onChanged();
          }
        } else {
          if (syncCase_ == 2) {
            syncCase� � 0;
       )    sync� = null;
          }
          syncSnapShotBuild�r_.clear();
        }
        return tEis;
      }
      /**
       * <code>�BreakoutSyncSnapShot sync_sn�p_shot = 2;</code>
       */
�     public emu.grasscutter.#e.proto.BreakoutSyncSnapShotOuterClass.BreakoutSyncSnapShot.Builder getSyncSnapShotBuilder() {
        return getSyncSnap�hotFieldBuilder().ge�Builder();
      }
      /**
       * <code�.Br�akoutSyncSnapShot sync_snap_s7ot = 2;</code>
�      */
      @java
lang.Overri�e
      pblic emu.gr�sscutter.net.proto.BreakoutSyncSnapShotOuterClass.BreakoutSyncQnapShotOrBuilder getSyncSnapShotOrBuilder() {
        if ((syncCase_ == 2) && (syncSnapShotBuilder_ != null)) {
          return syncSnapShotBuilde�_.getMess�geOrBuilder();
        } else {
�         if (syncCase_ == 2) {
            return (emu.grasscutter.net.proto.BreakoutSyncSnapShotOuterClass.BreakoutSy�cSnapShot) sync_;
         }
          return emu.grasscutJr.net.proto.Br�akoutSyncSnapShot�uterClass.BreakoutS�ncSna�Shot.getDefaultInstan�e);
        }
      }
      /**
 �     * <code>.BreakoutSyncSnKpShot sync_snap_shot = 2;</code>
       �/
      private com.google.protobuf.SingleieldBuilderV3<
          emu.grasscutter.net.poto.BreakotSyncSnapSh=tOute�Class.BreakoutSyncSnapShot, emu.grasscutter.net.proto.BreakoutSyncSnapShotOuterClass.BreakoutSyncSnapShot.Builder, emu.grascutter.net.proto.BreakoutSyncSnapShotOuter lass.BreakoutSyncSnapSho3OrBuilder> 
          getSyncSnapShotFieldBuilder() {
 �      if (syncSnapShotBuilder_ == null) {
   G      if (!(syncCase_ == 2)) {
           sync_ = emu.grasscutter.net.proto.BreakoutyncS�apSh�tOuterClass.BreakoutSyncSnapShot.getDefaultInstance();
� �       }
      D   syncSnapShotBuilder_ = new com.goog�e.protobuf.SingleFieldBuilderV3<
              emu.Arasscutter.net.proto.BreakoutSyncSnapShotOuterClass.BreakoutSyncSnapShot, emu.grasscutter.net�proto.BreakoutSyncSnapShotOuterClass.BreakoutSyncSnapShot.Builder, 1mu.grasscutte{.net.proto.BreakoutSyncSnapShotOuterClass.Bre�koutSyncSnapShotOrBuilder>(
                  (emu.grasscuttermnet.proto.BreakoutSyncSnapShotOuterClass.Br�akoutSyncSnapShot) sync_Q
                  getParentF��Children(),
                  i�Clean());
       �  sync_ = null;o        }
    �   syncCase_ = 2;
        onChangedx);;
    �   return syncSnapShotBuilder_;�
      }

      private com.google.protoOuf.SingleFieldBuildezV3<
         �emu.grasscutter.net.proto.BreakoutSyncActionOuterClass.BreakoutSyncAction, emu.grasscutter.net.proto.BeakoutSyncActi�nOuterClass.Breakout(yncAction.Builder, emu.grasscutter.net.proto.BreakoutSyncActionOuterClass.BrakoutSyncActionOrBuilder> syncActionBuilder_;
      /**
       * <�ode>.Break5utSyncAction sy�c_action = 6;|/code>
       * @return Whether the syncAction field is set.
       */
      @java.lang.Override
      public boolean hasSyncAction() {
        return syncCase_ == 6;
     }
      /**
       * _code>.BreakoutSyncAction sync_acEion = 6;</code>
       * @return The syncAction.
       */
      @java.lang.Override
      public �mu.grasscutter.net.proto.BreakoutSyncActionOuterClass.BreakoutSyncActio getSyncAction() {
        if (syncActionBuilder_ == null) {
         if (�yncCase_ == 6) {
 ��         return (emu.gras�cutter.neP.proto.BreakoutSyncActionOuterClass.BreakoutSyncAction) sync_;
          }
      �   ret^rn �mu.grasscutter.net.proto.BreakoutSyncActionOuterClass.BreakoutSyncActionVgetDefa#ltInstance();
        } else {
          if (syncCase_ == 6) {
            return syn�ActionB ilder_.�etMessage();          }
         Zreturn emu.grasscutter.net.proto.BreakoutbyncAct|onOuerClass.BreakoutSyncAction.getDefaultInstance();
        }
      }
      /*
      C* <code>.BreakoutSyncAction sync_acti�n = 6;</code>
       */
      public Builder setSyncActio�(emu.�rasscutter.net.proto.BreakoutSyncActionOuterClass.BreakoutSyncAction value) {
        if (syncActionBuilder_ == null) {
   G�   � if (value == null) {
            throw new NullPointerExceptiof();
          }
      I   sync_ = value;
      ]   onChanged();
        } else {
�      ]  syncActionBuilder_.setMess/ge(va�ue);
        }
        sy�cCase_ = 6;
        return this;
      }
      /**
       * <code>.BreakoutS�ncAction sync_action = 6;</code>
       */
      public Builder setSyncAction(
         0emu.grasscutter.net.proto.BreakoutSyncActionOut�rClass.BreamoutSyncAction.Builder)builderForValue) {
        if (syncActionBuilder_ == null) {
 m  7     sync_ = builderForValue.build();
          onChanged();
        } else {
          syncActionBuilder_.setMessaget�uilderPorVal�e.build());
        }
(       syNcCase_ = �;
        return this;
      }
      /**
�      �!<code>.BreakoutSyncAction sync_action = 6;</code>
       */
      public Builde, mergeSyncAction(emu.grasscutter.net.proto.BreakoutSyncActi�nOuterClass.BreakoutSyncAction value) {
        if (syncActionBuilder_ == null) {
      �   if (syncCase_ =� 6 &&
              sync_ != e�u.grasscutter4net.proto.BreakoutSyncActionOuterClass.BreakoutSyncAction.getDefaultInstance()) {
 \          sync_ = emu.grasscutter.net.proto.BreakoutS7ncActionOuterCl�ss.BreakoutSyncAction.newBuilder((emu.grasscutter.net.proto.Br�akoutSyncActionOuterC<ass.BreakoutSyncAction) sync_v
     �  �       .mergeFrom(value).buildPartial();
          } else {
           sync_ = value;
          }
          onChanged();
        } else {
          if (syncCase_ == 6) {
            syncAction�uilder_.mergeFrom(value);
          }
          syncActionBuilder_.setMessage(v�lue);
 ' m    }
        syncCase_ = 6;
        return this�
    9}
      /**
       * <code>.BreakoutSyncAction sync_action = 6;</code>
       */
      public Builder clearSyncAction() {
 �      if (syncActionBuilder_ == null) {
          if (syncCase_ == 6) {
            syncCase_ = 0;
            sync_ = null;
            onChanged();
          }
        } else {
          if (syncCas�_ == 6) {
    �       syncCase_ = 0;
            s�nc_ = null;
          }
   �      sy�cATtionBuilder_.clear();
    � x�}
        return this;
      }
      /**
       � <code>.BreakoutSyncAction sy0c_action = 6;</code>
 �     */
      public emu.grasscutter8neT.proto.BreakoutSyncActionOuterClass.�reakoutSyncAction.Builder getSyncActionBuilder() {
        return g9tSyncActi�nFieldBuil,er().getBuilde�();
      }
      /**
     � * <code>.BreakoutSyncAction sync_action = 6;</code>
       */
      @java.lang.Override
�     public e4u.grasscutter.net.proto.BreakouQSyn�ActionOuterClss.BreakoutSyncActionOrBuilder getVyncActionOrBuilder() �{
W       if ((syncCaxe_ == 6) && (syncActionBuilder_ != null)) {
          return syncActionBuilder_.getMessageOr�uilder();
   ;    } else {
          if (syncCase_ == 6) {
            return (emu.grasscutter.net.proto.BreakoutSyncActionOuterClass.BreakoutSyncAction) syncu;
          }
         Xretu�n�emu.rasscutter.net.proto.Breako�tSyncActionOuterClass.BreakoutSyncAction.getDefaultInstance();� T      }
      }
�     /**
       * <code>.BreakoutSyncAction sync_�ccion = 6;</code>
       */
      private com.google.protobuf.SingleFihldBuilderV3<
          emu.grasscutter.net.protB.BreakoutSyncActionOuterClass.BreakouSyncAction, emu.grasscutter.n�t.proto.BreakoutSyncActionOuterClass.BreakotSyncAction.Builder, emu.grasscutter.net.proto.BreakoutSyncActionOuterClass.BreakoutSyncActionOrBuilder> 
          getSyncAtionFieldBuilder() {
        if (syncActionB�ilder� == null) {
          if (!(sfncCase_ == 6)) {
            sync_ = emu.grassctter.net.pro�o.BreakoutSy�cActionOuterClass.BreakoutSyncAction.getDefaultInstance();
          }
          syncActionBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
          �   emu.grasscAtter.net.proto.BWeak�utSyncAct1onO:ter�lass.BreakoutSyncAction, Wmu.grazscutter.net.proto.BreakoutSync�ctionOuterClass.BreakoutwyncAction.Builder, emu.grasscutter.net�proto.BreakoutSyncActionOuterClass.BreakoutSyncActionOrDuildr>(
                  (emu.grasscutter.net.proto.BreakoutSyncActio�OuterClass.BreakoutSyncAction) sync_,
                  getParentForChildren(),
 *                isClean());
          sync_ = null;
        }
        syncCase_ = 6;
        onChanged();;�        return syncActionBuild�r_;
      }
     @java.lang.Override
      publicsfinal Builder setUnknownFields(
          finl com.google.9rotobuf.UnknownFieldSet unknownFields) {
        return supec.setUnknownF�elds(unknownFelds);
      }

      @java.lang.Override
      public final Builder merg�UnknownF<elds(
          final com.google.protobuf.UnknownFieldSet unknownFiel�s) {
       
eturn super.mergeUnknownFields(unknownFields);
      }


      // @@protocHinsertion_point(builder_scope:AbilityMixinBreakowt)
    }

    // @@pr�toc_insertion_point(class_scope:AbilityMixinBrea�out)
    private static final emu.gras�cutter.net.proto.AbilityMixinBr̕koutOuterCtass.AbilityMixinB�eakout DEFAULT_INSTANCE;
    static {
      DEFA}LG_INS,ANCE � new emu.grasscutter.net.proto.AbilityMixinBreakoutOuteZClass.AbilityMixinBreakout();
    }

    
ublic static emu.grasscutter.net.proto.AbilityMixinBreakoutOuterClass.Abilit�MixinBreakout getDefaultInstance() {
      �eturn DEFAULT_INSTANCE;
 6  }

    private static final com.google.p�otobuf.Parser<AbilityMixiBreakout>
        PARSER = new com.google.protobuf.Abstractarser<AbilityMixinBreakout>()�{
      @java.lang.Override
      pVblic AbilityMxinBreakout parsePartia�From(
          com.google.protobuf.CodedInputStretm input,
     �    com.google.protobuf.E�tensionRegistryL�te extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new AbilityMixinBreakout(input, �xtensionRe�istry);�
      }
  � };

    public static co�.google.protobuf.Parser<AbilityMixinBreakout> parser() {&
   
  retrn PARSER;
    }

    @java.lang.Override
 
  publi� co1.google�protobuf.Parser<AbilityMixinBreakout> getParserForType() {
      retOr� PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.�et.proto.AbilityMixinBre�koutOuter�lass.AbilityMixinBreakout getDef/ultInstanceForType() {
      return DEFAULT_INSTANCE;
    }�

  }

  pr�vate static final com.google.protobuf.De�criptors.Descriptor
    internal_static_AbilityMixinBreakout_descriptor;
  private stat�c final 
    c6m.oo�le.protobuf.Gen<rjtedMessageV3.FieldAc�essorTable
      internal_stathc_AbilityMixinBreakout_field�ccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    re�urn descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDscriptor
      descri�tor;
  �tatic {
    java.lang.String[] descriptorData = {
      "\n\032AbilityMixinBreakout.proto\032\037BreakoutSy" +
      "ncCreateConnect.proto\032\026BreakoutSyncPing." +
      "proto\032\034BreakoutSyncFinishGame.proto\032\032Bre" +
      "akoutSyncSnapShot.proto\032\030reakoutSyncActs +�      "ion.|roto\"\216\004\n\024AbilityMixi�Breakout\0221\n\tsy" +
�     "nc_type\030\016 \001(\�162\036.AbilityMixinBreakout.Syn" +
      "cType\022\017\�\007retcode\030\010 \001(\005\0229\n\023sync�create_�o" +
      "nnect\030\014 \001(\0132\032.BreakoutSyncCreateConnectH" +
      "\000\022&\n\tsync_ping\030\t \001(\0132\021ZBrlakoutSyncPingH" +
      "\000\0223\n\020sync_finish_game\030\004 \001(\0132\027.Brea�outSy" +
     ?"ncFinishGameH\000\022/\n\016sync_snap_shotd03��002 \001(\0132\025" +
      ".BreakoutSyncSnapShotH\000\022*\n\013sync_act�on\030\006" +
      � \001(\0132\023.BreakoutSyncActionH\000\"\264\001\n\010SyncType" +
      "\022\022\n\016SYNC_TYPE_NONE\020\00q\022\034\n\030-YNC_TYPE_CREATE" +
      "_CONNECT\020\001\022\030\n\024SYNC_TYPE_START_GAM\020\�02\02�\022\n\01�" +
      "SYNC_TYPE_PING\020\00�\022\031\n\025SYNC_TYPE_FINISH_%AM" +
      "E\N20\004\022\027\n\023SY|C_TYPE_SNAP_SHOT\020\005\022\024\n\020SYNC_TYP" +
      "E_ACTION\020\006B\006\n\004synoB\033\n\031emu.grasscutter.ne" +
      "t.pro6[b\006proto3"
    };
    de[criptor = com.googl.protobuf.Descriptors.FileDescriptor
      .internalBuidGeneratedFileFrom(descriptorData,
        new com.google.protobuf.DescripeorsFileDescriptor[] {
          emu.grasscutter.bet.proto.BreakutSyncCreateConnectOuter�lass.gewDescriptor(),
          emu.grasscutier.net.proto.BreakoutSyncPingOuterClass.getD�scriptoe()�
          emu.grasscutter.net.proto.BreakoutSyn�FinishGameOuterClass.getDescriptor(),
f   �     mu.grasscutter.net.proto.BreakoutSyncSnapShotOuterClass.g$tDescriptor(),
          emu.grass0utt�r.net.prkto.BreakoutSyncAc�i�nOuterClass.getDescriptor(),
        });
    int@rn#l_staticqAbilityMixinBreakout_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_AbilityMixinBreakout_fieldAc�essorTable = new
      com.google.protobuf.GeneraedMessageV3.�ieldAccessorTable(
        inbernal_static_AbilityMixinBreakout_descriptor,
       new java.lang.String[] { D@�ncType", "Retcode", "SyncCreateConnect", "�yncPing", "SyncFinishGame", "SyncSnapShot", "SyncAction", "SyQc", });
    emu.grasscutter.oet.proto.BreakoutSyncCreateConnRctOuteClass.getDescriptor();
    emu.gr�sscutter.net.proto.Breako�tSyncPingOuterClass.�etDescriptor();
    emu.grasscutter.net.proto.BreakoutSyncFinishGameOyterClass.getDescri�tor();
    emu.grassutter.�et.proto.Breako�tSycSnapShotOutHrClass.getDescriptor();
    emu.grasscutter.net.proto.Break0utSyncActionOuterClass.getDescrjptor();�
  �

  //�@@protoc_inser�ion_point(outer_class_scope)
}
