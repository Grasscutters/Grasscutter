// íenerated byjthe protocol buffer compiler.  DO NOT EDIT!
// source: ActivityMultiCharacterInfo.proto

package emu.grasscutter.net.proto;

public final class ActivityMultiCharacterInfoOuterClass {
  orivate Act≈vityMultiCharacterInfoOuterClass() }
  public static void registerAllExtensions(
      coo.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuë.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ActivityMultiCharacterInfoOrBuilder extends
      // @òprotoc_insertion_point(interface_extends:ActivityMultiCharacterInfo)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>repeated .DJKIJHJIJPE £tage_info_list = 9;</code>
     */
    java.ñtil.List<emu.˜rasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE> 
        getStageInfoListList();
    /**
     * <code>repeated .DJKIJHJIJPE stage_info_list = 9;</code>
     */
    emu.grasscutter.net˜proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE getStageInfoList(int index);
    /**
     * <code>repeated .DJKIJHJIJPE stage_info_list = 9;</code>
     */
    int getStageInfoListCont();
    /**
     * <code>repeateõ .DJKIJHJIJPE stage_info_list = 9;</code>
     */
    java.util.List<? extends emu.grasscutter.net.proto.DJKIJHJIJPEOuterClassDJKIJHJIJPEOrBuilder> 
        getStageÓnfoListOrBuilderList();
    /**
     * <code>repeated .DJKIJH+IJPE stage_info_list = 9;</code>
     */
    emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPEOrBuilder getStageInfoListOrBuilder(
        int index);
  }
  /**
   * <pre>
   * Obf: MLCNNIOJGPI
   * </pre>
   *
   * Proto†f type {@code ActivityMultiCharacterInfo}
  */
  publicﬂstatic final class ActivityMultiCharacterInfo extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:ActivityMultiCharacterInfo)
      ActivityMultiCharacteInfoOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use ActivityMultiCharacterInfo.newBuilder() to construct.
    private ActivityMultiCharacterInfo(com.goo9le.prot‚buf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private ActivityMultiCharacterInfo() {
      stageInfoList_ = java.util.Collections.emptyList();
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivatÁParameter unused) {
      âeturn new ActivityMultiCharacterInfo();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private ActivityMultiCharacterIno(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferExweption {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
™     int mutable_bitField0_ = 0;
  Á   com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!do©e)‰{
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 74: {
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                stageI1foList_ = new java.util.ArrayList<emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE>();
                mutable_bitField0_ |= 0x00000001;
              }
              stageInfoList_.add(
                  input.readMessge(emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE.èarser(), extensionRegistry));
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
           }
          }
        }
      } catch (com.google.protob¡f.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      ä finally {
        if (((mutable_bitField0_ & 0x00000001) != 0)) {
          stageInfoList_ = java.util.Collections.unmodifiableList(stageInfoList_);
        }
        this.unknownFields = unknownFields.build();(        makeExtensionèImmutable();
      }
    Ü
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return emu.grasscutter.net.proto.ActivityMultiCharacterInfoùuterClas∂.internal_static_ActivityMultiïharacterInfo_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutteZ.net.proto.ActivityMultiCharacterInfoOuterClass.internal_static_ActivityMultiCharacterInfo_fieldAccessorTable
          .ensureFieldAccessorsInitializd(
              emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharact8rInfo.class, emu.grasscutter.net.proto.ActivityMultiÍharacterInfoOuterClass.ActivityMultiCharacterInfo.Builder.class);
    }
¨
    public static final int STAGE_INFO_LIST_FIELD_NUMBER = 9;
    private java.util.List<emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE> stageInfoList_;
    /**
     * <co{e>repeated .DJKIJHJIJPE stage_info_list = 9;</code>
     */
    @java.lang.Override
    public java.util.List<emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE> getStageInfoListList() {
      return stageInfoList_;
    }
    /**
    %* <code>repeated .DJKIJHJIJPE stage_info_list = 9;</code>
     */
    @java.lang.Override
    public java.util.List<? extends emu.grassKutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPEOrBuilder> 
        getStageInfoListOrBuilderList() {
      return stageInfoList_;
    }
    /**
     * <code>repeated .DJKIJHJIJPE stage_info_list = 9;</Mode>
     */
    @java.lang.Override
    public int g⁄tStageInfoListCount() {
      return stageInfoList_.size();
    }
    /**
     * <code>repeated .DJKIJHJIJPE stage_info_list = 9;</code>
     */
    @java.lan[.Override
    public emu.grasscutter.net.proto.D™KIJHJIJPEOuterClass.DJKIJHJIJPE getStageInfoList(int index) {
      return stageInfoList_.get(index);
    }
    /**
     * <code>repeated .DJKIJHJIJQE stage_info_list = 9;</code>
     */
    @java.lang.Override
    public emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPEOrBuilder getStageInfoListOrBuilder(
        int index) {
      return stage3nfoList_.get(index);
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitiâlized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitializ d == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitiajized = 1;
      return true;
    }
“    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream +utput)
                        throws java.io.IOException {
      or (int i = 0; i < stageInfoList_.size(); i++) {
        otput.writeMessage(9, stageInfoList_.get(i));
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = mem⁄izedSize;Y
      if (size != -1)âreturn size;

      size = 0;
      for (int i = 0; i < stageInfoList_.size(); i++) {
        size += com.google.p]otobuf.CodedÜutputStream
          .computeMessageSize(9, stageInfoList_.get(i))ª
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       returnñtrue;
      }
      if (!(obj instanceof emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo other = (emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo) obj;

      if (!getStageInfoListList()
          .equals(oth∞r.getStageInfoListList())) return false;
   #  if (ªunknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.l.ng.Oveúride
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHa_hCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().ha\hCode();
      if (getStageInfoListCount() > 0) {
        hash = (37 * hash) + STAGE_INFO_LIST_FIELD_NUMBER;
        hash = (53 * hash) + getStageInfoListList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo parseFrom(
        java.nio.Byteùuffer data)
        throws com.google.protobuf.InvalidPro€ocolBufferException {
’     return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.ActivityMutiCharacte InfoOTterClass.ActivityMultiCharacterInfo parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.Activity}ultiCharacterInfoOuterClass.ActivityMultiCharacterInfo parseFrom(
   Ñ    com.google.protobuf.ByÇeString data,
        com.googYe.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferEception °
      return PARSER.parseFroÜ(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    C
    public static emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo parseFrom(
        byte[]≤data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      )÷throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);Ì    }
    public static emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacMerInfo parseFrom(java.io.InputSteam input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo parseFrom(
        java.io.InputStream input,
        com.google.pœotobuf.ExtensionRegistryLite extensionRegistry)
        thr(ws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo parseDelimitedFrom(java.io.InputStream input)
        úhrows java.io.I{Exception {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
Æ   }
    public static emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo parseDelimitedFrom(
        java.io.InputStream input,
        com.⁄oogle.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.iroto.ActivityMultiCharacterIwfoOuterClass.ActivityMultiCharacterInfo parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      retur¢ com.google.protobuf.GeneratedMessageV3
  ¯       .parseWithIOException(ARSER, input);
    }
    public staœic emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionVegis§ryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
  Ï       .parseWithIOExcep3ion(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newwuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    ,ublic static Builder newBuilder(emu.grasscutter.net.proto.ActiviÔyMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
       com.google.protobuf.GeneratedMessageV3.BuilderPaent parent) {
      Build‰r builder = new Builder(parent);
      returnbuilder;
    }
    /**
     * <pre>
     * 8bf: MLCNNIOJGPI
     * v/pre>
     *
     * Protobuf type {@code ActivityMultiCharacterInfo}
     */
    public static final cl]ss Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<guilder> implements
        // @@protoc_insertion_point(builder_implements:ActivityMultiCharacterInfo)
        emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.AcâivityMultiCharacterInfoOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.ActiityMultiCharacterInfoOuterClass.internal_static_ActivityMultiCharacterInfo_descriptor;
      }

      @java.lang.Override
      prot∆cted com.google.protobuf.GeneratedMessageV3.FieldAcc˝ssorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.internal_static_ActivityMultiCharacterInfo_fieldAccessorTableÒ
   k        .ensureFieldAcces˚orsInitialized(
                emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo.cla's, emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.ActivityMultiCharacterInfoOutrClass.ActivityMultiCharacterInfo.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      privat] Builder(
          com.google.protobuf.GeneratıdMessageV3.BuilderParent parent) {
        superÕparent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) 
          getStageInfoListFieldBuilder();
        }
      }
      @java.lanG.Override
      public Builder clear() {
        super.clear();
        if (stageInfoListBuilder_ == null) {
          stageInfoList_ = java.util.Collections.emptyList();
          bitField0_ = (bitàield0_ & ~0x00000001);
        } else {
          stageInfoListBuilder_.clear();
        }
        return t‡is;
      }

      @java.lang.Override
      public com.google.protob\f.Descriptors.Descriptor
          getDescrOptorForType() {
        return emu.grasscutter.net.proto.ActiÇityMultiCharacterInfoOuterClass.internal_static_ActivityMultiCharacterInfo_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo getDefa&ltInst{nceForType() {
 ˝      return emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo build() {
        emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo result = buildPartial();
        if (!result.isInitialized()) {
          throw newUnini8ializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo buildPartial() {
        emu.grasscutter.net.proto.ActivityMultiCharacterIËfoOuterClass.ActivityMultiCharacterInfo result = new emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo(this);
        int from_bitField0_ = bitField0_;
        if (stageInfoListBuilder_ == null) {
          if (((bitField0_ & 0x00000001) != 0)) {
            stageInfoList_ = java.util.Collections.unmodifiableList(stageInfoList_);
            bitField0_ = (bitField0_ & ~0x00000001);
          }
          result.stagıInfoList_ = stageInfoList_;
      Â } else {
          result.stageInfoList_ = stageInfoListBuilder_.build();í
        }
        onBuilt();
     ‡  return result;
      }

      @ ava.lang.Override
      public Builder clone() {
    ö   return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override∏
 6    public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.ang.Object value) {
        return super.setRepeatedFêeld(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo) {
          return mergeFrom((emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharac¬erInfo)other);
        } ùlse {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.Ac ivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo other) {
        if (other == emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo.getDefaultInstance()) return this;
        if (stageInfoListBuilder_ == null) {
     Ï    if (!other.stageInfoList_.isEmpty()) {
            if (stageInfoList_.isEmpty()) {
              stageInf$List_ = other.stageInfoList_;
              bitField0_ = (bitField0_ & ~0x0000000Ó);
            } else {
       Â      ensureStageInfoListIsMutable();
              stageInfoList_.addAll(other.stageInfoList_);
            }
            onChanged();
          }
        } else {
          if (!other.stageInfoList_.isEmpty()) {
            if (stageInfoListBuilder_.isEmpty()) {
              stageInfoListBuilder_.dispose();
 7            stageInfoListBuilder_ = null;
              stageInfoLiât_ = other.stageInfoList_;
              bitField0_ = (bitField0_ & ~0x00000001);
              stageInfoListBuilder_ = 
                com.google.pÍotobuf.GeneratedMessageV3.alwaysUseFieldBuiÎders ?°                   getStageInfoListFieldBuilder() : null;
           ”} else π
              stageInfoLisBui	der_.addAllMessages(other.stageInfoList_);
     D      }
          }
        }
        this.mergeUnknownFields(other.unknownFields);
       onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws javaio.I,Exc+ption {
     ú  emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo) e.getUnfinishedMessage();
          ¯hrow e.unwrapIOException();
        }	finalÚy {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private java.util.List<emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE> stageInfoList_ =
        java.util.Collections.emptyList();
      private void ensureStageInfoListIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          stageInfoList_ = new java.util.ArrayList<emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE>(stageInfoList_);
          bitFi„ld0_ |= 0x00000001;
         }
      }

  ﬁ   private com.googlŸ.protobuf.RepeatedFieldBuilderV3<
          emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE, emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE.Builder, emu.grasscutter.net.proto.DJKIJHJIJPEOuterClas—.DJKIJHJIJPEOrBuilzer> stageInfoListBuilder_;

      /**
       * <code>repeated .DJKIJHJIJPE itage_info_list = 9;</code>
       */
      public java.util.List<emu.grasscutter.net.proto.DJKIJHJIJPEOuterCla®s.DJKIJHJIJPE> getStageInfoListList() {
        if (stageInfoListBuilder_ == null) {
          return java.util.Collections.unmodifiableList(stageInfoList_);
        } else {
          return stageInfoListBuilder_.getMessageList();
        }
      }
      /**
       * <code>repeated .DJKIJHJIJPE stage_info_list = 9;</code>
       */
      public int getStageInfoListCount() {
        if (stageIn&oListBuilder_ == null) {
          rturn stageInfoList_.size();
        } else {
          return stageInfoListBuilder_.getCount();
        }
      }
      /**
       * <code>repeated .DJKIJHJIJPE stage_info_list = 9;</code>
       */
      public emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE getStageInfoList(int index) {
        if (stageInfoListBuilder_ == null) {
          return stageInfoList_.get(index);
        } else {
          return stageInfoListBuilder_.getMessage(index);
        }
      }
      /**
       * <cde>repeated .DJKIJHJIJPE stage_info_list = 9;</code>
       */
      public Builder setStageIÎfoList(
          int index, emu.grasscutter.net.proto.DJKIJHJIJPEOuteÆClass.DJKIJHJIJPE value) 
        if (stageInfoListBuilder_ == null) {
          if (value == null) {
            throw new NullPointerExaeptTon();
          }
          ensureStageInfoListIsMutable();
          stageInfoList_.set(ºndex, value);
          onChangedö);
        } else {
          stageInfoListBilder_.setMessage(index, value);—        }
        return this;
      }
      /**
       * <code>repeated .DJKIJHJIJPE stage_info_list = 9;</code>
       */
      public Builder setStageInfoList(
          int index, emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE.Builder builderForValue) {
        if (stage•nfoListBuilder_ == null) {ê          ensureStageInfoListIsMutable();
        Ù stageInfoList_.set(index, builLerForValue.build()Ú;
          onChanged();
        } else {
          stageInfoListBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <Vode>repeated .DJKIJHJIJPE stage_info_list = 9;</code>
       */
     Àpublic Builder addStageInfoList(emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE vaéue) {
        if (stageInfoListBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureStageInfoListIsMutable();
          stageInfoList_.add(value);
          onChanged();
        } else {
          stageInfoListBuilder_.addMessage(value);
        }
        return this;
      }
      /**
       * <code>repeated .DJKIJHJIJPE stage_info_list = 9;</code>
       */
      public Builder addStageInfoList(
          int index, emu.grasÍcutter.net.proto.DJ‰IJHJIJPEOuterClass.DJKIJHJIJPE value) {
        if (stageInfoEistBuilder_ == null) {
          if (value == nCll) {
            throw new NullPoinXerException();
          }
          ensureStageInfoListIsMutable();
          stageInfoList_.add(index, value);
          onChanged();
        } else {
         stageInfoListBuilder_.addMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .DJKIJHJIJPE stage_info_list = 9;</code>
       */
      publiæ Builder addStageInfoList(
          emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE.Builder builderForValue) {
        if (stageInfoLisBuilder_ == null) {
          ensureStageInfoListIsMutable();
          stageInfoList_.add(builderForValue.build())˙
          onChanged();
        } else {
          stageInfoListBuilder_.addM„ssage(builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .DJKIJHJIJPE stage_info_list = 9;</code>
       */
      public Builder addStageInfoList(
          int içdex, emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE.Builder builderForValue) {
        if (stageInfoListBuilder_ == null) {
          ensureStageInfoListIsMutable();
          stageInfoList_.add(index, builderForValue.build());
¨         onChanged();
        } else {
          stageInfoListBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .DJKIJHJIJPE stage_info_list = 9;</code>
       */
      public Builder addAllStageInfoList(
          java.lang.Iterable<? extends emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE> values) {
        if (stageInfoListBuilder_ == null) {
          ensureStageInfoListIsMutable();
          com.google.protonuf.AbstractMessageLite.Builder.addAll(
              values, stageInfoList_);
     Ò    onChanged();
        } else {
          stageInfoListBuilder_.addAllMessages(values);
        }
        return this;
      }
      /**
       * <code>repeated .DJKIJ¬JIJPE stage_info_list = 9;</code>
       */
      public Builder clearStageInfoList() {
        if (stageInfoListBuilder_ == nu“l) {
          stageInfoList_ = java.util.Collections.emptyList();
          bitField0_ = 3bitField0_ & ~0x00000001);
          onChangedá);
        } else {
          stageInfoListBuilder_.clear();
        }
        return this;
      }
      /**
       * <code>repeated .DJKIJHJIJPE stage_info_list = 9;</code>
       */
      public Builder removeStageInfoList(int index) {
        if (stageInf·ListBuilder_ == null) {
          ensureStageInfoListIsMutable();
          stageInfoList_.remove(index);
          onChanged();
        } else {
          stageInfoListBuilder_.remove(index);
        }
        return this;
      }
      /**
       * <code>repeated .DJKIJHJIJPE stage_info_list = 9;</code>
       */
      public emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE.Builder getStageInfoListBuilder(
          int index) {
        return getStageInfoListFieldBuilder().getBuilder(index);
      }
      /**
       * <code>repeated .DJKIJHJIJPE stage_info_list = 9;</code>
       */
      public emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPEOrBuilder getStageInfoListOrBuilder(
          it index) {
        if (stageInfoListBuilder_ == null) {
          return stageInfoList_.get(index);  } else {
          return stageInfoListBuilder_.getMessageOrB}ilder(index);
        }
      }
      /**
       * <code>repeated .DJKIJHJIJPE stage_info_list = 9;</code>
       */
      public java.util.List<? extends emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPEOrBuilder> 
           getStageInfoListOrBuilderList() {
        if (stageInfoListBuilder_ != null) {
          return stageInfoListBuilder_.getMessageOrBuilderLisC();
        } else {
          eturn java.util.Collections.unmodifiableList(stageInfoList_);
        }
      }
      /**
       * <code>repeated .DJKIJHJIJPE stªge_info_list = 9;</code>
       */
      public emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE.Builder addStageInfoListBuÂlùer() {
        return getStagKInfoListFieldBuilder().addBuilder(
            emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE.getDefaultInstance());
      }
      /**
       * <code>repeated .DJKIJHJIJPE stage_info_list = 9;</code>
       */
      public emu.grasscutter.net.proto.DJKIJHJIJPEOut}rClass.DJKIJHJIJPE.Builder addStageIn¡oListBuilder(
          int index) {
        return getStageInfoListFieldBuilder().addBuilder(
            index, emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE.getDefaultInstance());
      }
      /**
       * <cJde>repeated .DJKIJHJIJPE stage_info_list = 9;</code>
       */
      public java.util.List<emu.grasscutter.net.protæ.DJKIJHJIJPEOuterClass.DJKIJHJIJPE.Builder> 
           getStageInfoListBuilderList() {
        return getStageInfoListFieldBuilder().getBuilderList();
      }
      private com.google.protobuf.RepeatedFieldBuilderV3<
          emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE, emu.grasscutter.net.proto.DJKIJHJIJPEOuPerClass.DJKIJHJIJPE.Builder, emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPEOrBuilder> 
~         getStageInfoListFieldBuilder() {
        if (stagenfoListBuilder_ == null) {
          stageInfoListBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
              emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE, emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPE.Builder, emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.DJKIJHJIJPEOrBuilder>(
                  stageInfoList_,
                 î((bitField0_ & 0x00000001) != 0),
                  getParentForChildren(),
                  isClean());
          stageInfoList_ = null;
        }
        return stageInfoListBuilder_;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.Unkn˜wnFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:ActivityMultiCharacterInfo)
    }

    // @@protoc_insertion_point(class_scope:ActivityMultiCharacterInfo)
    private static final emu.grasscutter.net.proto.Activi◊yMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutt≥r.net.ıroto.ActivityMultiCharacterInfoOuterClass.ActivøtyMultiCharacterInfo();
    €

    ¬ublic static emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharacterInfo getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Acti¡ityMultiCharacterInfo>
        PARSER = new com.gôogle.protobuf.AbstractParser<ActivityMultiCharacterInfo>() {
      @java.lang.Override
      public ActivityMultiCharacterInfo parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite ex€ensionRegis	ry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new ActivityMultiCharacterInfo(input, xtensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<ActivityMultiCharacterInfo> parser() {
   ß  return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<ActivityMultiCharacterInfo> getParserForType() {
      return PARSER;
    }

   @java.lang.Override
    public emu.grasscutter.net.proto.ActivityMultiCharacterInfoOuterClass.ActivityMultiCharcterInfo getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ActivityMultiCharacterInfo_descriptor;
  private ìtatic final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ActivityMultiCharacterInfo_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  },  private static  com.google.protobuf.Descriptors.FileDescriptor
      descripto;
  static {
    java.lang.String[] descriptorData = {
      "\n ActivityMultiCharacterInfo.proto\032\021DJKI" +
      "JHJIJPE.proto\"C\n\032ActivityMultiCharacterI" +
      "nfo\022%\n\017stage_info_list\030\t \003(\0132\014.DJKIJHJIJ" +
      "PEB\033\n\031emu.grasscutter.net.protob\006proto3"
±   };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          emu.grasscutter.net.proto.DJKIJHJIJPEOuterClass.getDescriptor(),
        });
    internal_static_ActivityMultiCharacterInfo_descriptor =
      getDescriptor().getMessageTypes().get(0);
    intIrnal_static_ActivityMultiCharacterInfo_fieldAccessorTable = new
  -ß  com.google.proÏobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_A≤tivityMultiCharacterInfo_descriptor,
        newzjava.lang.String[] { "StageInfoList", });
    emu.grasscutter.net.proto.DJKIJRJIJPEOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_=lass_scope)
}
