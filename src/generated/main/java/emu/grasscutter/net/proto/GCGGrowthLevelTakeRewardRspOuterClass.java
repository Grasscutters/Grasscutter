// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: GCGGrowthLevelTakeRewardRsp.proto

package emu.grasscutter.net.proto;

public final class GCGGrowthLevelTakeRewardRspOuterClass {
  private GCGGrowthLevelTakeRewardRspOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface GCGGrowthLevelTakeRewardRspOrBuilder extends
      // @@protoc_insertion_point(interface_extends:GCGGrowthLevelTakeRewardRsp)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>repeated uint32 cfajcibjpln = 1;</code>
     * @return A list containing the cfajcibjpln.
     */
    java.util.List<java.lang.Integer> getCfajcibjplnList();
    /**
     * <code>repeated uint32 cfajcibjpln = 1;</code>
     * @return The count of cfajcibjpln.
     */
    int getCfajcibjplnCount();
    /**
     * <code>repeated uint32 cfajcibjpln = 1;</code>
     * @param index The index of the element to return.
     * @return The cfajcibjpln at the given index.
     */
    int getCfajcibjpln(int index);
  }
  /**
   * <pre>
   *enum EGBNAKCLKLL {
   *	option allow_alias= true;
   *	NONE = 0;
   *	PEPPOHPHJOJ = 7338;
   *	DCDNILFDFLB = 0;
   *	NNBKOLMPOEA = 1;
   *}
   * </pre>
   *
   * Protobuf type {@code GCGGrowthLevelTakeRewardRsp}
   */
  public static final class GCGGrowthLevelTakeRewardRsp extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:GCGGrowthLevelTakeRewardRsp)
      GCGGrowthLevelTakeRewardRspOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use GCGGrowthLevelTakeRewardRsp.newBuilder() to construct.
    private GCGGrowthLevelTakeRewardRsp(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private GCGGrowthLevelTakeRewardRsp() {
      cfajcibjpln_ = emptyIntList();
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new GCGGrowthLevelTakeRewardRsp();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private GCGGrowthLevelTakeRewardRsp(
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
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                cfajcibjpln_ = newIntList();
                mutable_bitField0_ |= 0x00000001;
              }
              cfajcibjpln_.addInt(input.readUInt32());
              break;
            }
            case 10: {
              int length = input.readRawVarint32();
              int limit = input.pushLimit(length);
              if (!((mutable_bitField0_ & 0x00000001) != 0) && input.getBytesUntilLimit() > 0) {
                cfajcibjpln_ = newIntList();
                mutable_bitField0_ |= 0x00000001;
              }
              while (input.getBytesUntilLimit() > 0) {
                cfajcibjpln_.addInt(input.readUInt32());
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
          cfajcibjpln_.makeImmutable(); // C
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.internal_static_GCGGrowthLevelTakeRewardRsp_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.internal_static_GCGGrowthLevelTakeRewardRsp_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp.class, emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp.Builder.class);
    }

    public static final int CFAJCIBJPLN_FIELD_NUMBER = 1;
    private com.google.protobuf.Internal.IntList cfajcibjpln_;
    /**
     * <code>repeated uint32 cfajcibjpln = 1;</code>
     * @return A list containing the cfajcibjpln.
     */
    @java.lang.Override
    public java.util.List<java.lang.Integer>
        getCfajcibjplnList() {
      return cfajcibjpln_;
    }
    /**
     * <code>repeated uint32 cfajcibjpln = 1;</code>
     * @return The count of cfajcibjpln.
     */
    public int getCfajcibjplnCount() {
      return cfajcibjpln_.size();
    }
    /**
     * <code>repeated uint32 cfajcibjpln = 1;</code>
     * @param index The index of the element to return.
     * @return The cfajcibjpln at the given index.
     */
    public int getCfajcibjpln(int index) {
      return cfajcibjpln_.getInt(index);
    }
    private int cfajcibjplnMemoizedSerializedSize = -1;

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
      if (getCfajcibjplnList().size() > 0) {
        output.writeUInt32NoTag(10);
        output.writeUInt32NoTag(cfajcibjplnMemoizedSerializedSize);
      }
      for (int i = 0; i < cfajcibjpln_.size(); i++) {
        output.writeUInt32NoTag(cfajcibjpln_.getInt(i));
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      {
        int dataSize = 0;
        for (int i = 0; i < cfajcibjpln_.size(); i++) {
          dataSize += com.google.protobuf.CodedOutputStream
            .computeUInt32SizeNoTag(cfajcibjpln_.getInt(i));
        }
        size += dataSize;
        if (!getCfajcibjplnList().isEmpty()) {
          size += 1;
          size += com.google.protobuf.CodedOutputStream
              .computeInt32SizeNoTag(dataSize);
        }
        cfajcibjplnMemoizedSerializedSize = dataSize;
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
      if (!(obj instanceof emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp other = (emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp) obj;

      if (!getCfajcibjplnList()
          .equals(other.getCfajcibjplnList())) return false;
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
      if (getCfajcibjplnCount() > 0) {
        hash = (37 * hash) + CFAJCIBJPLN_FIELD_NUMBER;
        hash = (53 * hash) + getCfajcibjplnList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp parseFrom(
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
    public static Builder newBuilder(emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp prototype) {
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
     *enum EGBNAKCLKLL {
     *	option allow_alias= true;
     *	NONE = 0;
     *	PEPPOHPHJOJ = 7338;
     *	DCDNILFDFLB = 0;
     *	NNBKOLMPOEA = 1;
     *}
     * </pre>
     *
     * Protobuf type {@code GCGGrowthLevelTakeRewardRsp}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:GCGGrowthLevelTakeRewardRsp)
        emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRspOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.internal_static_GCGGrowthLevelTakeRewardRsp_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.internal_static_GCGGrowthLevelTakeRewardRsp_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp.class, emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp.newBuilder()
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
        cfajcibjpln_ = emptyIntList();
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.internal_static_GCGGrowthLevelTakeRewardRsp_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp build() {
        emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp buildPartial() {
        emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp result = new emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp(this);
        int from_bitField0_ = bitField0_;
        if (((bitField0_ & 0x00000001) != 0)) {
          cfajcibjpln_.makeImmutable();
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.cfajcibjpln_ = cfajcibjpln_;
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
        if (other instanceof emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp) {
          return mergeFrom((emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp other) {
        if (other == emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp.getDefaultInstance()) return this;
        if (!other.cfajcibjpln_.isEmpty()) {
          if (cfajcibjpln_.isEmpty()) {
            cfajcibjpln_ = other.cfajcibjpln_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureCfajcibjplnIsMutable();
            cfajcibjpln_.addAll(other.cfajcibjpln_);
          }
          onChanged();
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
        emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private com.google.protobuf.Internal.IntList cfajcibjpln_ = emptyIntList();
      private void ensureCfajcibjplnIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          cfajcibjpln_ = mutableCopy(cfajcibjpln_);
          bitField0_ |= 0x00000001;
         }
      }
      /**
       * <code>repeated uint32 cfajcibjpln = 1;</code>
       * @return A list containing the cfajcibjpln.
       */
      public java.util.List<java.lang.Integer>
          getCfajcibjplnList() {
        return ((bitField0_ & 0x00000001) != 0) ?
                 java.util.Collections.unmodifiableList(cfajcibjpln_) : cfajcibjpln_;
      }
      /**
       * <code>repeated uint32 cfajcibjpln = 1;</code>
       * @return The count of cfajcibjpln.
       */
      public int getCfajcibjplnCount() {
        return cfajcibjpln_.size();
      }
      /**
       * <code>repeated uint32 cfajcibjpln = 1;</code>
       * @param index The index of the element to return.
       * @return The cfajcibjpln at the given index.
       */
      public int getCfajcibjpln(int index) {
        return cfajcibjpln_.getInt(index);
      }
      /**
       * <code>repeated uint32 cfajcibjpln = 1;</code>
       * @param index The index to set the value at.
       * @param value The cfajcibjpln to set.
       * @return This builder for chaining.
       */
      public Builder setCfajcibjpln(
          int index, int value) {
        ensureCfajcibjplnIsMutable();
        cfajcibjpln_.setInt(index, value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated uint32 cfajcibjpln = 1;</code>
       * @param value The cfajcibjpln to add.
       * @return This builder for chaining.
       */
      public Builder addCfajcibjpln(int value) {
        ensureCfajcibjplnIsMutable();
        cfajcibjpln_.addInt(value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated uint32 cfajcibjpln = 1;</code>
       * @param values The cfajcibjpln to add.
       * @return This builder for chaining.
       */
      public Builder addAllCfajcibjpln(
          java.lang.Iterable<? extends java.lang.Integer> values) {
        ensureCfajcibjplnIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, cfajcibjpln_);
        onChanged();
        return this;
      }
      /**
       * <code>repeated uint32 cfajcibjpln = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearCfajcibjpln() {
        cfajcibjpln_ = emptyIntList();
        bitField0_ = (bitField0_ & ~0x00000001);
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


      // @@protoc_insertion_point(builder_scope:GCGGrowthLevelTakeRewardRsp)
    }

    // @@protoc_insertion_point(class_scope:GCGGrowthLevelTakeRewardRsp)
    private static final emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp();
    }

    public static emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<GCGGrowthLevelTakeRewardRsp>
        PARSER = new com.google.protobuf.AbstractParser<GCGGrowthLevelTakeRewardRsp>() {
      @java.lang.Override
      public GCGGrowthLevelTakeRewardRsp parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new GCGGrowthLevelTakeRewardRsp(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<GCGGrowthLevelTakeRewardRsp> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<GCGGrowthLevelTakeRewardRsp> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.GCGGrowthLevelTakeRewardRspOuterClass.GCGGrowthLevelTakeRewardRsp getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_GCGGrowthLevelTakeRewardRsp_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_GCGGrowthLevelTakeRewardRsp_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n!GCGGrowthLevelTakeRewardRsp.proto\"2\n\033G" +
      "CGGrowthLevelTakeRewardRsp\022\023\n\013cfajcibjpl" +
      "n\030\001 \003(\rB\033\n\031emu.grasscutter.net.protob\006pr" +
      "oto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_GCGGrowthLevelTakeRewardRsp_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_GCGGrowthLevelTakeRewardRsp_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_GCGGrowthLevelTakeRewardRsp_descriptor,
        new java.lang.String[] { "Cfajcibjpln", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
