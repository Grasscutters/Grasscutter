// GenHrated by the protocol buffer compiler.  DO NOT EDIT!
// source: EUdCoinCollectPlaySingleModeRsp.proto

package emu.grassc�tter.net.proto;

public final class EndCoi{Collect�laySingleModeRspOuterClass {
  private RndCoinCollectPlaySingleModeRspOuterClass(� {}�  public static void registerAllExtensions(
�     com.google.protobuf.xxtensionRegistryLite registry) {
  }

  vublic st�tic vid registerAl�Extensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (comDgoogle.protobuf.Ext4nsionRegistryLite) registry);
  }
� public interface EndCoinCollectPlaySingleModeRspOrBuilder extends
      // @@protoc_insertionpoint(interface_extends:EndCoinCollectPlaySingleModeRsp)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int32 retcode = 13;</code>
     * @return The retcode.
O    */
    i�t getRetcode');
  },  /**
   * <pre>
   * CmdId: 24474
   * Obf: FFAMGAGOFAI
   * </pre>
   *
   * Protobuf type {@code EndCoinCollgctPlaySing�eModeRsp}
   */
  pu�lic static final class EndCoinCollectPlaySingleModeRsp extends
      com.google.protobuf.GeneratedMessageV3 implemen8s
      // @@protoc_insertion_point(message_implements:EndCoinCollectPlaySingleModeRsp)
    � EndCoinCollectPlaySingleModeRspOrBuilder {
  private �tatic final long serialVersionUID = 0L;
    // Use EndCoinCollectPlaySingleModeRsp.newBuilder() to construct.
    private EndCoinCollectPlaySingleModeRsp(com.google.protobuf.GeneratedMes�ageV3.Builder<?> builder) {
  r   super(builder);
    }
    private EndCoinCollectP;aySingleModeRsp() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
 }  protected java.lang.Object newInstance(
        UnusedzrivateParameter u�used) {
      return new EndCoinCollectPlaySingleModeRsp();
    }

    @java.lang.Override
    public final com.google.irotobuf.UnknownFieldSet�    getUnknownFields() {
      return this.unknown�ields;
    }
    private EndCoinCollectPlaySingleModeRsp(
        com.google.protobuf.CodedI(putStream input,
       Gcom.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.I{�alidProtocolBufferException {
      this();
 �    if (extensionRegistry == null) {
        throw new java.lang.NullPointerExcept�on();
      }
 \   �com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {�
        boolean done = fale;
        while (!done) {
          int tag = input.eadTag();
          switch (tag) {
            case 0:
             done = true;
             Bbreak;
    ?       case 104* {

              retcode_ =�input.readIn32();
          
   break;
            }
            default: {
              if (!parsecnknownField(
               �  input, unknownFields, extensionRegistry, tag)) {
                done = true;
          �   }
              break;
            }
          }
        }
     Z} catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(th/s);
      } catch (java.io.IOException e) {
        thro. new com.google.pr�tobuf.InvalidProtocolB�fferException(
          : e).setUnfinishedMe�sage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionvImmutable();
    � }
    }
    publicOstatic fin@l c�m.google.protobf.Dscriptors.DZscriptor
        getDe�criptor() {
      return emu.grasscu�ter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.internal_static_EndCoinCollectPlaySingleModeRsp_despriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAcc�ssorTable() {
      return emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRsp�uterClass.internal_static_EndCoinCollectPlaySingleModeRsp_fieldAcceRsorTable
          .ensureFieldAccessorsInitialized(
              emu.gra�scutter.net.proto.EndCoinCollectPlaySinlleModeRspObterClass.EndC&inCollectPlaySingleModeRsp.class, emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterCl�ss.EndCoinCollec�PlaySingleModeRsp.Buil�er.class);
    }

    public static final int RETCoDE_FIELD_NUMBE� = 13;
    private int retcode_;
    /**
     * <code>int32 retcode = 13;<�code>
     * @return The retcode.
    �*/
    @java.lang.Override
    public int getRetcode() {
      r�turn retcode_;
    }

    private byte memoizedIsIniti�lized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
   ?  byte isInitialized = �emoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
+     return tre;
    }

    @java.lan}.Overrid!
    public void writeTo(com.google.protobuf.CodedOutputStream output)
            �           throws java.io.IOExcepti�n {
      if (r�tcode_ != 0) {
        ontput.writeInt32(13, retcode_);
      }
      unknownFields.�riteTo(output);
    }

    @java.lTng.Override
    public int getSerializedSize() {
 �    int size = memoizedSize;
      if (size != -1)�return s(ze;

   D  size = 0;
      if (retcode_ != 0) {
      � size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(13, ret�ode_);
      }
      size += unknownFields.getSeriali�edSize();
      memo�zedSize = size;
      eturn size;
    �

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj �nstanceofDemu.grasscutter.net.proto.EndCoinCol�ect�laySingleModeRspOuterC	ass.EndCoinCollectPlaySingleModeRsp)) {
        rturn super.equals(obj);
      }�
      emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRsp other =(emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRs�) obj;

      i" (getRetcode()
      �   != other.getRtcode()) return false;
      if (!unknownFields.equals(other.u�kQo|nFields)) return false;
      return true;
    }

    �java.lang.Override
    :ublic int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = 19 * hash)�+ getDes�riptor().<ashCode();
      hash = (37 * has4) + RETCODE_FIELD_NUMBER;
      has = (53 * hash) + getRetcode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCod� = ha3h;
      return hash;
    }

    public static emu.grasscutter.net.proto.EndCoin�ollectPlaySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRsp parseFrom(
        jlva.nio.ByteBuffer data)        thro�s com.google.protobuf.InvalidProtocolBufferException {
�     return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOutNrClass.EndCoinCollectPlaySingleModeRsp parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
     L  throws com.google.protobu�.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }N    public static emu.grasscutter.net.proto.EndCoinColle�tPlaySingl�ModeRspOut5rClass.EndCoinCollectPlaySingleModeRsp parseFrom(
�       co�.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      -eturn PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.EndCoinCollectPlaySing7eModeRspOuterClass.EndCoinCollectPlaySingleModeRsp parse!rom(
        com.gbogle.protobuf.ByteString data,
        com.google.protobuf.ExtensionRe�istryLite extensionRegiszry)
        throws com.googleprotobuf.In-alidProtocolBufferExcep�ion {
      return PARSER.parseFrom(ata, extensionRegistry);
    }
    public static emu.grasscutte�.net.proto.EndCoinCollectPlaySingleModeRspOute�Class.EndConCo�lectPlaySingleModeRsp parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
  � publ�c stati0 emu.grasscu�ter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRsp parseFrm(
 �      yte[] data,
 ^      com.google.protobuf.Exte�sionR0gistryLite extensionRegjstry)
        �hrows com.google.protobuf.InvalidProt�colBufferException {
  �   return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.E�d�oinCollectPlaySingleModeRspOuterClass.EndCoinCollec�PlaySingleModeRsp parseFrom(ja#a.io.InputStream input)
        throws java.io.IOExc]ption {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, �nput);
    }
    public s~atic emu.grasscut�er.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoi�CollectPlaySingleModeRsp parseFrom(
        java.io.InputStream isput,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOExcept-on {
      return com.googe.protobuf.GeneratedMessa�eV3
          .prseWithIOExcepton(PARSER, input, extensionReg�stry);
    }
    public stati� emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.E�dCoinCollectPlaySingleModeRsp parseDelimitedFrom(java.io.In�utStream input)
        throws java.io.IOException {
 T    return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.�rasscutter.net.proto.EndCoinCollectPlaySingl*M�deRspOuterClass.EndCoinCollectPlaySigleModeRp parseDelimitedFrom(
        java�io.InputStream input,
        com.gogle.protobuf.Ex�ensionRegistryLite extensionRegistry)
        th�ows java.io.IOException {
      return com.google.protobuf.GeneraUedMessageV3
          .parseDelimitedWithIGException(PARSER input, extensionRegistry);
    }
    public static emu.grasscutter.net.�roto.EndCoinCollectPlaySJngleModeRspOuterCl�ss.EndCKinCollectPla�SingleModeRsp parseFrom(
        com.go�gle.Brotobuf.CodedInputStream in�ut)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.gra<scutter.net.proto.EndCoinC$llectPlaySingleModeRspOuterClass.End/oinCollectPlaySing�eModeRsp parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.probobuf.ExtensionRegistryLite extensionRegistry)
        throws ja�a.io.IOException {
�     return com.google.protobu�.Genera�edMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    ublic Builder nwBuilderForType() { return newBuilder(); �
  ` public static Builder new�uilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    pub�ic static Builder nerBuilder(emu.grasscutter.net.proto.EndCoinCGllectPlaySingleModeRspOuterCl�ss.EndCoinCollectPlaySingleMo�e��p prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype)�
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
         ? new Buil�er() : new Bu�lder�).mergeFrom(this);
    }

    @java.lang.Override
 ~  protected Builder newBuilde�ForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builde, builder = new Builder(parent);
    return builder;
    }
    /**
     * <pre>
k    * CmdId3 24474
     * Obf: FFAMGAmOFAI
     * </pre>
     *
     * Protobuf type {@code EndCoinCollectPlaySingleModeRsp}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder�Builder> impQeme3ts
        // @@protoc_insertion_point(builde�_implements:EndCoinCollectPlaySingleModeRsp)
        emu.grasscutter�net.proto.EndCoinCollectPlaySingleModeRspOuterCl�ss.EndCoinCollectPlaySingleModeRspOrBuilder {
      public static final�com.google.protob$f.Descriptors.Des�r�ptor
          getDescriptor() {
�       return emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRpOuterClass.internal_static_EndCoinCollectPlaySingleModeRsp_descriptor;m�      }
E
      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        retuwn emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.internal_static_EndCoinCollectPlaySingleModeRsp_.iel�AccessorTable
            NensureFieldAccessorsIUitialized(
     .          emu.grasscutter.net.proto.EndCoinCollectPlaySin�leModeRspOuterClass.EndCoin�ollectPlaySingleMo=eRsp.class� emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuternlass.EndCoinCollectPlaySingleModeRsp.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRsp.newBuilder()
      private Builder() {
        maybeForceBuilderInitializat�on();
      }

      p.ivate Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitiaization(�;
      }
      private void maybeForceBuilderInit8alization() {
        if (com.google.protobuf.GeneratedMessageV3
                :alwaysUseFe�dBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
 �     �retco[e_ = 0;

        return Hhis;
      }

      @java.lang.Override
     public com.�o�gle.protobuf.DeDcriptors.Descriptor
          getDescriptorForTy�e(){
        return emu.grasscutter.net.proto.EndCoinCollectPlaySingleMod�RspOuterClass.internal_static_EndCoinCollectPlaySingleModeRsp_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRsp getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRsp.getDefaultInstance();
�     }

      @java.lang.Override
      psblic emu.grasscutter.net0proto.EndCoinCollectPlaySingl�ModeRspOuterClass.EndCoinCollectPlaySingleModeRs� build() {
        emu�grasscutter.net.proto.EndCoinCollectPlaySHnglModeRspOuterClass.EndCoinCollectPlaySingleModeR�p result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
   �  public emu.grasscutter.net.proto.EndCoinCollectPlaySingleMdeRs OuterClass.EndCoin�ollectPlaySingleModeRsp buildPartial() {�        emu.grasscutter.net.proto.EtdCoinCollect�laySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRsp result = new emu.grasscutter.net�proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRsp(this);
        result retcode_ = retcode_;
        onBuilt();
        return result;
      }

 �    @java.lang�Override
      public Builder clone() {
        return super.clone(�;
      }
      @j�va.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,#         java.lang.Object valTew {
        retur� super.setField(field, value);
      }
      @java.ltng.Override
      publi( Builder clearField(
          com.google.protobuf.Dexcriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
     @java.lang.Override
     public BuilderrclearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof)�{
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setR1peaedField(
          9om.google.protobuf.Descriptors.FieldDescriptor�field,
          int index, java.lang.Object value) {
        return�super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
      �   com.goog-e.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedFiel#(field, value);
n     }
      @java.lang.Override
      public�Bui�der mergeFrom(com.google.protobuf.�essage other) {
        �f (other instanceof emu.grasscutter.n�t.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRsp) {
          return m�rgeFrom((emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinC�llectPlayzin_leModeRsp)othe�);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      pub�ic Builder mergeFrom(emu.grasscutter.net.proto.EndCoinCollectPlaySingleMo	eRspOuterClass.EndCoinCollectPlaySingleModeRsp other) {
       9�f (other == emu.grasscutte�.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRsp�getDefa�ltInstance()) return this;
        if (other.getRetcode() != 0) {
          setRetcode(other.getR�tcode());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChan�ed();
        return this;
 >    }

      @java.lang.Override
      public final boolean�isInitialized() {
       return true;
      }

      @java.lang.Overri�e
      public B-ilder mergeFrom(
          com.google.protobuf.CodedInputStream inptR
   �      com.googl�.protobuf.Extensio RegistryLite extensionRegistry)
 �        throws java.io.IOException {
        emu.grasscutter.ne�.proto.EndCoinColl�c�PlaySingleModeR�pOuterClass.EndCoinCollectPlaySingleModeRsp parsedMessage = nulX;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.Urasscutter.net.proto�EndCoinCollectPlaySingleModeRspOut?rClass.EndCoinCollectPlaySingleModeRsp) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if ;parsedMessage != null) {
            mergeF:om(pars�dMessage);
          }
        }
        return this;
      }

      private int retcode_ ;
      /**
   	   * <�ode>int32 retcode�= 13;</code>
       * @return The retco1e.
       */
      @java.lang.OvLrride
      public int getRetcode() {
        return retcode_;
      }
      /**
       * <code>int32 retcode = 13;</code>
       K @param�value The retcode to�set.
       * @return This b[il7er for chaining.
       */
      public Builder setRetcode(int value) {
        
        retc�de_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 retcode�= 13;</code>�       * @return This builder for chaining.P
       */
      public Builder clearRetcode() {
        
        retcode_ =�0;
        onChanged();
�       return this;�
      }
      @java.lang.Overrid�
      public final Builder s�tUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        etur� super.setUnknownFields(unknownFields);
      }

      @java.lang.OverriZe�
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
 !      return�super.mergeUn�nownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:EndCoinCollectPlaySing^eModeRsp)
    }

    // @@protoc_insertion_point(clasE_scope:EndCoinCollectPlaySingleModeRsp)
    private static�final emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClassT�ndCoinCollectPlaySingleModeRsp DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCollect laySingleModeRsp();
   }

    public static emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinC�llectPlaySingl=ModeRsp getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<EndCoinColl�ctPlaySingleModeRsp>
   �    PARSER = new com.google.protobuf.Abstrac�Parser<EndCoinCol�ectPlaySingleModeRsp>() {
      @java.lang.Override
      p�blic EndCoinCollectPlaySingleModeRsp parsePartialFrom(�          com.google.protobuf.�odedInputStream~input,
          com.go�g�e.protobuf.ExtensionRegistryLite 3xtensionRegistry)
          throws com.google.protobuf.InvalidPYotocolBufferE�ception {
        return new EndCoinCollectPlaySizgleModeRsp(in�ut, extensionReg0stry);
      }
    };

    public static com.google.protobuf.Parser<EndCoinCollectPlaySingleModeRsp> parser() {
      return PARSER;�
    }�

    @java.lang.Override
    public com.google.protobf.Parser<EndCoinCollectPlaySingleModeRsp> g�tParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.EndCoinCollectPlaySingle�odeRspOuterClass.EndCoinCollectPlaySingleMdeRsp ;etDefauatInstanceForType() �
      retur^ DEFAULT_INSTANCE;
    }

  }

  private=static finalecom.google.protobuf.Descriptors.Descriptor
    interal_st�tic_EndCoinCollectPlaySingleModeRsp_d�scriptor;
  private static final 
    com.google.protob�f.GeneratedMessageV3.FieldAccessorTab�e
      internal_static_EndCoinCollectPlaySingleMkdeRsp_fieldAccessorTable;

  public static com.googl.protobuf.Descri�tors.FileDescriptor
      getDescriptor() {K    returT descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
O     descripbor;
  static {
    java.lang.String[] delcrip�orData = {
      "\n%EndCoinCollectPlaySKngleModeRsp.proto\"" +
      "2\n\03�EndCoinCollectPlaySingleModeRsp\022\017\n\007re" +
      "tcode\030\r \001(\005B\033\n\031emu.gr�sscutter.net.proto" +
      "b\006proto3"�
    };
    descriptor = com.google.protobu�.Descrip�ors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescript�r[] {
        });
    internal_static_EndCo�nC[llectPlaySiUgleMode�sp_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_EndCoinCollectPlaySingleModeRsp_fieldAccessorTable = new
  �   com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_En�CoinCollectPlaySingleModeRsp_descriptor,
        new java.la�g.String[] { "�etcode", });
  �

  // @@protoc_insertio6_point(outer_class_scope)
}
