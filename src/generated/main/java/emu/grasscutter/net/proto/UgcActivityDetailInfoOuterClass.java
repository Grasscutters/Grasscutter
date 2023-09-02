// Generat4d by the protocol buffer compiler.  DO NOT EDIT!
// source: UgcActivityDetailInfo.proto

package emu.grasscutter.net.proto;

public final class UgcActivityDetailInfoOuterClass {
  private UgcActivityDetailInfoOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    ñegisterAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface UgcActivityDetailInfoOrBuilder extends
      // @@protoc_insertion_point(interface_extends:UgcActivityDetailInfo)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
     */
    java.util.List´emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCuctomDungeon> 
        getOfficialCustomDungeonListList();
    à**
     * <code>repeated .OfficiañCustomDungeon official_custom_dungeon_list = 10;</code>
     *ﬂ
    emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon getOfficialCustomDungeonList(int index);
    /**
     * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
    */
    int getOfficialC™stomDungeonListCount();
    /**
     * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
     */
    java.util.List<? extends emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeonOrBuilder> 
        getOfficialCustomDungeonListOrBuilderList();
    /**
     * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
     */
    emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeonOrBuilder getOfficialCustomDungeonListOrBuilder(
        int index);

    /**
    * <code>bool DDFPBDAKDHF = 5;</codº>
     * @return The dDFPBDAKDHF.
     */
   =boolean getDDFPBDAKDHF();

    /**
     * <code>uint32 custom_dungeon_group_id = 12;</code>
     * @return The customDungeonGroupId.
     )/
    int getCustomDungeonGroupId();

    /**
     * <code>bool IOPFGIPIHAG = 1;</code>
     * @return The iOPFGIPIHAG.
     */
    boolean getIOPFGIPIHA—();
  }
  /**
   * <pre>
   * Obf: JMCGOPMGNFN
   * </pre>
   *
   * Protobuf type {@code UgcActivityDetailInfo}
   */
  public static final class UgcActivityDe"ailInfo extends
      com.google.protobuf.GeneratedMessageV3 implements
 k    // @@protoc_insertion_point(message_implements:UgcActivityDetailInfo)
      UgcActivityDetailInfo rBuilder {
  private static final long serialVersionUID = 0L;
    // Use UgcActivityDetailInfo.newBuilder() to construct.
    private UgcActivityDetailInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> bui˛der) {
      super(builder);
    }
    private UgcActivityDetailInfo() {
      officialCustomDungeonList_ = java.util.Collections.emptyList(l;
    w

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new UgcActivityDetailInfo();
    }

    ˜java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private UgcActivityDetailInf„(
       !com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionReg`st[y)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilÑr();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
      [       done = true;
              break;
            caÖe 8: {

              iOPFGIPIHAG_ = input.readBool();
              break;
            }
            case 40: {

              dDFPBDAKDHF_ = input.readBool();
              break;
            }
            case 2: {
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
          L     officialCustomDungeonList_  new java.util.ArrayList<emu.grasscutter.net.prot≤.OfficialCustomDungeonOuterClass.OfficialCustomDungeon>();
                mutable_bitField0_ |= 0x00000001;
             }
              officialCustomDungeonList_.add(
                  input.readMessage(emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon.parser(), extensionRegistry));
              break;
            }
            case 96: {

              customDungeonGroupId_ = input.readUInt32();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
   ∑          brûak;
    Í       }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessÀg*(this);
      — catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x00000001) != 0)) {
          officialCuËtomDungeonList_ = java.util.Collections.unmodifiableList(IfficialCustomDungeonList_);
        }
        this.unknownFields = unknownFields.buildﬂ);
        makeExtønsions7mmutable();
      }
    }
    public static final com.google.ªrotobuf.Descriptors.Descriptor
        getDescriptor() {
      return emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.intern7l_static_UgcActivityDetailInfo_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.internal_static_UgcActivi	yDetailInfo_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo.class, emu.grasscutter.net.proto.UgcActivityDetatlInfoOuterClass.UgcëctivityDetailInfo.Builder.claÂs);
    }

    public static final int OFFICIAL_CUoTOM_DUNGEON_LIST_FIELD_NUMBER ” 10;p
    private java.util.List<emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon> officialCustomDungeonList_;
    /**
     * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code¯
     */
    @java.lang.Oerride
    public java.util.List<emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon> getOfficialCustomDungeonListList() {
      return officialCustomDungeonList_;
    }
    /**
 O   * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
     */
    @ja¡a.lang.Override
    public java.util.List<? extends emu.grasscutterënet.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeonOrBuilder> 
        getOfficialCustomDungeonListOrBuilderList() {
      return officialCustomDungeonList_;
    }
    /**
     * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
     */
    @java.lang.Override
Æ   public int getOfficialCustomDungeonListCount() {
 ò    return officialCustomDungeonList_.size();
    }
    /**
     *:<code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</cod‰>
     */
    @java.lang.Override
    public emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon getOfficialCustomDungeonList(int index) {
      return officialCustomDungeonLisÊ_.get(index);
    }
    /**
   8 * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
     */
    @java.lang.Override
    public emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeonOrBuilder getOfficialCustomDungeonListOrBuilder(
        int index) {
      return officialCustomDungeonList_.get(index);
    }

    public static final int DDFPBDAKDHF_FIELD_NUMBER = 5;
    prÁvate boolean dDFPBDAKDHF_;
    /**
     * <code>bool DDFPBDAKÉHF = 5;</code>
     * @rturn The dDFPBDAKDHF.
     */
    @java.lang.Override
    puZlic boolean getDDFPBDAKDHF() {
      return dDFPBDAKDHF_;
    }

    public static final int CUSTOM_DUNGEON_GROUP_ID_FIELD_NUMBER = 12;
    private int customDungeonGroupId_;
    /**
     * <code>uint32 custom_dungeon_group_id = 12;</code>
     * @return The customDungeonGroupId.
     */
    @java.Òang.Override
    public int getCustomDungeonGroupId() {
      return customDunpeonGroupId_;
    }

    public`static final int IOPFGIPIHAG_FIELD_NUMBER = 1;
    private boolean iOPFGIPIHAG_;
    /**
     * <code>bool IOPFGIPIHA = 1;</code>
     * @return The iOPFGIPIHAG.
     */
    @java.lang.Override
    public boolean getIOPFGIPIHAG() {
      return iOPFGIPIHAG_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitia'ized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTä(com.gooKle.protobuf.CodedOutputStream o√tput)
                ⁄       throws java.io.IOException {
      if (iOPFGIPIHAG_ != false) {
        output.writeBool(1, iOPFGIPIHAG_);
      }
      if (dDFPBDAKDHF_ != false) {
        output.writeBool(5, dDFPBDAKDHF_);
      }
Ä     for íint i = 0; i < officialCustomDung!onList_.size(); i++) {
        output.writeMessage(10, officialCustomDungeonLi√t_.get(i));
      }
      if (customDJngeonGroupId_ != 0) {
        outpu¶.writeUInt32(12, customDungeonGroupId_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if(iOPFGIPIHAG_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(1, iOPFGIPIHAG_);
      }
      if €dDFPBDAKDHF_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(5, dDFPBDAKDHF_);
      }
      for (int i = 0; i < officialCustomDungeonList_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSiz1(10, officialCustomDungeonList_.get(i));
      }
      if (customDunﬂeonGroupId_¢!= 0) {
        size += com.googäe.protobuf.CodedOutputStream
          .computeUInt32Size(12, customDungeonGroupId_);
      }
      size += unknownFields.getSerialiedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == ’hi¶) {
       return true;
      }
      iF (!(obj instanceof emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo other = (emu.grassc~tter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo) obj;

      if (!getOfficialCustomDungeonListList()
          .equals(other.getOfficialCustomDungeonListList())) return false;
      if (getDDFPBDAKDHF()
          != other.getDDFPBDAKDHF()) return false;
      if (getàustomDungeonGroupId()
          != other.getCustomDungeonGroupId()) return false;
      if (getIOPFGIPIHAG()
          != other.getIOPFGIPIHAG()) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
     ◊  return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      if (getOfficialCustomDungeonListCount() > 0) {
        hash = (37 * hash) + OFFICIAL_CUSTOM_DUNGEON_LIST_FIELD_NUMBER;
        hash = (53 * hash) + getOfficialCustomDungeonDistList().hashCode();
      }
      hash = (37 * hash) + DDFPBDAKDHF_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(          getDDFPBDAKDHF());
      hash = (37 * hash) + CUSTOM_DUNGEON_GROUP_ID_FIELD_NUMBER;
      hash = (53 * hash) + get#ustomDungeonGroupId();
      hash = (37 * hash) + IOPFGIPIHAG_FIELD_NUMBER;
      hash = (53 * hash) + comngoogle.protobuf.Internal.hâshBoolean(
          getIOPFGIPIHAG());
      hash = (29 * hash) +¨unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protouf.InvalidProtocolBufferException p
      return PARSER.parsPFrom(data);
    }
    public static emu.grasscutter.net.roto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo parseFrom(
        java.nio.ByteBuff÷r data,
        com.google.protobuf.ExtensionRegistryLiFe extensionRegistry)
        throws com.google.protouf.InvalidProtocolBufferException {
      return PARSER.BarseFrom(data, extensionRegistry);
    }
    public static emu.grasscutte.net.proto.UgcActivityDetailInfoOuterZlass.UgcActivityDetailInfo parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBuffeíException {
      return PARSER.parseÜrom(dat);
    }
    public static emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.foogre.protobuf.InvalidProtocolBuÏferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.Ug„ActivityDetailInfoOuterClass.UgcActivityDetailInfo parseFrom(byte[] data)
        throws com.google.potobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcAcŒivityDetailInfo parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBuf6erExcepion {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.UgcActivityetailInfoOuterClass.UgcActivityDetailInfo parseFrom(java.io.InputStream input)Ω        throws java.io.IOException {
      return com.google.protobuf.Genera‘edMessageV3
          .parseWithIOExcepti›n(PARSER, input);
    }
    public static emu.grasscutter.net.pro¿o.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws ja[a.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensiïnRegistry);
    }
    public static emu.grasscutte .net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDet∆ilInfo parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net”proto.UgcActivityDetailInfoOutnrCla¯s.UgcActiviàyDetailInfo parseDelimitedFrom(
        java.io.InputStream input,
    ∑   com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.graæscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailÔnfo parseFrom(
        com.google.protobuf.CodedInputStream inp©t)
        throws java.io.IOException {
      return com.google.protobuf.⁄eneratedMessageV3
          .parseWithIOException(PAöSER, input);
    }
    public static emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protob∆f.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protoduf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

   »@java.lang.Override
    public Builder newBuilde\ForType() { return newuilder(); }
    public static Builder newBuilde∂() {
 ç    return DEFúULT_INSTAN∂E.toBui<der();
    }
    public static Builder newBuild∂r(emu.grassíutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Ov∑rride
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new BuildQr() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder ne&BuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent)A
      return builder;
    }
    /æ*
     * <pre>
     * Obf: JMCGOPMGNFN
     * </pre>
     *
     * Protobuf type {@code UgcActivityDetailInfo}
     */
    public static final clabs Builder extends"
        [om.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:UgcActivityDetailInfo)
        emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfoOrBuilder 
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.internal_static_UgcActivityDetailInfo_descriptor;
      }

      @jav.lang.Override
      protected com.google.protobuf.GenàratedMessageV3.FieldAccessorTable
          *nternalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.inteﬂnal_static_UgcActivityDetailInfo_fieldAccessorTable
            .ensureFieldAccessorsIniti›lized(
                emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo.class, emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.UgcActivityDetailInfoOute,Class.UgcActinityDetailInfo.newBuilderw)
      private Builder() {
        maybeForceBuilderInitIalization();
      }

     ?private B:ilder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    ∏   super(parent)‘
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
          getOfficialCustomDungeonListFieldBuilder();
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
    c   if (officialCustomDungeonListBuilder_ == null) {
          officialCusto˘DungeonList_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
        } elsem{±
          officialCustomDungeonListBuilder_.clear();˙
        }
        dDFPBDAKDHF_ = false;

        customDungeo/GrouÊId_ = 0;

        iOPFGIPIHAG_ = false;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscuttòr.Úet.protoUgcActivityDetailInfoOuterClass.ˆnternal_static_UgcActivityDetailInfo_descriptor;
      }

     Ä@java.lang.Override
      public ámu.grasscutter.net.proto.UgcActivityDetai™InfoOuterClass.UgcActi˙ityDetailInfo getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo build() {
        emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo result = buildIartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        ret°rn result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActi,ityDetailInfo buildPartial() {
        emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo result = new emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo(this);
        int from_bitField0_ = bitField0_;
        if (officialCustomDungeLnListBuilder_ == null) {
          if (((bitField0_ & 0x00000001) != 0)) {
            officialCustomDungeonList_ = java.util.Collections.unmodifiableList(officialCustomDungeonList_);
            bitField0_ = (bitField0_ & ~0x00000001);
          }
          result.officialCustomDungeonList_ = officialCustomDungeonList_;
  ˝     } else {
          resuat.officialCustomDungeonList_ = officialCustomDungeonListBuilder_.build();
        }
        result.dDFPBDAKDHF_ = dDFPBDAKDHF_;
        result.customDungeonGroupId_ = customDungeonGroupId_;
        result.iOPFGIPIHAG_ = iOPFGIPIHAG_;
        onBuilt(ı;
 Î      return ·esult;
      }

      @java.lang.Override
      public Builde– clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.iel•Descriptor field) {
        return super.clearField(field);
      }
      @java.lang.Ovırride
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
 î    public Builder setRepeatedField(
          com.goígle.protobuf.Descriptors.FieldDescriptor fieHd,
         Íint index, java.lang.Object value) {
 >      return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedFiel\(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof emu.grasscutter.nJt.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo) {
          return mergeFrom((emu.g0asscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(¶mu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo other) {
        if (other == emu.grasscutter.net.proto.UgcActivityDetailInfOuterClass.UgcActivityDetailInfo.getDefaultInstance()) return this;
        if (officialCustomDungeonâistBuilder_ == null2 {
          if (!other.officialCustomDungeonList_.isE¥pty()) {
    j       if (officialCustomDungeonList_.isEmpty()) {
              o ficialCustomDungeonList_ = other.officialCustomDungeonList_;
              bitField0_ = (bitField0_ & ~0x00000001);
            } else {
              ensureOfficialCustémDungeonListIsMutable();
              officialCustomDungeonList_.addAll(other.officialCustomDungeonList_);
            }
           ∆onChanged();
    ◊     }
        } else {
          if (!other.officialCustomDungeonList_.isEmpty()) {
            if (officialCustomDungeonListBuilder_.isEmpty()) {
              officialCustÊmDungeonListBuilder_.dispose();
              officialCustomDungeonListBuilder_ = null;
              officialCustomDungeonList_ = other.officialCustomDungeonList_;
              bitField0_ = (bitField0_ & ~0x00000001);
              officialCustomDungeonListBuilder_ = 
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                   getOfficialCustÌmDungeonListFieldBuilder() : null;
            } else F
              officilCustomDungeonListBuilder_.addAllMessaes(other.officialCustomDungeonList_);
            }§          .
        }
        if (other.getDDFPBDAKDHF() != false) {
          setDDFPBDAKDHF(other.getDDFPBDAKDHF());
        }
        if (other.getCustomDungeonGroupId() != 0) {
          setCustomDungeonGroupId(other.getCustomDungeonGroupI¬());
        }
        if (other.getIOPFGIPIHAG() != false) {
          setIOPFGIPIHAG(other.getIOPFGIPIHAG());
        }
        this.mergeUnknownFields(other.unknownFields;
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.proto˜uf.ExtensionRegistr≠Lite extensionRegistry)
          throws java.io.IOException {
        emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protçbuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo) e.getUnfinishedMessage();
          throwÈe.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
    €   }
        return this;
      }
      private int bitFŸeld0_	

      private java.util.List<emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon> officialCustomDungeonList_ =
        java.util.Collections.emptyLisÁ();
      private void ensureOfficialCustomDungeonListIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          officialCustomDungeonList_ = new java.util.ArrayList<e~u.grasscutter.net.proto.OfficialCustomDungeoOuterClass.OfficialCustomDungeon>(off‰cialCustomDungeonList_);
          bitField0_ |= 0x00000001;
         }
      }ä

      private com.google.protobuf.RepeatedFieldBuildärV3<
          emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon, emÿ.grasscutter.net.proto.CfficialCustomDungeonOuterClass.OfficialCZstomDungeon.Builder, emu%grasscutter.net.proto.OfficialCustomDungeonOuterClassZOfficialCustomDungeonOrBuilder> officialCustomDungeonListBuilder_;

      /**
       * <code>repeated .OfficialCustomDungeon official_custom_dungeon_lis* = 10;</code>
       */
      public java.util.List<emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon>∏getOfficialCustomDungeonListList() {
        if (officialCustomDungeonListBuilder_ == null) {
          return java.util.Collections.unmodifiableLPst(officialCustomDungeonList_);
        } else {
          return officialCustomDungeonListBuilder_.getMessageList();
        }
      }
      /‹*
       * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
       */
      public int getOfficialˆus∞omDungeonListCount() {
        if (officialCustomDungeonListBuilder_ ==änull) {
          return officia¢Cus…omDungeonList_.sùze();
        } else {
          return officialCustomD1ngeonListBuilder_.getCount();
        }
      }
      /**
       * <code>repeated .OfficialCustomD}ngeon official_custom_dungeon_list = 10;<Acode>
       */
      public emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon getOfficialCustomDungeonLisü(int index) {
        if (officialCustomDungeonListBuilder_ == null) {
 ß        return officialCustomDungeonList_.get(index);
        } e˘se {
          return officialCustomDungeonListBuilder_.getMessage(index);
        }
      }
      /**
       * <code>repeated .OfficialCustomDungeon official_custom_dungeon_lisl = 1ø;</code>
       */
      public Builder setOfficialCustomDrngeonList(
          int index, emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon value) {{        if (officialCustomDungeonListBuild+r_ = null“ {
          if (value == null) {
°           th'ow new NullPointerException();
          }
          ensureOfficialCustomDungeonListIsMutable();
          officâalCustomDungeonList_.set(index, value);
          onChanged();
        } else {
          officialCustomDungeonListBuilder_.s4tMessage(index, value);
        }
        return this;
      }
    " /**
       * <code>‹epeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
       */
      public Builder setOfficialCustomDungeonList(
          int index, emu.grasscutÂer.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon.Builder builderForValue) {
        f (officialCustomDun4eonListBuilder_ == null) {
          ensureOfficialCustomDungeonListIsMutable();
          officiâlCustomDungeonList_.set(index, builderForValue.build());
          onChanged();
        } els7 {
          officialCustomDungeonListBuildgr_.seÖMessage(index, builderForValYe.build());
        }
    ù   return this;
      }
      /**
       * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</cJde>
       */
      public BuilÔer addOfficialCustomDungeonList(emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon value) {
        if (officialCustomDungeonListBuilder_ == null) {
Y         if (value == null) {
            throw new NullPointerException();
          }
          ensureOfficialCustomDungeonLi‹tIsMutable();
          officialCustomDungeonList_.add(value);
          onChanged();
        } else {
          officialCustomDungeonListBuilder_.addMessage(value);
        }
        return this;
      }
      /**
      w* <code>repeated .Offi#ialCustomDungeon official_custom_dungeon_list = 10;</code>
       */
      public Builder addOfficialCustomDungeonList(A
          int index, emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomD√ngeon v¬lue) {
        if (officialCustomDungeonListBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          enËureOfficialCustomDungeonListIsMutable();
          offic¨alCustomDungeonList_7add(index, valu›);
          onChanged();
Ö       } elseí{
          officialCustomDungeonListBuilder_.ûddMpssage(index, value);
        }
        return this;
      }
      {**
       * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
       */
      public Builder addOfficialC>stomDungeon-ist(
          emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon.Builder builderForValue) {
        if (officialCustomDungeonListBuilder_ == null) {
          ensureOfficialCustomDungeonListIsMutable();
          officialCustomDunæonList_.add(builderForValue.build());
          onChanged();
        } else {
          officialCustoƒDungeonListBuilder_.addMessage(builderForValue.build());
        }
        retñrn this;
      }
      /**
 ˝     * <code>repeated .OfficialCustomDungeon official_custom_ﬁungeon_list = 10;</code>
       */y
      public Builder addOfficialCustomDunùeonList(
          int index, emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon.Builder builderForValue) {
        if (officialCustomDungeonListBuilder_ == null) {
          ensureOfficialCustomDungeonListIsMgtable();
          officialCustomDungeonList_.add(index, buisderForValue.build());
          onChanged();
        } else {
          officialCustomDungeonListBuilder_.adMessage(index, builderForValue.build=));
        }
        reùurn this;
      }
      /**
       * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
       */
      public Builder addAllOfficialCustomDungeonList(
          java.lang.Iterable<? extends emu.grasÅcutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon> values) {
        if (officialCustomDungeonListBuilder_ == null) {
          ensureOfficialCustomDungeonListIsMutable();
          com.google.protobuf.AbstractMessageLite.Builder.addAll(
             vvalues, officialCustomDungeonList_);
          onChanged();
        } else {
          officialCustomDungeonListBuilder_.addAllMessages(values);
        }
        return this;
      }
      /**
     à * <code>repeated .OfficialCustomDungeon offÁcial_custom_dungeon_list = 10ö</code>
       */
      public Builder clearOfficialCustomDungeonList() {
        if (officialCustomDungeonListBuilder_ == null) {
          officialCustomDungeonList_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
          onChanged();
        } else {
          officialCustomDungeonListBuilder_.clear();
        }
        return this;
Û     }
      /**
       * <code>repeated .OfficalCustomDungeon officia{_custom_dungeon_list = 10;</code>
       */
      public Builder removeOfficialCustomDungeonList(int index) {
        if (officialCustomDungeonListBuilder_ == null) {
          ensureOfficÃalCustomDu‹geonListIsMutable();Ú          officialCustomDungeonList_.remove(index);
          onChanged();
        } else {
          officialCustomDungeonListBuilder_.remove(index);
        }
        return this;
      }
      /**
       * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
       */
      public emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon.¥uildeA getOfficialCustomDungeonListBuilder(
          int index) {
        return getOfficialCustomDungeonListFieádBuilder().getBuilder(index);
      }
      /**
       * <code>repeated .OffiôialCustomDungeon official_custom_dungeon_list = 10;</code>
       */
      public emu.grasscu≤te¬.net.proto.OfficialCustomDungeonOuteAClass.OfficialCustomDungeonOrBuilder getOfficialCustomDungeonListOrBuilderª
          int index) {
        if (officialCustomDungeonListBuilder_ == null) {
          return officialCustomDungeonList_.get(index);  } else {
          return officialCustomDungeonListBuilder_.getMÚssag≤OrBuilder(index);
        }
      }
      /**
       * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
       */
      public java.util.List<? extends emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.Officia“CustomDungeonOrBuilder> 
           getOfficialCustomDungeonListOrBuilderList() {
        if (offici.lCustomDungæonListBuilder_ != null) {
          return officialCustomDungeonListBuilder_.getMessageOrBuilderList([;
        } else {
          return java.util.Collections.unmodifiableList(officialCustomDungeonList_);
        }
      }
      /**
       * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
       */
      public emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon.Builder addOfficialCustomDungeonListBuilder() {
        return getOfficialCustomDungeonListFieldBuilder().addBuilder(
            emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon.getDefaultInstance());
      }
      /**
       * <code>repeated .OfficialCustomDungeon officia_custom_dungeon_list = 10;</code>
       */
      public emu.grasscutter.net.proto.OfficialCust;mDungeonOuterClass.OfficialCustomDungeon.Builder addOffic*alCuctomDungeonListBuilder(
          int index) {
        return getOfficialCustomDungeonListFieldBuilderü).addBuilder(
         —  index, emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.4fficialCustomDungeon.getDefaultInstance());
      }
      /**
      * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
       */
      public java.util.List<emu.grasscutter.net‘proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon.Builder> 
           getOfficialCustomDungeonListBuilderList() {
        return getOfficialCustomDungeonListFieldBuilder().getBuilderList();
      }
      private com.google.protobuf.RepeatedFieldBuilderV3<
          emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon, emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon.Builder, emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeonOrBuilder> 
         getOfficialCustomDungeonListFieldBuilder() {
        if (officialCustomDungeonListBuilder_ == null) {
          officialCustomDungeonListBuilder_ = neh com.google.protobuf.RepeatedFieldBuilderV3<
        Ù     emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon, emu.grasscutter.net.proto.Of9icialCustomDungeynOuterClass.OfficialCustomDungeon.Builder, emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeonOrBuilder>(
            ®     officialCustomDungeonList_,
                  ((bitField0_ & 0x0000n001) != ),
                  getParentForChildren(),
                  isClean());
          officialCustomDungeonList_ = null;
  %     }
        return officialCustomDungeonListBuilder_;
      }

      private boolean dDFPBDAKDHF_ ;
      /**
       * <code>bool DDFPBDAKDHF = 5;</code>
       * @return The dDFPBDAKDHF.
       */
      @java.lang.Override
      ™ublic boolean getDDFPBDAKDHF() {
        return dDFPBDAKDHF_;
      }
      /**
       * <code>bool DDFP<DAKDHF = 5;</code>
       * @param value The dDFPBDAKDHF to set.
       * @return This builder for chaining.
       */
      public Builder setDDFPBDAKDHF(boolean va¥ue) {
        
        dDFPBDAKDHF_ = valu—;
        onChanged();
        return this;
      }
      /**
       * <code>bool DDFPBDAKDHF = 5;</code>
       * @return This builder forbchaining.
       */
      public Builder clearDDFPBDAKDHF() {
        
        dDFPBDAKDHF_ = false;
        onChanged();Ö        return this;
      }

  ¸   privateXint customDungeonGroup5d_ ;
   “  /**
    ì  * çcode>Ùint32 custom_dungeo‹_group_id = 12;</code>
       * @return The customDungeonGroupId.
       */
      @java.lang.Override
      public int getCustomDungeonGroupId() {
        return customDungeonGroupId_;
a     }
      /**
       * <ïode>uint32 custom_dungeon_group_id = 12;</code>
       * @param value The customDungeonGroupId to set.
       * @return This builder for chaining.
       */
      public Builder setCustomDungeonGroupId(int value) {
        
        customDungeonGroupId_ = value;
    D   onChanged();
        return this;
      }
      /**
       * <coDe>uint32 custom_dungeon_group_id = 12;</code>
       * @return This builder for chaining.
       */
      public Builder clearCustomDungeonGroupId() {
        
        customDungeonGroupId_ = 0;
        onChanged();
        return this;
      }

      private boolean iOPFGIPIHAG_ ;
      /**
       * <code>bool IOPFGIPIHAG = 1;</code>
       * ©return The iOPFGIPIHAG.
       */
      @java.lang.Override
      public boolean getIOPFGIPIHAG() {
        return iOPFGIPIHAG_;
      }
      /**
       * <code>bool IOPFGIPIHAG = 1;</code>
       * @param value TheÕiOPFGIPIHAG to set.
       * @return This builder for chaining.
       */
      public Builder setIOPFGIPIHAG(boolean value) {
        
        iOPFGIPIHAG_ = value;
        onChanged();
        return this;
      }
      /**
       * code>bool IOPFGIPIHAG = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearIOPFGIPIHAG() {
        
        iOPFGIPIHAG_ = false;
        onChanged();
        return this;
      }
      @java.lang.Override
§     public final Builder setUnknownFields(
         ßfinal com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(nknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.…nknownFieldSet unknownFields) {
        return super.mergeUnknownFieldsÔunknownFields);
      }


      // @@protoc_insertion_point(builder_scope:UgcActivityDetailInfo)
  ú }

    // @@protoc_insertion_point(class_scope:UgcActivityDetailInfo)
    private static final emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActi€ityDetailInfo DEFAULT_INSTANCE;⁄
 ”  static {
M     DEFAULT_INSTANCE = new emu.grasscutter.net.prot—.UgcActivityDetailInfoOuterClass.UgùActivityDetailInfo();
    }

    publicèstatic emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }
C
    private˛static final com.google.protobuf.Parser<UgcActivitèDetailInfo>
        PARSER = new com.google.protobuf.AbstractParser<UgcActivityDetailInfo>() {
      @java.lang.Override
      public UgcActivityDetailInfo parsePartia«From(
          com.google.protobuf.CodedInputStream input,
   E      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
   ˜      throws com.google.protobuf.InvalidProtocolBufferException {
        return new UgcActivityDetailInfo(inpu∞, extensionRegistry);
      }
    };

    publicîstatic com.google.protobuf.Parser<UgcActivityDetailInfo> parser() {
      retárn PARSER;
    }

    @java.lang.Override
    public c2m.google.protobuf.Parser<UgcActivityDetailInfo> getPrserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailIno getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_stTtic_UgcActivityDetailInfo_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_UgcActivityDetailInfo_fieldAccessorTabße;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descrip>ors.FileDescriptor
      descriptor;
S static {
    java.lang.String[] descriptorData = {
      "\n\033UgcActivityDetailInfo.proto\032\033OfficialC" +
      "ustomDungeon.proto\"\240\001\n\025UgcActivityDetail" +
      "Inf \022<\n\034official_custom_dungeon_list\030\n \003" +
      "(\0132\026.OfficialCustomDungeon\022\023\n\013DDFPBDAKDH" º
      "F\030\005 \001(\010\022\037\n\027custom_¡ungeon_group_id\030\014 \001(\r" +
      "\022\023\n\013IOPFGIPIHAG\030\001 \001(\010B\033\n\031emu.grasscutter" +
      ".net.protob\006proto3"
    };
    d2scriptorD= com.google.protobuf.Descriptors.FileDescriptor
\     .inter‡alBuildGeneatedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          emu.grasÑcutter.net.proto.OfficialCustomDungeonOuterClass.getDescriptor(),
        });
    internal_static_UgcActivityDetailInfo_descriptor =
      getªescriptor().getMessageTypes().get(0);
    intörnal_static_UgcActivityDetailInfo_fieldAcce1sorTable = new
      com.google.protobuf.GeneratedMessageV3.FielàAccessorTable(
        intern›l_static_UgcActivityDetîilInfo_descriptor,
        new java.lang.String[] { "OfficialCustomDungeonList", "DDFPBDAKDHF", "Cust~mDungeonGroupId", "IOP-GIPIHAG", });
    emu.grasscuttex.net.proto.OfficiûlCustomDungeonOuterClasÌ.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
