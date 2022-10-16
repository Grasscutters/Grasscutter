// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: GetChatEmojiCollectionRsp.proto

package emu.grasscutter.net.proto;

public final class GetChatEmojiCollectionRspOuterClass {
  private GetChatEmojiCollectionRspOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface GetChatEmojiCollectionRspOrBuilder extends
      // @@protoc_insertion_point(interface_extends:GetChatEmojiCollectionRsp)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int32 retcode = 15;</code>
     * @return The retcode.
     */
    int getRetcode();

    /**
     * <code>.ChatEmojiCollectionData chat_emoji_collection_data = 8;</code>
     * @return Whether the chatEmojiCollectionData field is set.
     */
    boolean hasChatEmojiCollectionData();
    /**
     * <code>.ChatEmojiCollectionData chat_emoji_collection_data = 8;</code>
     * @return The chatEmojiCollectionData.
     */
    emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData getChatEmojiCollectionData();
    /**
     * <code>.ChatEmojiCollectionData chat_emoji_collection_data = 8;</code>
     */
    emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionDataOrBuilder getChatEmojiCollectionDataOrBuilder();
  }
  /**
   * <pre>
   * CmdId: 4033
   * EnetChannelId: 0
   * EnetIsReliable: true
   * </pre>
   *
   * Protobuf type {@code GetChatEmojiCollectionRsp}
   */
  public static final class GetChatEmojiCollectionRsp extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:GetChatEmojiCollectionRsp)
      GetChatEmojiCollectionRspOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use GetChatEmojiCollectionRsp.newBuilder() to construct.
    private GetChatEmojiCollectionRsp(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private GetChatEmojiCollectionRsp() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new GetChatEmojiCollectionRsp();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private GetChatEmojiCollectionRsp(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
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
            case 66: {
              emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData.Builder subBuilder = null;
              if (chatEmojiCollectionData_ != null) {
                subBuilder = chatEmojiCollectionData_.toBuilder();
              }
              chatEmojiCollectionData_ = input.readMessage(emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(chatEmojiCollectionData_);
                chatEmojiCollectionData_ = subBuilder.buildPartial();
              }

              break;
            }
            case 120: {

              retcode_ = input.readInt32();
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
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.internal_static_GetChatEmojiCollectionRsp_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.internal_static_GetChatEmojiCollectionRsp_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp.class, emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp.Builder.class);
    }

    public static final int RETCODE_FIELD_NUMBER = 15;
    private int retcode_;
    /**
     * <code>int32 retcode = 15;</code>
     * @return The retcode.
     */
    @java.lang.Override
    public int getRetcode() {
      return retcode_;
    }

    public static final int CHAT_EMOJI_COLLECTION_DATA_FIELD_NUMBER = 8;
    private emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData chatEmojiCollectionData_;
    /**
     * <code>.ChatEmojiCollectionData chat_emoji_collection_data = 8;</code>
     * @return Whether the chatEmojiCollectionData field is set.
     */
    @java.lang.Override
    public boolean hasChatEmojiCollectionData() {
      return chatEmojiCollectionData_ != null;
    }
    /**
     * <code>.ChatEmojiCollectionData chat_emoji_collection_data = 8;</code>
     * @return The chatEmojiCollectionData.
     */
    @java.lang.Override
    public emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData getChatEmojiCollectionData() {
      return chatEmojiCollectionData_ == null ? emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData.getDefaultInstance() : chatEmojiCollectionData_;
    }
    /**
     * <code>.ChatEmojiCollectionData chat_emoji_collection_data = 8;</code>
     */
    @java.lang.Override
    public emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionDataOrBuilder getChatEmojiCollectionDataOrBuilder() {
      return getChatEmojiCollectionData();
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
      if (chatEmojiCollectionData_ != null) {
        output.writeMessage(8, getChatEmojiCollectionData());
      }
      if (retcode_ != 0) {
        output.writeInt32(15, retcode_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (chatEmojiCollectionData_ != null) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(8, getChatEmojiCollectionData());
      }
      if (retcode_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(15, retcode_);
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
      if (!(obj instanceof emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp other = (emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp) obj;

      if (getRetcode()
          != other.getRetcode()) return false;
      if (hasChatEmojiCollectionData() != other.hasChatEmojiCollectionData()) return false;
      if (hasChatEmojiCollectionData()) {
        if (!getChatEmojiCollectionData()
            .equals(other.getChatEmojiCollectionData())) return false;
      }
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
      if (hasChatEmojiCollectionData()) {
        hash = (37 * hash) + CHAT_EMOJI_COLLECTION_DATA_FIELD_NUMBER;
        hash = (53 * hash) + getChatEmojiCollectionData().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp parseFrom(
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
    public static Builder newBuilder(emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp prototype) {
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
     * CmdId: 4033
     * EnetChannelId: 0
     * EnetIsReliable: true
     * </pre>
     *
     * Protobuf type {@code GetChatEmojiCollectionRsp}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:GetChatEmojiCollectionRsp)
        emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRspOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.internal_static_GetChatEmojiCollectionRsp_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.internal_static_GetChatEmojiCollectionRsp_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp.class, emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp.newBuilder()
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

        if (chatEmojiCollectionDataBuilder_ == null) {
          chatEmojiCollectionData_ = null;
        } else {
          chatEmojiCollectionData_ = null;
          chatEmojiCollectionDataBuilder_ = null;
        }
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.internal_static_GetChatEmojiCollectionRsp_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp build() {
        emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp buildPartial() {
        emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp result = new emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp(this);
        result.retcode_ = retcode_;
        if (chatEmojiCollectionDataBuilder_ == null) {
          result.chatEmojiCollectionData_ = chatEmojiCollectionData_;
        } else {
          result.chatEmojiCollectionData_ = chatEmojiCollectionDataBuilder_.build();
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
        if (other instanceof emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp) {
          return mergeFrom((emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp other) {
        if (other == emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp.getDefaultInstance()) return this;
        if (other.getRetcode() != 0) {
          setRetcode(other.getRetcode());
        }
        if (other.hasChatEmojiCollectionData()) {
          mergeChatEmojiCollectionData(other.getChatEmojiCollectionData());
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
        emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int retcode_ ;
      /**
       * <code>int32 retcode = 15;</code>
       * @return The retcode.
       */
      @java.lang.Override
      public int getRetcode() {
        return retcode_;
      }
      /**
       * <code>int32 retcode = 15;</code>
       * @param value The retcode to set.
       * @return This builder for chaining.
       */
      public Builder setRetcode(int value) {
        
        retcode_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 retcode = 15;</code>
       * @return This builder for chaining.
       */
      public Builder clearRetcode() {
        
        retcode_ = 0;
        onChanged();
        return this;
      }

      private emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData chatEmojiCollectionData_;
      private com.google.protobuf.SingleFieldBuilderV3<
          emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData, emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData.Builder, emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionDataOrBuilder> chatEmojiCollectionDataBuilder_;
      /**
       * <code>.ChatEmojiCollectionData chat_emoji_collection_data = 8;</code>
       * @return Whether the chatEmojiCollectionData field is set.
       */
      public boolean hasChatEmojiCollectionData() {
        return chatEmojiCollectionDataBuilder_ != null || chatEmojiCollectionData_ != null;
      }
      /**
       * <code>.ChatEmojiCollectionData chat_emoji_collection_data = 8;</code>
       * @return The chatEmojiCollectionData.
       */
      public emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData getChatEmojiCollectionData() {
        if (chatEmojiCollectionDataBuilder_ == null) {
          return chatEmojiCollectionData_ == null ? emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData.getDefaultInstance() : chatEmojiCollectionData_;
        } else {
          return chatEmojiCollectionDataBuilder_.getMessage();
        }
      }
      /**
       * <code>.ChatEmojiCollectionData chat_emoji_collection_data = 8;</code>
       */
      public Builder setChatEmojiCollectionData(emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData value) {
        if (chatEmojiCollectionDataBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          chatEmojiCollectionData_ = value;
          onChanged();
        } else {
          chatEmojiCollectionDataBuilder_.setMessage(value);
        }

        return this;
      }
      /**
       * <code>.ChatEmojiCollectionData chat_emoji_collection_data = 8;</code>
       */
      public Builder setChatEmojiCollectionData(
          emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData.Builder builderForValue) {
        if (chatEmojiCollectionDataBuilder_ == null) {
          chatEmojiCollectionData_ = builderForValue.build();
          onChanged();
        } else {
          chatEmojiCollectionDataBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /**
       * <code>.ChatEmojiCollectionData chat_emoji_collection_data = 8;</code>
       */
      public Builder mergeChatEmojiCollectionData(emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData value) {
        if (chatEmojiCollectionDataBuilder_ == null) {
          if (chatEmojiCollectionData_ != null) {
            chatEmojiCollectionData_ =
              emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData.newBuilder(chatEmojiCollectionData_).mergeFrom(value).buildPartial();
          } else {
            chatEmojiCollectionData_ = value;
          }
          onChanged();
        } else {
          chatEmojiCollectionDataBuilder_.mergeFrom(value);
        }

        return this;
      }
      /**
       * <code>.ChatEmojiCollectionData chat_emoji_collection_data = 8;</code>
       */
      public Builder clearChatEmojiCollectionData() {
        if (chatEmojiCollectionDataBuilder_ == null) {
          chatEmojiCollectionData_ = null;
          onChanged();
        } else {
          chatEmojiCollectionData_ = null;
          chatEmojiCollectionDataBuilder_ = null;
        }

        return this;
      }
      /**
       * <code>.ChatEmojiCollectionData chat_emoji_collection_data = 8;</code>
       */
      public emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData.Builder getChatEmojiCollectionDataBuilder() {
        
        onChanged();
        return getChatEmojiCollectionDataFieldBuilder().getBuilder();
      }
      /**
       * <code>.ChatEmojiCollectionData chat_emoji_collection_data = 8;</code>
       */
      public emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionDataOrBuilder getChatEmojiCollectionDataOrBuilder() {
        if (chatEmojiCollectionDataBuilder_ != null) {
          return chatEmojiCollectionDataBuilder_.getMessageOrBuilder();
        } else {
          return chatEmojiCollectionData_ == null ?
              emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData.getDefaultInstance() : chatEmojiCollectionData_;
        }
      }
      /**
       * <code>.ChatEmojiCollectionData chat_emoji_collection_data = 8;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData, emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData.Builder, emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionDataOrBuilder> 
          getChatEmojiCollectionDataFieldBuilder() {
        if (chatEmojiCollectionDataBuilder_ == null) {
          chatEmojiCollectionDataBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData, emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData.Builder, emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionDataOrBuilder>(
                  getChatEmojiCollectionData(),
                  getParentForChildren(),
                  isClean());
          chatEmojiCollectionData_ = null;
        }
        return chatEmojiCollectionDataBuilder_;
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


      // @@protoc_insertion_point(builder_scope:GetChatEmojiCollectionRsp)
    }

    // @@protoc_insertion_point(class_scope:GetChatEmojiCollectionRsp)
    private static final emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp();
    }

    public static emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<GetChatEmojiCollectionRsp>
        PARSER = new com.google.protobuf.AbstractParser<GetChatEmojiCollectionRsp>() {
      @java.lang.Override
      public GetChatEmojiCollectionRsp parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new GetChatEmojiCollectionRsp(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<GetChatEmojiCollectionRsp> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<GetChatEmojiCollectionRsp> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_GetChatEmojiCollectionRsp_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_GetChatEmojiCollectionRsp_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\037GetChatEmojiCollectionRsp.proto\032\035ChatE" +
      "mojiCollectionData.proto\"j\n\031GetChatEmoji" +
      "CollectionRsp\022\017\n\007retcode\030\017 \001(\005\022<\n\032chat_e" +
      "moji_collection_data\030\010 \001(\0132\030.ChatEmojiCo" +
      "llectionDataB\033\n\031emu.grasscutter.net.prot" +
      "ob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.getDescriptor(),
        });
    internal_static_GetChatEmojiCollectionRsp_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_GetChatEmojiCollectionRsp_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_GetChatEmojiCollectionRsp_descriptor,
        new java.lang.String[] { "Retcode", "ChatEmojiCollectionData", });
    emu.grasscutter.net.proto.ChatEmojiCollectionDataOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
