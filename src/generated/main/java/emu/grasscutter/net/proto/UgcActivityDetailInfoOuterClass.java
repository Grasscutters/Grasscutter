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
    �egisterAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface UgcActivityDetailInfoOrBuilder extends
      // @@protoc_insertion_point(interface_extends:UgcActivityDetailInfo)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
     */
    java.util.List�emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCuctomDungeon> 
        getOfficialCustomDungeonListList();
    �**
     * <code>repeated .Officia�CustomDungeon official_custom_dungeon_list = 10;</code>
     *�
    emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon getOfficialCustomDungeonList(int index);
    /**
     * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
   � */
    int getOfficialC�stomDungeonListCount();
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
    * <code>bool DDFPBDAKDHF = 5;</cod�>
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
    boolean getIOPFGIPIHA�();
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
      UgcActivityDetailInfo�rBuilder {
  private static final long serialVersionUID = 0L;
    // Use UgcActivityDetailInfo.newBuilder() to construct.
    private UgcActivityDetailInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> bui�der) {
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

    �java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private UgcActivityDetailInf�(
       !com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionReg`st[y)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuil�r();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
      [       done = true;
              break;
            ca�e 8: {

              iOPFGIPIHAG_ = input.readBool();
              break;
            }
            case 40: {

              dDFPBDAKDHF_ = input.readBool();
              break;
            }
            case 2: {
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
          L     officialCustomDungeonList_ � new java.util.ArrayList<emu.grasscutter.net.prot�.OfficialCustomDungeonOuterClass.OfficialCustomDungeon>();
                mutable_bitField0_ |= 0x00000001;
       �      }
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
   �          br�ak;
    �       }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMess�g*(this);
      � catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x00000001) != 0)) {
          officialCu�tomDungeonList_ = java.util.Collections.unmodifiableList(IfficialCustomDungeonList_);
        }
        this.unknownFields = unknownFields.build�);
        makeExt�nsions7mmutable();
      }
    }
    public static final com.google.�rotobuf.Descriptors.Descriptor
        getDescriptor() {
      return emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.intern7l_static_UgcActivityDetailInfo_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.internal_static_UgcActivi	yDetailInfo_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo.class, emu.grasscutter.net.proto.UgcActivityDetatlInfoOuterClass.Ugc�ctivityDetailInfo.Builder.cla�s);
    }

    public static final int OFFICIAL_CUoTOM_DUNGEON_LIST_FIELD_NUMBER � 10;p
    private java.util.List<emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon> officialCustomDungeonList_;
    /**
     * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code�
     */
    @java.lang.Oerride
    public java.util.List<emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon> getOfficialCustomDungeonListList() {
      return officialCustomDungeonList_;
    }
    /**
 O   * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
     */
    @ja�a.lang.Override
    public java.util.List<? extends emu.grasscutter�net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeonOrBuilder> 
        getOfficialCustomDungeonListOrBuilderList() {
      return officialCustomDungeonList_;
    }
    /**
     * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
     */
    @java.lang.Override
�   public int getOfficialCustomDungeonListCount() {
 �    return officialCustomDungeonList_.size();
    }
    /**
     *:<code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</cod�>
     */
    @java.lang.Override
    public emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon getOfficialCustomDungeonList(int index) {
      return officialCustomDungeonLis�_.get(index);
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
    pr�vate boolean dDFPBDAKDHF_;
    /**
     * <code>bool DDFPBDAK�HF = 5;</code>
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
    @java.�ang.Override
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
    public void writeT�(com.gooKle.protobuf.CodedOutputStream o�tput)
                �       throws java.io.IOException {
      if (iOPFGIPIHAG_ != false) {
        output.writeBool(1, iOPFGIPIHAG_);
      }
      if (dDFPBDAKDHF_ != false) {
        output.writeBool(5, dDFPBDAKDHF_);
      }
�     for �int i = 0; i < officialCustomDung!onList_.size(); i++) {
        output.writeMessage(10, officialCustomDungeonLi�t_.get(i));
      }
      if (customDJngeonGroupId_ != 0) {
        outpu�.writeUInt32(12, customDungeonGroupId_);
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
      if �dDFPBDAKDHF_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(5, dDFPBDAKDHF_);
      }
      for (int i = 0; i < officialCustomDungeonList_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSiz1(10, officialCustomDungeonList_.get(i));
      }
      if (customDun�eonGroupId_�!= 0) {
        size += com.goog�e.protobuf.CodedOutputStream
          .computeUInt32Size(12, customDungeonGroupId_);
      }
      size += unknownFields.getSerialiedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == �hi�) {
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
      if (get�ustomDungeonGroupId()
          != other.getCustomDungeonGroupId()) return false;
      if (getIOPFGIPIHAG()
          != other.getIOPFGIPIHAG()) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
     �  return memoizedHashCode;
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
      hash = (53 * hash) + comngoogle.protobuf.Internal.h�shBoolean(
          getIOPFGIPIHAG());
      hash = (29 * hash) +�unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protouf.InvalidProtocolBufferException p
      return PARSER.parsPFrom(data);
    }
    public static emu.grasscutter.net.roto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo parseFrom(
        java.nio.ByteBuff�r data,
        com.google.protobuf.ExtensionRegistryLiFe extensionRegistry)
        throws com.google.protouf.InvalidProtocolBufferException {
      return PARSER.BarseFrom(data, extensionRegistry);
    }
    public static emu.grasscutte.net.proto.UgcActivityDetailInfoOuterZlass.UgcActivityDetailInfo parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBuffe�Exception {
      return PARSER.parse�rom(dat);
    }
    public static emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.foogre.protobuf.InvalidProtocolBu�ferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.Ug�ActivityDetailInfoOuterClass.UgcActivityDetailInfo parseFrom(byte[] data)
        throws com.google.potobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcAc�ivityDetailInfo parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBuf6erExcepion {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.UgcActivityetailInfoOuterClass.UgcActivityDetailInfo parseFrom(java.io.InputStream input)�        throws java.io.IOException {
      return com.google.protobuf.Genera�edMessageV3
          .parseWithIOExcepti�n(PARSER, input);
    }
    public static emu.grasscutter.net.pro�o.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws ja[a.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensi�nRegistry);
    }
    public static emu.grasscutte .net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDet�ilInfo parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net�proto.UgcActivityDetailInfoOutnrCla�s.UgcActivi�yDetailInfo parseDelimitedFrom(
        java.io.InputStream input,
    �   com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.gra�scutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetail�nfo parseFrom(
        com.google.protobuf.CodedInputStream inp�t)
        throws java.io.IOException {
      return com.google.protobuf.�eneratedMessageV3
          .parseWithIOException(PA�SER, input);
    }
    public static emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protob�f.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protoduf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

   �@java.lang.Override
    public Builder newBuilde\ForType() { return newuilder(); }
    public static Builder newBuilde�() {
 �    return DEF�ULT_INSTAN�E.toBui<der();
    }
    public static Builder newBuild�r(emu.grass�utter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Ov�rride
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
    /�*
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
      protected com.google.protobuf.Gen�ratedMessageV3.FieldAccessorTable
          *nternalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.inte�nal_static_UgcActivityDetailInfo_fieldAccessorTable
            .ensureFieldAccessorsIniti�lized(
                emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo.class, emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.UgcActivityDetailInfoOute,Class.UgcActinityDetailInfo.newBuilderw)
      private Builder() {
        maybeForceBuilderInitIalization();
      }

     ?private B:ilder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    �   super(parent)�
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
          officialCusto�DungeonList_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
        } elsem{�
          officialCustomDungeonListBuilder_.clear();�
        }
        dDFPBDAKDHF_ = false;

        customDungeo/Grou�Id_ = 0;

        iOPFGIPIHAG_ = false;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutt�r.�et.protoUgcActivityDetailInfoOuterClass.�nternal_static_UgcActivityDetailInfo_descriptor;
      }

     �@java.lang.Override
      public �mu.grasscutter.net.proto.UgcActivityDetai�InfoOuterClass.UgcActi�ityDetailInfo getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo build() {
        emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo result = buildIartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        ret�rn result;
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
  �     } else {
          resuat.officialCustomDungeonList_ = officialCustomDungeonListBuilder_.build();
        }
        result.dDFPBDAKDHF_ = dDFPBDAKDHF_;
        result.customDungeonGroupId_ = customDungeonGroupId_;
        result.iOPFGIPIHAG_ = iOPFGIPIHAG_;
        onBuilt(�;
 �      return �esult;
      }

      @java.lang.Override
      public Builde� clone() {
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
          com.google.protobuf.Descriptors.�iel�Descriptor field) {
        return super.clearField(field);
      }
      @java.lang.Ov�rride
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
 �    public Builder setRepeatedField(
          com.go�gle.protobuf.Descriptors.FieldDescriptor fieHd,
         �int index, java.lang.Object value) {
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

      public Builder mergeFrom(�mu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo other) {
        if (other == emu.grasscutter.net.proto.UgcActivityDetailInfOuterClass.UgcActivityDetailInfo.getDefaultInstance()) return this;
        if (officialCustomDungeon�istBuilder_ == null2 {
          if (!other.officialCustomDungeonList_.isE�pty()) {
    j       if (officialCustomDungeonList_.isEmpty()) {
              o ficialCustomDungeonList_ = other.officialCustomDungeonList_;
              bitField0_ = (bitField0_ & ~0x00000001);
            } else {
              ensureOfficialCust�mDungeonListIsMutable();
              officialCustomDungeonList_.addAll(other.officialCustomDungeonList_);
            }
           �onChanged();
    �     }
        } else {
          if (!other.officialCustomDungeonList_.isEmpty()) {
            if (officialCustomDungeonListBuilder_.isEmpty()) {
              officialCust�mDungeonListBuilder_.dispose();
              officialCustomDungeonListBuilder_ = null;
              officialCustomDungeonList_ = other.officialCustomDungeonList_;
              bitField0_ = (bitField0_ & ~0x00000001);
              officialCustomDungeonListBuilder_ = 
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                   getOfficialCust�mDungeonListFieldBuilder() : null;
            } else F
              officilCustomDungeonListBuilder_.addAllMessaes(other.officialCustomDungeonList_);
            }�          .
        }
        if (other.getDDFPBDAKDHF() != false) {
          setDDFPBDAKDHF(other.getDDFPBDAKDHF());
        }
        if (other.getCustomDungeonGroupId() != 0) {
          setCustomDungeonGroupId(other.getCustomDungeonGroupI�());
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
          com.google.proto�uf.ExtensionRegistr�Lite extensionRegistry)
          throws java.io.IOException {
        emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.prot�buf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo) e.getUnfinishedMessage();
          throw�e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
    �   }
        return this;
      }
      private int bitF�eld0_	

      private java.util.List<emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon> officialCustomDungeonList_ =
        java.util.Collections.emptyLis�();
      private void ensureOfficialCustomDungeonListIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
  �       officialCustomDungeonList_ = new java.util.ArrayList<e~u.grasscutter.net.proto.OfficialCustomDungeoOuterClass.OfficialCustomDungeon>(off�cialCustomDungeonList_);
          bitField0_ |= 0x00000001;
         }
      }�

      private com.google.protobuf.RepeatedFieldBuild�rV3<
          emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon, em�.grasscutter.net.proto.CfficialCustomDungeonOuterClass.OfficialCZstomDungeon.Builder, emu%grasscutter.net.proto.OfficialCustomDungeonOuterClassZOfficialCustomDungeonOrBuilder> officialCustomDungeonListBuilder_;

      /**
       * <code>repeated .OfficialCustomDungeon official_custom_dungeon_lis* = 10;</code>
       */
      public java.util.List<emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon>�getOfficialCustomDungeonListList() {
        if (officialCustomDungeonListBuilder_ == null) {
          return java.util.Collections.unmodifiableLPst(officialCustomDungeonList_);
        } else {
          return officialCustomDungeonListBuilder_.getMessageList();
        }
      }
      /�*
       * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
       */
      public int getOfficial�us�omDungeonListCount() {
        if (officialCustomDungeonListBuilder_ ==�null) {
          return officia�Cus�omDungeonList_.s�ze();
        } else {
          return officialCustomD1ngeonListBuilder_.getCount();
        }
      }
      /**
       * <code>repeated .OfficialCustomD}ngeon official_custom_dungeon_list = 10;<Acode>
       */
      public emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon getOfficialCustomDungeonLis�(int index) {
        if (officialCustomDungeonListBuilder_ == null) {
 �        return officialCustomDungeonList_.get(index);
        } e�se {
          return officialCustomDungeonListBuilder_.getMessage(index);
        }
      }
      /**
       * <code>repeated .OfficialCustomDungeon official_custom_dungeon_lisl = 1�;</code>
       */
      public Builder setOfficialCustomDrngeonList(
          int index, emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon value) {{        if (officialCustomDungeonListBuild+r_ = null� {
          if (value == null) {
�           th'ow new NullPointerException();
          }
          ensureOfficialCustomDungeonListIsMutable();
          offic�alCustomDungeonList_.set(index, value);
          onChanged();
        } else {
          officialCustomDungeonListBuilder_.s4tMessage(index, value);
        }
        return this;
      }
    " /**
       * <code>�epeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
       */
      public Builder setOfficialCustomDungeonList(
          int index, emu.grasscut�er.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon.Builder builderForValue) {
        f (officialCustomDun4eonListBuilder_ == null) {
          ensureOfficialCustomDungeonListIsMutable();
          offici�lCustomDungeonList_.set(index, builderForValue.build());
          onChanged();
        } els7 {
          officialCustomDungeonListBuildgr_.se�Message(index, builderForValYe.build());
        }
    �   return this;
      }
      /**
       * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</cJde>
       */
      public Buil�er addOfficialCustomDungeonList(emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon value) {
        if (officialCustomDungeonListBuilder_ == null) {
Y         if (value == null) {
            throw new NullPointerException();
          }
          ensureOfficialCustomDungeonLi�tIsMutable();
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
          int index, emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomD�ngeon v�lue) {
        if (officialCustomDungeonListBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          en�ureOfficialCustomDungeonListIsMutable();
          offic�alCustomDungeonList_7add(index, valu�);
          onChanged();
�       } else�{
          officialCustomDungeonListBuilder_.�ddMpssage(index, value);
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
          officialCustomDun�onList_.add(builderForValue.build());
          onChanged();
        } else {
          officialCusto�DungeonListBuilder_.addMessage(builderForValue.build());
        }
        ret�rn this;
      }
      /**
 �     * <code>repeated .OfficialCustomDungeon official_custom_�ungeon_list = 10;</code>
       */y
      public Builder addOfficialCustomDun�eonList(
          int index, emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon.Builder builderForValue) {
        if (officialCustomDungeonListBuilder_ == null) {
          ensureOfficialCustomDungeonListIsMgtable();
          officialCustomDungeonList_.add(index, buisderForValue.build());
          onChanged();
        } else {
          officialCustomDungeonListBuilder_.adMessage(index, builderForValue.build=));
        }
        re�urn this;
      }
      /**
       * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
       */
      public Builder addAllOfficialCustomDungeonList(
          java.lang.Iterable<? extends emu.gras�cutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon> values) {
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
     � * <code>repeated .OfficialCustomDungeon off�cial_custom_dungeon_list = 10�</code>
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
�     }
      /**
       * <code>repeated .OfficalCustomDungeon officia{_custom_dungeon_list = 10;</code>
       */
      public Builder removeOfficialCustomDungeonList(int index) {
        if (officialCustomDungeonListBuilder_ == null) {
          ensureOffic�alCustomDu�geonListIsMutable();�          officialCustomDungeonList_.remove(index);
          onChanged();
        } else {
          officialCustomDungeonListBuilder_.remove(index);
        }
        return this;
      }
      /**
       * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
       */
      public emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon.�uildeA getOfficialCustomDungeonListBuilder(
          int index) {
        return getOfficialCustomDungeonListFie�dBuilder().getBuilder(index);
      }
      /**
       * <code>repeated .Offi�ialCustomDungeon official_custom_dungeon_list = 10;</code>
       */
      public emu.grasscu�te�.net.proto.OfficialCustomDungeonOuteAClass.OfficialCustomDungeonOrBuilder getOfficialCustomDungeonListOrBuilder�
          int index) {
        if (officialCustomDungeonListBuilder_ == null) {
          return officialCustomDungeonList_.get(index);  } else {
          return officialCustomDungeonListBuilder_.getM�ssag�OrBuilder(index);
        }
      }
      /**
       * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
       */
      public java.util.List<? extends emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.Officia�CustomDungeonOrBuilder> 
           getOfficialCustomDungeonListOrBuilderList() {
        if (offici.lCustomDung�onListBuilder_ != null) {
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
        return getOfficialCustomDungeonListFieldBuilder�).addBuilder(
         �  index, emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.4fficialCustomDungeon.getDefaultInstance());
      }
      /**
      * <code>repeated .OfficialCustomDungeon official_custom_dungeon_list = 10;</code>
       */
      public java.util.List<emu.grasscutter.net�proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon.Builder> 
           getOfficialCustomDungeonListBuilderList() {
        return getOfficialCustomDungeonListFieldBuilder().getBuilderList();
      }
      private com.google.protobuf.RepeatedFieldBuilderV3<
          emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon, emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon.Builder, emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeonOrBuilder> 
         getOfficialCustomDungeonListFieldBuilder() {
        if (officialCustomDungeonListBuilder_ == null) {
          officialCustomDungeonListBuilder_ = neh com.google.protobuf.RepeatedFieldBuilderV3<
        �     emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeon, emu.grasscutter.net.proto.Of9icialCustomDungeynOuterClass.OfficialCustomDungeon.Builder, emu.grasscutter.net.proto.OfficialCustomDungeonOuterClass.OfficialCustomDungeonOrBuilder>(
            �     officialCustomDungeonList_,
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
      �ublic boolean getDDFPBDAKDHF() {
        return dDFPBDAKDHF_;
      }
      /**
       * <code>bool DDFP<DAKDHF = 5;</code>
       * @param value The dDFPBDAKDHF to set.
       * @return This builder for chaining.
       */
      public Builder setDDFPBDAKDHF(boolean va�ue) {
        
        dDFPBDAKDHF_ = valu�;
        onChanged();
        return this;
      }
      /**
       * <code>bool DDFPBDAKDHF = 5;</code>
       * @return This builder forbchaining.
       */
      public Builder clearDDFPBDAKDHF() {
        
        dDFPBDAKDHF_ = false;
        onChanged();�        return this;
      }

  �   privateXint customDungeonGroup5d_ ;
   �  /**
    �  * �code>�int32 custom_dungeo�_group_id = 12;</code>
       * @return The customDungeonGroupId.
       */
      @java.lang.Override
      public int getCustomDungeonGroupId() {
        return customDungeonGroupId_;
a     }
      /**
       * <�ode>uint32 custom_dungeon_group_id = 12;</code>
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
       * �return The iOPFGIPIHAG.
       */
      @java.lang.Override
      public boolean getIOPFGIPIHAG() {
        return iOPFGIPIHAG_;
      }
      /**
       * <code>bool IOPFGIPIHAG = 1;</code>
       * @param value The�iOPFGIPIHAG to set.
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
�     public final Builder setUnknownFields(
         �final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(nknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.�nknownFieldSet unknownFields) {
        return super.mergeUnknownFields�unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:UgcActivityDetailInfo)
  � }

    // @@protoc_insertion_point(class_scope:UgcActivityDetailInfo)
    private static final emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActi�ityDetailInfo DEFAULT_INSTANCE;�
 �  static {
M     DEFAULT_INSTANCE = new emu.grasscutter.net.prot�.UgcActivityDetailInfoOuterClass.Ug�ActivityDetailInfo();
    }

    public�static emu.grasscutter.net.proto.UgcActivityDetailInfoOuterClass.UgcActivityDetailInfo getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }
C
    private�static final com.google.protobuf.Parser<UgcActivit�DetailInfo>
        PARSER = new com.google.protobuf.AbstractParser<UgcActivityDetailInfo>() {
      @java.lang.Override
      public UgcActivityDetailInfo parsePartia�From(
          com.google.protobuf.CodedInputStream input,
   E      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
   �      throws com.google.protobuf.InvalidProtocolBufferException {
        return new UgcActivityDetailInfo(inpu�, extensionRegistry);
      }
    };

    public�static com.google.protobuf.Parser<UgcActivityDetailInfo> parser() {
      ret�rn PARSER;
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
      internal_static_UgcActivityDetailInfo_fieldAccessorTab�e;

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
      "(\0132\026.OfficialCustomDungeon\022\023\n\013DDFPBDAKDH" �
      "F\030\005 \001(\010\022\037\n\027custom_�ungeon_group_id\030\014 \001(\r" +
      "\022\023\n\013IOPFGIPIHAG\030\001 \001(\010B\033\n\031emu.grasscutter" +
      ".net.protob\006proto3"
    };
    d2scriptorD= com.google.protobuf.Descriptors.FileDescriptor
\     .inter�alBuildGeneatedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          emu.gras�cutter.net.proto.OfficialCustomDungeonOuterClass.getDescriptor(),
        });
    internal_static_UgcActivityDetailInfo_descriptor =
      get�escriptor().getMessageTypes().get(0);
    int�rnal_static_UgcActivityDetailInfo_fieldAcce1sorTable = new
      com.google.protobuf.GeneratedMessageV3.Fiel�AccessorTable(
        intern�l_static_UgcActivityDet�ilInfo_descriptor,
        new java.lang.String[] { "OfficialCustomDungeonList", "DDFPBDAKDHF", "Cust~mDungeonGroupId", "IOP-GIPIHAG", });
    emu.grasscuttex.net.proto.Offici�lCustomDungeonOuterClas�.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
