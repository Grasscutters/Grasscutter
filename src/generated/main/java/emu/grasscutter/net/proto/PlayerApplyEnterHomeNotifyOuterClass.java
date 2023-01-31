// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: PlayerApplyEnterHomeNotify.proto

package emu.grasscutter.net.proto;

public final class PlayerApplyEnterHomeNotifyOuterClass {
  private PlayerApplyEnterHomeNotifyOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface PlayerApplyEnterHomeNotifyOrBuilder extends
      // @@protoc_insertion_point(interface_extends:PlayerApplyEnterHomeNotify)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.OnlinePlayerInfo srcPlayerInfo = 7;</code>
     * @return Whether the srcPlayerInfo field is set.
     */
    boolean hasSrcPlayerInfo();
    /**
     * <code>.OnlinePlayerInfo srcPlayerInfo = 7;</code>
     * @return The srcPlayerInfo.
     */
    emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo getSrcPlayerInfo();
    /**
     * <code>.OnlinePlayerInfo srcPlayerInfo = 7;</code>
     */
    emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfoOrBuilder getSrcPlayerInfoOrBuilder();

    /**
     * <code>uint32 srcAppId = 13;</code>
     * @return The srcAppId.
     */
    int getSrcAppId();
  }
  /**
   * <pre>
   *enum DDJPAJOBNFM {
   *	option allow_alias= true;
   *	NONE = 0;
   *	PEPPOHPHJOJ = 4771;
   *	DCDNILFDFLB = 0;
   *	NNBKOLMPOEA = 1;
   *}
   * </pre>
   *
   * Protobuf type {@code PlayerApplyEnterHomeNotify}
   */
  public static final class PlayerApplyEnterHomeNotify extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:PlayerApplyEnterHomeNotify)
      PlayerApplyEnterHomeNotifyOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use PlayerApplyEnterHomeNotify.newBuilder() to construct.
    private PlayerApplyEnterHomeNotify(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private PlayerApplyEnterHomeNotify() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new PlayerApplyEnterHomeNotify();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private PlayerApplyEnterHomeNotify(
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
            case 58: {
              emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo.Builder subBuilder = null;
              if (srcPlayerInfo_ != null) {
                subBuilder = srcPlayerInfo_.toBuilder();
              }
              srcPlayerInfo_ = input.readMessage(emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(srcPlayerInfo_);
                srcPlayerInfo_ = subBuilder.buildPartial();
              }

              break;
            }
            case 104: {

              srcAppId_ = input.readUInt32();
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
      return emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.internal_static_PlayerApplyEnterHomeNotify_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.internal_static_PlayerApplyEnterHomeNotify_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify.class, emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify.Builder.class);
    }

    public static final int SRCPLAYERINFO_FIELD_NUMBER = 7;
    private emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo srcPlayerInfo_;
    /**
     * <code>.OnlinePlayerInfo srcPlayerInfo = 7;</code>
     * @return Whether the srcPlayerInfo field is set.
     */
    @java.lang.Override
    public boolean hasSrcPlayerInfo() {
      return srcPlayerInfo_ != null;
    }
    /**
     * <code>.OnlinePlayerInfo srcPlayerInfo = 7;</code>
     * @return The srcPlayerInfo.
     */
    @java.lang.Override
    public emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo getSrcPlayerInfo() {
      return srcPlayerInfo_ == null ? emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo.getDefaultInstance() : srcPlayerInfo_;
    }
    /**
     * <code>.OnlinePlayerInfo srcPlayerInfo = 7;</code>
     */
    @java.lang.Override
    public emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfoOrBuilder getSrcPlayerInfoOrBuilder() {
      return getSrcPlayerInfo();
    }

    public static final int SRCAPPID_FIELD_NUMBER = 13;
    private int srcAppId_;
    /**
     * <code>uint32 srcAppId = 13;</code>
     * @return The srcAppId.
     */
    @java.lang.Override
    public int getSrcAppId() {
      return srcAppId_;
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
      if (srcPlayerInfo_ != null) {
        output.writeMessage(7, getSrcPlayerInfo());
      }
      if (srcAppId_ != 0) {
        output.writeUInt32(13, srcAppId_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (srcPlayerInfo_ != null) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(7, getSrcPlayerInfo());
      }
      if (srcAppId_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(13, srcAppId_);
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
      if (!(obj instanceof emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify other = (emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify) obj;

      if (hasSrcPlayerInfo() != other.hasSrcPlayerInfo()) return false;
      if (hasSrcPlayerInfo()) {
        if (!getSrcPlayerInfo()
            .equals(other.getSrcPlayerInfo())) return false;
      }
      if (getSrcAppId()
          != other.getSrcAppId()) return false;
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
      if (hasSrcPlayerInfo()) {
        hash = (37 * hash) + SRCPLAYERINFO_FIELD_NUMBER;
        hash = (53 * hash) + getSrcPlayerInfo().hashCode();
      }
      hash = (37 * hash) + SRCAPPID_FIELD_NUMBER;
      hash = (53 * hash) + getSrcAppId();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify parseFrom(
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
    public static Builder newBuilder(emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify prototype) {
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
     *enum DDJPAJOBNFM {
     *	option allow_alias= true;
     *	NONE = 0;
     *	PEPPOHPHJOJ = 4771;
     *	DCDNILFDFLB = 0;
     *	NNBKOLMPOEA = 1;
     *}
     * </pre>
     *
     * Protobuf type {@code PlayerApplyEnterHomeNotify}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:PlayerApplyEnterHomeNotify)
        emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotifyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.internal_static_PlayerApplyEnterHomeNotify_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.internal_static_PlayerApplyEnterHomeNotify_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify.class, emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify.newBuilder()
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
        if (srcPlayerInfoBuilder_ == null) {
          srcPlayerInfo_ = null;
        } else {
          srcPlayerInfo_ = null;
          srcPlayerInfoBuilder_ = null;
        }
        srcAppId_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.internal_static_PlayerApplyEnterHomeNotify_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify build() {
        emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify buildPartial() {
        emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify result = new emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify(this);
        if (srcPlayerInfoBuilder_ == null) {
          result.srcPlayerInfo_ = srcPlayerInfo_;
        } else {
          result.srcPlayerInfo_ = srcPlayerInfoBuilder_.build();
        }
        result.srcAppId_ = srcAppId_;
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
        if (other instanceof emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify) {
          return mergeFrom((emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify other) {
        if (other == emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify.getDefaultInstance()) return this;
        if (other.hasSrcPlayerInfo()) {
          mergeSrcPlayerInfo(other.getSrcPlayerInfo());
        }
        if (other.getSrcAppId() != 0) {
          setSrcAppId(other.getSrcAppId());
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
        emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo srcPlayerInfo_;
      private com.google.protobuf.SingleFieldBuilderV3<
          emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo, emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo.Builder, emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfoOrBuilder> srcPlayerInfoBuilder_;
      /**
       * <code>.OnlinePlayerInfo srcPlayerInfo = 7;</code>
       * @return Whether the srcPlayerInfo field is set.
       */
      public boolean hasSrcPlayerInfo() {
        return srcPlayerInfoBuilder_ != null || srcPlayerInfo_ != null;
      }
      /**
       * <code>.OnlinePlayerInfo srcPlayerInfo = 7;</code>
       * @return The srcPlayerInfo.
       */
      public emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo getSrcPlayerInfo() {
        if (srcPlayerInfoBuilder_ == null) {
          return srcPlayerInfo_ == null ? emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo.getDefaultInstance() : srcPlayerInfo_;
        } else {
          return srcPlayerInfoBuilder_.getMessage();
        }
      }
      /**
       * <code>.OnlinePlayerInfo srcPlayerInfo = 7;</code>
       */
      public Builder setSrcPlayerInfo(emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo value) {
        if (srcPlayerInfoBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          srcPlayerInfo_ = value;
          onChanged();
        } else {
          srcPlayerInfoBuilder_.setMessage(value);
        }

        return this;
      }
      /**
       * <code>.OnlinePlayerInfo srcPlayerInfo = 7;</code>
       */
      public Builder setSrcPlayerInfo(
          emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo.Builder builderForValue) {
        if (srcPlayerInfoBuilder_ == null) {
          srcPlayerInfo_ = builderForValue.build();
          onChanged();
        } else {
          srcPlayerInfoBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /**
       * <code>.OnlinePlayerInfo srcPlayerInfo = 7;</code>
       */
      public Builder mergeSrcPlayerInfo(emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo value) {
        if (srcPlayerInfoBuilder_ == null) {
          if (srcPlayerInfo_ != null) {
            srcPlayerInfo_ =
              emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo.newBuilder(srcPlayerInfo_).mergeFrom(value).buildPartial();
          } else {
            srcPlayerInfo_ = value;
          }
          onChanged();
        } else {
          srcPlayerInfoBuilder_.mergeFrom(value);
        }

        return this;
      }
      /**
       * <code>.OnlinePlayerInfo srcPlayerInfo = 7;</code>
       */
      public Builder clearSrcPlayerInfo() {
        if (srcPlayerInfoBuilder_ == null) {
          srcPlayerInfo_ = null;
          onChanged();
        } else {
          srcPlayerInfo_ = null;
          srcPlayerInfoBuilder_ = null;
        }

        return this;
      }
      /**
       * <code>.OnlinePlayerInfo srcPlayerInfo = 7;</code>
       */
      public emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo.Builder getSrcPlayerInfoBuilder() {
        
        onChanged();
        return getSrcPlayerInfoFieldBuilder().getBuilder();
      }
      /**
       * <code>.OnlinePlayerInfo srcPlayerInfo = 7;</code>
       */
      public emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfoOrBuilder getSrcPlayerInfoOrBuilder() {
        if (srcPlayerInfoBuilder_ != null) {
          return srcPlayerInfoBuilder_.getMessageOrBuilder();
        } else {
          return srcPlayerInfo_ == null ?
              emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo.getDefaultInstance() : srcPlayerInfo_;
        }
      }
      /**
       * <code>.OnlinePlayerInfo srcPlayerInfo = 7;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo, emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo.Builder, emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfoOrBuilder> 
          getSrcPlayerInfoFieldBuilder() {
        if (srcPlayerInfoBuilder_ == null) {
          srcPlayerInfoBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo, emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo.Builder, emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfoOrBuilder>(
                  getSrcPlayerInfo(),
                  getParentForChildren(),
                  isClean());
          srcPlayerInfo_ = null;
        }
        return srcPlayerInfoBuilder_;
      }

      private int srcAppId_ ;
      /**
       * <code>uint32 srcAppId = 13;</code>
       * @return The srcAppId.
       */
      @java.lang.Override
      public int getSrcAppId() {
        return srcAppId_;
      }
      /**
       * <code>uint32 srcAppId = 13;</code>
       * @param value The srcAppId to set.
       * @return This builder for chaining.
       */
      public Builder setSrcAppId(int value) {
        
        srcAppId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint32 srcAppId = 13;</code>
       * @return This builder for chaining.
       */
      public Builder clearSrcAppId() {
        
        srcAppId_ = 0;
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


      // @@protoc_insertion_point(builder_scope:PlayerApplyEnterHomeNotify)
    }

    // @@protoc_insertion_point(class_scope:PlayerApplyEnterHomeNotify)
    private static final emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify();
    }

    public static emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<PlayerApplyEnterHomeNotify>
        PARSER = new com.google.protobuf.AbstractParser<PlayerApplyEnterHomeNotify>() {
      @java.lang.Override
      public PlayerApplyEnterHomeNotify parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new PlayerApplyEnterHomeNotify(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<PlayerApplyEnterHomeNotify> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<PlayerApplyEnterHomeNotify> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.PlayerApplyEnterHomeNotifyOuterClass.PlayerApplyEnterHomeNotify getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_PlayerApplyEnterHomeNotify_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_PlayerApplyEnterHomeNotify_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n PlayerApplyEnterHomeNotify.proto\032\026Onli" +
      "nePlayerInfo.proto\"X\n\032PlayerApplyEnterHo" +
      "meNotify\022(\n\rsrcPlayerInfo\030\007 \001(\0132\021.Online" +
      "PlayerInfo\022\020\n\010srcAppId\030\r \001(\rB\033\n\031emu.gras" +
      "scutter.net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.getDescriptor(),
        });
    internal_static_PlayerApplyEnterHomeNotify_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_PlayerApplyEnterHomeNotify_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_PlayerApplyEnterHomeNotify_descriptor,
        new java.lang.String[] { "SrcPlayerInfo", "SrcAppId", });
    emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
