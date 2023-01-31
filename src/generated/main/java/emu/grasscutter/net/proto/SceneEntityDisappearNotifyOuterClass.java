// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: SceneEntityDisappearNotify.proto

package emu.grasscutter.net.proto;

public final class SceneEntityDisappearNotifyOuterClass {
  private SceneEntityDisappearNotifyOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface SceneEntityDisappearNotifyOrBuilder extends
      // @@protoc_insertion_point(interface_extends:SceneEntityDisappearNotify)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>repeated uint32 entityList = 13;</code>
     * @return A list containing the entityList.
     */
    java.util.List<java.lang.Integer> getEntityListList();
    /**
     * <code>repeated uint32 entityList = 13;</code>
     * @return The count of entityList.
     */
    int getEntityListCount();
    /**
     * <code>repeated uint32 entityList = 13;</code>
     * @param index The index of the element to return.
     * @return The entityList at the given index.
     */
    int getEntityList(int index);

    /**
     * <code>uint32 param = 10;</code>
     * @return The param.
     */
    int getParam();

    /**
     * <code>.VisionType disappearType = 7;</code>
     * @return The enum numeric value on the wire for disappearType.
     */
    int getDisappearTypeValue();
    /**
     * <code>.VisionType disappearType = 7;</code>
     * @return The disappearType.
     */
    emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType getDisappearType();
  }
  /**
   * <pre>
   *enum JBIJGLIONLH {
   *	option allow_alias= true;
   *	NONE = 0;
   *	PEPPOHPHJOJ = 280;
   *	DCDNILFDFLB = 0;
   *	NNBKOLMPOEA = 1;
   *}
   * </pre>
   *
   * Protobuf type {@code SceneEntityDisappearNotify}
   */
  public static final class SceneEntityDisappearNotify extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:SceneEntityDisappearNotify)
      SceneEntityDisappearNotifyOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use SceneEntityDisappearNotify.newBuilder() to construct.
    private SceneEntityDisappearNotify(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private SceneEntityDisappearNotify() {
      entityList_ = emptyIntList();
      disappearType_ = 0;
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new SceneEntityDisappearNotify();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private SceneEntityDisappearNotify(
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
            case 56: {
              int rawValue = input.readEnum();

              disappearType_ = rawValue;
              break;
            }
            case 80: {

              param_ = input.readUInt32();
              break;
            }
            case 104: {
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                entityList_ = newIntList();
                mutable_bitField0_ |= 0x00000001;
              }
              entityList_.addInt(input.readUInt32());
              break;
            }
            case 106: {
              int length = input.readRawVarint32();
              int limit = input.pushLimit(length);
              if (!((mutable_bitField0_ & 0x00000001) != 0) && input.getBytesUntilLimit() > 0) {
                entityList_ = newIntList();
                mutable_bitField0_ |= 0x00000001;
              }
              while (input.getBytesUntilLimit() > 0) {
                entityList_.addInt(input.readUInt32());
              }
              input.popLimit(limit);
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
          entityList_.makeImmutable(); // C
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.internal_static_SceneEntityDisappearNotify_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.internal_static_SceneEntityDisappearNotify_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify.class, emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify.Builder.class);
    }

    public static final int ENTITYLIST_FIELD_NUMBER = 13;
    private com.google.protobuf.Internal.IntList entityList_;
    /**
     * <code>repeated uint32 entityList = 13;</code>
     * @return A list containing the entityList.
     */
    @java.lang.Override
    public java.util.List<java.lang.Integer>
        getEntityListList() {
      return entityList_;
    }
    /**
     * <code>repeated uint32 entityList = 13;</code>
     * @return The count of entityList.
     */
    public int getEntityListCount() {
      return entityList_.size();
    }
    /**
     * <code>repeated uint32 entityList = 13;</code>
     * @param index The index of the element to return.
     * @return The entityList at the given index.
     */
    public int getEntityList(int index) {
      return entityList_.getInt(index);
    }
    private int entityListMemoizedSerializedSize = -1;

    public static final int PARAM_FIELD_NUMBER = 10;
    private int param_;
    /**
     * <code>uint32 param = 10;</code>
     * @return The param.
     */
    @java.lang.Override
    public int getParam() {
      return param_;
    }

    public static final int DISAPPEARTYPE_FIELD_NUMBER = 7;
    private int disappearType_;
    /**
     * <code>.VisionType disappearType = 7;</code>
     * @return The enum numeric value on the wire for disappearType.
     */
    @java.lang.Override public int getDisappearTypeValue() {
      return disappearType_;
    }
    /**
     * <code>.VisionType disappearType = 7;</code>
     * @return The disappearType.
     */
    @java.lang.Override public emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType getDisappearType() {
      @SuppressWarnings("deprecation")
      emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType result = emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType.valueOf(disappearType_);
      return result == null ? emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType.UNRECOGNIZED : result;
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
      getSerializedSize();
      if (disappearType_ != emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType.VISION_TYPE_NONE.getNumber()) {
        output.writeEnum(7, disappearType_);
      }
      if (param_ != 0) {
        output.writeUInt32(10, param_);
      }
      if (getEntityListList().size() > 0) {
        output.writeUInt32NoTag(106);
        output.writeUInt32NoTag(entityListMemoizedSerializedSize);
      }
      for (int i = 0; i < entityList_.size(); i++) {
        output.writeUInt32NoTag(entityList_.getInt(i));
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (disappearType_ != emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType.VISION_TYPE_NONE.getNumber()) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(7, disappearType_);
      }
      if (param_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(10, param_);
      }
      {
        int dataSize = 0;
        for (int i = 0; i < entityList_.size(); i++) {
          dataSize += com.google.protobuf.CodedOutputStream
            .computeUInt32SizeNoTag(entityList_.getInt(i));
        }
        size += dataSize;
        if (!getEntityListList().isEmpty()) {
          size += 1;
          size += com.google.protobuf.CodedOutputStream
              .computeInt32SizeNoTag(dataSize);
        }
        entityListMemoizedSerializedSize = dataSize;
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
      if (!(obj instanceof emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify other = (emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify) obj;

      if (!getEntityListList()
          .equals(other.getEntityListList())) return false;
      if (getParam()
          != other.getParam()) return false;
      if (disappearType_ != other.disappearType_) return false;
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
      if (getEntityListCount() > 0) {
        hash = (37 * hash) + ENTITYLIST_FIELD_NUMBER;
        hash = (53 * hash) + getEntityListList().hashCode();
      }
      hash = (37 * hash) + PARAM_FIELD_NUMBER;
      hash = (53 * hash) + getParam();
      hash = (37 * hash) + DISAPPEARTYPE_FIELD_NUMBER;
      hash = (53 * hash) + disappearType_;
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify parseFrom(
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
    public static Builder newBuilder(emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify prototype) {
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
     *enum JBIJGLIONLH {
     *	option allow_alias= true;
     *	NONE = 0;
     *	PEPPOHPHJOJ = 280;
     *	DCDNILFDFLB = 0;
     *	NNBKOLMPOEA = 1;
     *}
     * </pre>
     *
     * Protobuf type {@code SceneEntityDisappearNotify}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:SceneEntityDisappearNotify)
        emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotifyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.internal_static_SceneEntityDisappearNotify_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.internal_static_SceneEntityDisappearNotify_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify.class, emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify.newBuilder()
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
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        entityList_ = emptyIntList();
        bitField0_ = (bitField0_ & ~0x00000001);
        param_ = 0;

        disappearType_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.internal_static_SceneEntityDisappearNotify_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify build() {
        emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify buildPartial() {
        emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify result = new emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify(this);
        int from_bitField0_ = bitField0_;
        if (((bitField0_ & 0x00000001) != 0)) {
          entityList_.makeImmutable();
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.entityList_ = entityList_;
        result.param_ = param_;
        result.disappearType_ = disappearType_;
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
        if (other instanceof emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify) {
          return mergeFrom((emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify other) {
        if (other == emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify.getDefaultInstance()) return this;
        if (!other.entityList_.isEmpty()) {
          if (entityList_.isEmpty()) {
            entityList_ = other.entityList_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureEntityListIsMutable();
            entityList_.addAll(other.entityList_);
          }
          onChanged();
        }
        if (other.getParam() != 0) {
          setParam(other.getParam());
        }
        if (other.disappearType_ != 0) {
          setDisappearTypeValue(other.getDisappearTypeValue());
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
        emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private com.google.protobuf.Internal.IntList entityList_ = emptyIntList();
      private void ensureEntityListIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          entityList_ = mutableCopy(entityList_);
          bitField0_ |= 0x00000001;
         }
      }
      /**
       * <code>repeated uint32 entityList = 13;</code>
       * @return A list containing the entityList.
       */
      public java.util.List<java.lang.Integer>
          getEntityListList() {
        return ((bitField0_ & 0x00000001) != 0) ?
                 java.util.Collections.unmodifiableList(entityList_) : entityList_;
      }
      /**
       * <code>repeated uint32 entityList = 13;</code>
       * @return The count of entityList.
       */
      public int getEntityListCount() {
        return entityList_.size();
      }
      /**
       * <code>repeated uint32 entityList = 13;</code>
       * @param index The index of the element to return.
       * @return The entityList at the given index.
       */
      public int getEntityList(int index) {
        return entityList_.getInt(index);
      }
      /**
       * <code>repeated uint32 entityList = 13;</code>
       * @param index The index to set the value at.
       * @param value The entityList to set.
       * @return This builder for chaining.
       */
      public Builder setEntityList(
          int index, int value) {
        ensureEntityListIsMutable();
        entityList_.setInt(index, value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated uint32 entityList = 13;</code>
       * @param value The entityList to add.
       * @return This builder for chaining.
       */
      public Builder addEntityList(int value) {
        ensureEntityListIsMutable();
        entityList_.addInt(value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated uint32 entityList = 13;</code>
       * @param values The entityList to add.
       * @return This builder for chaining.
       */
      public Builder addAllEntityList(
          java.lang.Iterable<? extends java.lang.Integer> values) {
        ensureEntityListIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, entityList_);
        onChanged();
        return this;
      }
      /**
       * <code>repeated uint32 entityList = 13;</code>
       * @return This builder for chaining.
       */
      public Builder clearEntityList() {
        entityList_ = emptyIntList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
        return this;
      }

      private int param_ ;
      /**
       * <code>uint32 param = 10;</code>
       * @return The param.
       */
      @java.lang.Override
      public int getParam() {
        return param_;
      }
      /**
       * <code>uint32 param = 10;</code>
       * @param value The param to set.
       * @return This builder for chaining.
       */
      public Builder setParam(int value) {
        
        param_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 param = 10;</code>
       * @return This builder for chaining.
       */
      public Builder clearParam() {
        
        param_ = 0;
        onChanged();
        return this;
      }

      private int disappearType_ = 0;
      /**
       * <code>.VisionType disappearType = 7;</code>
       * @return The enum numeric value on the wire for disappearType.
       */
      @java.lang.Override public int getDisappearTypeValue() {
        return disappearType_;
      }
      /**
       * <code>.VisionType disappearType = 7;</code>
       * @param value The enum numeric value on the wire for disappearType to set.
       * @return This builder for chaining.
       */
      public Builder setDisappearTypeValue(int value) {
        
        disappearType_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>.VisionType disappearType = 7;</code>
       * @return The disappearType.
       */
      @java.lang.Override
      public emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType getDisappearType() {
        @SuppressWarnings("deprecation")
        emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType result = emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType.valueOf(disappearType_);
        return result == null ? emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType.UNRECOGNIZED : result;
      }
      /**
       * <code>.VisionType disappearType = 7;</code>
       * @param value The disappearType to set.
       * @return This builder for chaining.
       */
      public Builder setDisappearType(emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType value) {
        if (value == null) {
          throw new NullPointerException();
        }
        
        disappearType_ = value.getNumber();
        onChanged();
        return this;
      }
      /**
       * <code>.VisionType disappearType = 7;</code>
       * @return This builder for chaining.
       */
      public Builder clearDisappearType() {
        
        disappearType_ = 0;
        onChanged();
        return this;
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


      // @@protoc_insertion_point(builder_scope:SceneEntityDisappearNotify)
    }

    // @@protoc_insertion_point(class_scope:SceneEntityDisappearNotify)
    private static final emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify();
    }

    public static emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<SceneEntityDisappearNotify>
        PARSER = new com.google.protobuf.AbstractParser<SceneEntityDisappearNotify>() {
      @java.lang.Override
      public SceneEntityDisappearNotify parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new SceneEntityDisappearNotify(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<SceneEntityDisappearNotify> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<SceneEntityDisappearNotify> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_SceneEntityDisappearNotify_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_SceneEntityDisappearNotify_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n SceneEntityDisappearNotify.proto\032\020Visi" +
      "onType.proto\"c\n\032SceneEntityDisappearNoti" +
      "fy\022\022\n\nentityList\030\r \003(\r\022\r\n\005param\030\n \001(\r\022\"\n" +
      "\rdisappearType\030\007 \001(\0162\013.VisionTypeB\033\n\031emu" +
      ".grasscutter.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          emu.grasscutter.net.proto.VisionTypeOuterClass.getDescriptor(),
        });
    internal_static_SceneEntityDisappearNotify_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_SceneEntityDisappearNotify_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_SceneEntityDisappearNotify_descriptor,
        new java.lang.String[] { "EntityList", "Param", "DisappearType", });
    emu.grasscutter.net.proto.VisionTypeOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
