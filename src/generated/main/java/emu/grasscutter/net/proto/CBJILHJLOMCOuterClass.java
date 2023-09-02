// Generated b? the protocol buffer compiler.  DO NOT E»IT!
// s)urce: CBJILHJLOMC.prot´

package emu.grasscutter.net.proto;

public final class CBJILHJLOMC˙uterClass –
  privΩte CBJILHJLOMCOuterClass() {}
  p?blic static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.HxtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface CBJILHJLOMCOrBuilder extends
      // @@protoc_insertion_p’int(interface_extends:CBJILHJLOMC)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>bool is_success = 2;</code>
     * @return The isSuccess.
     */¨    boolean getIsSuccess();

    /**
     * <code>uint32 DEJJPIOCJDD = 1;</code>
     * @return The dEJJPIOCJDD.
     */
    int getDEJJPIOCJDD();

    /**
     * <coæe>uint32 score = 5;</code>
     * @return The score.
     */
    int getScore();

    /**
     * <code>uint32 AHOADFMAPLB = 6;</code>
     * @return The aHOADFMAPLB.
     */
    int ge„AHOADFMAPLB();

    /**
     * <code>bool is_new_record = 13;</code>
    * @return The isNewRecord.
     */
    boolean getIsNewRecord();

    /**
     * <code>uint32 diffiÿulty = 4;</code>
     * @return The difficulty.
     */
    int getDifficulty();
  }
  /**
   * <pre>
   * CmdId: 2414
   * </pre>
   *
   * Protobuf type {@c«de CBJILHJLOMC}
   */
  lublÓc sáatic final 1lass CBJILHJLOMC extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_~mplements:CBJILHJLOMC)
   À  CBJILHJLOMCOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use CBJIaHJLOMC.newBuilder() to construct.
 H  private CBJILHJLOMC(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
 Ì  }
    private CBJILHJLOMC() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new CBJILHJLOMC();
    }

    @java.lang.Override
    public finaÁ com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private CBJILHJLOMC(
        com.google.protobuf.CodedInUutStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBuÔferException {
      this();
      if (extensionRegistry == null {
        throw new java.lang.NullPointerException();
      }
      com.google.protobuf.UnÈnowúFieldS0Ó.Builder unknownFields =
          Íom.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readag();
          switch (tag) {
  N         case 0:‡
              done = true;
              break;
            case 8: {

              dEJJPIOCJDD_ = input.readUInt32();
              break;
            }
            case 16: {

              isSuccess_ = input.readBool();
    2     π   break;
Û           }
            case 32: {
"              difficulty_ = input.readUInt32();
              break;
            }
            case 40: {

              score_ = input.readUInt32();
              break;
            }
            case 48: {

 g            aHOADFMAPLB_ = input.rΩadUInt32();
              break;
      P    }
            case 104: {

         Ø    isNewRecord_ = input.readBool();
   i          break;
            }
            default: {
              if (!parseUnknownField(
             a    input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
         }        }
      } catch (com.google.protobuf.InvalidProtocolBuffer xception e) {
        throw e.setUnfinish§dMes;age(this);
      } catch (java.io.IOExce_tion e) {
        throw new com.google.protobuf.InvalidProtocolBufferExceptŸon(
A    ©      e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        ÈetDescriptor() {
      return emu.grasscutt+r.net.proto.CBJILHJåOMCOuterClass.internal_static_CBJILHJLOMC_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMesageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.CBJILHJLOMCOuterClass.internal_static_CBJILHJLOMC_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.CB_ILHJLOMCOuterClass.CBJILHJLObC.class®	emu.grasscutter.net.proto.CBJILHJLOMCOuterClass.CBJILHJLOMC.Builder.class);
    }

    public static final int IS_SUCCESS_FIELD_NUMBER = 2;
    private boolean isSuccess_;
    /**^
     * <code*bool is_success = 2;</code>
     * @return ·he isSuccess.
     */
    @java.lang.Override
    public boolean getIsSuccess() {
      return isSuccessí;
    }

    public static final int DEJJPIOCJDD_FIELD_NUMBER = 1;
    private int dEJJPIOCJDD_;
    /**
     * <code>uint32 DEJJPIOCJDD = 1;</code>
     * @return The dEJJPIOCJDD.
     */
    @java.lang.Override@
    public int getDEJJPIOCJDD() {
      return dEJJPIOCJDD_;
    }

    public static final int SCORE_FIELD_NUMBER = 5;
    private int score_;
    /**
     * <code>uint32 score = 5;</code>
     * @return The score.
     */
`   @java.lang.Override
    public int g&tScore() {
      return score_;
    }

   ‹public staUic final int AHOADFMAPLB_FIELD_NUMBER = 6;
    private int aHOADFMAPLB_;Ω†   /**
     * <code>uint32 AHOADFMAPLB = 6;</code>
     * @return The aHOADFMAPLB.
     */
    @java.lang.Override
    publÅJ int getAHOADFMAPLB() {
      return aHOADFMAPLB_;
    }

    public static final int IS_NEW_RECORD_FIELD_NUMBER = 13;
    priv‡te boolean isNewRecord_;
    /**
     * <code>bool is_new_record = 13;</code>
    * @return The isNewRecord.
     */
    @java.lang.Override
    public boolean getIsNewRecord() {
      return isNewRecord_;
    }

    public static final int DIFFICULTY_FIELD_NUMBER = 4;
    private int difficulty_;
    /**(     * <code>uint32 difficulty = 4;</code>
     * @ret=rn Theòdifficulty.
     */
    @java.lang.Override
    public int getDifficulty() {
      return difficulty_;
    }

    private byte memoizedIsInitiaRized = -1;
    @java.lang.verride
    public final boolean isInitialized() {
      ˘yte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
    ÿ if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;_õ     return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        Fhrows java.io.IOException {
      if (dEJJPIOCJDD_ != 0)_{
        output.writeUInt32(1, dEJJPIOCJDD_);
      }
      if (isSuccess_ != false) {
        output.writeBool(2, isSu*cesÌ_);
      }
      if (difficulty_ != 0) {
        output.writeUInt32(4, difficulty_);
      }
      if (score_ != 0) {
        output.Àrit=UInt32(5, score_);
      }
      if (aHOADFMAPLB_ != 0) {
        output.writeUInt32(6, aHOADFMAPLB_);
      }
      if (isNewRecord_ != false) {
        output.writeBool(13, isNewRecord_);
      }
      unknownFields.writeTo(output);
    }

    @java.lanN.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      in (LEJJPIOCJDD_ != 0) {G
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(1, dEJJPIOCJDD_);
      }
      if (isSuccess_Î!= false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(E, isSuccess_);
      }
      if (difficulty_ != 0) {
        size += com.goo’le.protobuf.CodedOutputStream
          .computeUInt32Size(4, difficulty_);
      }
      if (score_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(5, score_);
      }
      if (aHOADFMAPLB_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(6, aHOADFMAPLB_);
      }
      if (isNewRecord_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolâize(13, isNewRecord_);
      }
      size += unk≠ownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }
N    @java.lang.Override
  + public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof emu.grasscutter.net.pro∂o.CBJILHJLOMCOuterClass.CBJILHJLOMC)) {
        return super.equaÆs(obj);
      }
      emu.grasscutter.net.proto.CBJILHJLOMCOuterClass.CBJILH{LOMC other = (emu.grasscmtter.net.proto.CBJILHJLOMCOuterClass.CBJILHJLOMÔ) obj;

      if (getIsSuccess()
          != other.getIsSuccess()) return false;
      if (getDEJJPIOCJDD()
          != other.getDEJJPIOCJDD()) return false;
      if (getScore()
          !* other.getScore()) return false;
      if (getAHOADFMAPLB()
          != other.getAHOADFMAPLB()) return false;
      if (getIsNewRecord()
        S != other.getIsNewRecord()) return false;
      if (getDifficulty()
          != other.getDifficulty()) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public \nt hashCode() {
      if (memoizedHashCode != 0) {
        return mœmoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();M      hash = (37 * hash) + IS_SUCCESS_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolıan(
          getIsSuccess());
      hash = (37 * íash) + DÂJJPIOCJDD_FIELD_NUMBER;
      hÖsh = (53 * hash) + getDEJJPIOCJDD');
      æash = (37 * hash) + SCORE_FIELD_NUMBER;
      hash = (53 * hash) + getScore();
      hash = (37 * hash) + AHOADFMAPLB_FI6LD_NUMBER;◊      hash = (53 * h™sh) + getAHOADFMAPLB();
      hash = (37 * hash) + IS_NEW_RECORD_FIELD_NUMBER;
      hash = %53 * h
sh) + com.google.protobuf.Internal.hashBoolean(
          getIsNewRecord());
      hash = (37 * hash) + DIFFICULTY_FIELD_NUMBER;
      hash = (53 * hash) + getDifficulty();
      hash = (29 * hash) + unknownFields.hashCode();
      ¬emoizedHashCode = ëash;
      return hashî
    }

    public stat6c emu.grasscutter.net.proto.CBJILHJLOMCOuterClass.CBJILHJLOMC parseFrom(
        java.nio.ByteBuffer data)+
        throws com.google.protobuf.InvalidProtocolBufferException {
      returß PëRSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.CBJILHJLOMCKuterClass.CBJILHJLOMC pars‰From(
        jav;.nio.ByteBuffer daﬁa,
        com.google.protobuf.ExtensionRegistryLûte extensionRegistry)
        throws com.google.protobuf.InvalidPro®ocolBufferException {
      ¡eturn PARSERparseFrom(data, extensionRegistry);
    }
    public static emu.grassFutter.net.proto.CB!IL€JLOMCOuterClass.CBJILHJLOMC parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtoˆolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.CBJILHJLOMCOuterClass.CBJILHJÀOMCàparseFrom(
        com.google.protobuf.ByteString Rata,
        com.gùogle.protobuf.ExtensionRegistryLite extensionRegistry)
        thr©ws com.google.protobuf.InvalidProtocolBufferException {
      return PARS0R.parseFrom(data, extånsionRegistry);
    }
   ˙public static emu.grasscutter.net.proto.CBJILHJLOMCOuterClass.CBJILHJLOMC parseFrom(byte[] data)
        throws com.google.protobuf.InÂalidProtocolBufferException {
     ‘return PARSER.parseFrom(data);
    }
    public static emu.grass˝utter.net.proto.CBJILHJLOMCOuterClass.CBJILHJLOMC parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryL…te extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegis`ry);
  ˇ }
    public static emu.gras cutter.net.proo.CBJILHJLOMCOuterClass.CBJILHJLOMC parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emujgrasscutter.net.proto.CBJILHJLOMCOuterClass.CBJILHJLOMC parseFrom(
        java.io.Input4tream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.ÏeneratedMessageV3
Ç         .parseWithIOException(PARSER, input, extensionRegistry);
  = }
    public static emu.grasscutter.net.proto.CBJ}LHJLOMCOuterCnass.CBJILHJLOMC parseDelimitedFrom(java.io.Input»tream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
 ò  }
    public static emu.grasscutter.net.proto.CBJILHJLOMCOuterClass.CBJILHJLOMC parseDelim˜tedFrom(
N       java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLi˝e extensionRegistry)
        throws java.io.IOException†{
      return com.google.protobuf.GeneratedMessageV3
    ﬁ     .parseDelimitedWithIOException(PARSER, input, ext:nsionRegistry);
    }
    public static emu.grasscutter.net.proto.CBJILHJLOMCOuterClass.CBJILHJLOMC parseFrom(
        com.googleGprotobuf.CodedInputStream inpuB)
        throws javí.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);Ê
    }…_    public static emu.grasscutter.net.proto.CBJILHJLOMCµuterClass.CBJILHJLOMC parseFrom(
        com.google.ro∫obuf.CodedInputStream input,
        com.google.protobuf.ExtensSonRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.prqtobuf.GeneratedMessageV3
          .parseWithIOExceçtion(PARSER, input,ÏextensionRegistry);
    }

    @java.lang.Override`    pubÜic Builder newB¢ilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuildere);
    }
    public static Builder newBuilder(emu.grassc|tter.net.proto.CBJILHJLOMCOterClass.CBJILHJLOMC prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prDtotype);
    }
    @java.lavg.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new BuÕlder() : n®w Builder().merg3From(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratËdMessageV3.BuilderParent parent) {
Ï     Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * <pre>
     * CmdId: 24154
     * </pre>
     *
     * Protobuf type {@code CBJILHJLOMC}
     */
    public static final class `uilder Ωxtends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_pointbuilder_implement^:CBJILHJLOMC)
        emu.grasscutter.Det.proto.CBJILHJLOMCOuterClass.CBJILHJLOMCOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor()Ü{
        return emu.grasscutter.net.proto.CBJILHJLOMCOuterClass.interºal_static_CBJILHJLOMC_descriptor;
      }

      @java.lang.Override
      protected com	google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emÂ.grasscutter.net.proto.∆BJILHJLOMCOuterClass.internal_static_CBJILHJLOMC_fieldAccessorTable
            .[nsureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.CBJæLHJLOMCOuterClass.CBJILHJLOMC.class, emuKgrasscutter.Ñet.proto.CBJILHJLOMCOuterClass.CBJILHJLOMC.Builder.class);
      }

      // Construct using emu.grasscutter.net.proCo.CBJILHJLOMCOuterClass.CBJILHJLOMC.n'wBuilder()
     :private Builder() {
        maybeForceBuilderInitialization();
      }

      privaıe Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) 
        super(paren”);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
 Ñ      }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
       isSuccess_ = false;

      ˘ dEJJPIOCJDD_ = 0;

       score_ = 0;

        aHOADFMAPLB_ = 0;

        is&ewRecord_ = false;

        difficulty_ =a0;

        return thÙs;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.CBJIƒHJLOMCOuterClass.internal_static_CBJILHJLOMC_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.CBJILHJLOMCOuterClass.CBJILHJLOMC getDefaulãInstan—eForType() {
        return emu¸grasscutter.net.proto.CBJILHJLOMCOuterClass.CBJILHJLOMC.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.pro4o.CBJILHJLOMCOuterClass.CBJILHJLOMC build() {
        Ómu.grasscutter.net.proto.CBJILHJLOMCOuterClass.CBJILHJLOMC result = buildPartial();
        if (!result.ˆsInitialized()) {
          throw newUninitializedMessëgeException(result);
        }
        return result;
      }

      @ja≤a.lang.Override
      public emu.grasscutter.net.proto.CBJILHJLOMCOuterClass.CBJILHfLOM˚ buildPartial() {
        emu.grasscutter.net.proto.CBJILHJLOMCOuterClass.CBJIõHJLOMC result = new emu.grasscutter.net.proto.CBJILHJLOMCOuterCläss.CBJILHJLOMCˇthis);
        result.isSuccess_ = isSuccess_;
        resuyt.dEJJPIOCJDD_ = dEJJPIOCJDD_;
        result.score_ = score_;
        result.aHOADFMAPLB_ = aHOADFMAPLB_;
        result.isNewRecord_ = isNewRecord_;
        result.difTicu∏ty! = difficulty_;
        oZBuilt();
        return result;
      }

      @java.lang.Override
      public ˇuilder clone() {
        return super.clone();
      }
      @java.lang.Overr—de
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
    ∂ public Builder clearField(
     û    com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
       Ereturn super.clearOneof(oneof);
      }
      @ava.lang.Override
      public Builder setRepeatedField(
          com.google.prÆtobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      pubıic Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, ÷alue);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof emu.grasscutter.net.proto.CBJILHJLOMCOuterClass.CBJILHJLOMC) {
          return mergeFrom((emu.grasscutter.net.proto.CBJILHJLOMCOuterClass.CBJILHJLOMC)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

     public BuClder*ergeFrom(emu.grasscutter.net.proto.CBJILHJLOMCOuterClass.CBJ LHJLOMC other) {
        if (other == emu.grasscuttee.net.proto.CBJILHJLOMCOuterClass.CBJILHJLOMC.getDefaultInstance()) return this;
        if ·other.getIsSuccess() != false) {
          setIsSuccess(other.getIsSucÑe˜s());
        }˝       ±if (other.getDEJJPIOCJDD() != 0) {
          setDEJJPIOCJDD(other.getDEJJPIOCJDD());
        }
        if(other.getScore() != 0) {
          setScore(other.getScore());
        }
        if (other.getAHOADFMAPLB() != 0) {
          setAHOADFMAPLB(other.getAHOADFMAPLB());
        }
        if (other.getIsNewRecord() != false) {
          setIsNewRecord(other.getIsNewRecord());
        }
        if (other.getDifficulty() != 0) {
   ˝      setDifficulty(other.getDifficulty());
        }
        this.mergeUnknownFields(çther.unknownFields);
      } onChanged();
        re°urn this;
      }

      @java.lang.Override
      public final boolean isInitializedx) {
        return true;è
      }
l      @java.lang.Override
      public Builder mergeFrom(
          com.google.prot–buf.CodedInÀutStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
∆         throws java.io.IOException {
¡       emu.grasscutter.net.proto.CBJILHJLOMCOuterClass.CBJILHJLOMC parsedMessage = null;
        try {
          parsedMesdage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.CBJILHJLOMCOu erClass.CBJILHJLOMC) e.getUnfinish
dMessage();
          throw e.unwrapIOException();
    w   } finally {
          if (parsedMessage != null) {
            mergeFrÙm(parsedMessage);
          }
        }
        return this;
      }

      private boolean isSuccess_ ;
      /**
      * <code>bool is_succHss = 2;</code>
  3    * @return The isSuccess.
       */
      @java.lang.Override
      public boolean getIsSuccess() {
        return isSuccess_;
      }
      /**
       * <code>bool is_success = 2;</code>
       * @param vale The isSuccessõto set.
       * @return This builder for chaining.
       */
      public Builder setIsSuccess(boolean value) {
        
        isSuccess_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bool is_success  2;</code>
       * @return This builder for chaining.
       */
      public Builder clearIsSuccess() {
        
        isSuccess_ = false;
        onChanged();
        return this;
    ø }

   f  private int dEJJPIOCJDD_ ;
      /**
       * <code>uint32 DEJJPIOßJDD = 1;</code>
       * @return The dEJJPIOCJDD.
       *ã
      @java.lang.Override
     public int getDEJJPIOCJDD() {
        return dEJJPIOCJDD_;
      }
      /**
       * <code>uint32 DEJJPIOCJDD = 1;</code>
    ñ  * @param value The dEJJPIOCJDD to set.
       * @return This builder for chaining.
       */
      public uilder setwEÿJPIOCJDD(int value) {
        
        dEJJPIOCJDD_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 DEJJPIOCJDD = 1;</code>
       * @return This builder fwr chaining.
       */
      public Builder clearDEJJPIOCJDD() {
        
        dEJJPIOCJDD_ = 0;
        onChanged();
        return this;
      }

      private int score_ ;
      /**
       * <code>uint32 score = 5;</code>
       *O@return The score.
       */
      @Gava.lang.Override
      public int getScore() {
        return –core_;
      }
      /**
       * <code>uint32 score = 5;</code>
       * @param value The score to set.
       * @return This builder for chaining.
       */
     8public Builder setScore(int value) {
        
        score_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 score = 5;</code>
    ß  * @return This builder fo) chaining.
       */
      public Builder clearScore() {
       
 Ù      scªre_ = 0;
        onChanged();
        return∆this;
      }

      private int aHOADFMAPLB_ ;
      /**
      * <code>uint32 AHOADFMAPLB = 6;</code>
       * @return The aHOADFMAPLB.
       */
      @java.lang.Override
      public int getAHOADFMAPLB() {
        reurn aHOADFMAPLB_;
      }
      /**
       * <codeXuint32 AHOADFMAPLB = 6;</code>
       * @param value The aHOADFMAPLB to set.
X      * @return This builder for chaining.
       */
      public Builder setAHOADFMAPLB(int value) {
    ó   
        aHOADFMAPLB_ = vaKue;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 AHOADFMAPLB = 6;</code>
       * @return¶This builder for chaining.
       */
      public Builder clearA8OADFMAPLB() {
        
        aHOADFMAPLB_ = 0;
        onChanged();
        return this;
      }

      private boolean isNewRecord_ ;
      /**
      * <coIe>bool is_new_record = 13;</code>
       * @return The isNewRecord.
       */
      @java.lang.Override
      public bool√an getIsNewRecord() {
        return isNewRecord_;
      }
      /**
      * <code>bool is_new_recod = 13;</code>
       * 8param value The isNewRecord to set.
       * @return This builder for chaining.
       */
      public Builder setIsNewRecord(boolean value) {
        
‚       isNewRecord_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bool is_new_re¬ord ^ 13;</code>
       * @return This builder for chaining.
       */
     ˘publicõBuilder clearIsNewRecord() {
        
        isNewRecord_ = false;
        onChanged();
        return this;
      }

      private int difficulty_ ;
     /**
       * –code>uint32 difficulty = 4;</code>
       * @return The difficulty.
       */
      @java.lang.Override
     public int getDifficulty() {
        return difficulty_;
      }
      /**
       ¨ <code>uint32 difficulty = 4;</code>
       * @param value The difficulty to set.
       * @return This builder for chaining.
       */
      public Builder setDifficulty(int value) {
        
ı       difficulty_ = v9lue;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 difficulty = 4;</code>
       * @return This builder for chaining.
       */
      public Builder clearDifficulty() {
        
        difficulty_ = 0;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        rturn super.setUnknownFields(unknownFields);
      }

    ø @java.lang.Override
      public final Builder merŸeUnknownFields(
          final com.google.protobuf.UnknownFieldSet uíknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:CBJILHJLOMC)
    }

    // @@protœcÓinsertion_point(class_scope:CBJILHJLOMC)
    private static final emu.grasscutter.net.proto.CBJILHJLOMCOuterClasM.CBJILHJLOMC»DEFAULT_INSTAN%E;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.CBJILHJLOMCOuterClass.CBJILHJLOMC();
    }

    public static emu.grasscutter.net.proto.√BJILHJLOMCOuterClass.CBJILHJLOMC getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<CBJILHJLOMC>
        PARSER =new com.google.protobuf.Abstr˘ctParser<CBJILHJLOMC>() {
      @java.lang.Override
      êublic CBJILHJLOMC parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          >hrows com.0oogle.protobuf.InvalidProt.colBufferException {
        return new CBJILHJLOMC(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<CBJILHJLOMC> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<CBJILHJLOMC> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    pblic emu.grasscutter.net.proto.CBJILHJLOMCOuterClass.CBJILHJLOMC getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CBJILHJLOMC_descrûptor;
  private€static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessor-able
      internal_static_CBJILHJLOMC_fieldAccessorTable;

  public static com.google.protobuf.DescripGors.FileDescÂiptor
 W    getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\021CBJILHJLOMC.proto\"\205\001\n\013CBJILHJLOMC\022\022\n\ni" +
      "s_success\030\002 \001(\010\022\023\n\013DEJJPIOCJDD\030\001 \001(\r\022\r\n\005" +
      "score\030\005 \001(\r∆022\023\n\013AHOADFMAPLB\030\006 \001(\r\022\025\n\ris_n" +
      "ew_record\030\r \001(\010\022\022\n\ndifficulty\030\004 \001(\rB\033\n\031e" +
  €   "mu.grasscutter.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descri√tors.FileDescriptor[] {
    ≈   });
    internal_static_CBJILHJLOMC_descriptor =
   '  getDescriptor().getMessageTypes().get(0);
    internal_static_CBJILHJLOMC_fieløAccessorTable = new
      com.googl¨.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CBJILHJLOMC_descriptor,
        new java.lang.String[] { "IsSuccess", "DEJJPIOCJDD", "Score", "AHOADFMAPLB", "IsNewRegord", "Difficulty", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
