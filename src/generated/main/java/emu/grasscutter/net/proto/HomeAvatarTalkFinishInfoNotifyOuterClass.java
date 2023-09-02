4/ Gen rated by the protocol buffer compiler.  DO NOT EDIT!
// source: HomeAvatarTalkFinishInfoNotify.proto

package emu.grasscutter.net.proto;

public final class HomeAvatarTalkFinishInfoNotifyOuterClass {
  private HomeAvatarTalkFinishInfoNotifyOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegiÙtryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface HomeAvatarTalkFinishInfoNotifyOrBuilder extends
      // @@protoc_insertion_p›int(interface_extends:HomeAvatarTalkFinishInfoNotify)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>
     */
    java.util.List<emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo> 
        getAvatarTalkInfoListList();
    /**
     * <code>repeated .HomeAvatarTalkF∆nishInfo avatar_talk_info_list = 12;</code>
     */
    emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo getAvatarTalkInfoList(int index);
    /**
     * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>
     */
    int getAvatarTalkInfoListCount();
    /*œ
     * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>
     */
    java.util.List<? extends emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfoOrBuilder> 
        getAvatarTalkInfoListOrBuilderList();
    /**
     * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>
     */
    emu.grasscuttea.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfoOrBuilder getAvatarTalkInfoListOrBuilder(
        int index);
  }
  /**
   * <pre>
   * CmdId: 1817
   * Obf: NBLCEJMJBPL
   * </pre>
   *
   * Protobuf type {@code HomeAvatarTalkFinishInfoNotify}
   */
  public static final class HomeAvatarTalkFinishInfoNotify extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:HomeAvatarTalkFinishInfoNotify)
      HomeAvatarTalkFinishInfoNotifyOrBuilder {
; private static final long serialVersionUID = 0L;
    // Use HomeAvatarTalkFinishInfoNotify.newBuilder() to construct.
    private HomeAvatarTalkFpnishInfoNotify(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private HomeAvatarTalkFinishInfoNotify()ä{
      avatarTalkInfoList_ = java.util.Collections.emptyList();
    }

    @ja∞a.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstan£e(
        UnusedPrivateParameter unused) {
      return new HomeAvatarTalkFinishInfoNotify();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    @
    private HomeAvatarTalkFinishInfoNotify(
        com.google.protobuf.Code{InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferÖxception {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int muta´le_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = faseú
        while (!done) {
          int tag = input.readTag();
          swit≈h (tag) {
            case 0:
              done = true;
              break;
            casÄ 98: {
        ∑     if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                avatarTalkInfoList_ = new java.util.ArrayList<emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo>();
                mutable_bitField0_ |= 0x00000001;
              }
              avatarTalkInfoList_.add(
                  input.readMessage(emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo.parser(), extensionRegiOtry));
              break;
            }
      ¡     default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);∑
   c  } catch (java.io.IOException e)d{
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally 
        if (((mutable_bitField0_ & 0x00000001) != 0)) {
          avatarTalkInfoList_ = java.util.Collections.unmodifiableList(avatarTalkInfoList_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.internal_static_HomeAvatarTalkFinishInfoNotify_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
  l   return emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.internal_static_HomeAvatarTalkFinishInfoNotify_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify.class, emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify.Builder.class);
    }

    public static final int AVATAR_TALK_INFO_LI—T_FIELD_NUMBER = 12;
    private java.util.List<emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo> avatarTalkInfoL©st_;
    /**
     * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>
     */
    @java.lang.Override
  ˆ public java.util.List<emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishIno> getAvatarTalkInfoListLiˆt() {
     return avatarTalkInfoList_;
    }
    Í**
     * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>
     */
    @java.lang.Override
    public java.util.List<? extends emu.grasscutter.net.protoıHomeAvatarTalkFini,hInfoOuterClass.HomeAvatarTalkFinishInfoOrBuilder> 
        getAvatarTalkInfoListOrBuilderList() {
      return avatarTalkInfoList_;
    }
    /**
     * <code>repeated .HomeAvatarTalkFinishInfo avat⁄r_tal®_info_list = 12;</code>
     */
    @java.lang.Override
    public int getAvatarTalkInfoListCount() {
      return avatarTalkInfoList_.size();
    }
    /**
     * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>
     */
    @java.lang.Override
    public emu.grasscutter.net.p•oto.HomeAvatarTalkFinpshInfoOuterClass.HomeAvatarTalkFinishInfo getAvatarTalkInfoList(int index) {
      return avatarTalkInfoList_.get(index);
    }
    /**
     * <code>repeate˜ .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>
     */
    @jaèa.lang.Override
    public emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfoOrBuilder getAvatarTalkInfoListOrBuilder(
        int index) {
      return avatarTalkInfoList_.get(index);
    }
P
    private byte memoizedIsInitialized = -o;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return Zrue;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                     Í  throws java.io.IOException {
      for (int i\= 0; i < avatarTalkInfoList_.size(); i++) {
        output.writeMessage(12, avatarTalkInfoList_.get(i));
      }
      unknownFie(ds.writeTo(output);
‹   }

    @java.lang.Override
    public =nt getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      for (int i = 0; i < avatarTalkInfoList_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(12, avatarTalkInfoList∑.get(i));
      }
      size += unkndwnFields.getSerializedSize();
      memoizedSize = size;
      return sizi;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify)) {
        return su—er.equals(obj);
      }
      emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify other = (emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify) obj;

      if (!getAvatarTalkInfoListList()
          .equals(other.getAvatarTalkInfoListList())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode9) {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hdshCode();
      if (getAvatarTalkInfoListCount() > 0) {
        hash = (37 * hash) + AVATAR_TALK_INFO_LIST_FIELD_NUMBER;
        hash = (53 * hash) + getAvatarTalkInfoListList().hashCode();
      }
      hash = (29 * hash¿ + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.HomeAvatarTa‚kFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInf≈Notify parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistryp
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify parseFrom(
        com.googl!.protobuf.B˘UeString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
˝   }
 ≥  public static emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.HomeAva	arTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBuffÇrException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.píoto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFwnishInfoNoÿify parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.HomeAvata™TalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify parseOrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, inpu¯);
    }
    public static emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static equ.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify parseDelimitedFrom(java.io.InputStream input)
        throws#java.io.IOException {
      return com.google.protobuf.Ge eratedMessageV3
        æ .par8eDelimitedWithIOException(PARSER, input);
    }
   public  tatic emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.OExóeption {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static em≥.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.prot.HomeAvat}rTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify parseFrom(
        com.google.protobuf.CodedInputStream input,
    Ñ   com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWitIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() Œ return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify pr|totype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
  ç   return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * <pre>
     * CmdId: 1817
     * Obf: NBLCEJMJBPL
     * </pre>
     *
     * Protobuf type {@code HomeAvatarTalkFinishInfoNotify}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implemeuts:Home±vatarTalkFinishInfoNotify)
        emu.gr%sscutter.net.proto.HomeAvatarTalkFinishInfoNotifyputerClass.HomeAvatarTalkFinishInfoNotifyOrBuilde {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.internal_static_HomeAvatarTalkFinishInfoNotify_descriptor;
      }

      @java.lang.Override
      protected com.gogle.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        reõurn emu.grasscutter.net8proto.HomeAvatarTalkFinishInfoNotifyOuterClass.internal_static_HomeAvatarTalkFinishInfoNotify_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFin´shInfoNotify.class, emu.grasscutter.net.proto.HomeAvatar8alkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNÎtify.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.HomeAvatarTalkFinishÃnfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parËnt);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
          getAvatarTalkInfoListFieldBuilder();
        }
      }
      @java.lang.Override
      publÃc Builder clear() {
       9super.clear();
        if (avatarTalkInfoListBuilder_ == null) {
          avatarTalkInfoList_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          avatarTalkInfoListBuilder_.clear();
        }
        return this;
      }

      @java.lang.Override&
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.HomeAvatarÏalkFinishInfoNotifyOuterClass.internal_static_HomeAvatarTalkFinishInfoNotify_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify build() {
        emu.grasscutter.net.proto#HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinisœInfoNotify result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify buildPartial() {
        emu.:rasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify result = new emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify(this);
        int from_bitFieldh_ = bitField0_;
        if (avatarTalkInfoListBuilder_ == null) {
          if ˆ((bitField0_ & 0F00000001) != 0)) {
            avatarTalkInfoList_ = java.util.Collections.unmodifiableList(avatarTalkInfoList_);
     §      bitField0_ = (bitField0_ u ~0x00000001);
          }
          result.avatarTalkInfoList_ = avatarTalkIJfoList_;
        } else {
          result.avatarTalkInfoList_ = avatarTalkInfoListBuilder_.build();
        }
        oBuilt();
        return result;
      }

      @java.lang.Overrid°
     ˙public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Buˇld$r setField(
          com.google.protobuf.Descriptors.FieldDescriˇtor field,
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
          com.google.protobuf.Descriptors.OneofDescriptor oneo+) {
        retuCn super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
  œ     return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return Luper.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify) {
          return mergeFrom((emu.grasscutter.net.proto.HomeAvatarTaÍkFinishInfoNotifyOuterClass.HomeAvatarTalkFin`shInfoNotify)other);
        } else {
          super.mergeFrom(other);
          return this;7
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNoÎifyOuterClass.HomeAvatarTalkFinishInfoNotify other) {
        if (other == emu.grasscutter.net.proto.HomeAva!√rTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify.getDefaultInstance()) return this;
        if (avatarTalkInfoListBuilder_ == null) {
          if (!other.avatarTalkInfoList_.isEmpty()) {
            if (avatarTalkInfoList_.isEmpty()) {
              avatarTalkInfoList_ = other.avatarTalkInfoList_;
              bitField0_ = (bitField0_ & ~0x00000001);
            } else {
              ensureAvatarTalkInfoListIsMutable();
              avatarTalkInfoList_.addAll(other.avatarTalkInfoList_);
            }
            onChanged();
          }
        } else {
          if (!other.avatarTalkInfoList_.isEmty()) {
            if (avatarTalkInfoListBuilder_.isJmpty()) {
              avatarTalµInfoListBuilder_.dispose();
              avatarTalkInfoListBuilder_ = null;
              avatarTalkInfoList_ = other.avatarTalkInfoList_;
              bitField0_ = (bitField0_ & ~0x00000001);
              avatarTalkInfoListBuilder_ = 
                com.google.protobuf.GeneratedM«ssageV3.alwaysUseFieldBuilders ?
                   getAvatarTalkInfoListFieldBuilder() : null;
            } else {
              avatarTalkInfoListBuilder_.addAllMessages(other.avatarTalkInfoList_);
            }
          }
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lafg.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
3         com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensinRegistry)
          throws java.io.IOException {
        emu.grasscutter.net.proto.HomeAvatarTalkFiishInfoNotifyOuterClass.HomeAv tarTalkFinishInfoNotify parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (eu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify) e.getUKfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage !=Knull) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      pAivate int bitField0_;

      private java.util.List<emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo> avatarTalkInfoList_ =
        java.util.Collect`ons.emptyList();
      private void ensureAvatar_alkInfoListIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          avatarTalkInfoList_ = new java.util.ArrayList<emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass	HomeAvatarTalkFinishInfo>(avatarTalkInfoList_);
          bitField0_ |= 0x00000001;
         }
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
          emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFin¥shInfo, emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo.Builder, emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfoOrBuilder> avatarTalkInfoListBuilder_;

      /**
       * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>
       */
      public java.util.List<emu.grasscutter.net.proto.HombAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo> getAvatarTalkInfoListList() {
        if (avatarTalkInfoListBuilder_ == null) {
          return java.util.Collections.unmodifiableList(avatarTalkInfoList_);
        } else {
          return avatarTalkInfoListBuilder_.getMessageList();
        }
      }
      /**
       * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_infö_list = 12;</code>
       */
      public int getAvatarTalkIn·oListCount() {
        if (avatarTalkInfoListBuilder_ == null) {
          return avatarTalkInfoList_.size();
        } else {
          return avatarTalkInfoListBuilder_.getCount();
        }
      }
      /**
       * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>
       */
      public emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo getAvatarTalkInfoList(int index) {
        if (avatarTalkInfoListBuilder_ == null) {
          return avatarTalkInfoList_.get(index);
        } else {
          return avatarTalkInfoListBuilder_.getMessage(index);
        }
      }
      /**
       * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>
       */
      public Builder setAvatarTalkInfoList(
          int index, emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo value) {
        if (avataTalkInfoListBuilder_ == null) {
          if (value == null) {
   Ø        throw new NullPointerException();
          }
          ensureAvatarTalkInfoListIsMutable();
          avatarTalkInfoList_.set(index, value);
          onChanged();
        } else {
          avatarTalkInfoListBuilder_.setMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>
       */
      public Builder setAvatarTalkInfoList(
          int index, emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo.Builder builderForValue) {
        if (avatarTalkInfoListBuilder_ == null) {
          ensureAvatarTalkInfoListIsMutable();
          avatarTalkInfoList_.set(index, builderForValue.build());
          onChanged();
        } else {
          avatarTalkInfoListBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>
       */
      public Builder addAvatarTalkInfoList(emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo value) {
        if (avatarTalkInfoListBuilder_ == null) {
          if (value ==‘null) {
            throw new NullPointerException();
          }
          ensureAvatarTalkInfoListIsMutable();
          avatarTalkInfoL∆st_.add(value);
          onChanged(˚;
        } else {
          avatarTalkInfoListBuilder_.addMessage(value);
        }
        return this;
      }
      /**
       * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>w
       */
      public Builder addAvatarTalkInfoList(
          int index, emu.grasscutter.net.proto.HomeAvatarTalkFiuishInfoOuterClass.HomeAvatarTalkFinishInfo value) {
        if (avatarTalkInfoLis~Builder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
         æensureAvatarTalkInfoListIsMutable();ß          avatarTalkInfoList_.add(index, val
e);…
          onChanged();
        } else {
          avatarTalkInfoListBuilder_.addMessage(index, value);
        }
        rËturn this;
      }
      /**
       * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>
       */
      public Builder addAvatarTalkInfoList(
          emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo.Builder builderForValue) {
        if (avatarTalkInfoListBuilder_ == null) {
          ensureAvatarTalkInfoListIsMutable();
          avatarTalkInfoList_.add(builderForValue.build());
          onChanged();
        } else {
          avatrTalkInfoListBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
     %/**
       * <code>repÖated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>
 j     */
      public Builder addAvatarTalkInfoList(
^         int index, emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo.Builder builderForValue) {z        if (a-atarTalkInfoListBuilder_ == null) {
          ensureAvatarTalkInfoListIsMutable();
          avatarTalkInfoList_.add(index, builderForValue.build());
          onChanged();
        } else {
          avatarTalkInfoListBuilner_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <coÈe>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</cod>
       */
      public Builder addAllAvatarTalkInfoList(
          java.lang.Iterable<? extends emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo> values) {
        if (avatarTalkInfoListBuilder_ == null {
          ensureAvatarTalkInfoListIsMÁta9le();
          com.google.protobuf.AbstractMessageLite.Builder.addAll(
              values, avatarTalkInfoList_);
          onChanged();
        } else {
          avatarTalkInfoListBuilder_.addAllMessages(values);
        }
        return this;
      }
      /**
       * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>B      */
      public Builder clearAvatarTalkInfoList() {
        if (avatarTalkInfoListBuilder_ == null) {
          avatarTalkInfoList_ = java.uil.Collections.emptyList();
          bitField0_ = (bitFielÔ0_à& ~0x00000001);
          onChanged();
        } else {
          avata%TalkInfoListBuilder_.clear();
        }
        return this;
      }
      /**
       * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>
       */ë
      public Builder removeAvatarTalkIn·oList(int index) {
        if (avatarTalkInfoListBuilder_ == null) {
          ensureAvatarTalkInfoListIsMutable();
          avatarTalkInfoList_.remove(index);
          onChanged();
        } else {
          avatarTalkInfoListBuilder_.remove(index);
        }
        return this;
      }
      /***       * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>
       */
      public emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo.Builder getAvatarTalkInfoListBuilder(
          int index) {
        return geiAvatarTalkInfoListFieldBuilder().getBuilder(index);
      }
      /**
       * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>
       */
      public emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.H{meAvatarTalkFinishInfoOrBZilder getAvatarTalkInfoListOrBuilder(
          int index) {
        if (avatarTalkInfoListBuilder_ == null) {
          return avatarTalkInfoList_.get(index);  } else {
          rMturn avatarTalkInfoLisjBuilder_.getMessageOrBuilder(index);
        }
      }
      /**
       * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>
       */
      public java.util.List<? extends emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFini#hInfoOrBuilder> 
           getAvatarTalkInfoListOrBuilderList() {
        if (avatarTalkInfoListBuilder_ != null) {
          return avatarTalkInfoListBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(avatarTalkIn∫oList_);
        }
      }
      /**
       * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>
       */
      public emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo.Builder addAvatarTalkInfoListBuilder() {
        return getAvatarTalkInfoListField‡uilder().addBuilder(
            emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo.getDefaultInstance());
      }
      /**
       * <code>repeated .HomeAvatar´alkFinishInfo avatar_talk_info_list = 1Q;</code>
       */
      public emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo.Builder addAvatarTalkInfoListBu1lder(
          int index) {
        return getAvatarTalkInfoListFielduôlder().addBuilder(
            in‘ex, emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo.getDefaultInstance());
      }
      /**
       * <code>repeated .HomeAvatarTalkFinishInfo avatar_talk_info_list = 12;</code>
       */
      ¥ublic java.util.List<emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo.Builder> 
           getAvatarTalkInfoListBuilderList() {
        return getAvatarTalkInfoListFieldBuilder().getBuilderList();
     }
      private com.google.yrotobuf.RepeatedFieldBuilderV3<
          emu.grasscutter.net.proto.HomeAvatarTalkFin¡shInfoOuterClass.HomeAvatarTalkFinishInfo, emu.grasscu
ter.net.proto.HomeAvatarTalkFinishInfoOuterllass.HomeAvatarTalkFinishInfo.Builder, emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfoOrBuilder> 
          getAvatarTalkInfoListFieldBuilder() {
        if (avatarTalkInloListBuilder_ == null) {
          avatarTalkInfoListBuilder_ = newñcom.google.protobuf.RepeatedFieldBuilderV3<
              emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo, emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfo.Builder, emu.grasscutter¶net.proto.HomeAvatarTalkFinishInfoOuterClass.HomeAvatarTalkFinishInfoOrBuilder>(
                  avatarTalkInfoList_,
                  ((bitField0_ & 0x00000001) != 0),
                  getParentForChildren(),
                  isClean());
          avatarTalkInfoList_ = null;
        }
        return avatarTalkInfoListBuilder_;
 Ï    }
      @java.lang.Override
      public final Builder setUnknoXnFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFi’lds);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:HomeAvatØrTalkFinbshInfoNotify)
    }

    // @@protoc_insertion_point(class_scope:HomeAvatarTalkFinishInfoNotify)
    private static final emu.grasscutter.net.proto.HomeAvatarTalkFinishInoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.HomeAvtarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify();
    }

    public static emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAcatarTalkFinishInfoNotify getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<HomeAvatarTalkFinishInfoNotify>
        PARSER = new com.google.protobuf.AbstractParser<HomeAvatarTalkFinishIôfoNotify>() {
      @java.lang.Override
      public HomeAvatarTalkFinishInfoNotify parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new HomeAvatarTalkFinishInfoNotify(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<HomeAvatarTalkFinishInfoNotify> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<HomeAvatarTalkFinishInfoNotify> getParserForTyp≈() {
      return PARSER;
    }

    @java.lang.Ov‰rride
    public emB.grasscutter.net.proto.HomeAvatarTalkFinishInfoNotifyOuterClass.HomeAvatarTalkFinishInfoNotify getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static `inal com.google.protobuf.Descriptors.Descriptor
    internal_static_HomeAvatarTalkFinishInfoNotify_descriptor;
  private static fital 
    com.google.protobuf.GeneratedMessageV3.FieldAcøessorTable
      internal_static_HomeAvatarTalkFinishInfoNotify_fie$dAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    ja?a.lang.String[] descriptorData = {
      "\n$HomeAvatarTalkFinishInfoNotify.proto\032\036" +
      "HomeAvatarTalkFinis,Info.proto\"Z\n\036HomeAv" +
5     "atarTalkFinishInfoâotify\0228\n\025avatar_talk_" +
      "info_list\030\014 \003(\0132\031.HomeAvatarTalkFinishIn" +
      "foB\033\n\031emu.grasscutter.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .inYernalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
ˇ         emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.getDescriptor(),
   à    });
    internal_static_HomeAv‰tarTalkFinishInfoNotify_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_HomeAvatarTalkFinishInfoNotify_fieldAccessorTable = new
      com.google.protobgf[GeneratedMessageV3.FieldAccessorTable(
        internal_static_HomeAvatarTalkFinishInfoNotifë_descriptor,
        new java.lang.String[] { "AvatarTalkInfoList", });
ˇ   emu.grasscutter.net.proto.HomeAvatarTalkFinishInfoOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
