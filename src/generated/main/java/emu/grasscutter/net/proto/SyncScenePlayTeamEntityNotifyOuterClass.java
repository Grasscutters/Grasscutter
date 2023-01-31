// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: SyncScenePlayTeamEntityNotify.proto

package emu.grasscutter.net.proto;

public final class SyncScenePlayTeamEntityNotifyOuterClass {
  private SyncScenePlayTeamEntityNotifyOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface SyncScenePlayTeamEntityNotifyOrBuilder extends
      // @@protoc_insertion_point(interface_extends:SyncScenePlayTeamEntityNotify)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>uint32 sceneId = 1;</code>
     * @return The sceneId.
     */
    int getSceneId();

    /**
     * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
     */
    java.util.List<emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo> 
        getEntityInfoListList();
    /**
     * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
     */
    emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo getEntityInfoList(int index);
    /**
     * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
     */
    int getEntityInfoListCount();
    /**
     * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
     */
    java.util.List<? extends emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfoOrBuilder> 
        getEntityInfoListOrBuilderList();
    /**
     * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
     */
    emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfoOrBuilder getEntityInfoListOrBuilder(
        int index);
  }
  /**
   * <pre>
   *enum NNDMFHOFFGI {
   *	option allow_alias= true;
   *	NONE = 0;
   *	PEPPOHPHJOJ = 3227;
   *	DCDNILFDFLB = 0;
   *	NNBKOLMPOEA = 1;
   *}
   * </pre>
   *
   * Protobuf type {@code SyncScenePlayTeamEntityNotify}
   */
  public static final class SyncScenePlayTeamEntityNotify extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:SyncScenePlayTeamEntityNotify)
      SyncScenePlayTeamEntityNotifyOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use SyncScenePlayTeamEntityNotify.newBuilder() to construct.
    private SyncScenePlayTeamEntityNotify(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private SyncScenePlayTeamEntityNotify() {
      entityInfoList_ = java.util.Collections.emptyList();
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new SyncScenePlayTeamEntityNotify();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private SyncScenePlayTeamEntityNotify(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {

              sceneId_ = input.readUInt32();
              break;
            }
            case 114: {
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                entityInfoList_ = new java.util.ArrayList<emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo>();
                mutable_bitField0_ |= 0x00000001;
              }
              entityInfoList_.add(
                  input.readMessage(emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo.parser(), extensionRegistry));
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x00000001) != 0)) {
          entityInfoList_ = java.util.Collections.unmodifiableList(entityInfoList_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.internal_static_SyncScenePlayTeamEntityNotify_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.internal_static_SyncScenePlayTeamEntityNotify_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify.class, emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify.Builder.class);
    }

    public static final int SCENEID_FIELD_NUMBER = 1;
    private int sceneId_;
    /**
     * <code>uint32 sceneId = 1;</code>
     * @return The sceneId.
     */
    @java.lang.Override
    public int getSceneId() {
      return sceneId_;
    }

    public static final int ENTITYINFOLIST_FIELD_NUMBER = 14;
    private java.util.List<emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo> entityInfoList_;
    /**
     * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
     */
    @java.lang.Override
    public java.util.List<emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo> getEntityInfoListList() {
      return entityInfoList_;
    }
    /**
     * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
     */
    @java.lang.Override
    public java.util.List<? extends emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfoOrBuilder> 
        getEntityInfoListOrBuilderList() {
      return entityInfoList_;
    }
    /**
     * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
     */
    @java.lang.Override
    public int getEntityInfoListCount() {
      return entityInfoList_.size();
    }
    /**
     * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
     */
    @java.lang.Override
    public emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo getEntityInfoList(int index) {
      return entityInfoList_.get(index);
    }
    /**
     * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
     */
    @java.lang.Override
    public emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfoOrBuilder getEntityInfoListOrBuilder(
        int index) {
      return entityInfoList_.get(index);
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (sceneId_ != 0) {
        output.writeUInt32(1, sceneId_);
      }
      for (int i = 0; i < entityInfoList_.size(); i++) {
        output.writeMessage(14, entityInfoList_.get(i));
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (sceneId_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(1, sceneId_);
      }
      for (int i = 0; i < entityInfoList_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(14, entityInfoList_.get(i));
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify other = (emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify) obj;

      if (getSceneId()
          != other.getSceneId()) return false;
      if (!getEntityInfoListList()
          .equals(other.getEntityInfoListList())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + SCENEID_FIELD_NUMBER;
      hash = (53 * hash) + getSceneId();
      if (getEntityInfoListCount() > 0) {
        hash = (37 * hash) + ENTITYINFOLIST_FIELD_NUMBER;
        hash = (53 * hash) + getEntityInfoListList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
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
     *enum NNDMFHOFFGI {
     *	option allow_alias= true;
     *	NONE = 0;
     *	PEPPOHPHJOJ = 3227;
     *	DCDNILFDFLB = 0;
     *	NNBKOLMPOEA = 1;
     *}
     * </pre>
     *
     * Protobuf type {@code SyncScenePlayTeamEntityNotify}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:SyncScenePlayTeamEntityNotify)
        emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotifyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.internal_static_SyncScenePlayTeamEntityNotify_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.internal_static_SyncScenePlayTeamEntityNotify_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify.class, emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
          getEntityInfoListFieldBuilder();
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        sceneId_ = 0;

        if (entityInfoListBuilder_ == null) {
          entityInfoList_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          entityInfoListBuilder_.clear();
        }
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.internal_static_SyncScenePlayTeamEntityNotify_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify build() {
        emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify buildPartial() {
        emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify result = new emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify(this);
        int from_bitField0_ = bitField0_;
        result.sceneId_ = sceneId_;
        if (entityInfoListBuilder_ == null) {
          if (((bitField0_ & 0x00000001) != 0)) {
            entityInfoList_ = java.util.Collections.unmodifiableList(entityInfoList_);
            bitField0_ = (bitField0_ & ~0x00000001);
          }
          result.entityInfoList_ = entityInfoList_;
        } else {
          result.entityInfoList_ = entityInfoListBuilder_.build();
        }
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
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
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify) {
          return mergeFrom((emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify other) {
        if (other == emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify.getDefaultInstance()) return this;
        if (other.getSceneId() != 0) {
          setSceneId(other.getSceneId());
        }
        if (entityInfoListBuilder_ == null) {
          if (!other.entityInfoList_.isEmpty()) {
            if (entityInfoList_.isEmpty()) {
              entityInfoList_ = other.entityInfoList_;
              bitField0_ = (bitField0_ & ~0x00000001);
            } else {
              ensureEntityInfoListIsMutable();
              entityInfoList_.addAll(other.entityInfoList_);
            }
            onChanged();
          }
        } else {
          if (!other.entityInfoList_.isEmpty()) {
            if (entityInfoListBuilder_.isEmpty()) {
              entityInfoListBuilder_.dispose();
              entityInfoListBuilder_ = null;
              entityInfoList_ = other.entityInfoList_;
              bitField0_ = (bitField0_ & ~0x00000001);
              entityInfoListBuilder_ = 
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                   getEntityInfoListFieldBuilder() : null;
            } else {
              entityInfoListBuilder_.addAllMessages(other.entityInfoList_);
            }
          }
        }
        this.mergeUnknownFields(other.unknownFields);
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
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private int sceneId_ ;
      /**
       * <code>uint32 sceneId = 1;</code>
       * @return The sceneId.
       */
      @java.lang.Override
      public int getSceneId() {
        return sceneId_;
      }
      /**
       * <code>uint32 sceneId = 1;</code>
       * @param value The sceneId to set.
       * @return This builder for chaining.
       */
      public Builder setSceneId(int value) {
        
        sceneId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 sceneId = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearSceneId() {
        
        sceneId_ = 0;
        onChanged();
        return this;
      }

      private java.util.List<emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo> entityInfoList_ =
        java.util.Collections.emptyList();
      private void ensureEntityInfoListIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          entityInfoList_ = new java.util.ArrayList<emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo>(entityInfoList_);
          bitField0_ |= 0x00000001;
         }
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
          emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo, emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo.Builder, emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfoOrBuilder> entityInfoListBuilder_;

      /**
       * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
       */
      public java.util.List<emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo> getEntityInfoListList() {
        if (entityInfoListBuilder_ == null) {
          return java.util.Collections.unmodifiableList(entityInfoList_);
        } else {
          return entityInfoListBuilder_.getMessageList();
        }
      }
      /**
       * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
       */
      public int getEntityInfoListCount() {
        if (entityInfoListBuilder_ == null) {
          return entityInfoList_.size();
        } else {
          return entityInfoListBuilder_.getCount();
        }
      }
      /**
       * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
       */
      public emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo getEntityInfoList(int index) {
        if (entityInfoListBuilder_ == null) {
          return entityInfoList_.get(index);
        } else {
          return entityInfoListBuilder_.getMessage(index);
        }
      }
      /**
       * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
       */
      public Builder setEntityInfoList(
          int index, emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo value) {
        if (entityInfoListBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureEntityInfoListIsMutable();
          entityInfoList_.set(index, value);
          onChanged();
        } else {
          entityInfoListBuilder_.setMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
       */
      public Builder setEntityInfoList(
          int index, emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo.Builder builderForValue) {
        if (entityInfoListBuilder_ == null) {
          ensureEntityInfoListIsMutable();
          entityInfoList_.set(index, builderForValue.build());
          onChanged();
        } else {
          entityInfoListBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
       */
      public Builder addEntityInfoList(emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo value) {
        if (entityInfoListBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureEntityInfoListIsMutable();
          entityInfoList_.add(value);
          onChanged();
        } else {
          entityInfoListBuilder_.addMessage(value);
        }
        return this;
      }
      /**
       * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
       */
      public Builder addEntityInfoList(
          int index, emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo value) {
        if (entityInfoListBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureEntityInfoListIsMutable();
          entityInfoList_.add(index, value);
          onChanged();
        } else {
          entityInfoListBuilder_.addMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
       */
      public Builder addEntityInfoList(
          emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo.Builder builderForValue) {
        if (entityInfoListBuilder_ == null) {
          ensureEntityInfoListIsMutable();
          entityInfoList_.add(builderForValue.build());
          onChanged();
        } else {
          entityInfoListBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
       */
      public Builder addEntityInfoList(
          int index, emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo.Builder builderForValue) {
        if (entityInfoListBuilder_ == null) {
          ensureEntityInfoListIsMutable();
          entityInfoList_.add(index, builderForValue.build());
          onChanged();
        } else {
          entityInfoListBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
       */
      public Builder addAllEntityInfoList(
          java.lang.Iterable<? extends emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo> values) {
        if (entityInfoListBuilder_ == null) {
          ensureEntityInfoListIsMutable();
          com.google.protobuf.AbstractMessageLite.Builder.addAll(
              values, entityInfoList_);
          onChanged();
        } else {
          entityInfoListBuilder_.addAllMessages(values);
        }
        return this;
      }
      /**
       * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
       */
      public Builder clearEntityInfoList() {
        if (entityInfoListBuilder_ == null) {
          entityInfoList_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
          onChanged();
        } else {
          entityInfoListBuilder_.clear();
        }
        return this;
      }
      /**
       * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
       */
      public Builder removeEntityInfoList(int index) {
        if (entityInfoListBuilder_ == null) {
          ensureEntityInfoListIsMutable();
          entityInfoList_.remove(index);
          onChanged();
        } else {
          entityInfoListBuilder_.remove(index);
        }
        return this;
      }
      /**
       * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
       */
      public emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo.Builder getEntityInfoListBuilder(
          int index) {
        return getEntityInfoListFieldBuilder().getBuilder(index);
      }
      /**
       * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
       */
      public emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfoOrBuilder getEntityInfoListOrBuilder(
          int index) {
        if (entityInfoListBuilder_ == null) {
          return entityInfoList_.get(index);  } else {
          return entityInfoListBuilder_.getMessageOrBuilder(index);
        }
      }
      /**
       * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
       */
      public java.util.List<? extends emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfoOrBuilder> 
           getEntityInfoListOrBuilderList() {
        if (entityInfoListBuilder_ != null) {
          return entityInfoListBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(entityInfoList_);
        }
      }
      /**
       * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
       */
      public emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo.Builder addEntityInfoListBuilder() {
        return getEntityInfoListFieldBuilder().addBuilder(
            emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo.getDefaultInstance());
      }
      /**
       * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
       */
      public emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo.Builder addEntityInfoListBuilder(
          int index) {
        return getEntityInfoListFieldBuilder().addBuilder(
            index, emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo.getDefaultInstance());
      }
      /**
       * <code>repeated .PlayTeamEntityInfo entityInfoList = 14;</code>
       */
      public java.util.List<emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo.Builder> 
           getEntityInfoListBuilderList() {
        return getEntityInfoListFieldBuilder().getBuilderList();
      }
      private com.google.protobuf.RepeatedFieldBuilderV3<
          emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo, emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo.Builder, emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfoOrBuilder> 
          getEntityInfoListFieldBuilder() {
        if (entityInfoListBuilder_ == null) {
          entityInfoListBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
              emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo, emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfo.Builder, emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.PlayTeamEntityInfoOrBuilder>(
                  entityInfoList_,
                  ((bitField0_ & 0x00000001) != 0),
                  getParentForChildren(),
                  isClean());
          entityInfoList_ = null;
        }
        return entityInfoListBuilder_;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:SyncScenePlayTeamEntityNotify)
    }

    // @@protoc_insertion_point(class_scope:SyncScenePlayTeamEntityNotify)
    private static final emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify();
    }

    public static emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<SyncScenePlayTeamEntityNotify>
        PARSER = new com.google.protobuf.AbstractParser<SyncScenePlayTeamEntityNotify>() {
      @java.lang.Override
      public SyncScenePlayTeamEntityNotify parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new SyncScenePlayTeamEntityNotify(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<SyncScenePlayTeamEntityNotify> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<SyncScenePlayTeamEntityNotify> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.SyncScenePlayTeamEntityNotifyOuterClass.SyncScenePlayTeamEntityNotify getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_SyncScenePlayTeamEntityNotify_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_SyncScenePlayTeamEntityNotify_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n#SyncScenePlayTeamEntityNotify.proto\032\030P" +
      "layTeamEntityInfo.proto\"]\n\035SyncScenePlay" +
      "TeamEntityNotify\022\017\n\007sceneId\030\001 \001(\r\022+\n\016ent" +
      "ityInfoList\030\016 \003(\0132\023.PlayTeamEntityInfoB\033" +
      "\n\031emu.grasscutter.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.getDescriptor(),
        });
    internal_static_SyncScenePlayTeamEntityNotify_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_SyncScenePlayTeamEntityNotify_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_SyncScenePlayTeamEntityNotify_descriptor,
        new java.lang.String[] { "SceneId", "EntityInfoList", });
    emu.grasscutter.net.proto.PlayTeamEntityInfoOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
