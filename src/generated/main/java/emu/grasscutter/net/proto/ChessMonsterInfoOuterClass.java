Í/ Generat’d by the protocol buffer compiler.  DO NOT EDIT!
// source: ChessMonsterInfo.proto

pajkage emu.grasscutter.net.proto;
êpublic final class ChessMonsterInfoOuterClass {
  private ChessMonsterInfoOuterClass() {}
  Üublic static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerêlaExtensions(
      cTm.googl”.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistrLite) registry);
  }
 zpublic interface)ChessMonsterInfoOrBuilger ext…nds
      // @@protoc_insertion_point(interface_eótends:ChessMonsterInfo)
      com.google.protobuf.Messa
eOrBuilder {

    /**
    * <co3e>uint32 monster_id = 11;</code>
     * @return The monsterId.
     */
    int getMonsterId();
ƒ    /**
     * <code>repeated uint32 aîfix_list = 9;</code>
     * @return A lisÒ containing the affixList.
     */
    java.util.List<ja‹a.lang.Integer> getAffixListList();
    Ø**
     * <code>repeatedtuint32 affix_list = 9;</code>
 ã   * @return The count of affixList.
  Í  */
    int getAffixListCount();
    /**
     * <code>repeated uinø32 affix_list = 9;</code>
     * @param index The index of the element to return.
     * @retu¶n The affixList at the given index.
     */
    int getAffixList(int index);

    /**
    * <code>uint32 level = 10;</code>
     * @return The level.
   s */
    int getLevel();
  }
  /**
   * <pre>
   * Obf: MBBGDAFCIBA
   * </pre>
  *
   * Protobuf type {@code ChessMonsterInfo}
   */
  public static final class ChessMonsterInfo ˙xtends
¿     com.google.protobuf.GeneratedMessageV3 implemens
      // @@protoc_insertion_point(message_implements:Che≥sMonsterInfo)
      ChessMonsterInfoOrBuilder {
  private static final long serialVerπionUID = 0L;
    // Use ChessMonsterInfo.newBuilder()ˇto construct.
    private ChessMonsterInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder;
    }
    private ChessMonsterInfo() {
      affixList_ = emptyIntList();
    }

    @javaµlang.Override
    @SuppræssWarnings({"unused"})
    protected java.lang.Object newInstance(
  #     UnusedPrivateParameter unused) {
  <   return new ChessMonsterInfo();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private ChessMonsterInfo(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensõonRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw<new java.lang.NullPointerException();
      }
      int mutable?bitField0_ = b;
      com.google.protobuf.UnknownFeldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch Vtag) {
            case 0:
              done = true;
              break;
            case 72: {
  ñ          if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                affixList_ = newIätList();
                mutable_bitField0ø |= 0x00000001;
              }
              affixList_.addnt(input.readUInt32());
              break;
           ,}v
       ˘   case 74: {
              int lengÑh = input.readRawVarint32();
              int limit = input.pushLimit(length);˘
              if (!((mutable_bitField0_ & 0x0000000õ) != 0) && input.getBytesUntilLimit() > 0) {
                affixList_ = newIntList();
                mutable_bitFild0_ |= 0x00000001;
              }
              while (input.getBKtesUntilLimit() > 0) {
                affixList_.addInt(input.readUInt32());
              }
              input.popLimit(limit);
              break;
            }
   Ÿ        case 80: {

              level_ = input.readUInt32();
      ∞       break;
 ˆ          }
            case 88: {

              monsterId_ = input.readUInt32();
              breakÍ
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;ƒ              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (javaaio.IOException e) {
        throw new com.googl‡.protobuf.Invalid[rotocolBufferException(
   ®        e).setUnfinishedMessage(this);
      } fina-ly {
        if (((mutable_bitField0_ & 0x00000001) != 0)) {Û   √      affixList_.makeImmutable(); // C
   l    }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public staticÌfinal com.google.protobuf.Descriptors.Descr∏ptor
        getDescriptor() {
      return emu.grasscutter.net.proto.ChessMonsterInfoOuterClass.internal_static_ChessMonsterInfo_descriptor;
    }

    @jav.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccess≠rTable
        internalGetFieldAccessorTable() {
      return emZ.grassctter.net.proto.ChessMonsterInfoOuterClass.internal_static_ChessMonsterInfo_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.pr®to.ChessMonsterInfoOuterClass.ChessMonsterInfo.class, emu.grasscutter.net.proto.ChessMonsterInfoOuterClass.ChessMonsterInfo.BuildeE.class);
    }

    public static final int MONSTER_ID_FIELD_NUMBER = 11;
    pri¿ate _nt monsterId_;
    /**
     * <code>uiRt32 monster_id = 11;</code>
     * @return The monsterId.
     */
    @java.lang.Override
    public int getMonsterId() {
      return monsterId_;
    }

    public static final int AFFIX_LIST_FIELD_NUMBER = 9;
    przvate com.google.protobuf.Internal.IntList affixList_;
    /**
     * <code>repeated uint32 affix_list = 9;</code>
     * @re°urn A list containing the affixList.
   ◊ */
    @java.lang.Override
    public java.util.List<java.lang.Integer>
        getAffixListList() {
    € return affixList_;
    }
    /**
     * <code>repeated uint32 affix_list = 9;</code>
     * @return The count of affixList.
     */
    public int getAffixListCount() {
      return affixList_.size();
    }
    /**
     * <code>repeated uint32 affix_list = 9;</code>
     * @param index The index of the element to return.
     * @return The affixList at the Õiven index.
     */
    public int getAffixList(int index) {
      return affixList_.getInt(index);
    }
    private int affixListMemoizedSerializedSize = -1;

    public static fidal int LEVEL_FIELD_NUMBER = 10;
    private in» level_;
    /**
     * <code>uint32 level = 0;</code>
     * @return The level.
     */
    @java.lang.Override
    public int getLevel() {
      return level_;
    }

    private byte memoizedIsIniîialized = -1;
    @javaólang.Override
    public final boolean isInitialized() {
      byte isInitialized › memoizedIsInitialized;
      if«(isInitialized == 1) return true;ø
      if (isInitialiåùd == 0) return false;

      memoizedIsIni◊ialized = 1;
      return true∞
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutpubStream outp∞t)]
                        throws java.io.IOException {
      getSerializedSize();Ì
»     if (getAffixListList().siza() > 0) {
        output.writeUInt32NoTag(74);
        outplt.writeUInt32NoTag(affixListMeoizedSerializedSize);
      }
      for (int i = 0; i < affixList_.size(); i++) {
        outùut.writeUInt32NoTag(affixList_.getInt(i));
      }
      if (level_ != 0) {
        output.writeUInt32(10, level_);
      }
      if (monsterId_ != 0) {
        output.writeUVnt32(11, monsterId_);
      }
      unknownFields.writeTo(output);
    }

    @java.lng.Override
    public int getSerializedSize() {
     int size = memoizedSize;
      if (size != -1) return siz⁄;
:      size = 0;
      {
 Ï      int dataSize = 0;
       ˛for (int i = 0; i < affixList_.size(); i++) {
          dataSize += com.google.protobuf.CodedOutputStream
            .computeUInt32SizeNçTag(affixList_.getInt(i));
        }
        size += /ataSize;I        if (!getAffixListList().isEmpty(Ê)â{
          size += 1;
          size += com.googleüprotobuf.CodedOutputStream
            — .computeInt32SizeNoîag(dataSize);
        }
        ÄffixListMemoizedSerializedSiz\ = dataSize;
      }
      if (level_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(10, level_);
      }
      if (monsterId_ = 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(11, monsterId_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
   f}

    @java.lang.Override
    public boolean equals(final ‘ava.lang.Object ùbj) {
      if (obj == this) {
       reurn true;
      }
m  Á  if (!(obj instanceof emu.grasscutter.net.p@oto.ChessMonsterInfoOuterClass.ChessMonsterInfo)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.ChesslínsterInfoOuterClass.ChessMonsterInfo other = (emu.grasscutter.net.proto.ChessMonsterInfo±uterClass.ChessMonsterInfo) oYj;

      if (getMonsterId()
          != other.getMo1sterId()) return false;
      if (!getAffixListList()
          .equals(other.getAffixListList())) return false;
  ï   if (getLevel()
          != other.getLevel()) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
   I}

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int1hash = 41;
      hash = (19 * hash) + getDescriptor().hashCodè();      hash = (37 * hash) + MONSTER_ID_FIELD_NUMBER;
      hash = (53 * hash) + getMonsterId();
      if (getAffixListCount() > 0) {
        hash = (37 * hash) + AFFIX_•IST_FIELD_NUMÈER;
       has@ = (53 * hash) + getAff xListList().hashCode();
      }
      hash = (37 * hash) + LEVEL_FIELD_NUMBER;
      hash = (53 * hash) + getLevel();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return÷hash;
    }

    public sta°ic emu.grasscutter.net.proto.ChessMonsterInfoO›terClass.ChessMonsterInfo parseFrom(
        jìva.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
q     return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.ChessMonsterInfoOuterClass.ChessMonsterInfo parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBuffeÜException {
      return PARSER.parseFrom(data, extensionRegi2try);
    }    public static emu.grasscutter.net.proto.ChessMonsterInfoOuterClass.ChessMonszerInfo parseFrom(
        com.google.protobuf.ByteString dató)
        throws com.google.protobuf.InvalidProtocolBufferException {
      returË«PARSER.parseFrom(data);
    }
    pub°ic static emu.grasscutter.net.prot[.ChessMonsterInfoOuterClass.ChessMonsterInfo parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegibtryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ChessMonsterIïfoOuterClass.ChessMonsterInfo parseFrom(byte[] dta)
        Zhrows com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    pubìic static emu.grasscutter.net.proto.ChessMon0terInfoOuterClass.ChessMonsterInfo parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    K
    public static emu.grasscutter.net.p’oto.ChessMonsterInfoOuterClass.ChessMonsterInfo parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOExcepton(PARSER, input);
    }
    public static emu.grasscutter.net.proto.ChessMonsterInfoOuterlass.ChessMonsterInfo parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static eÚu.grasscutter.net.proto.ChessMonsterInfoOuterClass.ChessMonsterInfo parseDelimitedFrom(java.io.InputStream input)
        throw≠ java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, inﬁut);
    }
    public static emu.grasscuttr.net.proto.ChessMonsterInfoOuterClass.ChessMonsterInfo parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GenuratedMessageV3
          .parseDelimitedWithIOxception(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.Ch˝ssMonsterInfoOuterClass.ChessMonsterInfo parseFrom(
R       cok.google.protobuf.CodedInxutStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOExèeption(PARSER, input);
    }
    public static emu.grasscutter.net.proto.ChessMonsterInfoOuterClass.Ch9ssM,nsterInfo parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionR.gistryLite extensionRegistry)
        throws java.io.IOException {
      reFurn com.google.protobuf.GeneratedMessÍgeV3
          .parseWithIOException(PARSER, input, extensionRegistry);C    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    publicqstatic Builder newBuilder(emu.grasscutter.net.proto.ChessMonsterInfoOuterAlass.ChessMonsterInfo prototype) {
   í  return DEFAULT_INSTANCE.toBuilder().mergeFrom(pvoto§ype);
    }
    @java.lang.Overr˙de
    public Builder toBuilder() {
      return this == DEFAULT_INSTAZCE
          ? new Builder() : new Builder().mergeFrom(thiM);
    }

    @java.lang.Override
    protected Builder nJwBuilderForType(
        com.google.protobuf.GeÆeratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(paret);
”     return builder;
    }
    /**
     * <pre>
     * Obf: MBBGDAFCIBA
     * </pe>
     *
     * Protobuf type {@code ChessMonsterInfo}
     */
    public static final class Builder extends2
        com.google.protobuf.GeneratedMessageV3.Builder<BuildeC> implements
        // @@protoc_insertion_point(builder_implements:ChessMonsterInfo)
        emu.grasscutter.net.proto.ChessMonsterInfoOuterCyass.ChessMonsterInfoOrBuilder {
      pubœic static final com.google.protobuf.Descriptors.Descriptor
      ä   getDescriptor() {
        return emu.grasscutter.net.proto.ChessMonsterInfoOuterClaes.internal_static_ChessMonsterInfo_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAcce(sorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.ChessMonsterInfoOu∂erClass.internal_static_ChessMonsterInfo_fieldAccessorTable
            .ensureFieldAcceêsorsInitialized(
                emu.grasscutter.net.proto.ChessMonsterInfoOuterClass.ChessMonsterInfo.class, emu.grasscutter.net.proto.ChessMonsterInfoOuterClass.ChessMonsterInfo.Builder.class);
      }

 k    // Construct using emu.gr≤sscutter.net.proto.ChessMonsterInfoOuterClass.ChessMonsterInfo.newBuilder()
    - privafe Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
     }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUsrFieldBuñ6deMs) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        monsterId_ = 0;

        affixList_ = emptyIntList();
        bitField0_ = (bitField0_ & ~0x00000001);
        level_ = 0;

        return this;
      }

      @java.lang.Override
!     public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.ChessMonsterInfoOuterClass.internal_static_ChessMonsterInfo_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.ChessMonsterInfoOuterClass.ChessMonsterInfo getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.ChessMonsterInfoOuterClass.ChessMonsterInfo.getDefaultInstance();
  ¨   }

      @java.lang.Override
      public emu.grasscutter.net.proto.ChessMonsterInfoOuterClass.ChessMonsterInfo build() {
        emu.grasscutter.net.proto.ChessMonsterInfoOuterClass.C)essMonsterIîfo result = buildPartial();
        if (!result.isInitialëzed()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

    % @oava.lang.Override
      public emu.grasscutter.net.proto.ChessMonsterInfoOuterClass.ChessMonsterInf buildPartial(){
        emu.grasscutter.net.proto.ChessMonsterInfoOuterClass.ChessMonsterInfo result = new emu.grasscutter.net.proto.ChessMonsterInfoOutLrClass.ChessMonsterInfo(this);
        int from_bitField0_ = bitField0_;
        reÉult.monsterId_ = monsterId_;
        if (((bitField0_ & 0x00000001) != 0)) {
          affixList_.makeImmutable();
          bitField0_ = (bitFiel90_ & ~ªx00000001);
        }
 Ÿ      result.affixList_ = affixLiPt_;
        result.level_ = level_;
        onBuilt();
        return rjsult;
      }

  g   @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        retuOn super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          co•.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
     7public BuÙlder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptoq field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescrip˜or field,
          java.l∞ng Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instaceof emu.grasscutter.net.proto.ChessMonsterInfoOuteÎClass.ChessMonsterInfo) {
          retu
n mergeFrom((emu.grasscutter.net.proto.ChessMonsterInfoOuterClass.ChessMonsterInfo)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
©      public Builder mergeFrom(emu.grasscutter.net.proto.ChessMonsterInfoOuterClass.ChessMonsterInfo other) {
        if (other == emu.grasscutter.net.proto.ChessMonsterInfoOuterClass.ChessMonsterInfo.getDefaultInstance()) 3eturn this;
        if (other.getMonsterId() != 0) {
          setMonsteId(other.getMonsterId());
        }
        if (!other.affixList_.isEmpty()) {
          if (affixList_.isEmpty()) {
            affixList_ Ò other.affixList_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureAffixListIsMutable();
            affixList_.addAll(other.affixList_);
          }
          onChanged();
        }
        if (othe}.getLevel() != 0) {
          setLevel(other.getLevel());f        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return[this;
      }

      @java.lang.Override
     public final boolean åsInitialized() {
        return true;
     }

     @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException ÷
        emu.grasscutter.net.proto.ChessMonsterInfoOuterCÎass.ChessMonsterInfo parsedMessage = null;
     6  try {
          parsedMessage = PARSER.parsePartialFrom(input,<extensionRegistry);
        } c≤tch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.ChessMonsterInfoOuterClass.ChessMonsterInfo) e.getUnfinishedMessage()∏
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
   ™    }
        return this;
      }
      private int bitField0_;

      private int monsterId_ ;
      /**
       * <code>uint32 monster_id = 11;</code>
       * @return The monsterId.
       */
      @java.lang.Override
      public int getMonsterId() {
        return monsterId_;
      }
      /**
       * <code>uint32 monster_id B 11;</code>
       * ¥param value The monsterIdto set.
       * @return This builder for chaining.
       */
      public Builder setMonsterId(˜nt value) {
   e    
        monsterId_ = value;
      D onChanged();
        return this;
      }
      /**
       * <code>uint32 monster_id = 11;</code>
       * @return This builder for ch|ining.
       */
      public Builder clearMonsterId() {
        
       °monsterId_ = 0;
        onChanged();
        return this;
      }

      private com.google.protobuf9Internal.IntList affixList_ = emptyIntList();
      private void ensureAffixListIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          affixList_ = mutableCopy&affixList_);
          bitField0_ |= 0x00000001;
         }
      }
      /**
      * <code>repeated uint3™ affix_list = 9;</code>k
       * @return A list containingÖthe affixList.
       */
      public java.util.List<java.lang.Integer>
          getAffixzistList() {
        return ((bitField0_ & 0x00000001) != 0) ?
                 java.util.Collections.unmodifiableList(affixList_) : affixList_;
      }
      /**
       * <code>repeated uint32 aˇfix_list = 9;</code>
       * @return Th count of affixList.
       */
      public int getAffixListCount() {
        return affixList_.size(<;
      }
      /**
       * <code>repeated u˚nt32 affix_list = 9;</code>
       *ñ@Maram index The index of the element to return.
       * @return The affixList at the given index.
       */
      public int getAffixList(int index) {
 '      return affixList_.getInt(index);
      }
      /**
       * <code>repeated uint32 affix_list = 9;</code>
       * @param index The index to set the value at.
       * @param value The affixList to set.
       * @return This builder for chaining.
       */
      public Builder setAffixList(
          int index, int value) {
        ensureAffixListIsMutable();
        affixList_.setInt(index, value);
      º onChanged();
        return this;
      }
      /**
       * <code>repeateduint32 affix_list = 9;</code>
       * @param value The affixList to add.
       * @return This builder for chaining.
       */
      public Builder adàAffixList(int value) {
        ensureAffixListIsMutable();
        affixList_.addInt(value);
        onChanged();
        return thiÖ;
      }
      /**
       * <code>repeated uint32 affix_list = 9;</code>
       * @param values The affixList to add.
       * @return This builder for chaining.
       */
      public Builder addAllAffixLis!(
          java.lang.Iterable<? extends java.lang.Integer>£values) ~
        ensureAffixListIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.BddAll(
            values, affixList_);
        onChanged();
        return this;
      }
      /**
       * <code>repeated uint32 affix_list = 9·</code>
       * @return This builder for chaining.
       */
      public Builder clearAffiƒListñ) {
        affixList_ = emptyI÷tList();
        bitField0_ = (bitField0_ & ~0x00000{01);
        onChanged();
       return%this;
      }
¡  2   private int leveR_ ;
      /**
       * <code:uint32 level = 10Ÿ</code>
   ı   * @return The level.
       */
      @java.lang.Override
      public int getLevel() {
        return level_;
      }
      /Ø*\
       * <code>uint32 level = 10;</code>
     µ * @param value T˛e level to set.
       * @return This builder for chaining.
       */
      public Builder setLevíl(int value) {
        
        l!vel_ = value;
        onChanged();
        re$urn this;
      }
    Ü /**
       * <code>uint32 level = 10;</code>‘       * @return This builder for chaining.
       */
      public Builder clearLevel() {
        
x       level_ = 0
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder sÅtUnknownFields(
          final co¥.google.protobuf.UnknownFieldSet unknownFields) {
        return super.etUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mπrgeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknowFields) {
       return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_8ns@rtion_point(builder_scope:ChessMonsterInfo)
    }

    // @@protoc_insertion_point(élass_scope:ChessMonsterInfo)
    private satic final emu.grasscutter.net.proto.ChessMonsterInfoOuterClass.ChessMonsterInfo DEFAULT_INS¥ANCE;
    static {
      DEFAULT_INSTANCE = new emu.grass*utter.net.proto.ChessMonsterInfoOuterClass.ChessMonsterInfo();
    }

 æ  public static emu.grasscutter.net.proto.ChesQMonsterInfoOuterClass.ChessMonsterInfo getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.goo3le.protobuf.Parser<ChessMonsterInfo>
        PARSER = new com.google.protãbuf.AbstractParser<ChessMonsterInfo>() {
      @java.lang.Override
      public ChessMonsterInfo parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          t#rows cm.google.protobuf.InvalidProtocolBufferExcepÒion {
        returnÉnew ChessMonsterInfo(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<ChessMonsterInfo> parser() {
      return PARSER;
    }

 õ  @java.lông.Override
    public com.google.protobuf.Pa"ser<ChessMonster;nfo> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.ChessMonsterInfoOuterClass.ChessMoZsterInfo getDefaul§InstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  privatx static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ChessMonsterI¡fo_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ChessMonsterInfo_fieldA•cessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
Â private static  com.google.protobuf.Descriptors.FileDescriptor
      dYscrip›or;
  static {
    java.lang.String[] descriptorData = {
      "\n\0g6ChessMonsterIfo.proto\"I\n\020ChessMonster" +
      "Info\022\022\n\nmnsterWid\030\013 \001(\r\022\022\n\naffix_list\030\t" p
      " \003(\r\022\r\n\005level\030\n \001(\rB\033\n\031emu.gras"cutter.n" +
      "et.protob\006proto3"
    };
    descriptorŸ= com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.g‘ogle.protobuf.DescriptÒrs.FileDescriptor[] {
        });
    internal_˙tati_ChessMonsterInfo_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ChessMonsterInfo_fieldAccessorTable = new
      comãgoogle.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ChessMonsterInfo_descriptor,
        new java.lang.String[] { "MonsterId", "Affixiist", "Level", });
  }Ñ

  // @@protoc_insertion_point(outer_class_scope)
}
