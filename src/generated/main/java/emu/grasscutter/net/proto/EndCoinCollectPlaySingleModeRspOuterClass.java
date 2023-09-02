// GenHrated by the protocol buffer compiler.  DO NOT EDIT!
// source: EUdCoinCollectPlaySingleModeRsp.proto

package emu.grasscËtter.net.proto;

public final class EndCoi{CollectœlaySingleModeRspOuterClass {
  private RndCoinCollectPlaySingleModeRspOuterClass(û {}’  public static void registerAllExtensions(
˘     com.google.protobuf.xxtensionRegistryLite registry) {
  }

  vublic st„tic vid registerAl˜Extensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (comDgoogle.protobuf.Ext4nsionRegistryLite) registry);
  }
à public interface EndCoinCollectPlaySingleModeRspOrBuilder extends
      // @@protoc_insertionpoint(interface_extends:EndCoinCollectPlaySingleModeRsp)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int32 retcode = 13;</code>
     * @return The retcode.
O    */
    iât getRetcode');
  },  /**
   * <pre>
   * CmdId: 24474
   * Obf: FFAMGAGOFAI
   * </pre>
   *
   * Protobuf type {@code EndCoinCollgctPlaySingúeModeRsp}
   */
  pu¿lic static final class EndCoinCollectPlaySingleModeRsp extends
      com.google.protobuf.GeneratedMessageV3 implemen8s
      // @@protoc_insertion_point(message_implements:EndCoinCollectPlaySingleModeRsp)
    Ì EndCoinCollectPlaySingleModeRspOrBuilder {
  private Ÿtatic final long serialVersionUID = 0L;
    // Use EndCoinCollectPlaySingleModeRsp.newBuilder() to construct.
    private EndCoinCollectPlaySingleModeRsp(com.google.protobuf.GeneratedMesáageV3.Builder<?> builder) {
  r   super(builder);
    }
    private EndCoinCollectP;aySingleModeRsp() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
 }  protected java.lang.Object newInstance(
        UnusedzrivateParameter u“used) {
      return new EndCoinCollectPlaySingleModeRsp();
    }

    @java.lang.Override
    public final com.google.irotobuf.UnknownFieldSetï    getUnknownFields() {
      return this.unknownãields;
    }
    private EndCoinCollectPlaySingleModeRsp(
        com.google.protobuf.CodedI(putStream input,
       Gcom.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.I{°alidProtocolBufferException {
      this();
 É    if (extensionRegistry == null) {
        throw new java.lang.NullPointerExceptµon();
      }
 \   ícom.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {∑
        boolean done = fale;
        while (!done) {
          int tag = input.eadTag();
          switch (tag) {
            case 0:
             done = true;
             Bbreak;
    ?       case 104* {

              retcode_ =Óinput.readIn32();
          
   break;
            }
            default: {
              if (!parsecnknownField(
               »  input, unknownFields, extensionRegistry, tag)) {
                done = true;
          ∆   }
              break;
            }
          }
        }
     Z} catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(th/s);
      } catch (java.io.IOException e) {
        thro. new com.google.prªtobuf.InvalidProtocolBòfferException(
          : e).setUnfinishedMe®sage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionvImmutable();
    ÿ }
    }
    publicOstatic fin@l c‚m.google.protobf.Dscriptors.DZscriptor
        getDeÈcriptor() {
      return emu.grasscuåter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.internal_static_EndCoinCollectPlaySingleModeRsp_despriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAcc—ssorTable() {
      return emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRsp∞uterClass.internal_static_EndCoinCollectPlaySingleModeRsp_fieldAcceRsorTable
          .ensureFieldAccessorsInitialized(
              emu.graúscutter.net.proto.EndCoinCollectPlaySinlleModeRspObterClass.EndC&inCollectPlaySingleModeRsp.class, emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClìss.EndCoinCollec≥PlaySingleModeRsp.Builòer.class);
    }

    public static final int RETCoDE_FIELD_NUMBEÓ = 13;
    private int retcode_;
    /**
     * <code>int32 retcode = 13;<îcode>
     * @return The retcode.
    ú*/
    @java.lang.Override
    public int getRetcode() {
      ròturn retcode_;
    }

    private byte memoizedIsIniti¬lized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
   ?  byte isInitialized = ˘emoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
+     return tre;
    }

    @java.lan}.Overrid!
    public void writeTo(com.google.protobuf.CodedOutputStream output)
            ˆ           throws java.io.IOExceptiÅn {
      if (rÿtcode_ != 0) {
        ontput.writeInt32(13, retcode_);
      }
      unknownFields.èriteTo(output);
    }

    @java.lTng.Override
    public int getSerializedSize() {
 ¿    int size = memoizedSize;
      if (size != -1)–return s(ze;

   D  size = 0;
      if (retcode_ != 0) {
      ø size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(13, ret∑ode_);
      }
      size += unknownFields.getSerialiÚedSize();
      memoﬁzedSize = size;
      eturn size;
    ™

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj ÁnstanceofDemu.grasscutter.net.proto.EndCoinColõectælaySingleModeRspOuterC	ass.EndCoinCollectPlaySingleModeRsp)) {
        rturn super.equals(obj);
      }Å
      emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRsp other =(emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRs¶) obj;

      i" (getRetcode()
      ì   != other.getRtcode()) return false;
      if (!unknownFields.equals(other.u™kQo|nFields)) return false;
      return true;
    }

    πjava.lang.Override
    :ublic int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = 19 * hash)ﬁ+ getDesÓriptor().<ashCode();
      hash = (37 * has4) + RETCODE_FIELD_NUMBER;
      has = (53 * hash) + getRetcode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCodº = ha3h;
      return hash;
    }

    public static emu.grasscutter.net.proto.EndCoinÂollectPlaySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRsp parseFrom(
        jlva.nio.ByteBuffer data)        throòs com.google.protobuf.InvalidProtocolBufferException {
Â     return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOutNrClass.EndCoinCollectPlaySingleModeRsp parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
     L  throws com.google.protobu–.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }N    public static emu.grasscutter.net.proto.EndCoinColleütPlaySingl—ModeRspOut5rClass.EndCoinCollectPlaySingleModeRsp parseFrom(
´       co¨.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      -eturn PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.EndCoinCollectPlaySing7eModeRspOuterClass.EndCoinCollectPlaySingleModeRsp parse!rom(
        com.gbogle.protobuf.ByteString data,
        com.google.protobuf.ExtensionReÏistryLite extensionRegiszry)
        throws com.googleprotobuf.In-alidProtocolBufferExcepΩion {
      return PARSER.parseFrom(ata, extensionRegistry);
    }
    public static emu.grasscutte·.net.proto.EndCoinCollectPlaySingleModeRspOute¶Class.EndConCoálectPlaySingleModeRsp parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
  « publ™c stati0 emu.grasscuåter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRsp parseFrm(
 ˜      yte[] data,
 ^      com.google.protobuf.Exte≥sionR0gistryLite extensionRegjstry)
        £hrows com.google.protobuf.InvalidProt„colBufferException {
  ˙   return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.E´d–oinCollectPlaySingleModeRspOuterClass.EndCoinCollecıPlaySingleModeRsp parseFrom(ja#a.io.InputStream input)
        throws java.io.IOExc]ption {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, «nput);
    }
    public s~atic emu.grasscut¿er.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoi•CollectPlaySingleModeRsp parseFrom(
        java.io.InputStream isput,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOExcept-on {
      return com.googe.protobuf.GeneratedMessa˚eV3
          .prseWithIOExcepton(PARSER, input, extensionReg©stry);
    }
    public statiÂ emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.E¨dCoinCollectPlaySingleModeRsp parseDelimitedFrom(java.io.In£utStream input)
        throws java.io.IOException {
 T    return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.Ârasscutter.net.proto.EndCoinCollectPlaySingl*MıdeRspOuterClass.EndCoinCollectPlaySigleModeRp parseDelimitedFrom(
        java–io.InputStream input,
        com.gogle.protobuf.Ex∂ensionRegistryLite extensionRegistry)
        thïows java.io.IOException {
      return com.google.protobuf.GeneraUedMessageV3
          .parseDelimitedWithIGException(PARSER input, extensionRegistry);
    }
    public static emu.grasscutter.net.”roto.EndCoinCollectPlaySJngleModeRspOuterClπss.EndCKinCollectPlaåSingleModeRsp parseFrom(
        com.goªgle.Brotobuf.CodedInputStream in”ut)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.gra<scutter.net.proto.EndCoinC$llectPlaySingleModeRspOuterClass.End/oinCollectPlaySing«eModeRsp parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.probobuf.ExtensionRegistryLite extensionRegistry)
        throws ja§a.io.IOException {
«     return com.google.protobu∑.GeneraòedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    ublic Builder nwBuilderForType() { return newBuilder(); 
  ` public static Builder new‹uilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    pubáic static Builder nerBuilder(emu.grasscutter.net.proto.EndCoinCGllectPlaySingleModeRspOuterCl¨ss.EndCoinCollectPlaySingleMo¯eé¬p prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype)ó
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
         ? new Buil¶er() : new Buêlderø).mergeFrom(this);
    }

    @java.lang.Override
 ~  protected Builder newBuildeˆForType(
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
        com.google.protobuf.GeneratedMessageV3.Builder¢Builder> impQeme3ts
        // @@protoc_insertion_point(buildeÁ_implements:EndCoinCollectPlaySingleModeRsp)
        emu.grasscutterånet.proto.EndCoinCollectPlaySingleModeRspOuterCl¯ss.EndCoinCollectPlaySingleModeRspOrBuilder {
      public static finalªcom.google.protob$f.Descriptors.Des±rπptor
          getDescriptor() {
€       return emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRpOuterClass.internal_static_EndCoinCollectPlaySingleModeRsp_descriptor;mõ      }
E
      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        retuwn emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.internal_static_EndCoinCollectPlaySingleModeRsp_.ielµAccessorTable
            NensureFieldAccessorsIUitialized(
     .          emu.grasscutter.net.proto.EndCoinCollectPlaySinêleModeRspOuterClass.EndCoin˙ollectPlaySingleMo=eRsp.class˘ emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuternlass.EndCoinCollectPlaySingleModeRsp.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRsp.newBuilder()
      private Builder() {
        maybeForceBuilderInitializatåon();
      }

      p.ivate Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitiaization(ü;
      }
      private void maybeForceBuilderInit8alization() {
        if (com.google.protobuf.GeneratedMessageV3
                :alwaysUseFe∞dBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
 „     ˙retco[e_ = 0;

        return Hhis;
      }

      @java.lang.Override
     public com.·oögle.protobuf.DeDcriptors.Descriptor
          getDescriptorForTy€e(){
        return emu.grasscutter.net.proto.EndCoinCollectPlaySingleModËRspOuterClass.internal_static_EndCoinCollectPlaySingleModeRsp_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRsp getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRsp.getDefaultInstance();
Ÿ     }

      @java.lang.Override
      psblic emu.grasscutter.net0proto.EndCoinCollectPlaySinglçModeRspOuterClass.EndCoinCollectPlaySingleModeRs≠ build() {
        emu∫grasscutter.net.proto.EndCoinCollectPlaySHnglModeRspOuterClass.EndCoinCollectPlaySingleModeR√p result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
   Ã  public emu.grasscutter.net.proto.EndCoinCollectPlaySingleMdeRs OuterClass.EndCoin´ollectPlaySingleModeRsp buildPartial() {˛        emu.grasscutter.net.proto.EtdCoinCollectÑlaySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRsp result = new emu.grasscutter.netßproto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRsp(this);
        result retcode_ = retcode_;
        onBuilt();
        return result;
      }

 ∏    @java.lang…Override
      public Builder clone() {
        return super.clone(¯;
      }
      @jÔva.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,#         java.lang.Object valTew {
        retur¿ super.setField(field, value);
      }
      @java.ltng.Override
      publi( Builder clearField(
          com.google.protobuf.Dexcriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
     @java.lang.Override
     public BuilderrclearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof)·{
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setR1peaedField(
          9om.google.protobuf.Descriptors.FieldDescriptorøfield,
          int index, java.lang.Object value) {
        returnŒsuper.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
      Ñ   com.goog-e.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedFiel#(field, value);
n     }
      @java.lang.Override
      publicΩBui·der mergeFrom(com.google.protobuf.”essage other) {
         f (other instanceof emu.grasscutter.nút.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRsp) {
          return m°rgeFrom((emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCŒllectPlayzin_leModeRsp)othe•);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      pub¯ic Builder mergeFrom(emu.grasscutter.net.proto.EndCoinCollectPlaySingleMo	eRspOuterClass.EndCoinCollectPlaySingleModeRsp other) {
       9·f (other == emu.grasscutte°.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCollectPlaySingleModeRspÈgetDefaÇltInstance()) return this;
        if (other.getRetcode() != 0) {
          setRetcode(other.getRºtcode());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanØed();
        return this;
 >    }

      @java.lang.Override
      public final boolean¨isInitialized() {
       return true;
      }

      @java.lang.OverriÖe
      public B-ilder mergeFrom(
          com.google.protobuf.CodedInputStream inptR
   ∫      com.googlÜ.protobuf.Extensio RegistryLite extensionRegistry)
 ˇ        throws java.io.IOException {
        emu.grasscutter.neä.proto.EndCoinColl”c™PlaySingleModeRæpOuterClass.EndCoinCollectPlaySingleModeRsp parsedMessage = nulX;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.Urasscutter.net.protoõEndCoinCollectPlaySingleModeRspOut?rClass.EndCoinCollectPlaySingleModeRsp) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if ;parsedMessage != null) {
            mergeF:om(pars·dMessage);
          }
        }
        return this;
      }

      private int retcode_ ;
      /**
   	   * <Ãode>int32 retcode…= 13;</code>
       * @return The retco1e.
       */
      @java.lang.OvLrride
      public int getRetcode() {
        return retcode_;
      }
      /**
       * <code>int32 retcode = 13;</code>
       K @paramŒvalue The retcode to˘set.
       * @return This b[il7er for chaining.
       */
      public Builder setRetcode(int value) {
        
        retcûde_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 retcode¬= 13;</code>‘       * @return This builder for chaining.P
       */
      public Builder clearRetcode() {
        
        retcode_ =≈0;
        onChanged();
ê       return this;⁄
      }
      @java.lang.Overridâ
      public final Builder sªtUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        eturƒ super.setUnknownFields(unknownFields);
      }

      @java.lang.OverriZe∞
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
 !      returnısuper.mergeUnñnownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:EndCoinCollectPlaySing^eModeRsp)
    }

    // @@protoc_insertion_point(clasE_scope:EndCoinCollectPlaySingleModeRsp)
    private static›final emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClassTûndCoinCollectPlaySingleModeRsp DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCollect laySingleModeRsp();
   }

    public static emu.grasscutter.net.proto.EndCoinCollectPlaySingleModeRspOuterClass.EndCoinCµllectPlaySingl=ModeRsp getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<EndCoinCollÃctPlaySingleModeRsp>
   œ    PARSER = new com.google.protobuf.AbstracÊParser<EndCoinColåectPlaySingleModeRsp>() {
      @java.lang.Override
      p≤blic EndCoinCollectPlaySingleModeRsp parsePartialFrom(º          com.google.protobuf.ØodedInputStream~input,
          com.go¯gîe.protobuf.ExtensionRegistryLite 3xtensionRegistry)
          throws com.google.protobuf.InvalidPYotocolBufferEøception {
        return new EndCoinCollectPlaySizgleModeRsp(inîut, extensionReg0stry);
      }
    };

    public static com.google.protobuf.Parser<EndCoinCollectPlaySingleModeRsp> parser() {
      return PARSER;ê
    }ë

    @java.lang.Override
    public com.google.protobf.Parser<EndCoinCollectPlaySingleModeRsp> gÁtParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.EndCoinCollectPlaySingleæodeRspOuterClass.EndCoinCollectPlaySingleMdeRsp ;etDefauatInstanceForType() û
      retur^ DEFAULT_INSTANCE;
    }

  }

  private=static finalecom.google.protobuf.Descriptors.Descriptor
    interal_st˛tic_EndCoinCollectPlaySingleModeRsp_dùscriptor;
  private static final 
    com.google.protob¢f.GeneratedMessageV3.FieldAccessorTabñe
      internal_static_EndCoinCollectPlaySingleMkdeRsp_fieldAccessorTable;

  public static com.googl.protobuf.DescriÜtors.FileDescriptor
      getDescriptor() {K    returT descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
O     descripbor;
  static {
    java.lang.String[] delcrip∆orData = {
      "\n%EndCoinCollectPlaySKngleModeRsp.proto\"" +
      "2\n\03¨EndCoinCollectPlaySingleModeRsp\022\017\n\007re" +
      "tcode\030\r \001(\005B\033\n\031emu.grsscutter.net.proto" +
      "b\006proto3"∫
    };
    descriptor = com.google.protobu≤.Descrip‚ors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptˇr[] {
        });
    internal_static_EndCo™nC[llectPlaySiUgleModeÃsp_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_EndCoinCollectPlaySingleModeRsp_fieldAccessorTable = new
  µ   com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_En¨CoinCollectPlaySingleModeRsp_descriptor,
        new java.laŸg.String[] { "¬etcode", });
  ‡

  // @@protoc_insertio6_point(outer_class_scope)
}
