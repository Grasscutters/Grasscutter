// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: DelMailRsp.proto

package emu.grasscutter.net.proto;

public final class DelMailRspOuterClass {
  private DelMailRspOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface DelMailRspOrBuilder extends
      // @@protoc_insertion_point(interface_extends:DelMailRsp)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int32 retcode = 2;</code>
     * @return The retcode.
     */
    int getRetcode();

    /**
     * <code>repeated uint32 mail_id_list = 11;</code>
     * @return A list containing the mailIdList.
     */
    java.util.List<java.lang.Integer> getMailIdListList();
    /**
     * <code>repeated uint32 mail_id_list = 11;</code>
     * @return The count of mailIdList.
     */
    int getMailIdListCount();
    /**
     * <code>repeated uint32 mail_id_list = 11;</code>
     * @param index The index of the element to return.
     * @return The mailIdList at the given index.
     */
    int getMailIdList(int index);
  }
  /**
   * <pre>
   * enum CmdId {
   *   option allow_alias = true;
   *   NONE = 0;
   *   CMD_ID = 1461;
   *   ENET_CHANNEL_ID = 0;
   *   ENET_IS_RELIABLE = 1;
   * }
   * </pre>
   *
   * Protobuf type {@code DelMailRsp}
   */
  public static final class DelMailRsp extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:DelMailRsp)
      DelMailRspOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use DelMailRsp.newBuilder() to construct.
    private DelMailRsp(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private DelMailRsp() {
      mailIdList_ = emptyIntList();
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new DelMailRsp();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private DelMailRsp(
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
            case 16: {

              retcode_ = input.readInt32();
              break;
            }
            case 88: {
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                mailIdList_ = newIntList();
                mutable_bitField0_ |= 0x00000001;
              }
              mailIdList_.addInt(input.readUInt32());
              break;
            }
            case 90: {
              int length = input.readRawVarint32();
              int limit = input.pushLimit(length);
              if (!((mutable_bitField0_ & 0x00000001) != 0) && input.getBytesUntilLimit() > 0) {
                mailIdList_ = newIntList();
                mutable_bitField0_ |= 0x00000001;
              }
              while (input.getBytesUntilLimit() > 0) {
                mailIdList_.addInt(input.readUInt32());
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
          mailIdList_.makeImmutable(); // C
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return emu.grasscutter.net.proto.DelMailRspOuterClass.internal_static_DelMailRsp_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.DelMailRspOuterClass.internal_static_DelMailRsp_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp.class, emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp.Builder.class);
    }

    public static final int RETCODE_FIELD_NUMBER = 2;
    private int retcode_;
    /**
     * <code>int32 retcode = 2;</code>
     * @return The retcode.
     */
    @java.lang.Override
    public int getRetcode() {
      return retcode_;
    }

    public static final int MAIL_ID_LIST_FIELD_NUMBER = 11;
    private com.google.protobuf.Internal.IntList mailIdList_;
    /**
     * <code>repeated uint32 mail_id_list = 11;</code>
     * @return A list containing the mailIdList.
     */
    @java.lang.Override
    public java.util.List<java.lang.Integer>
        getMailIdListList() {
      return mailIdList_;
    }
    /**
     * <code>repeated uint32 mail_id_list = 11;</code>
     * @return The count of mailIdList.
     */
    public int getMailIdListCount() {
      return mailIdList_.size();
    }
    /**
     * <code>repeated uint32 mail_id_list = 11;</code>
     * @param index The index of the element to return.
     * @return The mailIdList at the given index.
     */
    public int getMailIdList(int index) {
      return mailIdList_.getInt(index);
    }
    private int mailIdListMemoizedSerializedSize = -1;

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
      if (retcode_ != 0) {
        output.writeInt32(2, retcode_);
      }
      if (getMailIdListList().size() > 0) {
        output.writeUInt32NoTag(90);
        output.writeUInt32NoTag(mailIdListMemoizedSerializedSize);
      }
      for (int i = 0; i < mailIdList_.size(); i++) {
        output.writeUInt32NoTag(mailIdList_.getInt(i));
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (retcode_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, retcode_);
      }
      {
        int dataSize = 0;
        for (int i = 0; i < mailIdList_.size(); i++) {
          dataSize += com.google.protobuf.CodedOutputStream
            .computeUInt32SizeNoTag(mailIdList_.getInt(i));
        }
        size += dataSize;
        if (!getMailIdListList().isEmpty()) {
          size += 1;
          size += com.google.protobuf.CodedOutputStream
              .computeInt32SizeNoTag(dataSize);
        }
        mailIdListMemoizedSerializedSize = dataSize;
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
      if (!(obj instanceof emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp other = (emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp) obj;

      if (getRetcode()
          != other.getRetcode()) return false;
      if (!getMailIdListList()
          .equals(other.getMailIdListList())) return false;
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
      hash = (37 * hash) + RETCODE_FIELD_NUMBER;
      hash = (53 * hash) + getRetcode();
      if (getMailIdListCount() > 0) {
        hash = (37 * hash) + MAIL_ID_LIST_FIELD_NUMBER;
        hash = (53 * hash) + getMailIdListList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp parseFrom(
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
    public static Builder newBuilder(emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp prototype) {
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
     * enum CmdId {
     *   option allow_alias = true;
     *   NONE = 0;
     *   CMD_ID = 1461;
     *   ENET_CHANNEL_ID = 0;
     *   ENET_IS_RELIABLE = 1;
     * }
     * </pre>
     *
     * Protobuf type {@code DelMailRsp}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:DelMailRsp)
        emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRspOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.DelMailRspOuterClass.internal_static_DelMailRsp_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.DelMailRspOuterClass.internal_static_DelMailRsp_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp.class, emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp.newBuilder()
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
        retcode_ = 0;

        mailIdList_ = emptyIntList();
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.DelMailRspOuterClass.internal_static_DelMailRsp_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp build() {
        emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp buildPartial() {
        emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp result = new emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp(this);
        int from_bitField0_ = bitField0_;
        result.retcode_ = retcode_;
        if (((bitField0_ & 0x00000001) != 0)) {
          mailIdList_.makeImmutable();
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.mailIdList_ = mailIdList_;
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
        if (other instanceof emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp) {
          return mergeFrom((emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp other) {
        if (other == emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp.getDefaultInstance()) return this;
        if (other.getRetcode() != 0) {
          setRetcode(other.getRetcode());
        }
        if (!other.mailIdList_.isEmpty()) {
          if (mailIdList_.isEmpty()) {
            mailIdList_ = other.mailIdList_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureMailIdListIsMutable();
            mailIdList_.addAll(other.mailIdList_);
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
        emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private int retcode_ ;
      /**
       * <code>int32 retcode = 2;</code>
       * @return The retcode.
       */
      @java.lang.Override
      public int getRetcode() {
        return retcode_;
      }
      /**
       * <code>int32 retcode = 2;</code>
       * @param value The retcode to set.
       * @return This builder for chaining.
       */
      public Builder setRetcode(int value) {
        
        retcode_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 retcode = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearRetcode() {
        
        retcode_ = 0;
        onChanged();
        return this;
      }

      private com.google.protobuf.Internal.IntList mailIdList_ = emptyIntList();
      private void ensureMailIdListIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          mailIdList_ = mutableCopy(mailIdList_);
          bitField0_ |= 0x00000001;
         }
      }
      /**
       * <code>repeated uint32 mail_id_list = 11;</code>
       * @return A list containing the mailIdList.
       */
      public java.util.List<java.lang.Integer>
          getMailIdListList() {
        return ((bitField0_ & 0x00000001) != 0) ?
                 java.util.Collections.unmodifiableList(mailIdList_) : mailIdList_;
      }
      /**
       * <code>repeated uint32 mail_id_list = 11;</code>
       * @return The count of mailIdList.
       */
      public int getMailIdListCount() {
        return mailIdList_.size();
      }
      /**
       * <code>repeated uint32 mail_id_list = 11;</code>
       * @param index The index of the element to return.
       * @return The mailIdList at the given index.
       */
      public int getMailIdList(int index) {
        return mailIdList_.getInt(index);
      }
      /**
       * <code>repeated uint32 mail_id_list = 11;</code>
       * @param index The index to set the value at.
       * @param value The mailIdList to set.
       * @return This builder for chaining.
       */
      public Builder setMailIdList(
          int index, int value) {
        ensureMailIdListIsMutable();
        mailIdList_.setInt(index, value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated uint32 mail_id_list = 11;</code>
       * @param value The mailIdList to add.
       * @return This builder for chaining.
       */
      public Builder addMailIdList(int value) {
        ensureMailIdListIsMutable();
        mailIdList_.addInt(value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated uint32 mail_id_list = 11;</code>
       * @param values The mailIdList to add.
       * @return This builder for chaining.
       */
      public Builder addAllMailIdList(
          java.lang.Iterable<? extends java.lang.Integer> values) {
        ensureMailIdListIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, mailIdList_);
        onChanged();
        return this;
      }
      /**
       * <code>repeated uint32 mail_id_list = 11;</code>
       * @return This builder for chaining.
       */
      public Builder clearMailIdList() {
        mailIdList_ = emptyIntList();
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


      // @@protoc_insertion_point(builder_scope:DelMailRsp)
    }

    // @@protoc_insertion_point(class_scope:DelMailRsp)
    private static final emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp();
    }

    public static emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<DelMailRsp>
        PARSER = new com.google.protobuf.AbstractParser<DelMailRsp>() {
      @java.lang.Override
      public DelMailRsp parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new DelMailRsp(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<DelMailRsp> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<DelMailRsp> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_DelMailRsp_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DelMailRsp_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\020DelMailRsp.proto\"3\n\nDelMailRsp\022\017\n\007retc" +
      "ode\030\002 \001(\005\022\024\n\014mail_id_list\030\013 \003(\rB\033\n\031emu.g" +
      "rasscutter.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_DelMailRsp_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_DelMailRsp_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_DelMailRsp_descriptor,
        new java.lang.String[] { "Retcode", "MailIdList", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
